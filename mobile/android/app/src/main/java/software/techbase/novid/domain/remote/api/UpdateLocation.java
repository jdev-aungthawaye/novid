package software.techbase.novid.domain.remote.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import retrofit2.Call;
import software.techbase.novid.domain.remote.domain.RestInvoker;
import software.techbase.novid.domain.remote.domain.XApplicationAPIServiceContext;
import software.techbase.novid.domain.remote.service.XApplicationAPIService;

/**
 * Created by Wai Yan on 3/31/20.
 */
public class UpdateLocation extends RestInvoker<XApplicationAPIService, UpdateLocation.Request, Void> {

    public UpdateLocation() {
        super(XApplicationAPIServiceContext.getAPIService());
    }

    @Override
    protected Call<Void> call(Request request) {
        return this.getService().updateLocation(request);
    }

    public static class Request implements Serializable {

        @SerializedName("lat")
        public double lat;

        @SerializedName("lng")
        public double lng;

        @SerializedName("collectedAt")
        public long collectedAt;
    }
}

