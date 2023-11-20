package br.ifsul.objectfinder_ifsul;

import java.io.IOException;

import br.ifsul.objectfinder_ifsul.dto.LostObjectDTO;
import br.ifsul.objectfinder_ifsul.dto.UsuarioDTO;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;

public abstract class ObjectFinderAPI {
    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .build();

    private static final ObjectFinderService OBJECT_FINDER_SERVICE = RETROFIT.create(ObjectFinderService.class);

    private interface ObjectFinderService {
        Call<Integer> findUserByEmailAndPassword(@Body UsuarioDTO usuarioDTO);
        Call<Void> saveLostObject(@Body LostObjectDTO lostObjectDTO);

    }

    public static Integer findUserByEmailAndPassword(UsuarioDTO usuarioDTO) throws IOException {
        Call<Integer> idCall = OBJECT_FINDER_SERVICE.findUserByEmailAndPassword(usuarioDTO);
        Response<Integer> idResponse = idCall.execute();
        return idResponse.body();
    }
}
