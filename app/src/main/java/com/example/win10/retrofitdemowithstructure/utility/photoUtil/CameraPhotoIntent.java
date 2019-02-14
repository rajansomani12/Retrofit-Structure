package com.example.win10.retrofitdemowithstructure.utility.photoUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.example.win10.retrofitdemowithstructure.BuildConfig;
import com.example.win10.retrofitdemowithstructure.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CameraPhotoIntent {

    public static final int REQUEST_CODE_CAMERA = 101;
    final String TAG = this.getClass().getSimpleName();

    private String photoPath;
    private Context context;

    public CameraPhotoIntent(Context context) {
        this.context = context;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public Intent takePhotoIntent() throws IOException {
        Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (in.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = createImageFile();

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri contentUri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    contentUri = FileProvider.getUriForFile(context,
                            BuildConfig.APPLICATION_ID + ".fileprovider",
                            photoFile);
                } else {
                    contentUri = Uri.fromFile(photoFile);
                }

                in.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            }
        }
        return in;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        /*File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);*/

        /*File storageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                context.getString(R.string.app_name));*/

        /*File storageDir = new File(Environment.getExternalStorageDirectory() + File.separator + context.getString(R.string.app_name));*/

        File storageDir = new File(Environment.getExternalStorageDirectory(), context.getString(R.string.app_name));

        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                Log.d("IMApp APP", "Oops! Failed create ");
                return null;
            }
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        photoPath = image.getAbsolutePath();
        return image;
    }

    public void addToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photoPath);
        Uri contentUri = Uri.fromFile(f);

        /*Uri contentUri = FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider",
                f);*/

        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }
}
