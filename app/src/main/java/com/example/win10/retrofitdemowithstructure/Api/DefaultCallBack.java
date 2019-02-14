package com.example.win10.retrofitdemowithstructure.Api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class DefaultCallBack<T> implements Callback<T> {
    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        Log.w("CT_", "--->   " + "Status code : " + response.code());
        if (response.code() != 200) {
            Log.w("CT_", " --->   " + " Response NOT OK : " + response.raw().message());
        }
        if (response.body() != null) {
            Log.w("CT_", "--->   " + " RESPONSE = " + String.valueOf(response.body()));
            onSuccess(response.body(), response.code());
        } else {
            onError(response.errorBody(), response.code());
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        Log.w("CT_", "--->   " + " ON_Failure = " + t.toString());
        Log.w("CT_", "--->   " + " ON_Failure Localize Message = " + t.getLocalizedMessage());
        Log.w("CT_", "--->   " + " ON_Failure Message = " + t.getMessage());
        if (!call.isCanceled())
            onError(null, -1);
    }

    public abstract void onSuccess(final T response, int code);

    public abstract void onError(@Nullable ResponseBody body, int code);
}
