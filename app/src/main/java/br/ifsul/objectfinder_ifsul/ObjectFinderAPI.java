package br.ifsul.objectfinder_ifsul;

import br.ifsul.objectfinder_ifsul.dto.LoginDTO;
import br.ifsul.objectfinder_ifsul.services.CategoryService;
import br.ifsul.objectfinder_ifsul.services.UserService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public abstract class ObjectFinderAPI {
    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl("http://192.168.12.13:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static CategoryService getCategoryService() {
        return RETROFIT.create(CategoryService.class);
    }

    public static UserService getUserService() {
        return RETROFIT.create(UserService.class);
    }
}