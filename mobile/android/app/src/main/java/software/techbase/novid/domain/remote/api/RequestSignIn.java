package software.techbase.novid.domain.remote.api;

import java.io.Serializable;

import retrofit2.Call;
import software.techbase.novid.domain.remote.domain.RestInvoker;
import software.techbase.novid.domain.remote.domain.XApplicationAPIServiceContext;
import software.techbase.novid.domain.remote.service.XApplicationAPIService;

/**
 * Created by Wai Yan on 3/31/20.
 */
public class RequestSignIn extends RestInvoker<XApplicationAPIService, RequestSignIn.Request, Void> {

    public RequestSignIn() {
        super(XApplicationAPIServiceContext.getAPIService());
    }

    @Override
    protected Call<Void> call(Request request) {
        return getService().requestSignIn(request);
    }

    public static class Request implements Serializable {

        public String mobile;
    }
}
