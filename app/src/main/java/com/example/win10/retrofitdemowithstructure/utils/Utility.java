package com.example.win10.retrofitdemowithstructure.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by sheeni on 20/3/18.
 */

public class Utility {

    public static boolean checkPermissionForAccess(Activity activity, String[] requirePermission, int requestCode) {
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
                activity.requestPermissions(requestPermission, requestCode);
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
}
