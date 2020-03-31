package software.techbase.novid.domain.remote.domain;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import software.techbase.novid.cache.sharepreferences.UserInfoStorage;
import software.techbase.novid.component.android.xlogger.XLogger;

public class RetrofitService<T> {

    private T service;

    public RetrofitService(Class<T> serviceClass, final String baseUrl) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(120, TimeUnit.SECONDS);
        httpClient.readTimeout(120, TimeUnit.SECONDS);

        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            Request.Builder requestBuilder;

            if (UserInfoStorage.getInstance().isUserLoggedIn()) {
                requestBuilder = original.newBuilder()
                        .header("X-TOKEN", UserInfoStorage.getInstance().getAccessToken())
                        .header("X-USER-ID", Long.toString(UserInfoStorage.getInstance().getUserId()));
            } else {
                requestBuilder = original.newBuilder();
            }

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = httpClient
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(new NullOrEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        this.service = retrofit.create(serviceClass);
        XLogger.debug(this.getClass(), "RetrofitService - built for : %s " + serviceClass.getName());
    }

    public T getService() {
        return this.service;
    }
}
