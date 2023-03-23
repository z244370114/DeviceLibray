package com.example.devicelibrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.devicelibrary.ListName.hardwareList
import com.google.gson.Gson
import com.zy.devicelibrary.data.HardwareData


class ListDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val map = JsonMap.getMap(Gson().toJson(HardwareData()))
        val list = mutableListOf<ItemData>()
        var i = 0
        map.forEach {
            val itemData = ItemData()
            itemData.cnName = hardwareList[i]
            itemData.ehName = it.key
            itemData.content = it.value.toString()
            list.add(itemData)
            i++
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
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
            holder.tvCnName.text = "(${content.cnName})"
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

}