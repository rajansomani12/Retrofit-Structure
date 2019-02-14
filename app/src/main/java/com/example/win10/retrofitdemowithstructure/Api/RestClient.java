package com.example.win10.retrofitdemowithstructure.Api;


import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestClient {

    //	public static final String BASE_URL = "http://www.mobileapplicationservices.com/fleet/webservice/";  // live
    public static final String BASE_URL = "https://reqres.in/"; // local
    private static final int TIME = /*240*/60 * 15;
    private static final String TAG = RestClient.class.getSimpleName();
    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static RestClient clientInstance;

    private static OkHttpClient httpClient = new OkHttpClient().newBuilder()
            .connectTimeout(TIME, TimeUnit.SECONDS)
            .readTimeout(TIME, TimeUnit.SECONDS)
            .writeTimeout(TIME, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(new UnauthorisedInterceptor())
            .addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(@NonNull Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();
                   /* if (PrefsManager.containsKey(PrefsManager.PREFS_TOKEN)) {
                        requestBuilder.addHeader("token", PrefsManager.readStringPrefsVal(PrefsManager.PREFS_TOKEN));
                        Log.d(TAG + " OkHttp Header -- ", "Token : " + PrefsManager.readStringPrefsVal(PrefsManager.PREFS_TOKEN));
                    }*/
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            })
            .build();
    private ApiInterface apiInterface;

    private RestClient() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.client(httpClient);
        Retrofit retrofit = builder.build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static RestClient getInstance() {
        if (clientInstance == null) {
            clientInstance = new RestClient();
        }
        return clientInstance;
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }
}