package com.example.devicelibrary

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.devicelibrary.FormatJson.format
import com.google.gson.Gson
import com.zy.devicelibrary.data.HardwareData


class MainActivity : AppCompatActivity() {

    private val permissions = arrayOf(
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.READ_PHONE_STATE
    )
    val REQUEST_CODE_CALL_PHONE = 50

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            requestPermissions(permissions, REQUEST_CODE_CALL_PHONE)
        }
        findViewById<Button>(R.id.button1).setOnClickListener {
            startActivity(Intent(this, ListDataActivity::class.java))
        }

        val tvDeviceInfos = findViewById<TextView>(R.id.tvDeviceInfos)


        tvDeviceInfos.text = format(Gson().toJson(HardwareData()))
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {


    }
}