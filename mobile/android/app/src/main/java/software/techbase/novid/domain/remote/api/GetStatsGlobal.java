package software.techbase.novid.domain.remote.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import retrofit2.Call;
import software.techbase.novid.domain.remote.domain.RestInvoker;
import software.techbase.novid.domain.remote.domain.StatsAPIServiceContext;
import software.techbase.novid.domain.remote.service.StatsAPIService;

/**
 * Created by Wai Yan on 3/30/20.
 */
public class GetStatsGlobal extends RestInvoker<StatsAPIService, Void, GetStatsGlobal.Response> {

    public GetStatsGlobal() {
        super(StatsAPIServiceContext.getAPIService());
    }

    @Override
    protected Call<Response> call(Void aVoid) {
        return this.getService().getStatsGlobal();
    }

    public static class Response implements Serializable {

        @SerializedName("cases")
        public int cases;

        @SerializedName("deaths")
        public int deaths;

        @SerializedName("recovered")
        public int recovered;
    }
}
