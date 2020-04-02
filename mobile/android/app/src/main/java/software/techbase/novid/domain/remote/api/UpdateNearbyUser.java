package software.techbase.novid.domain.remote.api;

import java.io.Serializable;

import retrofit2.Call;
import software.techbase.novid.domain.remote.domain.RestInvoker;
import software.techbase.novid.domain.remote.domain.XApplicationAPIServiceContext;
import software.techbase.novid.domain.remote.service.XApplicationAPIService;

/**
 * Created by Wai Yan on 3/31/20.
 */
public class UpdateNearbyUser extends RestInvoker<XApplicationAPIService, UpdateNearbyUser.Request, Void> {

    public UpdateNearbyUser() {
        super(XApplicationAPIServiceContext.getAPIService());
    }

    @Override
    protected Call<Void> call(Request request) {
        return this.getService().updateNearbyUser(request);
    }

    public static class Request implements Serializable {

        public long nearByUserId;
        public long collectedAt;
    }
}

