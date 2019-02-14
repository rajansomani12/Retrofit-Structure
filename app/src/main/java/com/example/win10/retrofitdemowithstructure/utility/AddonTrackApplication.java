package com.example.win10.retrofitdemowithstructure.utility;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;


public class AddonTrackApplication extends Application {

    public static AddonTrackApplication addonTrackApplication = null;
    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    private void setAppContext(Context mAppContext) {
        appContext = mAppContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();


        if (addonTrackApplication == null) {
            addonTrackApplication = this;
        }

        this.setAppContext(getApplicationContext());

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        /*LocaleUtils.updateConfig(this, newConfig);*/
        LocaleManager.setLocale(this);
    }

}
