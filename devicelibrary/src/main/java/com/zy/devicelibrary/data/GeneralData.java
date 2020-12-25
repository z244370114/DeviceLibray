package com.zy.devicelibrary.data;

import android.os.Build;

import com.zy.devicelibrary.utils.GeneralUtils;
import com.zy.devicelibrary.utils.LanguageUtils;
import com.zy.devicelibrary.utils.NetWorkUtils;
import com.zy.devicelibrary.utils.OtherUtils;

import static com.zy.devicelibrary.utils.FileUtils.getSDDeviceTxt;
import static com.zy.devicelibrary.utils.NetWorkUtils.getMacAddress;

public class GeneralData {


    /**
     * deviceid : 860988036722077
     * and_id : 1b065e245158d574
     * gaid : 9b65fd82-a170-437c-8446-cc913ab9a56a
     * network_operator_name :
     * network_operator :
     * network_type : WIFI
     * phone_type : GSM
     * phone_number :
     * mcc : 0
     * mnc : 0
     * locale_iso_3_language : zho
     * locale_iso_3_country : CHN
     * time_zone_id : Asia/Shanghai
     * locale_display_language : 中文
     * imsi :
     * cid : 2147483647
     * dns :
     * uuid : 00000000-65c7-9a56-c5f2-80650033c587
     * imei : 860988036722077
     * mac : D8:9A:34:26:13:AA
     * language : zh
     * ui_mode_type : UI_MODE_TYPE_APPLIANCE
     * security_patch : 2018-12-05
     */

    public String and_id;
    public String gaid;
    public String network_operator_name;
    public String network_operator;
    public String network_type;
    public String phone_type;
    public String mcc;
    public String bluetooth_mac;
    public String mnc;
    public String locale_iso_3_language;
    public String locale_iso_3_country;
    public String time_zone_id;
    public String locale_display_language;
    public String cid;
    public String dns;
    public String uuid;
    public int slot_count;
    public String meid;
    public String imei1;
    public String imei2;
    public String mac;
    public String language;
    public String ui_mode_type;
    public String security_patch;

    {
        and_id = GeneralUtils.getAndroidID();
        gaid = GeneralUtils.gaid;
        network_operator_name = GeneralUtils.getNetworkOperatorName();
        network_operator = GeneralUtils.getNetworkOperator();
        network_type = GeneralUtils.getNetworkType();
        phone_type = GeneralUtils.getPhoneType();
        mcc = GeneralUtils.getMcc();
        mnc = GeneralUtils.getMnc();
        cid = GeneralUtils.getCidNumbers();
        dns = GeneralUtils.getLocalDNS();
        uuid = GeneralUtils.getMyUUID();
        slot_count = OtherUtils.getPhoneSimCount();
        meid = GeneralUtils.getMeid();
        locale_iso_3_country = LanguageUtils.getSystemLanguage().getISO3Country();
        locale_iso_3_language = LanguageUtils.getSystemLanguage().getISO3Language();
        locale_display_language = LanguageUtils.getSystemLanguage().getDisplayLanguage();
        language = LanguageUtils.getSystemLanguage().getLanguage();
        imei1 = GeneralUtils.getIMEI(0);
        imei2 = GeneralUtils.getIMEI(1);
        ui_mode_type = GeneralUtils.getUiModeType();
        time_zone_id = LanguageUtils.getCurrentTimeZone();
        mac = getMacAddress();
        bluetooth_mac = NetWorkUtils.getBluetoothMac();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            security_patch = Build.VERSION.SECURITY_PATCH;
        }
    }

}
