package jdev.novid.component.rest;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitService<T> {

    private static final Logger LOG = LogManager.getLogger(RetrofitService.class);

    private Class<T> serviceClass;

    private T service;

    public RetrofitService(Class<T> serviceClass, final String baseUrl, final Map<String, String> headers,
            boolean disableSslVerification) {

        this.serviceClass = serviceClass;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

            @Override
            public void log(String message) {

                LOG.debug("Retrofit : {}", message);

            }

        });

        // set your desired log level
        logging.setLevel(Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.addInterceptor(new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();
                Request.Builder requestBuilder = null;
                requestBuilder = original.newBuilder();

                if (headers != null) {

                    for (String key : headers.keySet()) {

                        requestBuilder.header(key, headers.get(key));

                    }

                }

                Request request = requestBuilder.build();
                return chain.proceed(request);

            }

        });

        httpClientBuilder.callTimeout(Duration.ofSeconds(15));
        httpClientBuilder.readTimeout(Duration.ofSeconds(15));
        httpClientBuilder.addInterceptor(logging);

        if (disableSslVerification) {

            this.disableSslVerification(httpClientBuilder);

        }

        OkHttpClient client = httpClientBuilder.build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(new NullOrEmptyConverterFactory())
                .addConverterFactory(JacksonConverterFactory.create()).client(client).build();

        this.service = retrofit.create(this.serviceClass);

    }

    public T getService() {

        return this.service;

    }

    private void disableSslVerification(OkHttpClient.Builder httpClientBuilder) {

        // Create a trust manager that does not validate certificate chains
        final TrustManager[] trustManager = new TrustManager[] { new X509TrustManager() {

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                    throws CertificateException {

            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                    throws CertificateException {

            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {

                return new java.security.cert.X509Certificate[] {};

            }

        } };

        // Install the all-trusting trust manager
        final SSLContext sslContext;

        try {

            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManager, new java.security.SecureRandom());

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            httpClientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustManager[0]);
            httpClientBuilder.hostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {

                    return true;

                }

            });

        } catch (KeyManagementException | NoSuchAlgorithmException e) {

            throw new RuntimeException(e);

        }

    }

}