package software.techbase.novid.domain.remote.api;

import java.io.Serializable;

import retrofit2.Call;
import software.techbase.novid.domain.remote.domain.RestInvoker;
import software.techbase.novid.domain.remote.domain.XApplicationAPIServiceContext;
import software.techbase.novid.domain.remote.service.XApplicationAPIService;

/**
 * Created by Wai Yan on 3/31/20.
 */
public class UpdateNearbyDevice extends RestInvoker<XApplicationAPIService, UpdateNearbyDevice.Request, Void> {

    public UpdateNearbyDevice() {
        super(XApplicationAPIServiceContext.getAPIService());
    }

    @Override
    protected Call<Void> call(Request request) {
        return null;
    }

    public static class Request implements Serializable {

        public String self; //MY_MAC_ADDRESS
        public String nearBy; //THEIR_MAC_ADDRESS
        public String deviceName; //THEIR DEVICE
        public long lat;
        public long lng;
        public long collectedAt;
    }
}

