package br.ifsul.objectfinder_ifsul.screens;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import static androidx.navigation.fragment.FragmentKt.findNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import br.ifsul.objectfinder_ifsul.ObjectFinderAPI;
import br.ifsul.objectfinder_ifsul.R;
import br.ifsul.objectfinder_ifsul.databinding.FragmentCreateLostObjectBinding;
import br.ifsul.objectfinder_ifsul.dto.CategoryNameDTO;
import br.ifsul.objectfinder_ifsul.dto.CreateLostObjectDTO;
import br.ifsul.objectfinder_ifsul.dto.ErrorDTO;
import br.ifsul.objectfinder_ifsul.factories.SharedPreferencesFactory;
import br.ifsul.objectfinder_ifsul.mappers.ErrorDTOMapper;
import br.ifsul.objectfinder_ifsul.services.CategoryService;
import br.ifsul.objectfinder_ifsul.services.LostObjectService;
import br.ifsul.objectfinder_ifsul.utils.NumberUtils;
import br.ifsul.objectfinder_ifsul.utils.TextViewUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateLostObjectFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static FragmentCreateLostObjectBinding binding;

    private List<String> categories;

    private CategoryService categoryService;

    private LostObjectService lostObjectService;

    private DatePickerFragment datePickerFragment;

    private SharedPreferences sharedPreferences;

    private NavController navController;

    private String category;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateLostObjectBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initServices();
        initToolbar();
        initNavController();
        initSharedPreferences();
        initDatePicker();
        initEvents();
    }

    private void initSharedPreferences() {
        sharedPreferences = SharedPreferencesFactory
                .getSharedPreferencesWithPrivateMode(requireContext(), "shared");
    }

    private void initServices() {
        categoryService = ObjectFinderAPI.getCategoryService();
        lostObjectService = ObjectFinderAPI.getLostObjectService();
    }

    private void initToolbar() {
        NavController navController = findNavController(this);
        AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(navController.getGraph())
                .build();
        setupWithNavController(binding.toolbar, navController, appBarConfig);
    }

    private void initNavController() {
        navController = findNavController(this);
    }
    private void initDatePicker() {
        datePickerFragment = new DatePickerFragment();
    }

    private void initCategoriesList() {
        categoryService.getCategoryNames().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<CategoryNameDTO>> call, @NonNull Response<List<CategoryNameDTO>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;

                    categories = response.body().stream()
                            .map(CategoryNameDTO::getName)
                            .collect(Collectors.toList());

                    requireActivity().runOnUiThread(() -> {
                        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(requireContext(),
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

    private void initEvents() {
        binding.categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = categories.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                category = categories.get(0);
            }
        });

        binding.btnCreateLostObject.setOnClickListener(view -> {
            Long userID = sharedPreferences.getLong("id", 0);
            String lostObjectName = TextViewUtils.convertToString(binding.lostObjectNameEditText);
            String lostObjectDescription = TextViewUtils.convertToString(binding.lostObjectDescriptionEditText);
            String lostObjectDate = TextViewUtils.convertToString(binding.lostObjectDateTextView);

            CreateLostObjectDTO lostObjectDTO = new CreateLostObjectDTO();
            lostObjectDTO.setName(lostObjectName);
            lostObjectDTO.setDescription(lostObjectDescription);
            lostObjectDTO.setFoundedDate(lostObjectDate);
            lostObjectDTO.setCategory(category);
            lostObjectDTO.setUserID(userID);

            addLostObjectDTO(lostObjectDTO);



        });
    }

    private void addLostObjectDTO(CreateLostObjectDTO lostObjectDTO) {
        lostObjectService.save(lostObjectDTO).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                navController.navigate(R.id.action_createLostObjectFragment_to_homeFragment);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                try {
                    ErrorDTO errorDTO = ErrorDTOMapper.toDTO(call.execute());
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
                    alertDialogBuilder.setTitle("Erro ao adicionar um objeto perdido");
                    alertDialogBuilder.setMessage(errorDTO.getMessage());

                    alertDialogBuilder.create().show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
}
