package br.ifsul.objectfinder_ifsul.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.List;
import br.ifsul.objectfinder_ifsul.ObjectFinderAPI;
import br.ifsul.objectfinder_ifsul.adapter.LostObjectAdapter;
import br.ifsul.objectfinder_ifsul.databinding.ActivityHomeBinding;
import br.ifsul.objectfinder_ifsul.dto.CategoryNameDTO;
import br.ifsul.objectfinder_ifsul.services.CategoryService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private final ArrayList<String> names = new ArrayList<>();

    private CategoryService categoryService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onResume() {
        super.onResume();
        initServices();

        categoryService.getCategoryNames().enqueue(new Callback<List<CategoryNameDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<CategoryNameDTO>> call, @NonNull Response<List<CategoryNameDTO>> response) {
                if (response.isSuccessful()) {
                    List<CategoryNameDTO> categoriesNameDTO = response.body();
                    System.out.println(categoriesNameDTO);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CategoryNameDTO>> call, @NonNull Throwable t) {
                System.out.println(t.getMessage());
            }
        });


        binding.lostObjectsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.lostObjectsRecyclerView.setAdapter(new LostObjectAdapter(names));
        binding.lostObjectsRecyclerView.setHasFixedSize(true);


        binding.searchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterNames(newText);
                return false;
            }
        });
    }

    private void initServices() {
        categoryService = ObjectFinderAPI.getCategoryService();
    }

    private void filterNames(String queryName) {
        ArrayList<String> filteredNames = new ArrayList<>();
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
    }
}