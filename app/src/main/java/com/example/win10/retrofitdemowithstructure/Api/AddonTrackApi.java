package com.example.win10.retrofitdemowithstructure.Api;


import com.example.win10.retrofitdemowithstructure.listener.DataResponseListener;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddonTrackApi {
    private static AddonTrackApi addonTrackApi = null;

    private static ApiInterface apiService = RestClient.getInstance().getApiInterface();


    public static AddonTrackApi getInstance() {
        if (addonTrackApi == null)
            return new AddonTrackApi();
        return addonTrackApi;
    }


    /* ---------------------------------------------------------------- GET Method ---------------------------------------------------------------------------- */


    /*----------------------------------------------------------Login----------------------------------------------*/

    public void login(String device_name, String offset,
                      String username, String password, DataResponseListener dataResponseListener) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("device_name", device_name);
        params.put("offset", offset);
        params.put("secret_key", "android_secrate_key");
        params.put("username", username);
        params.put("password", password);


        new GeneralRetrofit(apiService.postWebserviceCall("user/signIn", params), params, dataResponseListener).call();

    }

    /*--------------------------------------------------------------------getUserInformation-------------------------------------*/
    public void getUserInfo(DataResponseListener dataResponseListener) {
        new GeneralRetrofit(apiService.getWebserviceCall("user/getInfoUser"), null, dataResponseListener).call();

    }
    /*-------------------------------------------------------get device by business (Tab item title)-----------------------------*/

    /*  public void getDeviceByBusiness(DataResponseListener dataResponseListener) {
          new GeneralRetrofit(apiService.getWebserviceCall("businessdevice/getDeviceByBusiness?business_id=" + PrefsManager.getUserData().getBusinessId()), null, dataResponseListener).call();
      }
  */
    /*---------------------------------------------------------------Vehicle dashBoard detail(online,offline,idel----------------*/
    public void vehicleDashBoard(DataResponseListener dataResponseListener) {
        HashMap<String, Object> params = new HashMap<>();
        new GeneralRetrofit(apiService.postWebserviceCall("dashbord/appVehicleDashbord", params), params, dataResponseListener).call();

    }

    /*------------------------------------------Get Total Kelometer for Dashboard Vehicle----------------------------------------*/
    public void vehicleDashboardGetTotalKM(DataResponseListener dataResponseListener) {
        HashMap<String, Object> params = new HashMap<>();
        new GeneralRetrofit(apiService.postWebserviceCall("observation/getkilometers", params), params, dataResponseListener).call();

    }


    public void getTotalAlertCount(DataResponseListener dataResponseListener) {
        new GeneralRetrofit(apiService.getWebserviceCall("alert/getVehicleTrackingAlerts"), null, dataResponseListener).call();
    }

    /*----------------------------------------------------------For Personal Dashboard------------------------------------------*/


    /*---------------------------------------------------------------Personal dashBoard detail(online,offline,idel----------------*/

    public void personalDashBoard(DataResponseListener dataResponseListener) {
        HashMap<String, Object> params = new HashMap<>();
        new GeneralRetrofit(apiService.postWebserviceCall("dashbord/appPersonalDashbord", params), params, dataResponseListener).call();

    }

    public void getPersonalTrackingAlert(DataResponseListener dataResponseListener) {
        new GeneralRetrofit(apiService.getWebserviceCall("alert/getPersonalTrackingAlerts"), null, dataResponseListener).call();
    }


    /*----------------------------------------------------------------------Manage Geofence list-------------------------------*/


    /*public void getAllgeofenceByBusiness(DataResponseListener dataResponseListener) {
        new GeneralRetrofit(apiService.getWebserviceCall("geofence/getAllGeofenceByBusiness?business_id=" + PrefsManager.getUserData().getBusinessId()), null, dataResponseListener).call();

    }
*/
    /*----------------------------------------------------------Add Geofence----------------------------------------------*/

    public void addGeofence(String circleDistance, String geofenceName,
                            boolean isActive, String geofencePoint, DataResponseListener dataResponseListener) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("circle_distance", circleDistance);
        params.put("geofence_name", geofenceName);
        params.put("is_active", isActive);
        params.put("geofence_point", geofencePoint);

        new GeneralRetrofit(apiService.postWebserviceCall("geofence/addNew", params), params, dataResponseListener).call();

    }

    /*-----------------------------------------------------------------FAQ's API------------------------------------------------*/

    public void getFAQ(DataResponseListener dataResponseListener) {
        new GeneralRetrofit(apiService.getWebserviceCall("FAQS/getlist"), null, dataResponseListener).call();
    }

    /*-----------------------------------------------------------------About Us-----------------------------------------------*/

    public void loadAboutUs(DataResponseListener dataResponseListener) {
        new GeneralRetrofit(apiService.getWebserviceCall("aboutus/getAboutUsContent"), null, dataResponseListener).call();

    }


    /*---------------------------------------------------------------------getSubject (Contact us)---------------------------------------*/

    public void getSubject(DataResponseListener dataResponseListener) {
        new GeneralRetrofit(apiService.getWebserviceCall("contact/getSubject"), null, dataResponseListener).call();

    }

    /*------------------------------------------------------------------Contact Us--------------------------------------------*/
    public void contactUs(String name, String mobile,
                          String email, String message, String subject, DataResponseListener dataResponseListener) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("mobile", mobile);
        params.put("email", email);
        params.put("message", message);
        params.put("subject", subject);


        new GeneralRetrofit(apiService.postWebserviceCall("contact/contactUs", params), params, dataResponseListener).call();

    }

    /*--------------------------------------------------------------OTP (Forget Password)--------------------------------*/


    public void requestOTP(String email, DataResponseListener dataResponseListener) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);
        new GeneralRetrofit(apiService.postWebserviceCall("forgot_password/sendOTP", params), params, dataResponseListener).call();

    }


    /*-----------------------------------------------------------------getReportList-----------------------------------------*/

    public void getReportList(DataResponseListener dataResponseListener) {
        HashMap<String, Object> params = new HashMap<>();
        new GeneralRetrofit(apiService.postWebserviceCall("report/getReportList", params), params, dataResponseListener).call();

    }
    /*------------------------------------------------My Account----------------------------------------*/

    public void myAccount(String firstname, String lastname, String email, String mobile, String timezone_id, String default_map, String speed_unit
            , String map_view, String default_landing_page, String language_id, String profile_image, DataResponseListener dataResponseListener) {

        MultipartBody.Part receiptFile = MultipartUtil.prepareFilePart("profile_image", profile_image);

        Map<String, RequestBody> requestParam = new HashMap<>();
        requestParam.put("firstname", MultipartUtil.createPartFromString(firstname));
        requestParam.put("lastname", MultipartUtil.createPartFromString(lastname));
        requestParam.put("email", MultipartUtil.createPartFromString(email));
        requestParam.put("mobile", MultipartUtil.createPartFromString(mobile));
        requestParam.put("timezone_id", MultipartUtil.createPartFromString(timezone_id));
        requestParam.put("default_map", MultipartUtil.createPartFromString(default_map));
        requestParam.put("speed_unit", MultipartUtil.createPartFromString(speed_unit));
        requestParam.put("map_view", MultipartUtil.createPartFromString(map_view));
        requestParam.put("default_landing_page", MultipartUtil.createPartFromString(default_landing_page));
        requestParam.put("language_id", MultipartUtil.createPartFromString(language_id));

        new GeneralRetrofit(apiService.myProfile(requestParam, receiptFile), null, dataResponseListener).
                call();

    }

    /*-----------------------------------------get TimeZone List------------------------------------*/

    public void getTimeList(DataResponseListener dataResponseListener) {
        new GeneralRetrofit(apiService.getWebserviceCall("general/getTimeZoneList "), null, dataResponseListener).call();

    }
    /*------------------------------------------Default Landing Page--------------------------------*/

    public void getDefaultLandingPageList(DataResponseListener dataResponseListener) {
        new GeneralRetrofit(apiService.getWebserviceCall("systempage/getAllSystemPage"), null, dataResponseListener).call();

    }

    /*---------------------------------------------Language List-------------------------------------*/

    public void getLanguageList(DataResponseListener dataResponseListener) {
        new GeneralRetrofit(apiService.getWebserviceCall("language/getAllLanguage"), null, dataResponseListener).call();

    }


    /*--------------------------------------------getDeviceList (According to GeofenceId)-------------------*/

    public void getDeviceList(String geoFenceId, DataResponseListener dataResponseListener) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("geofence_id", geoFenceId);
        new GeneralRetrofit(apiService.postWebserviceCall("device_geofence/getDeviceByGeofence", params), params, dataResponseListener).call();

    }

    /*------------------------------------------------Assign Geofence----------------------------------------------*/

    public void postAssignGeofence(String geoFenceId, String deviceId, DataResponseListener dataResponseListener) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("geofence_id", geoFenceId);
        params.put("device_id", deviceId);
        new GeneralRetrofit(apiService.postWebserviceCall("device_geofence/assignDevicesToGeofence", params), params, dataResponseListener).call();

    }


    /*----------------------------------------------Dashboard P2P-----------------------------------------------------*/

    public void getP2PDashBoard(DataResponseListener dataResponseListener) {
        HashMap<String, Object> params = new HashMap<>();
        new GeneralRetrofit(apiService.postWebserviceCall("dashbord/appP2PDashbord", params), params, dataResponseListener).call();

    }


    /*---------------------------------------------------P2P Alert----------------------------------------------------*/

    public void getP2PDashBoardAlert(DataResponseListener dataResponseListener) {
        new GeneralRetrofit(apiService.getWebserviceCall("alert/getP2PTrackingAlerts"), null, dataResponseListener).call();

    }

    /*----------------------------------------------------DashBoard OBD------------------------------------------------*/

    public void getOBDDashBoard(DataResponseListener dataResponseListener) {
        HashMap<String, Object> params = new HashMap<>();
        new GeneralRetrofit(apiService.postWebserviceCall("dashbord/appObdDashbord", params), params, dataResponseListener).call();

    }

    /*----------------------------------------------------Dashboard OBD Alerts-------------------------------------------*/


    public void getOBDDashBoardAlert(DataResponseListener dataResponseListener) {
        new GeneralRetrofit(apiService.getWebserviceCall("alert/getObdTrackingAlerts"), null, dataResponseListener).call();

    }

    /*-------------------------------------------------------DashBoard getTotalKm OBD-----------------------------------*/

    public void getTotalKmOBD(DataResponseListener dataResponseListener) {
        HashMap<String, Object> params = new HashMap<>();
        new GeneralRetrofit(apiService.postWebserviceCall("dashbord/getobdkilometers", params), params, dataResponseListener).call();

    }

    /*---------------------------------------------------------Change Password--------------------------------------*/


    /*  public void changePassword(String newPass,String currentPass,DataResponseListener dataResponseListener) {
          HashMap<String, Object> params = new HashMap<>();
          params.put("new_password",newPass);
          params.put("currant_password",currentPass);
          params.put("user_id",PrefsManager.getUserData().getUserId());
          new GeneralRetrofit(apiService.postWebserviceCall("user/changeUserPassword", params), params, dataResponseListener).call();
  *//*
    }
*/
    /*------------------------------------------------update Profile Image----------------------------------------*/
    public void profileImageUpdate(String profile_image, DataResponseListener dataResponseListener) {

        MultipartBody.Part receiptFile = MultipartUtil.prepareFilePart("file", profile_image);

        Map<String, RequestBody> requestParam = new HashMap<>();

        new GeneralRetrofit(apiService.profileImageChange(requestParam, receiptFile), null, dataResponseListener).
                call();

    }

    public void getExample(DataResponseListener dataResponseListener) {
        new GeneralRetrofit(apiService.getWebserviceCall("/api/users?page=2"), null, dataResponseListener).call();

    }


    public void postExample(String email, String password, DataResponseListener dataResponseListener) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        new GeneralRetrofit(apiService.postWebserviceCall("/api/register", params), params, dataResponseListener).call();

    }

}