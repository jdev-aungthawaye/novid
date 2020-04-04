package software.techbase.novid.domain.remote.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import software.techbase.novid.domain.remote.api.GetContacts;

/**
 * Created by Wai Yan on 3/30/20.
 */
public interface DataAPIService {

    @GET(value = "/es-aungthawaye/novid/master/data/{fileName}")
    Call<List<GetContacts.Response>> getContacts(@Path("fileName") String fileName);
}
