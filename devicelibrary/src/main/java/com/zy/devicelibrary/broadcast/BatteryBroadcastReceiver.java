package com.zy.devicelibrary.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zy.devicelibrary.UtilsApp;
import com.zy.devicelibrary.data.BatteryStatusData;
import com.zy.devicelibrary.utils.FileUtils;

import static android.os.BatteryManager.BATTERY_HEALTH_DEAD;
import static android.os.BatteryManager.BATTERY_HEALTH_GOOD;
import static android.os.BatteryManager.BATTERY_HEALTH_OVERHEAT;
import static android.os.BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE;
import static android.os.BatteryManager.BATTERY_HEALTH_UNKNOWN;
import static android.os.BatteryManager.BATTERY_PLUGGED_AC;
import static android.os.BatteryManager.BATTERY_PLUGGED_USB;
import static android.os.BatteryManager.BATTERY_PLUGGED_WIRELESS;
import static android.os.BatteryManager.BATTERY_STATUS_CHARGING;
import static android.os.BatteryManager.BATTERY_STATUS_DISCHARGING;
import static android.os.BatteryManager.BATTERY_STATUS_FULL;
import static android.os.BatteryManager.BATTERY_STATUS_NOT_CHARGING;
import static android.os.BatteryManager.BATTERY_STATUS_UNKNOWN;
import static com.zy.devicelibrary.utils.OtherUtils.getBrightness;
import static com.zy.devicelibrary.utils.OtherUtils.getDeviceid;

public class BatteryBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        BatteryStatusData batteryStatusData = new BatteryStatusData();
        String action = intent.getAction();
        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
//            String charge_type = "";
            int charge_type = 0;
            switch (intent.getIntExtra("status", BATTERY_STATUS_UNKNOWN)) {
                case BATTERY_STATUS_CHARGING:
//                    charge_type = "充电状态";
                    charge_type = BATTERY_STATUS_CHARGING;
                    break;
                case BATTERY_STATUS_DISCHARGING:
//                    charge_type = "放电状态";
                    charge_type = BATTERY_STATUS_DISCHARGING;
                    break;
                case BATTERY_STATUS_NOT_CHARGING:
//                    charge_type = "未充电";
                    charge_type = BATTERY_STATUS_NOT_CHARGING;
                    break;
                case BATTERY_STATUS_FULL:
//                    charge_type = "充满电";
                    charge_type = BATTERY_STATUS_FULL;
                    break;
                case BATTERY_STATUS_UNKNOWN:
//                    charge_type = "未知道状态";
                    charge_type = BATTERY_STATUS_UNKNOWN;
                    break;
            }

//            String battery_health = "";
            int battery_health = 0;
            switch (intent.getIntExtra("health", BATTERY_HEALTH_UNKNOWN)) {
                case BATTERY_HEALTH_UNKNOWN:
//                    battery_health = "未知错误";
                    battery_health = BATTERY_HEALTH_UNKNOWN;
                    break;
                case BATTERY_HEALTH_GOOD:
//                    battery_health = "状态良好";
                    battery_health = BATTERY_HEALTH_GOOD;
                    break;
                case BATTERY_HEALTH_DEAD:
//                    battery_health = "电池没有电";
                    battery_health = BATTERY_HEALTH_DEAD;
                    break;
                case BATTERY_HEALTH_OVER_VOLTAGE:
//                    battery_health = "电池电压过高";
                    battery_health = BATTERY_HEALTH_OVER_VOLTAGE;
                    break;
                case BATTERY_HEALTH_OVERHEAT:
//                    battery_health = "电池过热";
                    battery_health = BATTERY_HEALTH_OVERHEAT;
                    break;
            }

//            String status = "";
            int status = 0;
            switch (intent.getIntExtra("plugged", 0)) {
                case BATTERY_PLUGGED_AC:
//                    status = "plugged ac";
                    status = BATTERY_PLUGGED_AC;
                    break;
                case BATTERY_PLUGGED_USB:
//                    status = "plugged usb";
                    status = BATTERY_PLUGGED_USB;
                    break;
                case BATTERY_PLUGGED_WIRELESS:
//                    status = "plugged wireless";
                    status = BATTERY_PLUGGED_WIRELESS;
                    break;
            }
            batteryStatusData.charge_type = status;
            batteryStatusData.battery_pct = intent.getIntExtra("level", 0);    //目前电量
            batteryStatusData.battery_temperature = intent.getIntExtra("temperature", 0);  //电池温度
            batteryStatusData.is_charging = charge_type;
            batteryStatusData.battery_health = battery_health;
            batteryStatusData.screen_brightness = getBrightness();
//            batteryStatusData.device_id = getDeviceid();
            UtilsApp.batteryStatusData = batteryStatusData;
        }
    }
}
