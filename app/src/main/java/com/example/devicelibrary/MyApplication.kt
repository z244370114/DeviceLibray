package com.example.devicelibrary

import android.app.Application
import com.zy.devicelibrary.UtilsApp

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        UtilsApp.init(this)
    }

}