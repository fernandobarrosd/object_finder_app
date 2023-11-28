package br.ifsul.objectfinder_ifsul.screens;

import static android.app.DatePickerDialog.OnDateSetListener;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import br.ifsul.objectfinder_ifsul.ObjectFinderAPI;
import br.ifsul.objectfinder_ifsul.R;
import br.ifsul.objectfinder_ifsul.classes.Date;
import br.ifsul.objectfinder_ifsul.databinding.ActivityFirstStepCreateLostObjectBinding;
import br.ifsul.objectfinder_ifsul.design_patterns.factories.ArrayAdapterFactory;
import br.ifsul.objectfinder_ifsul.design_patterns.factories.DateFactory;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstStepCreateLostObjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        initService();
        Uri uri = getIntent().getParcelableExtra("photo_uri", Uri.class);
        String lostObjectName = getIntent().getStringExtra("lostObjectName");
        String lostObjectDescription = getIntent().getStringExtra("lostObjectDescription");
        String lostObjectDate = getIntent().getStringExtra("lostObjectDate");

        binding.lostObjectPhoto.setImageURI(uri);
        binding.lostObjectName.setText(lostObjectName);
        binding.lostObjectDescription.setText(lostObjectDescription);

        if (lostObjectDate != null) {
            binding.lostObjectDate.setText(lostObjectDate);
        }
        else {
            binding.lostObjectDate.setText(getString(R.string.lost_object_date_hint));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initEvents();
        initCategoriesList();

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
        binding.lostObjectDate.setOnClickListener(this::showDatePicker);
        binding.btnNextStep.setOnClickListener(this::goToSecondStep);
    }

    private void showDatePicker(View view) {
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    private void goToSecondStep(View view) {
        Uri photoUri = getIntent().getParcelableExtra("photo_uri", Uri.class);
        String lostObjectName = TextViewUtils.convertToString(binding.lostObjectName);
        String lostObjectDescription = TextViewUtils.convertToString(binding.lostObjectDescription);
        String lostObjectDate = TextViewUtils.convertToString(binding.lostObjectDate);

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
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            return new DatePickerDialog(requireActivity(), this, day, month, year);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int day, int month, int year) {
            System.out.println(day + " " + month + " " + year);
        }
    }
}
