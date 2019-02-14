package com.example.win10.retrofitdemowithstructure.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String BASE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";
    public static final String PARSE_dd_MMMM_yyyy = "dd MMMM yyyy";
    public static final String PARSE_dd_MM_yyyy_hh_mm_a = "dd MM yyyy hh:mm a";
    public static final String PARSE_yyyy_MM_dd_hh_mm_a = "yyyy-MM-dd hh:mm a";
    public static final String PARSE_dd_MMM_yyyy = "dd-MMM-yyyy";


    /* ------------------------------ Date related Function ------------------------------ */

    public static String getCurrentDateTime(String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date());
    }

    public static String parseDate(Date date, String outputPattern) {
        return new SimpleDateFormat(outputPattern, Locale.getDefault()).format(date);
    }

    public static String parseDate(String Date, String CurrentPattern, String OutputPattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(CurrentPattern, Locale.getDefault());

        try {
            java.util.Date startDate = sdf.parse(Date);
            sdf.applyPattern(OutputPattern);
            return sdf.format(startDate);
        } catch (Exception e) {
            return "";
        }
    }

    public static String parseDate(String Date, String OutputPattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(BASE_DATE_FORMAT, Locale.getDefault());

        try {
            Date startDate = sdf.parse(Date);
            sdf.applyPattern(OutputPattern);
            return sdf.format(startDate);
        } catch (Exception e) {
            return "";
        }

    }

    public static String parsePickerDate(int Year, int Month, int day) {

        String sYear = String.valueOf(Year);
        String sMonth = String.valueOf((Month + 1));
        String sDay = String.valueOf(day);

        if (sDay.length() == 1)
            sDay = "0" + sDay;

        if (sMonth.length() == 1)
            sMonth = "0" + sMonth;

        if (sDay.length() == 1)
            sDay = "0" + sDay;

        return sYear + "-" + sMonth + "-" + sDay;
    }

    public static String parsePickerTime(int Hour, int Minute, int Second) {

        String sHour = String.valueOf(Hour);
        String sMinute = String.valueOf(Minute);
        String sSecond = String.valueOf(Second);

        if (sHour.length() == 1)
            sHour = "0" + sHour;

        if (sMinute.length() == 1)
            sMinute = "0" + sMinute;

        if (sSecond.length() == 1)
            sSecond = "0" + sSecond;

        return sHour + ":" + sMinute + ":" + sSecond;
    }

    public static int[] seperateDate(String strDate) {
        String str[] = strDate.split("-");
        int[] date = new int[3];
        date[0] = Integer.parseInt(str[0]);
        date[1] = Integer.parseInt(str[1]);
        date[2] = Integer.parseInt(str[2]);

        return date;

    }

    public static boolean isFutureDate(String birthDateStr) {

        String pattern = "yyyy-mm-dd";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
            Date birthDate = sdf.parse(birthDateStr);
            Date currentDate = null;
            currentDate = sdf.parse(getCurrentDateTime(pattern));

            if (currentDate.getTime() < birthDate.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Date parseStringToDate(String Date, String CurrentPattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(CurrentPattern, Locale.getDefault());

        try {
            return sdf.parse(Date);
        } catch (Exception e) {
            return null;
        }
    }

}
