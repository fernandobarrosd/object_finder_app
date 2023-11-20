package br.ifsul.objectfinder_ifsul.screens.fragments;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import br.ifsul.objectfinder_ifsul.design_patterns.factories.AlertDialogBuilderFactory;
import br.ifsul.objectfinder_ifsul.design_patterns.factories.SharedPreferencesFactory;
import br.ifsul.objectfinder_ifsul.screens.LoginActivity;
import br.ifsul.objectfinder_ifsul.databinding.FragmentProfileBinding;
import br.ifsul.objectfinder_ifsul.utils.IntentUtils;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        sharedPreferences = SharedPreferencesFactory
                .getSharedPreferencesWithPrivateMode(requireActivity(), "shared");
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.btnLogout.setOnClickListener(this::executeLogoutButton);
    }

    private void executeLogoutButton(View view) {
        AlertDialog.Builder customDialogBuilder = AlertDialogBuilderFactory
                .withMessageAndText(requireContext(),
                        "Deseja sair?",
                        "Sair do app");

        customDialogBuilder.setPositiveButton("Sim", (dialog, which) -> {
            dialog.dismiss();
            logout();
        });
        customDialogBuilder.setNegativeButton("Não", (dialog, which) -> dialog.cancel());
        customDialogBuilder.create().show();
    }

    private void logout() {
        sharedPreferences.edit().remove("id").apply();
        IntentUtils.createIntentAndStart(requireActivity(), LoginActivity.class);
    }
}
