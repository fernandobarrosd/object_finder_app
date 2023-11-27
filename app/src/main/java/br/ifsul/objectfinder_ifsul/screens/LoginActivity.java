package br.ifsul.objectfinder_ifsul.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Optional;

import br.ifsul.objectfinder_ifsul.ObjectFinderAPI;
import br.ifsul.objectfinder_ifsul.databinding.ActivityLoginBinding;
import br.ifsul.objectfinder_ifsul.design_patterns.factories.ToastFactory;
import br.ifsul.objectfinder_ifsul.design_patterns.strategy.Validation;
import br.ifsul.objectfinder_ifsul.design_patterns.strategy.implementations.ValidationEmail;
import br.ifsul.objectfinder_ifsul.design_patterns.strategy.implementations.ValidationPassword;
import br.ifsul.objectfinder_ifsul.dto.LoginDTO;
import br.ifsul.objectfinder_ifsul.services.UserService;
import br.ifsul.objectfinder_ifsul.utils.IntentUtils;
import br.ifsul.objectfinder_ifsul.design_patterns.factories.SharedPreferencesFactory;
import br.ifsul.objectfinder_ifsul.utils.TextViewUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private SharedPreferences sharedPreferences;

    private UserService userService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = SharedPreferencesFactory.
                getSharedPreferencesWithPrivateMode(this, "shared");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initService() {
        userService = ObjectFinderAPI.getUserService();
    }



    @Override
    protected void onResume() {
        super.onResume();
        initService();
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

    private LoginDTO createLoginDTO(String email, String password) {
        return new LoginDTO.LoginBuilder()
                .email(email)
                .password(password)
                .getLoginDTO();
    }

    private void login() {
        String email = TextViewUtils.convertToString(binding.inputEmail);
        String password = TextViewUtils.convertToString(binding.inputPassword);

        LoginDTO loginDTO = createLoginDTO(email, password);

        userService.login(loginDTO).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Long> call, @NonNull Response<Long> response) {
                if (response.isSuccessful()) {
                    Optional<Long> idOptional = Optional.ofNullable(response.body());

                    if (idOptional.isPresent()) {
                        Long id = idOptional.get();

                        runOnUiThread(() -> {
                            sharedPreferences.edit()
                                    .putLong("id", id)
                                    .apply();

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Long> call, @NonNull Throwable t) {
                System.out.println(t.getMessage());
            }
        });



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
