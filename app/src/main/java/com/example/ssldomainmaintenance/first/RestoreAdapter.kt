package com.example.ssldomainmaintenance.first

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ssldomainmaintenance.MyAdapter1
import com.example.ssldomainmaintenance.R
import com.example.ssldomainmaintenance.activity.domainDataItem
import com.example.ssldomainmaintenance.activity.restoreItem
import java.text.SimpleDateFormat
import java.util.*


class RestoreAdapter(val context: Context, val restoreList: List<restoreItem>) :RecyclerView.Adapter<RestoreAdapter.ViewHolder>(){

    private lateinit var myListener: onItemClicklistener

    interface onItemClicklistener {

        fun onItemClick(dataItem: restoreItem)
    }

    fun setOnClicklistener(listener: onItemClicklistener) {

        myListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.card_layout3,parent,false)
        return ViewHolder(itemView,myListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.head.text = restoreList[position].domain_name
        holder.dat.text = getDate(restoreList[position].expires_on)
        holder.itemView.setOnClickListener {
            myListener .onItemClick(restoreList[position])
        }
    }

    override fun getItemCount(): Int {
        return restoreList.size
    }


    class ViewHolder(itemView:View,listener: RestoreAdapter.onItemClicklistener):RecyclerView.ViewHolder(itemView){
        var head: TextView
        var dat:TextView
        init {
            head = itemView.findViewById(R.id.nameDelete)
            dat =itemView.findViewById(R.id.txt2)

        }
    }
    fun getDate(timestamp: Long): String {
        val time = Date(timestamp * 1000)
        val sdf = SimpleDateFormat("d MMM yyyy")
        return sdf.format(time)
    }
}