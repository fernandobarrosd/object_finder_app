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
import br.ifsul.objectfinder_ifsul.R;
import br.ifsul.objectfinder_ifsul.classes.Date;
import br.ifsul.objectfinder_ifsul.databinding.ActivityFirstStepCreateLostObjectBinding;
import br.ifsul.objectfinder_ifsul.design_patterns.factories.ArrayAdapterFactory;
import br.ifsul.objectfinder_ifsul.design_patterns.factories.DateFactory;
import br.ifsul.objectfinder_ifsul.utils.IntentUtils;
import br.ifsul.objectfinder_ifsul.utils.NumberUtils;
import br.ifsul.objectfinder_ifsul.utils.TextViewUtils;

public class FirstStepCreateLostObjectActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    private static ActivityFirstStepCreateLostObjectBinding binding;
    private final String[] CATEGORIES = new String[]{"Eletrônicos", "Roupas"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstStepCreateLostObjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        ArrayAdapter<String> lostObjectCategoriesAdapter = ArrayAdapterFactory
                .withValues(this, CATEGORIES, android.R.layout.simple_spinner_dropdown_item);
        binding.lostObjectCategories.setAdapter(lostObjectCategoriesAdapter);
        binding.lostObjectDate.setOnClickListener(this::showDatePicker);
        binding.btnNextStep.setOnClickListener(this::goToSecondStep);
    }

    private void showDatePicker(View view) {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "datePicker");
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

    private static class DatePickerFragment extends DialogFragment implements OnDateSetListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            Date date = DateFactory.fromCalendar(Calendar.getInstance());
            return new DatePickerDialog(requireContext(),this, date.getYear(),
                    date.getMonth(), date.getYear());
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String formatDay = NumberUtils.formatToTwoNumbers(dayOfMonth);
            String formatMonth = NumberUtils.formatToTwoNumbers(monthOfYear + 1);
            String formatDate = String.format("%s/%s/%s", formatDay, formatMonth, year);
            binding.lostObjectDate.setText(formatDate);
        }
    }
}
