package com.example.win10.retrofitdemowithstructure.utility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;


/**
 * Created by hitendra on 10/1/18.
 */

public class CameraPhoto {

    private File file;

    public Intent getCameraIntent(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
            //	intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

            String authorities = AddonTrackApplication.getAppContext().getPackageName() + ".fileprovider";
            Uri imageUri = FileProvider.getUriForFile(AddonTrackApplication.getAppContext(), authorities, file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            return intent;
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            return intent;
        }
    }

    public File getFile() {
        return file;
    }
}
