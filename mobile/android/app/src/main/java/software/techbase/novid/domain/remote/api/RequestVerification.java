package software.techbase.novid.domain.remote.api;

import java.io.Serializable;

import retrofit2.Call;
import software.techbase.novid.domain.remote.domain.RestInvoker;
import software.techbase.novid.domain.remote.domain.XApplicationAPIServiceContext;
import software.techbase.novid.domain.remote.service.XApplicationAPIService;

/**
 * Created by Wai Yan on 3/31/20.
 */
public class RequestVerification extends RestInvoker<XApplicationAPIService, RequestVerification.Request, Void> {

    public RequestVerification() {
        super(XApplicationAPIServiceContext.getAPIService());
    }

    @Override
    protected Call<Void> call(Request request) {
        return this.getService().requestVerification(request);
    }

    public static class Request implements Serializable {

        public String mobile;
    }
}
