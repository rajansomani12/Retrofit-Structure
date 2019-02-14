package com.example.win10.retrofitdemowithstructure.Api;


import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


public interface ApiInterface {

    @GET
    Call<JsonElement> getWebserviceCall(@Url String url);

    @GET
    Call<JsonElement> getWebserviceCall(@Url String url, @QueryMap HashMap<String, Object> body);

    @POST
    Call<JsonElement> postWebserviceCall(@Url String url, @Body HashMap<String, Object> body);


    @POST
    Call<JsonElement> postWebserviceCall(@Url String url);


    @Multipart
    @POST("AssignTrackerbySales")
    Call<JsonElement> assignTrackerbySales(@PartMap Map<String, RequestBody> params, @Part MultipartBody.Part[] bodyImg);

    @Multipart
    @POST("addbusiness")
    Call<JsonElement> addCustomer(@PartMap Map<String, RequestBody> params, @Part MultipartBody.Part profileImage, @Part MultipartBody.Part panImage, @Part MultipartBody.Part adharImage);

    @Multipart
    @POST("addpayment")
    Call<JsonElement> addCustomer(@PartMap Map<String, RequestBody> params, @Part MultipartBody.Part receiptImg);


    @POST
    Call<JsonElement> downloadAtt(@Url String url, @Header("fileID") String token);


    @GET
    @Streaming
    Call<ResponseBody> downloadAtt(@Url String url, @Header("Token") String token, @Header("UserID") int UserID, @Header("fileID") String fileId);

    @Multipart
    @POST("SaveRemovalDetails")
    Call<JsonElement> saveRemovalDetails(@PartMap Map<String, RequestBody> params, @Part MultipartBody.Part[] bodyImg);

    @Multipart
    @POST("SaveReplacementDetails")
    Call<JsonElement> saveReplacementDetails(@PartMap Map<String, RequestBody> params, @Part MultipartBody.Part[] bodyImg);

    @Multipart
    @POST("InsertCases")
    Call<JsonElement> insertCases(@PartMap Map<String, RequestBody> params, @Part MultipartBody.Part[] bodyImg);


    @Multipart
    @POST("InsertLead")
    Call<JsonElement> insertLead(@PartMap Map<String, RequestBody> params, @Part MultipartBody.Part[] bodyImg);


    @GET
    Call<ResponseBody> getUserInfo(@Header("Token") String token);


    /*----------------------------------------Uploading an Image --------------------------------*/

    @Multipart
    @POST("user/MyAccount")
    Call<JsonElement> myProfile(@PartMap Map<String, RequestBody> params, @Part MultipartBody.Part profile_image);

    /*------------------------------------------Change Profile Image---------------------------------------*/

    @Multipart
    @POST("user/updateProfilePic")
    Call<JsonElement> profileImageChange(@PartMap Map<String, RequestBody> params, @Part MultipartBody.Part profile_image);
}