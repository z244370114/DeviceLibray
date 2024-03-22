package com.zy.devicelibrary.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 判断设备是否为折叠类
 */
public class DevicesUtils {
    /**
     * 手机设备工具类
     */
    private final static String TAG = "DevicesUtils";
    private final static List<String> HUAWEI_FOLD_DEVICES;
    private final static List<String> HUAWEI_FOLD_MODELS;
    private final static String HUAWEI_BRAND = "HUAWEI";
    private final static String SAMSUNG_BRAND = "samsung";
    private static String mAutoJudgeFoldDevicesConfig = null;

    static {
        HUAWEI_FOLD_DEVICES = new ArrayList<>();
        HUAWEI_FOLD_DEVICES.add("unknownRLI");
        HUAWEI_FOLD_DEVICES.add("HWTAH");
        HUAWEI_FOLD_DEVICES.add("MRX-AL09");
        HUAWEI_FOLD_DEVICES.add("HWMRX");
        HUAWEI_FOLD_DEVICES.add("TAH-AN00m");
        HUAWEI_FOLD_DEVICES.add("HWTAH-C");
        HUAWEI_FOLD_DEVICES.add("RHA-AN00m");
        HUAWEI_FOLD_DEVICES.add("unknowRHA");
        HUAWEI_FOLD_DEVICES.add("unknownTXL");
        HUAWEI_FOLD_DEVICES.add("HWTET");
        HUAWEI_FOLD_DEVICES.add("PAL-AL00");
        HUAWEI_FOLD_DEVICES.add("PAL-LX9");
        HUAWEI_FOLD_DEVICES.add("DHF-AL00");
        HUAWEI_FOLD_DEVICES.add("DHF-LX9");
        HUAWEI_FOLD_MODELS = new ArrayList<>();
        HUAWEI_FOLD_MODELS.add("TAH-N29m");
        HUAWEI_FOLD_MODELS.add("ALT-AL00");
        HUAWEI_FOLD_MODELS.add("TGW-AL00");
        HUAWEI_FOLD_MODELS.add("ALT-L29");
        HUAWEI_FOLD_MODELS.add("TGW-L29");
        HUAWEI_FOLD_MODELS.add("ALT-AL10");
        HUAWEI_FOLD_MODELS.add("TWH-AL10");
    }

    /**
     * 折叠屏判断
     *
     * @return
     */
    public static boolean isFoldDevice(Context context) {
        if (SAMSUNG_BRAND.equalsIgnoreCase(Build.BRAND)) {
            if (TextUtils.equals("SM-F9000", Build.MODEL)) {
                return true;
            }
        }
        if (HUAWEI_BRAND.equalsIgnoreCase(Build.BRAND)
                && (HUAWEI_FOLD_DEVICES.contains(Build.DEVICE) || HUAWEI_FOLD_MODELS.contains(Build.MODEL))) {
            Log.i(TAG, "is HUAWEI Fold devices");
            return true;
        }
        if (autoJudgeFoldDevices(context)) {
            // 不根据手机型号，自动判断折叠屏
            if (isOPPOFold()) {
                Log.i(TAG, "is OPPO Fold devices");
                return true;
            }
            if (isVivoFoldableDevice()) {
                Log.i(TAG, "is VIVO Fold devices");
                return true;
            }
            if (isXiaomiFold()) {
                Log.i(TAG, "is Xiaomi Fold devices");
                return true;
            }
            if (isHwFoldableDevice(context)) {
                Log.i(TAG, "is HW Fold devices");
                return true;
            }
        }
        Log.i(TAG, "is Fold devices false");
        return false;
    }

    private static boolean autoJudgeFoldDevices(Context context) {
        if (context == null) {
            return true;
        }
        if (mAutoJudgeFoldDevicesConfig == null) {
            mAutoJudgeFoldDevicesConfig = PreferenceManager.getDefaultSharedPreferences(context).getString("AU_Common_auto_judge_fold", "true");
        }
        return TextUtils.equals("true", mAutoJudgeFoldDevicesConfig);
    }

    /**
     * 判断当前设备是否是Android Pad
     *
     * @param resources
     * @return
     */
    public final static boolean isPad(Resources resources) {
        return (resources.getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断是否为oppo的折叠屏
     *
     * @return
     */
    public static boolean isOPPOFold() {
        boolean isFold = false;
        try {
            Class<?> cls = Class.forName("com.oplus.content.OplusFeatureConfigManager");
            Method instance = cls.getMethod("getInstance");
            Object configManager = instance.invoke(null);
            Method hasFeature = cls.getDeclaredMethod("hasFeature", String.class);
            Object object = hasFeature.invoke(configManager, "oplus.hardware.type.fold");
            if (object instanceof Boolean) {
                isFold = (boolean) object;
            }
        } catch (Throwable e) {
            Log.e(TAG, "isOPPOFold devices error");
        }
        return isFold;
    }

    /**
     * 判断是否为vivo的折叠屏
     *
     * @return
     */
    private static boolean isVivoFoldableDevice() {
        try {
            Class<?> c = Class.forName("android.util.FtDeviceInfo");
            Method m = c.getMethod("getDeviceType");
            Object dType = m.invoke(c);
            return "foldable".equals(dType);
        } catch (Throwable e) {
            Log.e(TAG, "isVivoFoldableDevice devices error");
        }
        return false;
    }

    /**
     * 判断是否为小米折叠屏
     *
     * @return
     */
    public static boolean isXiaomiFold() {
        try {
            Class c = Class.forName("android.os.SystemProperties");
            Method m = c.getMethod("getInt", String.class, int.class);
            int type = (int) m.invoke(c, "persist.sys.muiltdisplay_type", 0);
            return type == 2;
        } catch (Throwable e) {
            Log.e(TAG, "isXiaomiFold devices error");
        }
        return false;
    }

    /**
     * 判断是否为华为折叠屏
     *
     * @param context
     * @return
     */
    public static boolean isHwFoldableDevice(Context context) {
        try {
            if (context == null) {
                Log.e(TAG, "isHwFoldableDevice context is null");
                return false;
            }
            if ("HUAWEI".equalsIgnoreCase(Build.MANUFACTURER)
                    && context.getPackageManager().hasSystemFeature("com.huawei.hardware.sensor.posture")) {
                return true;
            }
        } catch (Throwable e) {
            Log.e(TAG, "isHwFoldableDevice devices error");
        }
        return false;
    }
}