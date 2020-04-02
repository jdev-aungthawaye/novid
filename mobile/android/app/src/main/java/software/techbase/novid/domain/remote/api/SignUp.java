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
public class SignUp extends RestInvoker<XApplicationAPIService, SignUp.Request, SignUp.Response> {

    public SignUp() {
        super(XApplicationAPIServiceContext.getAPIService());
    }

    @Override
    protected Call<SignUp.Response> call(Request request) {
        return getService().signUp(request);
    }

    public static class Request implements Serializable {

        @SerializedName("name")
        public String name;

        @SerializedName("nric")
        public String nric;

        @SerializedName("mobile")
        public String mobile;

        @SerializedName("code")
        public String code;
    }

    public static class Response implements Serializable {

        @SerializedName("token")
        public String token;

        @SerializedName("userId")
        public long userId;
    }
}
