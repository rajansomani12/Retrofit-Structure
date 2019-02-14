package com.example.win10.retrofitdemowithstructure.Api;

import android.content.Context;

import com.example.win10.retrofitdemowithstructure.utility.AddonTrackApplication;

import java.io.IOException;

import okhttp3.Interceptor;


public class UnauthorisedInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Response response = chain.proceed(chain.request());
        if (response.code() == 401) {
            Context context = AddonTrackApplication.getAppContext();
        }
        return response;
    }
}