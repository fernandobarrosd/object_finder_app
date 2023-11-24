package br.ifsul.objectfinder_ifsul.screens;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import br.ifsul.objectfinder_ifsul.R;
import br.ifsul.objectfinder_ifsul.design_patterns.factories.SharedPreferencesFactory;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = SharedPreferencesFactory
                .getSharedPreferencesWithPrivateMode(this, "shared");

    }

    @Override
    protected void onStart() {
        super.onStart();
        int id = sharedPreferences.getInt("id", 0);

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

        /*if (id == 0) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, HomeActivity.class);
        }
        startActivity(intent);
        finish();*/
    }
}