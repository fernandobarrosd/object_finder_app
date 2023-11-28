package br.ifsul.objectfinder_ifsul.services;

import java.util.List;

import br.ifsul.objectfinder_ifsul.dto.CategoryNameDTO;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {
    @GET("categories")
    Call<List<CategoryNameDTO>> getCategoryNames();
}