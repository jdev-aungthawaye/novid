package software.techbase.novid.domain.remote.api;

import java.io.Serializable;

import retrofit2.Call;
import software.techbase.novid.domain.remote.domain.RestInvoker;
import software.techbase.novid.domain.remote.domain.XApplicationAPIServiceContext;
import software.techbase.novid.domain.remote.service.XApplicationAPIService;

/**
 * Created by Wai Yan on 3/31/20.
 */
public class SignUp extends RestInvoker<XApplicationAPIService, SignUp.Request, SignUp.Response> {

    public SignUp() {
        super(XApplicationAPIServiceContext.getAPIService());
    }

    @Override
    protected Call<SignUp.Response> call(Request request) {
        return getService().signUp(request);
    }

    public static class Request implements Serializable {

        public String name;
        public String nric;
        public String mobile;
        public String code;
    }

    public static class Response implements Serializable {

        public String token;
        public long userId;
    }
}
