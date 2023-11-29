package br.ifsul.objectfinder_ifsul.screens;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import androidx.activity.result.ActivityResultLauncher;
import static androidx.activity.result.contract.ActivityResultContracts.GetContent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import br.ifsul.objectfinder_ifsul.ObjectFinderAPI;
import br.ifsul.objectfinder_ifsul.R;
import br.ifsul.objectfinder_ifsul.databinding.ActivityFirstStepCreateLostObjectBinding;
import br.ifsul.objectfinder_ifsul.dto.CategoryNameDTO;
import br.ifsul.objectfinder_ifsul.services.CategoryService;
import br.ifsul.objectfinder_ifsul.utils.IntentUtils;
import br.ifsul.objectfinder_ifsul.utils.NumberUtils;
import br.ifsul.objectfinder_ifsul.utils.TextViewUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstStepCreateLostObjectActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    private static ActivityFirstStepCreateLostObjectBinding binding;

    private List<String> categories;

    private CategoryService categoryService;

    private DatePickerFragment datePickerFragment;

    private LostObjectTakePictureDialog lostObjectTakePictureDialog;

    private static ActivityResultLauncher<String>  cameraLauncher;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstStepCreateLostObjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        //initService();
        Uri uri = getIntent().getParcelableExtra("photo_uri", Uri.class);
        String lostObjectName = getIntent().getStringExtra("lostObjectName");
        String lostObjectDescription = getIntent().getStringExtra("lostObjectDescription");
        String lostObjectDate = getIntent().getStringExtra("lostObjectDate");

        binding.lostObjectPhotoImageView.setImageURI(uri);
        binding.lostObjectNameEditText.setText(lostObjectName);
        binding.lostObjectDescriptionEditText.setText(lostObjectDescription);

        if (lostObjectDate != null) {
            binding.lostObjectDateTextView.setText(lostObjectDate);
        }
        else {
            binding.lostObjectDateTextView.setText(getString(R.string.lost_object_date_hint));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initActivityResultLaunchers();
        initDialogs();
        initEvents();
        //initCategoriesList();

    }

    private void initDialogs()  {
        datePickerFragment = new DatePickerFragment();
        lostObjectTakePictureDialog = new LostObjectTakePictureDialog();
    }

    private void initActivityResultLaunchers() {
        cameraLauncher = registerForActivityResult(new GetContent(), uri -> {

        });
    }

    private void initCategoriesList() {
        categoryService.getCategoryNames().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<CategoryNameDTO>> call, @NonNull Response<List<CategoryNameDTO>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;

                    categories = response.body()
                            .stream()
                            .map(CategoryNameDTO::getName)
                            .collect(Collectors.toList());

                    runOnUiThread(() -> {
                        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                categories);
                        binding.categoriesSpinner.setAdapter(categoriesAdapter);
                    });

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CategoryNameDTO>> call, @NonNull Throwable t) {

            }
        });
    }

    private void initService() {
        categoryService = ObjectFinderAPI.getCategoryService();
    }

    private void initEvents() {
        binding.lostObjectDateTextView.setOnClickListener(this::showDatePicker);
        binding.btnNextStep.setOnClickListener(this::goToSecondStep);
        binding.lostObjectPhotoImageView.setOnClickListener(view -> {
            lostObjectTakePictureDialog.show(getSupportFragmentManager(), "picture_dialog");
        });
    }

    private void showDatePicker(View view) {
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void goToSecondStep(View view) {
        Uri photoUri = getIntent().getParcelableExtra("photo_uri", Uri.class);
        String lostObjectName = TextViewUtils.convertToString(binding.lostObjectNameEditText);
        String lostObjectDescription = TextViewUtils.convertToString(binding.lostObjectDescriptionEditText);
        String lostObjectDate = TextViewUtils.convertToString(binding.lostObjectDateTextView);

        IntentUtils.createIntentAndStart(this,
                SecondStepCreateLostObjectActivity.class, intent -> {
                    intent.putExtra("lostObjectName", lostObjectName);
                    intent.putExtra("lostObjectDescription", lostObjectDescription);
                    intent.putExtra("lostObjectDate", lostObjectDate);
                    intent.putExtra("photo_uri", photoUri);
        });
        finish();
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            return new DatePickerDialog(requireActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            String dayString = NumberUtils.formatToTwoNumbers(day);
            String monthString = NumberUtils.formatToTwoNumbers(month + 1);
            String yearString = NumberUtils.formatToTwoNumbers(year);
            String date = String.format("%s/%s/%s", dayString, monthString, yearString);

            binding.lostObjectDateTextView.setText(date);


        }
    }

    public static class LostObjectTakePictureDialog  extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireActivity());
            alertDialogBuilder.setTitle("Escolha uma opção");
            alertDialogBuilder.setItems(new String[]{"Abrir câmera", "Abrir galeria"}, (dialog, which) -> {
                if (which == 0) {
                    cameraLauncher.launch("image/*");
                }

            });

            return alertDialogBuilder.create();
        }


    }
}
