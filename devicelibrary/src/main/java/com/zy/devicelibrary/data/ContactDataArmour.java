package com.zy.devicelibrary.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.zy.devicelibrary.UtilsApp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactDataArmour implements Serializable {

    public static long serialVersionUID = 4290939387652332756L;

    public String name = "";
    public String mobile = "";
    public String updated_time;
    public String lastTimeContacted;
    public int timesContacted;
    public int starred;
    public String email = "";

    public static List<ContactDataArmour> getContactList(List<ContactDataArmour> contactDataArmourList) {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        ContentResolver contentResolver = UtilsApp.getApp().getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor query = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null, null);
            while (query.moveToNext()) {
                ContactDataArmour contactInfo = new ContactDataArmour();
                contactInfo.name = query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contactInfo.mobile = query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactInfo.updated_time = query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_LAST_UPDATED_TIMESTAMP));
                contactInfo.lastTimeContacted = query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LAST_TIME_CONTACTED));
                contactInfo.timesContacted = query.getInt(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED));
                contactInfo.starred = query.getInt(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED));
                Cursor emails = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
                while (emails.moveToNext()) {
                    contactInfo.email = emails.getString(emails.getColumnIndex(
                            ContactsContract.CommonDataKinds.Email.DATA));
                }
                contactDataArmourList.add(contactInfo);
            }
            if (!query.isClosed()) {
                query.close();
            }
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }
        return contactDataArmourList;
    }

    public static List<ContactDataArmour> getContactList1() {
        List<ContactDataArmour> contactDataArmourList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        Cursor cursor = null;
        try {
            ContentResolver contentResolver = UtilsApp.getApp().getContentResolver();
            cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null, null);
            while (cursor.moveToNext()) {
                ContactDataArmour contactInfo = new ContactDataArmour();
                contactInfo.name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contactInfo.mobile = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactInfo.updated_time = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_LAST_UPDATED_TIMESTAMP));
                contactInfo.lastTimeContacted = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LAST_TIME_CONTACTED));
                contactInfo.timesContacted = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED));
                contactInfo.starred = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED));
                contactDataArmourList.add(contactInfo);
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
        return contactDataArmourList;
    }


    public static List<ContactDataArmour> getContactListActivity(List<ContactDataArmour> contactDataArmourList) {
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
                ContactDataArmour contactInfo = new ContactDataArmour();
                contactInfo.name = name;
                contactInfo.mobile = number;
                contactDataArmourList.add(contactInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return contactDataArmourList;
    }


}
