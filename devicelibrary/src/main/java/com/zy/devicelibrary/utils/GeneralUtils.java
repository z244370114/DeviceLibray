package com.zy.devicelibrary.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;

import com.zy.devicelibrary.UtilsApp;
import com.zy.devicelibrary.data.SimCardData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

import static android.content.res.Configuration.UI_MODE_TYPE_APPLIANCE;
import static android.content.res.Configuration.UI_MODE_TYPE_CAR;
import static android.content.res.Configuration.UI_MODE_TYPE_DESK;
import static android.content.res.Configuration.UI_MODE_TYPE_NORMAL;
import static android.content.res.Configuration.UI_MODE_TYPE_TELEVISION;
import static android.content.res.Configuration.UI_MODE_TYPE_UNDEFINED;
import static android.content.res.Configuration.UI_MODE_TYPE_VR_HEADSET;
import static android.content.res.Configuration.UI_MODE_TYPE_WATCH;
import static android.telephony.TelephonyManager.PHONE_TYPE_CDMA;
import static android.telephony.TelephonyManager.PHONE_TYPE_GSM;
import static android.telephony.TelephonyManager.PHONE_TYPE_NONE;
import static android.telephony.TelephonyManager.PHONE_TYPE_SIP;
import static com.zy.devicelibrary.utils.OtherUtils.getJudgeSIMCount;


public class GeneralUtils {

    public static String gaid = "";

    public enum NetworkType {
        NETWORK_ETHERNET,
        NETWORK_WIFI,
        NETWORK_5G,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }


