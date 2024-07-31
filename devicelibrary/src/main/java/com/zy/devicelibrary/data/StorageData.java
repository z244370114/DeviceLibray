package com.zy.devicelibrary.data;

import com.zy.devicelibrary.utils.FileUtils;
import com.zy.devicelibrary.utils.StorageClassSizeUtils;

import static com.zy.devicelibrary.utils.OtherUtils.getAvailMemory;
import static com.zy.devicelibrary.utils.OtherUtils.getContactGroupCount;
import static com.zy.devicelibrary.utils.OtherUtils.getExternalAvailableSize;
import static com.zy.devicelibrary.utils.OtherUtils.getExternalTotalSize;
import static com.zy.devicelibrary.utils.OtherUtils.getInternalAvailableSize;
import static com.zy.devicelibrary.utils.OtherUtils.getInternalTotalSize;
import static com.zy.devicelibrary.utils.OtherUtils.getTotalMemory;

import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class StorageData {

    public long ram_total_size;
    public long ram_usable_size;
    public long internal_storage_total;
    public long internal_storage_usable;
    public long memory_card_size;
    public long memory_card_size_use;
    public long music_size;
    public long podcasts_size;
    public long ringtones_size;
    public long alarms_size;
    public long notifications_size;
    public long pictures_size;
    public long movies_size;
    public long download_size;
    public long dcim_size;
    public long documents_size;
    public long screenshots_size;
    public long audiobooks_size;

    {
        ram_total_size = getTotalMemory();
        ram_usable_size = getAvailMemory();
        internal_storage_total = getInternalTotalSize();
        internal_storage_usable = getInternalAvailableSize();
        StorageClassSizeUtils instance = StorageClassSizeUtils.INSTANCE;
        music_size = instance.getDirectorySize(instance.getExternalPublicDirectory(Environment.DIRECTORY_MUSIC));
        podcasts_size = instance.getDirectorySize(instance.getExternalPublicDirectory(Environment.DIRECTORY_PODCASTS));
        ringtones_size = instance.getDirectorySize(instance.getExternalPublicDirectory(Environment.DIRECTORY_ALARMS));
        alarms_size = instance.getDirectorySize(instance.getExternalPublicDirectory(Environment.DIRECTORY_RINGTONES));
        notifications_size = instance.getDirectorySize(instance.getExternalPublicDirectory(Environment.DIRECTORY_NOTIFICATIONS));
        pictures_size = instance.getDirectorySize(instance.getExternalPublicDirectory(Environment.DIRECTORY_PICTURES));
        movies_size = instance.getDirectorySize(instance.getExternalPublicDirectory(Environment.DIRECTORY_MOVIES));
        download_size = instance.getDirectorySize(instance.getExternalPublicDirectory(Environment.DIRECTORY_DOWNLOADS));
        dcim_size = instance.getDirectorySize(instance.getExternalPublicDirectory(Environment.DIRECTORY_DCIM));
        documents_size = instance.getDirectorySize(instance.getExternalPublicDirectory(Environment.DIRECTORY_DOCUMENTS));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            screenshots_size = instance.getDirectorySize(instance.getExternalPublicDirectory(Environment.DIRECTORY_SCREENSHOTS));
            audiobooks_size = instance.getDirectorySize(instance.getExternalPublicDirectory(Environment.DIRECTORY_AUDIOBOOKS));
        }
//        memory_card_size = getExternalTotalSize();
//        memory_card_size_use = getExternalTotalSize() - getExternalAvailableSize();
    }

}