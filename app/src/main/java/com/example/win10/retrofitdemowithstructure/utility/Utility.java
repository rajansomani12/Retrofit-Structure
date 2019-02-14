package com.example.win10.retrofitdemowithstructure.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win10.retrofitdemowithstructure.BuildConfig;
import com.example.win10.retrofitdemowithstructure.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;


public class Utility {


    public static final String STATE = "STATE";
    public static final String CITY = "CITY";
    public static final String ZIP_CODE = "ZIP_CODE";

    @SuppressLint("NewApi")
    public static int getDeviceWidth(Activity activity) {

        WindowManager wm = activity.getWindowManager();
        Point point = new Point();

        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }

    @SuppressLint("NewApi")
    public static int getDeviceHeight(Activity activity) {
        WindowManager wm = activity.getWindowManager();
        Point point = new Point();

        wm.getDefaultDisplay().getSize(point);
        return point.y;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static String getDeviceName() {

        String manufacturer = Build.MANUFACTURER;

        String model = Build.MODEL;

        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }

        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {

        if (TextUtils.isEmpty(str)) {
            return str;
        }

        char[] arr = str.toCharArray();

        boolean capitalizeNext = true;

        String phrase = "";

        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }

        return phrase;
    }

    public static void hideKeyboard(final Activity activity) {
        final View view = activity.getCurrentFocus();
        final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view == null) {
                    View view2 = new View(activity);
                    imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
                } else {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }, 125);
    }

    public static void showMessage(Activity activity, boolean isSuccess, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(isSuccess ? Color.GREEN : Color.WHITE);
        textView.setMaxLines(3);
        snackbar.show();
    }

    public static void showMessageWithAction(Activity activity, boolean isSuccess, String message, View.OnClickListener onActionClickListener) {
        showMessageWithAction(activity, isSuccess, message, "Retry", onActionClickListener);
    }

    public static void showMessageWithAction(Activity activity, boolean isSuccess, String message, String actionName, View.OnClickListener onActionClickListener) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(isSuccess ? Color.GREEN : Color.WHITE);
        textView.setMaxLines(3);
        snackbar.setAction(actionName, onActionClickListener);
        snackbar.show();
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showNoInternetAvailable(Activity activity) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), activity.getString(R.string.msg_no_internet_available), Snackbar.LENGTH_LONG);
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public static void showNoInternetAvailableWithAction(Activity activity, View.OnClickListener onActionClickListener) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), activity.getString(R.string.msg_no_internet_available), Snackbar.LENGTH_LONG);
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.setAction("Retry", onActionClickListener);
        snackbar.show();
    }

    /*public static boolean isVehicleNumber(String vehicleNumber) {
		Pattern vehiclePattern = Pattern.compile("^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$");
        *//*Pattern vehiclePattern = Pattern.compile("^[A-Z]{2} [0-9]{2} [A-Z]{2} [0-9]{4}$");*//*
     *//*Pattern vehiclePattern = Pattern.compile("/(([A-Za-z]){2,3}(|-)(?:[0-9]){1,2}(|-)(?:[A-Za-z]){2}(|-)([0-9]){1,4})|(([A-Za-z]){2,3}(|-)([0-9]){1,4})/");*//*
        return vehiclePattern.matcher(vehicleNumber).find();
    }*/

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected() && netInfo.isAvailable();
    }

    public static void showAlertDialog(Context context, String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getString(R.string.app_name));
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static boolean isEmail(String email) {
        Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        return emailPattern.matcher(email).find();
    }

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((int) (targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static String getAddress(Double latStr, Double lngStr, Activity activity) throws IOException {

        double lat = latStr;
        double lng = lngStr;

        String fullAddress = "", countryName = "";

        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());

        List<Address> addressList = geocoder.getFromLocation(lat, lng, 1);

        if (addressList != null && addressList.size() > 0) {
            Address address = addressList.get(0);

            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                try {
                    fullAddress += address.getAddressLine(i) + ", ";
                    Log.i("full address==", fullAddress);
                } catch (Exception e) {
                    fullAddress += "";
                }
            }

        }
        return fullAddress + " " + countryName;

    }

    public static String getLatLngDetails(Double latStr, Double lngStr, Context context, String whichDetails) {

        if (latStr == null || lngStr == null) {
            return "---";
        }

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(latStr, lngStr, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                switch (whichDetails) {
                    case STATE:
                        try {
                            return address.getAdminArea();
                        } catch (Exception e) {
                            return "";
                        }

                    case CITY:
                        try {
                            if (address.getLocality() != null)
                                return address.getLocality();
                            else
                                return address.getSubAdminArea();
                        } catch (Exception e) {
                            return "---";
                        }
                    case ZIP_CODE:
                        try {
                            return address.getPostalCode();
                        } catch (Exception e) {
                            return "---";
                        }
                    default:
                        return "---";

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            return "---";
        }
        return "---";
    }

    public static String[] getLatLngDetails(Double latStr, Double lngStr, Context context) {

        String[] addressDetails = new String[3];
        if (latStr == null || lngStr == null) {
            return addressDetails;
        }

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(latStr, lngStr, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                try {
                    if (address.getLocality() != null)
                        addressDetails[0] = address.getLocality();
                    else
                        addressDetails[0] = address.getSubAdminArea();
                } catch (Exception e) {
                    addressDetails[0] = "";
                }

                try {
                    addressDetails[1] = address.getAdminArea();
                } catch (Exception e) {
                    addressDetails[1] = "";
                }

                try {
                    addressDetails[2] = address.getPostalCode();
                } catch (Exception e) {
                    addressDetails[2] = "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return addressDetails;
        }
        return addressDetails;
    }


  /*  public static void changeHintColor(Context context, WritableSpinner editText, String replaceString) {
        String event = editText.getHint().toString();
        int startPos = event.indexOf(replaceString);
        int endPos = startPos + replaceString.length();

        Spannable spanText = Spannable.Factory.getInstance().newSpannable(editText.getHint()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
        spanText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.red)), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(spanText);
    }*/

    public static void changeHintColor(Context context, TextView editText, String replaceString) {
        String event = editText.getHint().toString();
        int startPos = event.indexOf(replaceString);
        int endPos = startPos + replaceString.length();

        Spannable spanText = Spannable.Factory.getInstance().newSpannable(editText.getHint()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
        spanText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.red)), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(spanText);
    }

    public static void changeColorUnderLine(Context context, AppCompatTextView textView, String replaceString) {
        String event = textView.getText().toString();
        int startPos = event.indexOf(replaceString);
        int endPos = startPos + replaceString.length();

        Spannable spanText = Spannable.Factory.getInstance().newSpannable(textView.getText()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
        /*spanText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.dark_blue)), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/
        spanText.setSpan(new StyleSpan(Typeface.BOLD), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spanText, AppCompatTextView.BufferType.SPANNABLE);
    }

    public static void changeTextBoldSize(AppCompatTextView textView, String replaceString) {
        String event = textView.getText().toString();
        int startPos = event.indexOf(replaceString);
        int endPos = startPos + replaceString.length();

        Spannable spanText = Spannable.Factory.getInstance().newSpannable(textView.getText());
        spanText.setSpan(new StyleSpan(Typeface.BOLD), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new RelativeSizeSpan(1.2f), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spanText, AppCompatTextView.BufferType.SPANNABLE);
    }

    public static void changeTextBoldSizeColor(AppCompatTextView textView, String replaceString, int color) {
        String event = textView.getText().toString();
        int startPos = event.indexOf(replaceString);
        int endPos = startPos + replaceString.length();

        Spannable spanText = Spannable.Factory.getInstance().newSpannable(textView.getText());
        spanText.setSpan(new StyleSpan(Typeface.BOLD), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new RelativeSizeSpan(1.2f), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new ForegroundColorSpan(color), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spanText, AppCompatTextView.BufferType.SPANNABLE);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    private static String converToHtml(String html) {
        return html.replace("\n", "<br>");
    }


    public static float dpTopixel(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return dp * density;
    }

    public static float pixelTodp(Context context, float pixel) {
        float density = context.getResources().getDisplayMetrics().density;
        return pixel / density;
    }

    public static int dp(float value) {
        float density = 1;
        return (int) Math.ceil(density * value);
    }


    public static int dpToPx(float dp, Context context) {
        return dpToPx(dp, context.getResources());
    }

    private static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    public static void showAppDetailsIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static boolean isEmpty(TextView textView) {
        return textView.getText().toString().trim().length() == 0;
    }

    public static String fileExt(String url) {
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.contains("%")) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.contains("/")) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }

    public static Uri getUriFromFile(File photoFile, Context context) {
        Uri photoURI = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(context,
                        BuildConfig.APPLICATION_ID + ".fileprovider",
                        photoFile);

            }
        } else {
            photoURI = Uri.fromFile(photoFile);
        }
        return photoURI;
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/FLEET Master");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm", Locale.getDefault()).format(new Date());
        File mediaFile;
        String mImageName = "FLEET_" + timeStamp + ".jpeg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public static void showAlert(Activity activity, boolean isSuccess, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(activity.getResources().getColor(R.color.black));
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(isSuccess ? activity.getResources().getColor(R.color.white) : Color.WHITE);
        textView.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD_ITALIC));
        textView.setMaxLines(3);
        snackbar.show();
    }
}
