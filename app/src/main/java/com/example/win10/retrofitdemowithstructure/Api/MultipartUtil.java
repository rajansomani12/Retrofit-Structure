package com.example.win10.retrofitdemowithstructure.Api;

import android.support.annotation.NonNull;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

class MultipartUtil {

    /* -------------------------------  Multipart Util method --------------------------------------- */
    @NonNull
    static RequestBody createPartFromString(String partValue) {
        /*return RequestBody.create(MediaType.parse("text/plain"), partValue);*/
        return RequestBody.create(MultipartBody.FORM, partValue);

    }

    @NonNull
    static MultipartBody.Part prepareFilePart(String partName, String filePath) {
        File file = new File(filePath);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"),
                        file
                );
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, File file) {

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"),
                        file
                );
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
