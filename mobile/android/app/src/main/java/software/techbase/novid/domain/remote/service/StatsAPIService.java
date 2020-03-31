package software.techbase.novid.domain.remote.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import software.techbase.novid.domain.remote.api.GetStatsByCountry;
import software.techbase.novid.domain.remote.api.GetStatsGlobal;

/**
 * Created by Wai Yan on 3/30/20.
 */
public interface StatsAPIService {

    @GET(value = "/countries/{country}")
    Call<GetStatsByCountry.Response> getStatsByCountry(@Path("country") String country);

    @GET(value = "/all")
    Call<GetStatsGlobal.Response> getStatsGlobal();
}
