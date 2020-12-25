package com.zy.devicelibrary.data;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.zy.devicelibrary.UtilsApp;

import java.util.ArrayList;
import java.util.List;

public class AppListDataArmour {

    public String appName;
    public String packageName;
    public long firstInstallTime;
    public long lastUpdateTime;
    public String versionName;
    public int versionCode;
    public int flags;
    public int appType;

    public List<String> specialPermissonList = new ArrayList<>();

    public static List<AppListDataArmour> getAppListData() {
        List<AppListDataArmour> appListDataList = new ArrayList<>();
        PackageManager packageManager = UtilsApp.getApp().getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        StringBuffer permiss = new StringBuffer();
        permiss.append("android.permission.ACCESS_MOCK_LOCATION")
                .append("android.permission.BIND_APPWIDGET")
                .append("android.permission.BLUETOOTH")
                .append("android.permission.BLUETOOTH_ADMIN")
                .append("android.permission.BRICK")
                .append("android.permission.BROADCAST_PACKAGE_REMOVED")
                .append("android.permission.CONTROL_LOCATION_UPDATES")
                .append("android.permission.NFC")
                .append("android.permission.READ_CALENDAR")
                .append("android.permission.READ_SMS")
                .append("android.permission.WRITE_SMS")
                .append("android.permission.RECEIVE_MMS")
                .append("android.permission.RECEIVE_SMS")
                .append("android.permission.SEND_SMS")
                .append("android.permission.RECORD_AUDIO")
                .append("android.permission.READ_CALL_LOG")
                .append("android.permission.WRITE_CONTACTS")
                .append("android.permission.WRITE_GSERVICES")
                .append("android.permission.WRITE_SECURE_SETTINGS");
        for (PackageInfo packageInfo : installedPackages) {
            AppListDataArmour appListData = new AppListDataArmour();
            appListData.appName = (String) packageInfo.applicationInfo.loadLabel(packageManager);
            appListData.packageName = packageInfo.packageName;
            appListData.firstInstallTime = packageInfo.firstInstallTime;
            appListData.lastUpdateTime = packageInfo.lastUpdateTime;
            appListData.versionName = packageInfo.versionName;
            appListData.versionCode = packageInfo.versionCode;
            appListData.appType = ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? 1 : 0;
            appListData.flags = packageInfo.applicationInfo.flags;
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                String[] requestedPermissions;
                try {
                    requestedPermissions = packageManager.getPackageInfo(packageInfo.packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;
                    if (requestedPermissions != null && requestedPermissions.length > 0) {
                        for (String pers : requestedPermissions) {
                            if (permiss.toString().contains(pers)) {
                                appListData.specialPermissonList.add(pers);
                            }
                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            appListDataList.add(appListData);
        }
        return appListDataList;
    }

}
