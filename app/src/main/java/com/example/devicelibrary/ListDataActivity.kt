package com.example.devicelibrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.devicelibrary.ListName.generalList
import com.example.devicelibrary.ListName.hardwareList
import com.example.devicelibrary.ListName.mediaList
import com.example.devicelibrary.ListName.otherList
import com.example.devicelibrary.ListName.simList
import com.example.devicelibrary.ListName.storageList
import com.google.gson.Gson
import com.zy.devicelibrary.data.*
import com.zy.devicelibrary.utils.GeneralUtils
import com.zy.devicelibrary.utils.StorageQueryUtil


class ListDataActivity : AppCompatActivity() {


    private var appList: MutableList<AppListData.AppListInfo>? = null
    private var contactList: MutableList<ContactDataArmour>? = null
    private var map: MutableMap<String, Any>? = null
    private lateinit var recyclerView: RecyclerView
    var type = ""
    val list = mutableListOf<ItemData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data)
        type = intent.getStringExtra("type").toString()
        recyclerView = findViewById(R.id.recyclerView)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener {
                finish()
            }
            supportActionBar!!.title = type
        }
        initData()
    }

    private fun initData() {
        var lists: Array<String>? = null
        when (type) {
            "硬件" -> {
                map = JsonMap.getMap(Gson().toJson(HardwareData()))
                lists = hardwareList
            }

            "通用数据" -> {
                map = JsonMap.getMap(Gson().toJson(GeneralData()))
                lists = generalList
            }

            "SD卡界面" -> {
                map = JsonMap.getMap(Gson().toJson(GeneralUtils.getSimCardInfo()))
                lists = simList
            }

            "存储界面" -> {
                map = JsonMap.getMap(
                    Gson().toJson(
                        StorageQueryUtil.queryWithStorageManager(
                            StorageData()
                        )
                    )
                )
                lists = storageList
            }

            "其他数据界面" -> {
                map = JsonMap.getMap(Gson().toJson(OtherData()))
                lists = otherList
            }

            "APP安装" -> {
                appList = AppListData.getAppListData(AppListData()).list
            }

            "联系人" -> {
                contactList = ContactDataArmour.getContactList1()
            }

            "媒体文件" -> {
                map = JsonMap.getMap(Gson().toJson(MediaFilesData()))
                lists = mediaList
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        if (type == "联系人") {
            recyclerView.adapter = MyContactAdapter(contactList)
            return
        } else if (type == "APP安装") {
            recyclerView.adapter = MyAppAdapter(appList)
            return
        }
        var i = 0
        map?.forEach {
            val itemData = ItemData()
            itemData.cnName = lists!![i]
            itemData.ehName = it.key
            itemData.content = it.value.toString()
            list.add(itemData)
            i++
        }
        recyclerView.adapter = MyAdapter(list)
    }


    private class MyAdapter(private val dataList: List<ItemData>?) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val content = dataList!![position]
            holder.tvCnName.text = content.cnName
            holder.tvName.text = content.ehName
            holder.tvContent.text = content.content
        }

        override fun getItemCount(): Int {
            return dataList?.size ?: 0
        }

        internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvName: TextView
            val tvCnName: TextView
            val tvContent: TextView

            init {
                tvCnName = itemView.findViewById(R.id.tvCnName)
                tvName = itemView.findViewById(R.id.tvName)
                tvContent = itemView.findViewById(R.id.tvContent)
            }
        }
    }

    private class MyContactAdapter(private val dataList: List<ContactDataArmour>?) :
        RecyclerView.Adapter<MyContactAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val content = dataList!![position]
            holder.tvName.text = content.name
            holder.tvContent.text = content.mobile
        }

        override fun getItemCount(): Int {
            return dataList?.size ?: 0
        }

        internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvName: TextView
            val tvContent: TextView

            init {
                tvName = itemView.findViewById(R.id.tv_name)
                tvContent = itemView.findViewById(R.id.tv_phone)
            }
        }
    }

    private class MyAppAdapter(private val dataList: List<AppListData.AppListInfo>?) :
        RecyclerView.Adapter<MyAppAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val content = dataList!![position]
            holder.tvName.text = content.app_name
            holder.tvContent.text = content.version_name
            holder.ivPhoto.setImageDrawable(content.app_icon)
        }

        override fun getItemCount(): Int {
            return dataList?.size ?: 0
        }

        internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvName: TextView
            val tvContent: TextView
            val ivPhoto: ImageView

            init {
                ivPhoto = itemView.findViewById(R.id.iv_photo)
                tvName = itemView.findViewById(R.id.tv_name)
                tvContent = itemView.findViewById(R.id.tv_phone)
            }
        }
    }

}