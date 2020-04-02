package software.techbase.novid.domain.remote.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import software.techbase.novid.domain.remote.api.DoSignIn;
import software.techbase.novid.domain.remote.api.RequestSignIn;
import software.techbase.novid.domain.remote.api.RequestVerification;
import software.techbase.novid.domain.remote.api.SignUp;
import software.techbase.novid.domain.remote.api.UpdateLocation;
import software.techbase.novid.domain.remote.api.UpdateNearbyUser;
import software.techbase.novid.domain.remote.constant.APIEndPoint;

/**
 * Created by Wai Yan on 2019-07-25.
 */
public interface XApplicationAPIService {

    @POST(value = APIEndPoint.PUBLIC_CONTEXT + "/request-verification")
    Call<Void> requestVerification(@Body RequestVerification.Request request);

    @POST(value = APIEndPoint.PUBLIC_CONTEXT + "/sign-up")
    Call<SignUp.Response> signUp(@Body SignUp.Request request);

    @POST(value = APIEndPoint.PUBLIC_CONTEXT + "/request-sign-in")
    Call<Void> requestSignIn(@Body RequestSignIn.Request request);

    @POST(value = APIEndPoint.PUBLIC_CONTEXT + "/do-sign-in")
    Call<DoSignIn.Response> doSignIn(@Body DoSignIn.Request request);

    @POST(value = APIEndPoint.PRIVATE_CONTEXT + "/update-location")
    Call<Void> updateLocation(@Body UpdateLocation.Request request);

    @POST(value = APIEndPoint.PRIVATE_CONTEXT + "/update-near-by-user")
    Call<Void> updateNearbyUser(@Body UpdateNearbyUser.Request request);
}
