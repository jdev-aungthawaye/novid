package jdev.novid.integration.client.smspoh;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SmsPohService {

    @POST("send")
    public Call<SendSms.Response> sendSms(@Body SendSms.Request request);

}
