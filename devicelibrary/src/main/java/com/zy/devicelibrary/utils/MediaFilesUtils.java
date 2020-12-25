package com.zy.devicelibrary.utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;

import com.zy.devicelibrary.UtilsApp;

import java.io.File;

public class MediaFilesUtils {

    public static boolean isSDCardEnableByEnvironment() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static int getImagesInternal() {
        String[] projection = {MediaStore.Images.Media.DATA};
        int count = getDataCount(MediaStore.Images.Media.INTERNAL_CONTENT_URI, projection);
        return count;
    }

    public static int getImagesExternal() {
        String[] projection = {MediaStore.Images.Media.DATA};
        int count = getDataCount(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection);
        return count;
    }

    public static int getAudioInternal() {
        String[] projection = {MediaStore.Audio.Media.DATA};
        int count = getDataCount(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, projection);
        return count;
    }

    public static int getAudioExternal() {
        String[] projection = {MediaStore.Audio.Media.DATA};
        int count = getDataCount(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection);
        return count;
    }

    public static int getVideoInternal() {
        String[] projection = {MediaStore.Video.Media.DATA};
        int count = getDataCount(MediaStore.Video.Media.INTERNAL_CONTENT_URI, projection);
        return count;
    }

    public static int getVideoExternal() {
        String[] projection = {MediaStore.Video.Media.DATA};
        int count = getDataCount(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection);
        return count;
    }

    public static int getDownloadFilesCount() {
        try {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).listFiles().length;
        } catch (Exception ignored) {
            return -1;
        }
    }

    public static int getDataCount(Uri uri, String[] projection) {
        if (ActivityCompat.checkSelfPermission(UtilsApp.getApp(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            return 0;
        }
        int count = 0;
        ContentResolver contentResolver = UtilsApp.getApp().getContentResolver();
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        if (cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    private static String getAbsolutePath(final File file) {
        if (file == null) return "";
        return file.getAbsolutePath();
    }

}
