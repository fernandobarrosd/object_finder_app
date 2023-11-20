package br.ifsul.objectfinder_ifsul.screens;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.ifsul.objectfinder_ifsul.databinding.ActivitySecondStepCreateLostObjectBinding;
import br.ifsul.objectfinder_ifsul.models.LostObject;
import br.ifsul.objectfinder_ifsul.utils.IntentUtils;

public class SecondStepCreateLostObjectActivity extends AppCompatActivity {
    private String lostObjectName, lostObjectDescription, lostObjectDate;
    private Uri photoUri;

    private ActivitySecondStepCreateLostObjectBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondStepCreateLostObjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        lostObjectName = getIntent().getStringExtra("lostObjectName");
        lostObjectDescription = getIntent().getStringExtra("lostObjectDescription");
        lostObjectDate = getIntent().getStringExtra("lostObjectDate");
        photoUri = getIntent().getParcelableExtra("photo_uri", Uri.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.btnBackStep.setOnClickListener(this::backToFirstStep);
        binding.btnCreateLostObject.setOnClickListener(this::executeCreateButton);
    }

    private void executeCreateButton(View view) {
        LostObject lostObject = createLostObject();
    }

    private void backToFirstStep(View view) {
        IntentUtils.createIntentAndStart(this, FirstStepCreateLostObjectActivity.class,
                intent -> {
                    intent.putExtra("lostObjectName", lostObjectName);
                    intent.putExtra("lostObjectDescription", lostObjectDescription);
                    intent.putExtra("lostObjectDate", lostObjectDate);
                    intent.putExtra("photo_uri", photoUri);
        });
    }

    private LostObject createLostObject() {
        return new LostObject.LostObjectBuilder()
                .setName(lostObjectName)
                .setDescription(lostObjectDescription)
                .setCreatedDate(lostObjectDate)
                .setPhotoUri(photoUri)
                .build();
    }
}
