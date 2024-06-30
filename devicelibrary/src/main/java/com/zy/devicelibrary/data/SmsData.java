package com.zy.devicelibrary.data;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.zy.devicelibrary.UtilsApp;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信
 */
public class SmsData {


    public String id;
    public String address;
    public String body;
    public long date;

    public int type;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public List<SmsData> readSmsList() {
        List<SmsData> smsDataList = new ArrayList<>();
        Uri smsUri = Telephony.Sms.CONTENT_URI;
        String[] projection = new String[]{
                Telephony.Sms._ID,
                Telephony.Sms.ADDRESS,
                Telephony.Sms.BODY,
                Telephony.Sms.DATE
        };
        Cursor cursor = UtilsApp.getApp().getContentResolver().query(smsUri, projection, null, null, Telephony.Sms.DEFAULT_SORT_ORDER);
        if (cursor != null && cursor.moveToFirst()) {
            SmsData smsData = new SmsData();
            do {
                smsData.id = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms._ID));
                //手机号
                smsData.address = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                //短信内容
                smsData.body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY));
                //时间
                smsData.date = cursor.getLong(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE));
                //1.接受 2.发送
                smsData.type = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Sms.TYPE));
                smsDataList.add(smsData);
                Log.d("SMS", "ID: " + id + ", Address: " + address + ", Body: " + body + ", Date: " + date);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return smsDataList;
    }

}
