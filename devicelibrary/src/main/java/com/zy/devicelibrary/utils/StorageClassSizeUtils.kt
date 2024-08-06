package com.zy.devicelibrary.utils

import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import com.zy.devicelibrary.UtilsApp
import java.io.File

class StorageClassSizeUtils {

    companion object{

//        @RequiresApi(Build.VERSION_CODES.Q)
//        var directoryList = arrayOf(
//            Environment.DIRECTORY_MUSIC,
//            Environment.DIRECTORY_PODCASTS,
//            Environment.DIRECTORY_ALARMS,
//            Environment.DIRECTORY_RINGTONES,
//            Environment.DIRECTORY_NOTIFICATIONS,
//            Environment.DIRECTORY_PICTURES,
//            Environment.DIRECTORY_MOVIES,
//            Environment.DIRECTORY_DOWNLOADS,
//            Environment.DIRECTORY_DCIM,
//            Environment.DIRECTORY_DOCUMENTS,
//            Environment.DIRECTORY_AUDIOBOOKS,
//        )

        val units = arrayOf("B", "KB", "MB", "GB", "TB")


        @RequiresApi(Build.VERSION_CODES.KITKAT)
        @JvmStatic
        fun calculateUsage() {
            val imageSize =
                getDirectorySize(getExternalPublicDirectory(Environment.DIRECTORY_PICTURES))
            val audioSize =
                getDirectorySize(getExternalPublicDirectory(Environment.DIRECTORY_MUSIC))
            val fileSize =
                getDirectorySize(getExternalPublicDirectory(Environment.DIRECTORY_DOWNLOADS))
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @JvmStatic
        fun getExternalPublicDirectory(type: String): File? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                UtilsApp.getApp().getExternalFilesDir(type)
            } else {
                Environment.getExternalStoragePublicDirectory(type)
            }
        }
        @JvmStatic
        fun getDirectorySize(dir: File?): Long {
            if (dir == null || !dir.exists()) return 0
            val listFiles = dir.listFiles()
            var size: Long = 0
            if (listFiles != null) {
                for (file in listFiles) {
                    size += if (file.isDirectory) {
                        getDirectorySize(file)
                    } else {
                        file.length()
                    }
                }

            }
            return size
        }

        @JvmStatic
        fun formatSize(size: Long): String {
            var unitIndex = 0
            var sizeInUnits = size.toDouble()
            while (sizeInUnits >= 1024 && unitIndex < units.size - 1) {
                sizeInUnits /= 1024.0
                unitIndex++
            }
            return "%.2f %s".format(sizeInUnits, units[unitIndex])
        }
    }

}