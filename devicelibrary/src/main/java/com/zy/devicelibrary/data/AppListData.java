package com.zy.devicelibrary.data;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.zy.devicelibrary.UtilsApp;
import com.zy.devicelibrary.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class AppListData {

    public List<AppListInfo> list = new ArrayList<>();

    public static class AppListInfo {

        public String app_name;
        public String package_name;
        public long in_time;
        public long up_time;
        public String version_name;
        public int version_code;
        public int flags;
        public int app_type;
        public List<String> special_permisson_list = new ArrayList<>();

    }

    public static AppListData getAppListData(AppListData appListData) {
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
            AppListInfo appListInfo = new AppListInfo();
            appListInfo.app_name = (String) packageInfo.applicationInfo.loadLabel(packageManager);
            appListInfo.package_name = packageInfo.packageName;
            appListInfo.in_time = packageInfo.firstInstallTime;
            appListInfo.up_time = packageInfo.lastUpdateTime;
            appListInfo.version_name = packageInfo.versionName;
            appListInfo.version_code = packageInfo.versionCode;
            appListInfo.app_type = ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? 1 : 0;
            appListInfo.flags = packageInfo.applicationInfo.flags;
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String[] requestedPermissions;
                try {
                    requestedPermissions = packageManager.getPackageInfo(packageInfo.packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;
                    if (requestedPermissions != null && requestedPermissions.length > 0) {
                        for (String pers : requestedPermissions) {
                            if (permiss.toString().contains(pers)) {
                                appListInfo.special_permisson_list.add(pers);
                            }
                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            appListData.list.add(appListInfo);
        }
        return appListData;
    }


}
