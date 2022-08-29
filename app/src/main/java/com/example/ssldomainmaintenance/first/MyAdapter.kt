package com.example.ssldomainmaintenance

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ssldomainmaintenance.activity.sslDataItem
import java.text.SimpleDateFormat
import java.util.*

class MyAdapter(val context: Context, val ssList: List<sslDataItem>):RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private lateinit var slistener:onItemClickListener

    interface onItemClickListener
    {
        fun onItemClick(dataItem: sslDataItem)
    }
    fun setOnItemClickListener(listener: onItemClickListener){

        slistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(itemView,slistener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = ssList[position].name
        holder.sub_title.text = getDate(ssList[position].expires_on)
        holder.itemView.setOnClickListener {
            slistener .onItemClick(ssList[position])
        }
    }

    override fun getItemCount(): Int {
        return ssList.size
    }

    class ViewHolder(itemView: View,listener: onItemClickListener):RecyclerView.ViewHolder(itemView) {
        var title:TextView
        var sub_title:TextView
        init {
            title = itemView.findViewById(R.id.item_title)
            sub_title = itemView.findViewById(R.id.expDisplay)
        }
    }

    fun getDate(timestamp: Long): String {
        val time = Date(timestamp * 1000)
        val sdf = SimpleDateFormat("d MMM yyyy")
        return sdf.format(time)
    }
}