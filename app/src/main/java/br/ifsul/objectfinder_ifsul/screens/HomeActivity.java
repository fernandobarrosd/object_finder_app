package br.ifsul.objectfinder_ifsul.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import br.ifsul.objectfinder_ifsul.ObjectFinderAPI;
import br.ifsul.objectfinder_ifsul.R;
import br.ifsul.objectfinder_ifsul.adapter.LostObjectAdapter;
import br.ifsul.objectfinder_ifsul.databinding.ActivityHomeBinding;
import br.ifsul.objectfinder_ifsul.design_patterns.factories.SharedPreferencesFactory;
import br.ifsul.objectfinder_ifsul.dto.LostObjectDTO;
import br.ifsul.objectfinder_ifsul.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private final List<LostObjectDTO> lostObjects = new ArrayList<>();

    private UserService userService;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        sharedPreferences = SharedPreferencesFactory.getSharedPreferencesWithPrivateMode(this, "shared");
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        initToolbar();
        initService();
    }

    private void initRecyclerView() {
        binding.lostObjectsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.lostObjectsRecyclerView.setAdapter(new LostObjectAdapter(lostObjects));
        binding.lostObjectsRecyclerView.setHasFixedSize(true);
        binding.lostObjectsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
    }


    @Override
    protected void onResume() {
        super.onResume();
        initService();
        long userID = sharedPreferences.getLong("id", 0);

        if (userID != 0) {
            userService.getAll0bjectsByUserID(userID).enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<List<LostObjectDTO>> call, @NonNull Response<List<LostObjectDTO>> response) {
                    if (response.isSuccessful()) {
                        List<LostObjectDTO> lostObjectsDtos = response.body();
                        assert lostObjectsDtos != null;
                        lostObjects.addAll(lostObjectsDtos);

                        runOnUiThread(HomeActivity.this::initRecyclerView);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<LostObjectDTO>> call, @NonNull Throwable t) {

                }
            });
        }









        /*binding.searchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterNames(newText);
                return false;
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemID = item.getItemId();

        if (menuItemID == R.id.logout) {
            sharedPreferences.edit().remove("id").apply();
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initService() {
        userService = ObjectFinderAPI.getUserService();
    }

    /*private void filterNames(String queryName) {
        List<String> filteredNames = new ArrayList<>();
        if (!queryName.isEmpty()) {
            names.forEach(name -> {
                if (name.toLowerCase().contains(queryName.toLowerCase())) {
                    filteredNames.add(name);
                }
            });
            binding.lostObjectsRecyclerView.setAdapter(new LostObjectAdapter(filteredNames));
        }
        else {
            binding.lostObjectsRecyclerView.setAdapter(new LostObjectAdapter(names));
        }
    }*/
}