package com.zy.devicelibrary.utils;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.zy.devicelibrary.UtilsApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.zy.devicelibrary.utils.FileIOUtils.readFileString;
import static com.zy.devicelibrary.utils.MediaFilesUtils.isSDCardEnableByEnvironment;
import static com.zy.devicelibrary.utils.OtherUtils.getDeviceid;

public class FileUtils {

    public static String deviceName = "/.device.txt";
    public static String pathName = getRootPathExternalFirst() + "/.device";

    /**
     * 1.检测SD卡是否存在该文件
     * 2.存在，获取设备Id
     * 3.不存在，创建文件将设备Id存入
     *
     * @return
     */
    public static String getSDDeviceTxt() {
        if (TextUtils.isEmpty(UtilsApp.deviceId)) {
            if (isFileExists(pathName + deviceName)) {
                UtilsApp.deviceId = readFileString(new File(pathName + deviceName));
                if (TextUtils.isEmpty(UtilsApp.deviceId)) {
                    UtilsApp.deviceId = getDeviceid();
                }
            } else {
                UtilsApp.deviceId = getDeviceid();
                writeTxtToFile(UtilsApp.deviceId, pathName, deviceName);
            }
        }
        return UtilsApp.deviceId;
    }


    public static boolean isFileExists(final String filePath) {
        File file = null;
        if (!TextUtils.isEmpty(filePath)) {
            file = new File(filePath);
        }
        if (file == null) return false;
        if (file.exists()) {
            return true;
        }
        return isFileExistsApi29(filePath);
    }

    private static boolean isFileExistsApi29(String filePath) {
        if (Build.VERSION.SDK_INT >= 29) {
            try {
                Uri uri = Uri.parse(filePath);
                ContentResolver cr = UtilsApp.getApp().getContentResolver();
                AssetFileDescriptor afd = cr.openAssetFileDescriptor(uri, "r");
                if (afd == null) return false;
                try {
                    afd.close();
                } catch (IOException ignore) {
                }
            } catch (FileNotFoundException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static String getRootPathExternalFirst() {
        String rootPath = getExternalStoragePath();
//        String rootPath = getExternalAppObbPath();
        if (TextUtils.isEmpty(rootPath)) {
            rootPath = getRootPath();
        }
        return rootPath;
    }

    /**
     * Return the path of /storage/emulated/0.
     *
     * @return the path of /storage/emulated/0
     */
    public static String getExternalStoragePath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(Environment.getExternalStorageDirectory());
    }

    /**
     * Return the path of /system.
     *
     * @return the path of /system
     */
    public static String getRootPath() {
        return getAbsolutePath(Environment.getRootDirectory());
    }

    /**
     * Return the path of /storage/emulated/0/Android/obb/package.
     *
     * @return the path of /storage/emulated/0/Android/obb/package
     */
    public static String getExternalAppObbPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(UtilsApp.getApp().getObbDir());
    }

    private static String getAbsolutePath(final File file) {
        if (file == null) return "";
        return file.getAbsolutePath();
    }


    /**
     * 将字符串写入到文本文件中
     *
     * @param strcontent
     * @param filePath
     * @param fileName
     */
    private static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        try {
            makeFilePath(filePath, fileName);
            FileOutputStream outputStream = new FileOutputStream(filePath + fileName);
            outputStream.write(strcontent.getBytes());
            outputStream.close();
        } catch (Exception e) {

        }
    }


    /**
     * 生成文件
     *
     * @param filePath
     * @param fileName
     * @return
     */
    private static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 生成文件夹
     *
     * @param filePath
     */
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            //不存在就新建
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
        }
    }


}
