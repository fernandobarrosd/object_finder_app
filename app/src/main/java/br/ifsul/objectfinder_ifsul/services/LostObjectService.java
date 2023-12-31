package br.ifsul.objectfinder_ifsul.services;

import java.util.List;

import br.ifsul.objectfinder_ifsul.dto.CreateLostObjectDTO;
import br.ifsul.objectfinder_ifsul.dto.LostObjectDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LostObjectService {
    @GET("objects/{id}")
    Call<LostObjectDTO> findById(@Path("id") Long id);

    @GET("objects/name/{name}")
    Call<List<LostObjectDTO>> findByName(@Path("name") String name);

    @POST
    Call<Void> save(@Body CreateLostObjectDTO createLostObjectDTO);
}