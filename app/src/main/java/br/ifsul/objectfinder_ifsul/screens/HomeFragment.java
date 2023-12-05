package br.ifsul.objectfinder_ifsul.screens;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import java.util.ArrayList;
import java.util.List;
import br.ifsul.objectfinder_ifsul.ObjectFinderAPI;
import br.ifsul.objectfinder_ifsul.R;
import br.ifsul.objectfinder_ifsul.adapter.LostObjectAdapter;
import br.ifsul.objectfinder_ifsul.databinding.FragmentHomeBinding;
import br.ifsul.objectfinder_ifsul.dto.LostObjectDTO;
import br.ifsul.objectfinder_ifsul.services.LostObjectService;
import br.ifsul.objectfinder_ifsul.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private final List<LostObjectDTO> lostObjects = new ArrayList<>();

    private UserService userService;

    private LostObjectService lostObjectService;

    private SharedPreferences sharedPreferences;

    private NavController navController;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNavController();
    }

    @Override
    public void onStart() {
        super.onStart();
        //initToolbar();
        initRecyclerView();
        //initServices();
    }


    private void initRecyclerView() {
        LostObjectDTO lostObjectDTO = new LostObjectDTO();
        lostObjectDTO.setName("test");
        lostObjectDTO.setDescription("test");
        lostObjectDTO.setFoundedDate("test");
        lostObjectDTO.setIsFounded("sim");
        lostObjectDTO.setCategory("test");
        binding.lostObjectsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.lostObjectsRecyclerView.setAdapter(new LostObjectAdapter(List.of(lostObjectDTO, lostObjectDTO)));
        binding.lostObjectsRecyclerView.setHasFixedSize(true);
        binding.lostObjectsRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
    }

    private void initNavController() {
        navController = findNavController(this);
    }

    private void initServices() {
        userService = ObjectFinderAPI.getUserService();
        lostObjectService = ObjectFinderAPI.getLostObjectService();
    }

    private void initToolbar() {
        //setSupportActionBar(binding.toolbar);
    }

    private void initEvents() {
        binding.addObjectFloatingActionButton.setOnClickListener(view -> {
            navController.navigate(R.id.action_homeFragment_to_firstStepCreateLostObjectFragment);
        });
        binding.searchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //filterNames(newText);
                return false;
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        initEvents();
        //long userID = sharedPreferences.getLong("id", 0);

        /*if (userID != 0) {
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
        }*/
    }

    private void logout() {
        sharedPreferences.edit().remove("id").apply();
    }

    private AlertDialog createTryLogoutAlert() {
        AlertDialog.Builder tryLogoutAlertBuilder = new AlertDialog.Builder(requireContext());

        tryLogoutAlertBuilder.setTitle("Tem certeza que deseja sair?");

        tryLogoutAlertBuilder.setItems(new String[]{"Sim", "Não"}, ((dialogInterface, item) -> {
            if (item == 0) logout();
        }));

        return tryLogoutAlertBuilder.create();
    }



    private void filterNames(String queryName) {
        lostObjectService.findByName(queryName).enqueue(new Callback<>(){

            @Override
            public void onResponse(@NonNull Call<List<LostObjectDTO>> call, @NonNull Response<List<LostObjectDTO>> response) {
                if (response.isSuccessful()) {
                    List<LostObjectDTO> lostObjectDTOS = response.body();
                    System.out.println(lostObjectDTOS);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<LostObjectDTO>> call, @NonNull Throwable t) {

            }
        });
    }
}