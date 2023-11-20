package br.ifsul.objectfinder_ifsul.screens;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import br.ifsul.objectfinder_ifsul.ObjectFinderAPI;
import br.ifsul.objectfinder_ifsul.databinding.ActivityLoginBinding;
import br.ifsul.objectfinder_ifsul.dto.UsuarioDTO;
import br.ifsul.objectfinder_ifsul.design_patterns.factories.ToastFactory;
import br.ifsul.objectfinder_ifsul.design_patterns.strategy.Validation;
import br.ifsul.objectfinder_ifsul.design_patterns.strategy.implementations.ValidationEmail;
import br.ifsul.objectfinder_ifsul.design_patterns.strategy.implementations.ValidationPassword;
import br.ifsul.objectfinder_ifsul.utils.IntentUtils;
import br.ifsul.objectfinder_ifsul.design_patterns.factories.SharedPreferencesFactory;
import br.ifsul.objectfinder_ifsul.utils.TextViewUtils;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = SharedPreferencesFactory.
                getSharedPreferencesWithPrivateMode(this, "shared");
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.btnLogin.setOnClickListener(this::executeLoginButton);
    }

    private void executeLoginButton(View view) {
        if (isValidFields()) {
            login();
        }
        else {
            showToast("The fields are invalids");
        }
    }

    private void login() {
        String email = TextViewUtils.convertToString(binding.inputEmail);
        String password = TextViewUtils.convertToString(binding.inputPassword);

        IntentUtils.createIntentAndStart(this, AuthenticatedActivity.class);

    }


    private void showToast(String text) {
        ToastFactory.withLengthLong(this, text).show();
    }

    private boolean isValidFields() {
        String email = TextViewUtils.convertToString(binding.inputEmail);
        String password = TextViewUtils.convertToString(binding.inputPassword);

        boolean isValidEmail = Validation.validate(email, new ValidationEmail());
        boolean isValidPassword = Validation.validate(password, new ValidationPassword());

        return isValidEmail && isValidPassword;

    }


}
