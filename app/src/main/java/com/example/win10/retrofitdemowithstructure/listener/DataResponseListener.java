package com.example.win10.retrofitdemowithstructure.listener;


public interface DataResponseListener {
    public void onData_SuccessfulResponse(String stringResponse);

    public void onData_FailureResponse(String errorResponse);
}