    public static boolean isChekSelfPermission(String permission) {
        if (ActivityCompat.checkSelfPermission(UtilsApp.getApp(), permission)
                != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }


    @SuppressLint("HardwareIds")
    public static String getAndroidID() {
        String id = Settings.Secure.getString(
                UtilsApp.getApp().getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        if ("9774d56d682e549c".equals(id)) return "";
        return id == null ? "" : id;
    }


    public static String getNetworkOperatorName() {
        return getTelephonyManager().getNetworkOperatorName();
    }

    public static String getNetworkOperator() {
        return getTelephonyManager().getNetworkOperator();
    }


    public static String getSimOperatorByMnc() {
        String operator = getTelephonyManager().getSimOperator();
        if (operator == null) return "";
        switch (operator) {
            case "46000":
            case "46002":
            case "46007":
            case "46020":
                return "中国移动";
            case "46001":
            case "46006":
            case "46009":
                return "中国联通";
            case "46003":
            case "46005":
            case "46011":
                return "中国电信";
            default:
                return operator;
        }
    }

    public static String getMcc() {
        String networkOperator = getTelephonyManager().getNetworkOperator();
        if (!TextUtils.isEmpty(networkOperator)) {
            return networkOperator.substring(0, 3);
        } else {
            return "";
        }
    }

    public static String getMnc() {
        String networkOperator = getTelephonyManager().getNetworkOperator();
        if (!TextUtils.isEmpty(networkOperator)) {
            return networkOperator.substring(3);
        } else {
            return "";
        }
    }

    public static String getNetworkType() {
        if (isChekSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE)) {
            return "NETWORK_NO";
        }
        if (isEthernet()) {
            return "NETWORK_ETHERNET";
        }
        ConnectivityManager cm =
                (ConnectivityManager) UtilsApp.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return null;
        }
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return "NETWORK_WIFI";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return "NETWORK_2G";

                    case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return "NETWORK_3G";

                    case TelephonyManager.NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return "NETWORK_4G";

                    case TelephonyManager.NETWORK_TYPE_NR:
                        return "NETWORK_5G";
                    default:
                        String subtypeName = info.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                || subtypeName.equalsIgnoreCase("WCDMA")
                                || subtypeName.equalsIgnoreCase("CDMA2000")) {
                            return "NETWORK_3G";
                        } else {
                            return "NETWORK_UNKNOWN";
                        }
                }
            } else {
                return "NETWORK_UNKNOWN";
            }
        }
        return "NETWORK_NO";
    }

    public static String getPhoneType() {
        int phoneType = getTelephonyManager().getPhoneType();
        switch (phoneType) {
            case PHONE_TYPE_NONE:
                return "PHONE_TYPE_NONE";
            case PHONE_TYPE_GSM:
                return "PHONE_TYPE_GSM";
            case PHONE_TYPE_CDMA:
                return "PHONE_TYPE_CDMA";
            case PHONE_TYPE_SIP:
                return "PHONE_TYPE_SIP";
        }
        return "";
    }

    private static boolean isEthernet() {
        final ConnectivityManager cm =
                (ConnectivityManager) UtilsApp.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        final NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (info == null) return false;
        NetworkInfo.State state = info.getState();
        if (null == state) return false;
        return state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING;
    }

    @SuppressLint("HardwareIds")
    public static String getIMSI() {
        if (isChekSelfPermission(Manifest.permission.READ_PHONE_STATE)) {
            return "";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                getTelephonyManager().getSubscriberId();
            } catch (SecurityException e) {
                e.printStackTrace();
                return "";
            }
        }
        return getTelephonyManager().getSubscriberId();
    }

    public static String getIMSI(int subId) {
        TelephonyManager telephonyManager = getTelephonyManager();// 取得相关系统服务
        Class<?> telephonyManagerClass = null;
        String imsi = "";
        try {
            telephonyManagerClass = Class.forName("android.telephony.TelephonyManager");
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
                Method method = telephonyManagerClass.getMethod("getSubscriberId", int.class);
                imsi = (String) method.invoke(telephonyManager, subId);
            } else if (android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.LOLLIPOP) {
                Method method = telephonyManagerClass.getMethod("getSubscriberId", long.class);
                imsi = (String) method.invoke(telephonyManager, (long) subId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (imsi == null) {
            imsi = "";
        }
        return imsi;
    }

    @SuppressLint("MissingPermission")
    public static SimCardData getSimCardInfo() {
        SimCardData simCardData = new SimCardData();
        simCardData.sim_count = getJudgeSIMCount();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            //1.版本超过5.1，调用系统方法
            SubscriptionManager mSubscriptionManager = (SubscriptionManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            List<SubscriptionInfo> activeSubscriptionInfoList = null;
            if (mSubscriptionManager != null) {
                try {
                    activeSubscriptionInfoList = mSubscriptionManager.getActiveSubscriptionInfoList();
                } catch (Exception ignored) {
                }
            }
            if (activeSubscriptionInfoList != null && activeSubscriptionInfoList.size() > 0) {
                //1.1.1 有使用的卡，就遍历所有卡
                for (int i = 0; i < activeSubscriptionInfoList.size(); i++) {
                    SubscriptionInfo subscriptionInfo = activeSubscriptionInfoList.get(i);
                    if (i == 0) {
                        simCardData.imsi1 = getIMSI(subscriptionInfo.getSimSlotIndex());
                        simCardData.sim_country_iso1 = subscriptionInfo.getCountryIso();
                        simCardData.sim_serial_number1 = subscriptionInfo.getIccId();
                        simCardData.number1 = subscriptionInfo.getNumber();
                    } else {
                        simCardData.imsi2 = getIMSI(subscriptionInfo.getSimSlotIndex());
                        simCardData.sim_country_iso2 = subscriptionInfo.getCountryIso();
                        simCardData.sim_serial_number2 = subscriptionInfo.getIccId();
                        simCardData.number2 = subscriptionInfo.getNumber();
                    }
                }
            }
        }
        return simCardData;
    }

    public static String getImei() {
        return getImeiOrMeid(true);
    }

    public static String getMeid() {
        return getImeiOrMeid(false);
    }

    public static String getImeiOrMeid(boolean isImei) {
        if (isChekSelfPermission(Manifest.permission.READ_PHONE_STATE)) {
            return "";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return "";
        }
        TelephonyManager tm = getTelephonyManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (isImei) {
                return getMinOne(tm.getImei(0), tm.getImei(1));
            } else {
                return getMinOne(tm.getMeid(0), tm.getMeid(1));
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String ids = getSystemPropertyByReflect(isImei ? "ril.gsm.imei" : "ril.cdma.meid");
            if (!TextUtils.isEmpty(ids)) {
                String[] idArr = ids.split(",");
                if (idArr.length == 2) {
                    return getMinOne(idArr[0], idArr[1]);
                } else {
                    return idArr[0];
                }
            }
            String id0 = tm.getDeviceId();
            String id1 = "";
            try {
                Method method = tm.getClass().getMethod("getDeviceId", int.class);
                id1 = (String) method.invoke(tm,
                        isImei ? PHONE_TYPE_GSM
                                : PHONE_TYPE_CDMA);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (isImei) {
                if (id0 != null && id0.length() < 15) {
                    id0 = "";
                }
                if (id1 != null && id1.length() < 15) {
                    id1 = "";
                }
            } else {
                if (id0 != null && id0.length() == 14) {
                    id0 = "";
                }
                if (id1 != null && id1.length() == 14) {
                    id1 = "";
                }
            }
            return getMinOne(id0, id1);
        } else {
            String deviceId = tm.getDeviceId();
            if (isImei) {
                if (deviceId != null && deviceId.length() >= 15) {
                    return deviceId;
                }
            } else {
                if (deviceId != null && deviceId.length() == 14) {
                    return deviceId;
                }
            }
        }
        return "";
    }

    private static String getMinOne(String s0, String s1) {
        boolean empty0 = TextUtils.isEmpty(s0);
        boolean empty1 = TextUtils.isEmpty(s1);
        if (empty0 && empty1) return "";
        if (!empty0 && !empty1) {
            if (s0.compareTo(s1) <= 0) {
                return s0;
            } else {
                return s1;
            }
        }
        if (!empty0) return s0;
        return s1;
    }

    private static String getSystemPropertyByReflect(String key) {
        try {
            @SuppressLint("PrivateApi")
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method getMethod = clz.getMethod("get", String.class, String.class);
            return (String) getMethod.invoke(clz, key, "");
        } catch (Exception e) {/**/}
        return "";
    }


    /**
     * 基站编号
     *
     * @return
     */
    public static String getCidNumbers() {
        if (getTelephonyManager().getPhoneType() == PHONE_TYPE_GSM) {
            if (ActivityCompat.checkSelfPermission(UtilsApp.getApp(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            final GsmCellLocation location = (GsmCellLocation) getTelephonyManager().getCellLocation();
            if (location != null) {
                return String.valueOf(location.getCid());
            }
        }
        return "";
    }


    /**
     * 获取DNS
     *
     * @return
     */
    public static String getLocalDNS() {
        Process cmdProcess = null;
        BufferedReader reader = null;
        String dnsIP = "";
        try {
            cmdProcess = Runtime.getRuntime().exec("getprop net.dns1");
            reader = new BufferedReader(new InputStreamReader(cmdProcess.getInputStream()));
            dnsIP = reader.readLine();
            return dnsIP;
        } catch (IOException e) {
            return null;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
            }
            cmdProcess.destroy();
        }
    }

    public static String getUUids() {
        return UUID.randomUUID().toString();
    }

    public static String getMyUUID() {
        if (isChekSelfPermission(Manifest.permission.READ_PHONE_STATE)) {
            return "";
        }
        final TelephonyManager tm = getTelephonyManager();
        final String tmDevice, tmSerial, androidId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tmDevice = "";
            tmSerial = "";
        } else {
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
        }
        androidId = "" + android.provider.Settings.Secure.getString(UtilsApp.getApp().getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }


    /**
     * @param slotId slotId为卡槽Id，它的值为 0、1；
     * @return
     */
    public static String getIMEI(int slotId) {
        try {
            TelephonyManager manager = getTelephonyManager();
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei = (String) method.invoke(manager, slotId);
            return imei;
        } catch (Exception e) {
            return "";
        }
    }

    public static void getGaid() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    gaid = AdvertisingIdClient.getGoogleAdId(UtilsApp.getApp());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static String getUiModeType() {
        UiModeManager mUiModeManager = (UiModeManager) UtilsApp.getApp().getSystemService(Context.UI_MODE_SERVICE);
        int uiMode = mUiModeManager.getCurrentModeType();
        switch (uiMode) {
            case UI_MODE_TYPE_UNDEFINED:
                return "UI_MODE_TYPE_UNDEFINED";
            case UI_MODE_TYPE_NORMAL:
                return "UI_MODE_TYPE_NORMAL";
            case UI_MODE_TYPE_DESK:
                return "UI_MODE_TYPE_DESK";
            case UI_MODE_TYPE_CAR:
                return "UI_MODE_TYPE_CAR";
            case UI_MODE_TYPE_TELEVISION:
                return "UI_MODE_TYPE_TELEVISION";
            case UI_MODE_TYPE_APPLIANCE:
                return "UI_MODE_TYPE_APPLIANCE";
            case UI_MODE_TYPE_WATCH:
                return "UI_MODE_TYPE_WATCH";
            case UI_MODE_TYPE_VR_HEADSET:
                return "UI_MODE_TYPE_VR_HEADSET";
            default:
                return Integer.toString(uiMode);
        }
    }

    private static TelephonyManager getTelephonyManager() {
        return (TelephonyManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SERVICE);
    }


}