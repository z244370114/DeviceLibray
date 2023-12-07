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
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
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
            val intent = Intent(this, ListDataActivity::class.java)
            intent.putExtra("type", "硬件")
            startActivity(intent)
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            val intent = Intent(this, ListDataActivity::class.java)
            intent.putExtra("type", "通用数据")
            startActivity(intent)
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            val intent = Intent(this, ListDataActivity::class.java)
            intent.putExtra("type", "SD卡界面")
            startActivity(intent)
        }
        findViewById<Button>(R.id.button4).setOnClickListener {
            val intent = Intent(this, ListDataActivity::class.java)
            intent.putExtra("type", "存储界面")
            startActivity(intent)
        }
        findViewById<Button>(R.id.button5).setOnClickListener {
            val intent = Intent(this, ListDataActivity::class.java)
            intent.putExtra("type", "其他数据界面")
            startActivity(intent)
        }
        findViewById<Button>(R.id.button6).setOnClickListener {
            val intent = Intent(this, ListDataActivity::class.java)
            intent.putExtra("type", "APP安装")
            startActivity(intent)
        }
        findViewById<Button>(R.id.button7).setOnClickListener {
            val intent = Intent(this, ListDataActivity::class.java)
            intent.putExtra("type", "联系人")
            startActivity(intent)
        }
        findViewById<Button>(R.id.button8).setOnClickListener {
            val intent = Intent(this, ListDataActivity::class.java)
            intent.putExtra("type", "媒体文件")
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

    }

}