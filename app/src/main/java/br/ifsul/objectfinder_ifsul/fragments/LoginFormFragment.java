package br.ifsul.objectfinder_ifsul.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import java.io.IOException;
import java.util.Optional;
import br.ifsul.objectfinder_ifsul.ObjectFinderAPI;
import br.ifsul.objectfinder_ifsul.R;
import br.ifsul.objectfinder_ifsul.databinding.FragmentLoginFormBinding;
import br.ifsul.objectfinder_ifsul.dto.ErrorDTO;
import br.ifsul.objectfinder_ifsul.factories.SharedPreferencesFactory;
import br.ifsul.objectfinder_ifsul.mappers.ErrorDTOMapper;
import br.ifsul.objectfinder_ifsul.strategies.Validation;
import br.ifsul.objectfinder_ifsul.strategies.implementations.ValidationEmail;
import br.ifsul.objectfinder_ifsul.strategies.implementations.ValidationPassword;
import br.ifsul.objectfinder_ifsul.dto.LoginDTO;
import br.ifsul.objectfinder_ifsul.services.UserService;
import br.ifsul.objectfinder_ifsul.utils.TextViewUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFormFragment extends Fragment {
    private FragmentLoginFormBinding binding;
    private NavController  navController;

    private SharedPreferences sharedPreferences;

    private UserService userService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSharedPreferences();
        initNavController();
        initService();
        initListeners();
    }

    private void initSharedPreferences() {
        sharedPreferences = SharedPreferencesFactory
                .getSharedPreferencesWithPrivateMode(requireContext(), "shared");
    }

    private void initNavController() {
        navController = findNavController(requireParentFragment());
    }

    private void initListeners() {
        binding.btnLogin.setOnClickListener(view -> {
            if (isValidFields()) {
                String email = TextViewUtils.convertToString(binding.inputEmail);
                String password = TextViewUtils.convertToString(binding.inputPassword);

                LoginDTO loginDTO = new LoginDTO.LoginBuilder()
                        .email(email)
                        .password(password)
                        .getLoginDTO();

                navController.navigate(R.id.action_loginFragment_to_homeFragment);

                //login(loginDTO);

            }
            else {
                AlertDialog alertDialog = createInvalidCredentialsDialog();
                alertDialog.show();
            }
        });
    }

    private AlertDialog createInvalidCredentialsDialog() {
        return new AlertDialog.Builder(requireContext())
                .setTitle("E-mail ou senha inválidos")
                .setPositiveButton("Ok", (dialog, item) -> dialog.dismiss())
                .create();
    }

    private void initService() {
        userService = ObjectFinderAPI.getUserService();
    }

    private void login(LoginDTO loginDTO) {
        userService.login(loginDTO).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Long> call, @NonNull Response<Long> response) {
                if (response.isSuccessful()) {
                    Optional<Long> idOptional = Optional.ofNullable(response.body());

                    idOptional.ifPresent(id -> requireActivity().runOnUiThread(() -> {
                        navController.navigate(R.id.action_loginFragment_to_homeFragment);
                        sharedPreferences.edit().putLong("id", id).apply();
                    }));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Long> call, @NonNull Throwable t) {
                try {
                    ErrorDTO errorDTO = ErrorDTOMapper.toDTO(call.execute());
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
                    alertDialogBuilder.setTitle("Erro ao efetuar o login");
                    alertDialogBuilder.setMessage(errorDTO.getMessage());

                    alertDialogBuilder.create().show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private boolean isValidFields() {
        String email = TextViewUtils.convertToString(binding.inputEmail);
        String password = TextViewUtils.convertToString(binding.inputPassword);

        boolean isValidEmail = Validation.validate(email, new ValidationEmail());
        boolean isValidPassword = Validation.validate(password, new ValidationPassword());

        return isValidEmail && isValidPassword;

    }

}