package br.ifsul.objectfinder_ifsul.screens;

import static androidx.core.splashscreen.SplashScreen.installSplashScreen;
import static android.Manifest.permission;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import br.ifsul.objectfinder_ifsul.databinding.ActivityAuthenticatedBinding;
import br.ifsul.objectfinder_ifsul.design_patterns.factories.*;
import br.ifsul.objectfinder_ifsul.utils.IntentUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AuthenticatedActivity extends AppCompatActivity {
    private ActivityAuthenticatedBinding binding;
    private static final String[] PERMISSIONS = new String[]{permission.CAMERA, permission.READ_EXTERNAL_STORAGE};
    private static final String[] IMAGE_OPTIONS = new String[]{"Abrir a câmera", "Abrir a galeria"};

    private final ActivityResultLauncher<Intent> cameraLauncher = ActivityResultLauncherFactory.
            createCameraLauncher(this, this::goToCreateLostObject);

    private final ActivityResultLauncher<String> galeryLauncher = ActivityResultLauncherFactory
            .createGalleryLauncher(this, this::goToCreateLostObject);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthenticatedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        installSplashScreen(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        initNavigation();
        requestPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.btnOpenCamera.setOnClickListener(v -> {
            AlertDialog.Builder imageDialogBuiler = new AlertDialog.Builder(this);

            imageDialogBuiler.setTitle("Qual opção deseja?");
            imageDialogBuiler.setItems(IMAGE_OPTIONS, (dialog, which) -> {
                if (which == 0) {
                    if (hasPermission()) {
                        openCamera();
                    }
                    else {
                        AlertDialog.Builder builder = AlertDialogBuilderFactory
                                .withMessageAndText(this,
                                "Ative a permissão de usar a câmera antes!",
                                "Ação negada!");

                        builder.setPositiveButton("Abrir configurações", (dialog2, which2) -> {
                            dialog2.cancel();
                            openAppSettings();
                        });
                        builder.setNegativeButton("Rejeitar", (dialog2, which2) -> dialog2.cancel());
                        builder.create().show();
                    }
                }
                else {
                    openGallery();
                }
                dialog.cancel();
            });
            imageDialogBuiler.create().show();
        });
    }



    private void openAppSettings() {
        Intent intent = IntentFactory.createAppSettingsIntent(this);
        startActivity(intent);
    }

    private void initNavigation() {
       NavController navController = Navigation.findNavController(binding.autenticatedNavHost);
       BottomNavigationView bottomNavigation = binding.autenticatedBottomNavigation;
       NavigationUI.setupWithNavController(bottomNavigation, navController);
    }

    private void goToCreateLostObject(Uri uri) {
        IntentUtils.createIntentAndStart(this, FirstStepCreateLostObjectActivity.class,
                intent -> intent.putExtra("photo_uri", uri));
    }

    private void goToCreateLostObject(Intent intent) {
        IntentUtils.createIntentAndStart(this, FirstStepCreateLostObjectActivity.class,
                intent2 -> intent2.putExtra("photo_uri", intent.getData()));
    }


    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
    }

    private Boolean hasPermission() {
        return ContextCompat.checkSelfPermission(this, permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }


    private void openCamera() {
        Intent cameraIntent = IntentFactory.createCameraIntent();
        cameraLauncher.launch(cameraIntent);
    }

    private void openGallery() {
        galeryLauncher.launch("image/*");
    }
}



