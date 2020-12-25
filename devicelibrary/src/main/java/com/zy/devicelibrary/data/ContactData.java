package com.zy.devicelibrary.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.zy.devicelibrary.UtilsApp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactData implements Serializable {

    public static long serialVersionUID = 4290239387652332756L;

    public List<ContactInfo> list = new ArrayList<>();

    public static class ContactInfo {

        public static long serialVersionUID = 4120239387652332756L;

        public String contact_display_name = "";
        public String number = "";
        public long up_time;
        public long last_time_contacted;
        public int times_contacted;
        public int starred;
        public String email = "";
    }

    public static ContactData getContactList(ContactData contactData) {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        ContentResolver contentResolver = UtilsApp.getApp().getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor query = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null, null);
            while (query.moveToNext()) {
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.contact_display_name = query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contactInfo.number = query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactInfo.up_time = query.getLong(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_LAST_UPDATED_TIMESTAMP));
                contactInfo.last_time_contacted = query.getLong(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LAST_TIME_CONTACTED));
                contactInfo.times_contacted = query.getInt(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED));
                contactInfo.starred = query.getInt(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED));
                Cursor emails = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
                while (emails.moveToNext()) {
                    contactInfo.email = emails.getString(emails.getColumnIndex(
                            ContactsContract.CommonDataKinds.Email.DATA));
                }
                contactData.list.add(contactInfo);
            }
            if (!query.isClosed()) {
                query.close();
            }
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return contactData;
    }

    public static ContactData getContactList1(ContactData contactData) {
        long startTime = System.currentTimeMillis(); //起始时间
        Cursor cursor = null;
        try {
            ContentResolver contentResolver = UtilsApp.getApp().getContentResolver();
            cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.contact_display_name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contactInfo.number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactInfo.up_time = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_LAST_UPDATED_TIMESTAMP));
                contactInfo.last_time_contacted = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LAST_TIME_CONTACTED));
                contactInfo.times_contacted = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED));
                contactInfo.starred = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED));
                contactData.list.add(contactInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        long endTime = System.currentTimeMillis(); //结束时间
        long runTime = endTime - startTime;
        System.out.println("runTime = " + runTime);
        return contactData;
    }


}
