package com.example.win10.retrofitdemowithstructure.FileUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;

import com.example.win10.retrofitdemowithstructure.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FileUtility {

    private static final String FILE_DIRECTORY = "AddonTrack";
    private static final String FILE_PREFIX = "AddonTrack_";

    private final static String ParentDir = Environment.getExternalStorageDirectory() + File.separator + FILE_DIRECTORY;

    private final static String MainImageDir = ParentDir + File.separator + "Image";
    private final static String MainVideoDir = ParentDir + File.separator + "Video";

    private final static String ImageReceivedDir = MainImageDir + File.separator + "Received";
    private final static String ImageSentDir = MainImageDir + File.separator + "Sent";

    private final static String VideoReceivedDir = MainVideoDir + File.separator + "Received";
    private final static String VideoSentDir = MainVideoDir + File.separator + "Sent";

    public static void makeDirs() {

        File parenDir = new File(ParentDir);
        if (!parenDir.exists()) parenDir.mkdir();

        File mainImageDir = new File(MainImageDir);
        if (!mainImageDir.exists()) mainImageDir.mkdir();

        File mainVideoDir = new File(MainVideoDir);
        if (!mainVideoDir.exists()) mainVideoDir.mkdir();

        File imageReceivedDir = new File(ImageReceivedDir);
        if (!imageReceivedDir.exists()) imageReceivedDir.mkdir();

        File imageSentDir = new File(ImageSentDir);
        if (!imageSentDir.exists()) imageSentDir.mkdir();

        File videoReceivedDir = new File(VideoReceivedDir);
        if (!videoReceivedDir.exists()) videoReceivedDir.mkdir();

        File videoSentDir = new File(VideoSentDir);
        if (!videoSentDir.exists()) videoSentDir.mkdir();
    }

    public static String getMimeType(String path) {
        String extention = path.substring(path.lastIndexOf("."));
        String mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(extention);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeTypeMap);
    }

    public static File writeFile(byte[] data, String mimeType, String user) {

        try {

            String fileName = "media_" + System.currentTimeMillis() + "." + mimeType.split("/")[1];
            String path;

            path = mimeType.contains("image") ? ImageReceivedDir : VideoReceivedDir;

            File media_file = new File(path + File.separator + fileName);

            FileOutputStream out = new FileOutputStream(media_file);
            out.write(data);
            out.close();

            return media_file;

        } catch (IOException e) {
            return null;
        }
    }

    public static byte[] readFile(File file) {

        byte[] byteArray = null;

        try {

            InputStream inputStream = new FileInputStream(file);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 8];
            int bytesRead;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArray;
    }

    public static void openMedia(Context context, File file) {

        String mType = getMimeType(file.getPath());

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), mType.contains("image") ? "image/*" : "video/*");
        context.startActivity(intent);
    }

    public static void rotateImage(Activity activity, File file) {

        activity.getContentResolver().notifyChange(Uri.fromFile(file), null);

        int rotationAngle;

        try {

            ExifInterface exif = new ExifInterface(file.getAbsolutePath());

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    rotationAngle = 0;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotationAngle = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotationAngle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotationAngle = 90;
                    break;
                default:
                    rotationAngle = 0;
                    break;
            }
        } catch (Exception e) {
            rotationAngle = 0;
        }

        if (rotationAngle == 0)
            return;

        try {

            BitmapFactory.Options bounds = new BitmapFactory.Options();
            bounds.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getAbsolutePath(), bounds);

            BitmapFactory.Options opts = new BitmapFactory.Options();
            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);

            Matrix matrix = new Matrix();

            matrix.postRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
            FileOutputStream fos = new FileOutputStream(file);
            rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getSmallBitmap(File file) {

        try {

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, o);

            //The new size we want to scale to
            final int REQUIRED_SIZE = 100;

            //Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(file), null, o2);

        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static File createTemporaryFile() {

        try {

            File tempDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.temp/");

            if (!tempDir.exists()) tempDir.mkdir();

            return File.createTempFile("picture", ".png", tempDir);
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap getThumbnail(Context context, File file, String mimeType) {

        context.getContentResolver().notifyChange(Uri.fromFile(file), null);

        try {

            if (mimeType.contains("image")) {

                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(new FileInputStream(file), null, o);

                //The new size we want to scale to
                final int REQUIRED_SIZE = 100;

                //Find the correct scale value. It should be the power of 2.
                int scale = 1;

                while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;

                //Decode with inSampleSize
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;

                return BitmapFactory.decodeStream(new FileInputStream(file), null, o2);
            } else {
                return ThumbnailUtils.createVideoThumbnail(file.getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Uri getUriFromFileProvider(Context context, String pathName) {
        Uri contentUri;
        File photoFile = new File(pathName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentUri = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".fileprovider",
                    photoFile);
        } else {
            contentUri = Uri.fromFile(photoFile);
        }
        return contentUri;
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/" + FILE_DIRECTORY);

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        /*String mImageName = "JDD_" + "SharedImage" + ".jpeg";*/
        String mImageName = FILE_PREFIX + timeStamp + ".jpeg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public static File getOutputMediaFile(String fileName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/" + FILE_DIRECTORY);

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        /*String mImageName = "JDD_" + "SharedImage" + ".jpeg";*/
        String mImageName = FILE_PREFIX + fileName;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

}
