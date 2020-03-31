package software.techbase.novid.domain.remote.client;

import androidx.annotation.NonNull;

import io.reactivex.Observable;
import retrofit2.Response;
import software.techbase.novid.domain.remote.api.GetStatsByCountry;
import software.techbase.novid.domain.remote.api.GetStatsGlobal;

/**
 * Created by Wai Yan on 3/30/20.
 */
public class StatsAPIClient {

    public static Observable<Response<GetStatsByCountry.Response>> getStatsByCountry(@NonNull String country) {

        GetStatsByCountry.Request request = new GetStatsByCountry.Request();
        request.country = country;
        return new GetStatsByCountry().invoke(request);
    }

    public static Observable<Response<GetStatsGlobal.Response>> getStatsGlobal() {

        return new GetStatsGlobal().invoke(null);
    }
}
