package com.example.win10.retrofitdemowithstructure.utility.photoUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class GalleryPhoto {

    final String TAG = this.getClass().getSimpleName();

    private Context context;
    private Uri photoUri;

    public GalleryPhoto(Context context) {
        this.context = context;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public Intent openGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        return Intent.createChooser(intent, getChooserTitle());
    }

    public String getChooserTitle() {
        return "Select Pictures";
    }

    public String getPath() {

        String path;
        // SDK < API11
        path = RealPathUtil.getRealPathFromURI_API19(context, photoUri);

        return path;
    }

}