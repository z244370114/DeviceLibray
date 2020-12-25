package com.zy.devicelibrary.utils;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.zy.devicelibrary.UtilsApp;
import com.zy.devicelibrary.data.NetWorkData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class NetWorkUtils {

    /**
     * 获取本机WIFI设备详细信息
     *
     * @param wifiInfos
     * @return
     */
    public static NetWorkData getNetWorkInfo(NetWorkData wifiInfos) {
        WifiManager mWifiManager = (WifiManager) UtilsApp.getApp().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = mWifiManager.getConnectionInfo();
        wifiInfos.current_wifi.bssid = info.getBSSID();
        String ssid = info.getSSID().replace("\"", "");
        wifiInfos.current_wifi.name = ssid;
        wifiInfos.current_wifi.ssid = ssid;
        wifiInfos.current_wifi.mac = info.getMacAddress();
        wifiInfos.ip = int2ip(info.getIpAddress());
        wifiInfos.configured_wifi.addAll(getAroundWifiDeciceInfo());
        return wifiInfos;
    }

    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 搜索到的周边WIFI信号信息
     *
     * @return
     */
    public static List<NetWorkData.NetWorkInfo> getAroundWifiDeciceInfo() {
        StringBuffer sInfo = new StringBuffer();
        WifiManager mWifiManager = (WifiManager) UtilsApp.getApp().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanResults = mWifiManager.getScanResults();//搜索到的设备列表
        List<NetWorkData.NetWorkInfo> wifiLists = new ArrayList<>();
        for (ScanResult scanResult : scanResults) {
            wifiLists.add(new NetWorkData.NetWorkInfo(scanResult.BSSID, scanResult.SSID, scanResult.SSID));
        }
        return wifiLists;
    }

    /**
     * 获取MAC地址
     *
     * @return
     */
    public static String getMacAddress() {
        String mac = "02:00:00:00:00:00";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault();
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMacAddresss();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = getMacFromHardware();
        }
        return mac;
    }


    /**
     * Android  6.0 之前（不包括6.0）
     * 必须的权限  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     *
     * @return
     */
    private static String getMacDefault() {
        String mac = "02:00:00:00:00:00";

        WifiManager wifi = (WifiManager) UtilsApp.getApp().getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return mac;
        }
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
        }
        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }

    /**
     * Android 6.0（包括） - Android 7.0（不包括）
     *
     * @return
     */
    private static String getMacAddresss() {
        String WifiAddress = "02:00:00:00:00:00";
        try {
            WifiAddress = new BufferedReader(new FileReader(new File("/sys/class/net/wlan0/address"))).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return WifiAddress;
    }


    /**
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET" />
     *
     * @return
     */
    private static String getMacFromHardware() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }


    public static String getBluetoothMac() {
        String bluetoothAddress = "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            bluetoothAddress = Settings.Secure.getString(UtilsApp.getApp().getContentResolver(), "bluetooth_address");
        } else {
            bluetoothAddress = getBluetoothAddressSdk23(BluetoothAdapter.getDefaultAdapter());
        }
        return bluetoothAddress;
    }

    @TargetApi(23)
    public static String getBluetoothAddressSdk23(BluetoothAdapter adapter) {
        if (adapter == null) return null;
        Class<? extends BluetoothAdapter> btAdapterClass = adapter.getClass();
        try {
            Class<?> btClass = Class.forName("android.bluetooth.IBluetooth");
            Field bluetooth = btAdapterClass.getDeclaredField("mService");
            bluetooth.setAccessible(true);
            Method btAddress = btClass.getMethod("getAddress");
            btAddress.setAccessible(true);
            return (String) btAddress.invoke(bluetooth.get(adapter));
        } catch (Exception e) {
            return "";
        }
    }
}
