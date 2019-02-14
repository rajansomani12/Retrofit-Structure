package com.example.win10.retrofitdemowithstructure.Api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.win10.retrofitdemowithstructure.listener.DataResponseListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class GeneralRetrofit {

    private final Call<JsonElement> call;
    private final Object params;
    private DataResponseListener dataResponseListener;
    private Callback<JsonElement> postCall = new Callback<JsonElement>() {
        @Override
        public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
            Log.w("CT_", "--->   " + " Status code : " + response.code());
            if (response.code() != 200) {
                Log.w("CT_", "--->   " + " Response NOT OK : " + response.raw().message());
            }
            if (response.body() != null) {
                Log.w("CT_", "--->   " + " RESPONSE = " + String.valueOf(response.body()));
                String responseString = String.valueOf(response.body());
                if (dataResponseListener != null)
                    dataResponseListener.onData_SuccessfulResponse(responseString);
            } else {
                if (dataResponseListener != null) {
                    try {
                        String errorResponse = new Gson().fromJson(response.errorBody().string(), JsonElement.class).toString();
                        JSONObject jsonObject = new JSONObject(errorResponse);
                        dataResponseListener.onData_FailureResponse(jsonObject.getJSONObject("result").getString("message"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        dataResponseListener.onData_FailureResponse(null);
                    }
                }
            }

        }

        @Override
        public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
            Log.w("CT_", "--->   " + " ON_Failure = " + t.toString());
            Log.w("CT_", "--->   " + " ON_Failure Localize Message = " + t.getLocalizedMessage());
            Log.w("CT_", "--->   " + " ON_Failure Message = " + t.getMessage());
            if (!call.isCanceled())
                if (dataResponseListener != null)
                    dataResponseListener.onData_FailureResponse(null);
        }
    };

    GeneralRetrofit(Call<JsonElement> call, Object params, DataResponseListener dataResponseListener) {
        this.call = call;
        this.params = params;
        this.dataResponseListener = dataResponseListener;

    }

    Call<JsonElement> call() {
        Log.w("CT_", "--->   " + " API URL = " + call.request().url());
        if (params != null && call.request().body() != null) {
            Log.w("CT_", "--->   " + " Passing Params = " + new Gson().toJson(params));
        }

        call.enqueue(postCall);
        return call;
    }


}