package com.example.win10.retrofitdemowithstructure.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.service.autofill.UserData;

import com.google.gson.Gson;

import java.util.Set;


public class PrefsManager {

    public static final String PREFS_TOKEN = "PREFS_TOKEN";
    public static final String PREFS_USER_INFO = "USER_INFO";
    public static final String PREFS_LANGUAGE = "PREFS_LANGUAGE";
    public static final String USER_ID = "USER_ID";
    public static final String PREFS_VEHICLECOUNT = "PREFS_VEHICLECOUNT";
    public static final String PREFS_PERSONAL_COUNT = "PREFS_PERSONAL_COUNT";
    public static final String PREFS_MANAGE_GEOFENCE_DATA = "PREFS_MANAGE_GEOFENCE_DATA";
    public static final String PREFS_USERNAME = "PREFS_USERNAME";
    public static final String PREFS_PASSWORD = "PREFS_PASSWORD";
    public static final String PREFS_OBD_COUNT = "PREFS_OBD_COUNT";
    public static final String PREFS_P2P_COUNT = "PREFS_P2P_COUNT";
    private static final String SP_NAME = "PREFS_ADDON_TRACK";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SP_NAME, context.MODE_PRIVATE); // Context.MODE_PRIVATE);
    }

    /**
     * ---     String      ---
     **/
    public static void savePrefsVal(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(AddonTrackApplication.getAppContext()).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String readStringPrefsVal(String key) {
        return getSharedPreferences(AddonTrackApplication.getAppContext()).getString(key, null);
    }

    /**
     * ---     boolean      ---
     **/
    public static void savePrefsVal(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(AddonTrackApplication.getAppContext()).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean readBooleanPrefsVal(String key) {
        return getSharedPreferences(AddonTrackApplication.getAppContext()).getBoolean(key, false);
    }

    /**
     * ---     float      ---
     **/
    public static void savePrefsVal(String key, float value) {
        SharedPreferences.Editor editor = getSharedPreferences(AddonTrackApplication.getAppContext()).edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float readFloatPrefsVal(String key) {
        return getSharedPreferences(AddonTrackApplication.getAppContext()).getFloat(key, 0.0F);
    }

    /**
     * ---     int      ---
     **/
    public static void savePrefsVal(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences(AddonTrackApplication.getAppContext()).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int readIntPrefsVal(String key) {
        return getSharedPreferences(AddonTrackApplication.getAppContext()).getInt(key, 0);
    }

    /**
     * ---     long      ---
     **/
    public static void savePrefsVal(String key, long value) {
        SharedPreferences.Editor editor = getSharedPreferences(AddonTrackApplication.getAppContext()).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long readLongPrefsVal(String key) {
        return getSharedPreferences(AddonTrackApplication.getAppContext()).getLong(key, 0L);
    }

    /**
     * ---     String-Set      ---
     **/
    public static void savePrefsVal(String key, Set<String> stringSet) {
        SharedPreferences.Editor editor = getSharedPreferences(AddonTrackApplication.getAppContext()).edit();
        editor.putStringSet(key, stringSet);
        editor.apply();
    }

    public static Set<String> readStringSetPrefsVal(String key) {
        return getSharedPreferences(AddonTrackApplication.getAppContext()).getStringSet(key, null);
    }

    public static boolean containsKey(String key) {
        return getSharedPreferences(AddonTrackApplication.getAppContext()).contains(key);
    }

    public static void removeKey(String key) {
        SharedPreferences.Editor editor = getSharedPreferences(AddonTrackApplication.getAppContext()).edit();
        editor.remove(key);
        editor.apply();
    }

    public static void removeAllKey() {
        getSharedPreferences(AddonTrackApplication.getAppContext()).edit().clear().apply();
    }

	/*public static void setUserInfo(Userinfo userinfo) {
		SharedPreferences.Editor editor = getSharedPreferences(FleetMasterApplication.getAppContext()).edit();
		editor.putString(PREFS_USER_INFO, new Gson().toJson(userinfo));
		Log.e("sss", "sss" + PREFS_USER_INFO);
		editor.apply();
	}

	public static Userinfo getUserInfo() {
		return new Gson().fromJson(getSharedPreferences(FleetMasterApplication.getAppContext()).getString(PREFS_USER_INFO, null), Userinfo.class);
	}
*/

    public static String getLanguage() {
        return getSharedPreferences(AddonTrackApplication.getAppContext()).getString(PREFS_LANGUAGE, "en");
    }

    public static void setLanguage(String value) {
        SharedPreferences.Editor editor = getSharedPreferences(AddonTrackApplication.getAppContext()).edit();
        editor.putString(PREFS_LANGUAGE, value);
        editor.apply();
    }

    public static UserData getUserData() {
        return new Gson().fromJson(getSharedPreferences(AddonTrackApplication.getAppContext()).getString(PREFS_USER_INFO, null), UserData.class);
    }

    public static void setUserData(UserData userData) {
        SharedPreferences.Editor editor = getSharedPreferences(AddonTrackApplication.getAppContext()).edit();
        editor.putString(PREFS_USER_INFO, new Gson().toJson(userData));
        editor.apply();
    }












    /*public static boolean isSalesAdmin() {
		return getUserInfo().getUserType().equalsIgnoreCase("Sales Admin");
    }
    public static ArrayList<UserData> getRegisteredUserList() {
        Type type = new TypeToken<ArrayList<UserData>>() {
        }.getType();
        return new Gson().fromJson(PrefsManager.readStringPrefsVal(PrefsManager.REGISTERD_USER_LIST), type);
    }

    public static ArrayList<UserData> getUnregisteredUserList() {
        Type type = new TypeToken<ArrayList<UserData>>() {
        }.getType();
        return new Gson().fromJson(PrefsManager.readStringPrefsVal(PrefsManager.UN_REGISTERD_USER_LIST), type);
    }*/

    /*
    1	MODE_APPEND
    This will append the new preferences with the already existing preferences

    2	MODE_ENABLE_WRITE_AHEAD_LOGGING
    Database open flag. When it is set , it would enable write ahead logging by default

    3	MODE_MULTI_PROCESS
    This method will check for modification of preferences even if the sharedpreference instance has already been loaded

    4	MODE_PRIVATE
    By setting this mode , the file can only be accessed using calling application

    5	MODE_WORLD_READABLE
    This mode allow other application to read the preferences

    6	MODE_WORLD_WRITEABLE
    This mode allow other application to write the preferences
    */
}
