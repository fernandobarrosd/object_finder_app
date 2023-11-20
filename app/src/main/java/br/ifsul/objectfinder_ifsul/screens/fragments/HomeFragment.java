package br.ifsul.objectfinder_ifsul.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import br.ifsul.objectfinder_ifsul.adapter.LostObjectAdapter;
import br.ifsul.objectfinder_ifsul.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private final ArrayList<String> names = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        names.add("Pendrive");
        names.add("Sapato");
        names.add("Casaco rosa");
        names.add("Estojo");

        binding.lostObjectsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
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