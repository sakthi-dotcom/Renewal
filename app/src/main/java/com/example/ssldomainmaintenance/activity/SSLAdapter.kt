package com.example.ssldomainmaintenance.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ssldomainmaintenance.R
import com.example.ssldomainmaintenance.first.RestoreAdapter
import kotlinx.android.synthetic.main.card_layout4.view.*
import java.text.SimpleDateFormat
import java.util.*

class SSLAdapter(val context: Context,val sslrestorelist:List<sslRestoreItem>):RecyclerView.Adapter<SSLAdapter.ViewHolder>(){

    private lateinit var myListener: onItemClicklistener

    interface onItemClicklistener {

        fun onItemClick(dataItem: sslRestoreItem)
    }

    fun setOnClicklistener(listener: onItemClicklistener) {

        myListener = listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.card_layout4,parent,false)
        return ViewHolder(itemView,myListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.ssl_name.text = sslrestorelist[position].name
        holder.expp_date.text = getDate(sslrestorelist[position].expires_on)
        holder.itemView.setOnClickListener {
            myListener .onItemClick(sslrestorelist[position])
        }
    }

    override fun getItemCount(): Int {
        return sslrestorelist.size
    }


    fun getDate(timestamp: Long): String {
        val time = Date(timestamp * 1000)
        val sdf = SimpleDateFormat("d MMM yyyy")
        return sdf.format(time)
    }
    class ViewHolder(itemView: View,listener: SSLAdapter.onItemClicklistener):RecyclerView.ViewHolder(itemView){
        var ssl_name:TextView
        var expp_date:TextView
        init {
            ssl_name = itemView.nameDelete1
            expp_date = itemView.text2
        }

    }
}