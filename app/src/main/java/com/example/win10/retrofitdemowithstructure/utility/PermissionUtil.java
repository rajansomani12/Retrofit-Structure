package com.example.win10.retrofitdemowithstructure.utility;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.win10.retrofitdemowithstructure.R;

import java.util.ArrayList;


public class PermissionUtil {

    /* ------------ Permission required  ----------------- */
    public static final int REQUEST_ALL_PERMISSION = 1;
    public static final int REQUEST_FINE_LOCATION_CODE = 2;
    public static final int REQUEST_PHONE_STATE_CODE = 3;
    public static final int REQUEST_READ_CONTACT = 4;
    public static final int REQUEST_CAMERA = 5;
    public static final int REQUEST_READ_STORAGE = 6;
    public static final int REQUEST_WRITE_STORAGE = 7;

    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String ACCESS_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String ACCESS_READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String ACCESS_CAMEARA = Manifest.permission.CAMERA;
    public static final String ACCESS_READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String ACCESS_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public static boolean checkAllPermission(Activity activity) {
        return PermissionUtil.checkPermissionForAccess(activity,
                new String[]{
                        ACCESS_READ_STORAGE,
                        ACCESS_WRITE_STORAGE, ACCESS_CAMEARA, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}
                , REQUEST_ALL_PERMISSION);
    }

    public static boolean checkPermissionForAccess(Context activity, String[] requirePermission, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            ArrayList<String> permissionArray = new ArrayList<>();
            for (String aRequirePermission : requirePermission) {
                if (ContextCompat.checkSelfPermission(activity, aRequirePermission)
                        != PackageManager.PERMISSION_GRANTED) {
                    permissionArray.add(aRequirePermission);
                }
            }

            if (permissionArray.size() > 0) {
                String[] requestPermission;
                requestPermission = permissionArray.toArray(new String[permissionArray.size()]);
                ((Activity) activity).requestPermissions(requestPermission, requestCode);

            } else {
//                Toast.makeText(MainActivity.this, "Permission Granted M", Toast.LENGTH_SHORT).show();
                return true;
            }
        } else {
//            Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public static boolean checkPermissionForAccessShould(final Activity activity, final String requirePermission, final int requestCode, String infoMessage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(activity, requirePermission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requirePermission)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage(infoMessage);
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                activity.requestPermissions(new String[]{requirePermission}, requestCode);
                            }
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    activity.requestPermissions(new String[]{requirePermission}, requestCode);
                }
            } else {
                Toast.makeText(AddonTrackApplication.getAppContext(), "Permission Granted M", Toast.LENGTH_SHORT).show();
                return true;
            }
        } else {
            Toast.makeText(AddonTrackApplication.getAppContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public static void ifNotGrantedWithDontAsk(final Activity activity, final String requirePermission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, requirePermission)) {
                showPermissionDailog(activity);
            }
        }
    }


    public static void showPermissionDailog(final Activity activity) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(R.string.msg_permission_step);
        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
//                startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

}
