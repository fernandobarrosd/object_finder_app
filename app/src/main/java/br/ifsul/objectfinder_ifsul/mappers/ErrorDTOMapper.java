package br.ifsul.objectfinder_ifsul.mappers;

import com.google.gson.Gson;

import java.io.IOException;

import br.ifsul.objectfinder_ifsul.dto.ErrorDTO;
import retrofit2.Response;

public interface ErrorDTOMapper {
    static ErrorDTO toDTO(Response<?> response) throws IOException {
        assert response.errorBody() != null;
        String responseString = response.errorBody().string();
        Gson gson = new Gson();
        return gson.fromJson(responseString, ErrorDTO.class);
    }
}