package br.ifsul.objectfinder_ifsul.services;

import java.util.List;

import br.ifsul.objectfinder_ifsul.dto.LoginDTO;
import br.ifsul.objectfinder_ifsul.dto.LostObjectDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("users/login")
    Call<Long> login(@Body LoginDTO loginDTO);

    @GET("users/{id}/objects/")
    Call<List<LostObjectDTO>> getAll0bjectsByUserID(@Path("id") Long id);


}