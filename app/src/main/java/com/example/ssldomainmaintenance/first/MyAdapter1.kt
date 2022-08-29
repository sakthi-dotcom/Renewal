package com.example.ssldomainmaintenance

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ssldomainmaintenance.activity.domainDataItem
import java.text.SimpleDateFormat
import java.util.*


//@Suppress("DEPRECATION")
class MyAdapter1(val context: Context, val domainList: List<domainDataItem>): RecyclerView.Adapter<MyAdapter1.MyViewHolder1>() {

    private lateinit var myListener: onItemClicklistener

    interface onItemClicklistener {

        fun onItemClick(dataItem: domainDataItem)
    }

    fun setOnClicklistener(listener: onItemClicklistener) {

        myListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder1 {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_layout2, parent, false)
        return MyViewHolder1(itemView, myListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder1, position: Int) {

        holder.title.text = domainList[position].domain_name
       holder.sub_title.text = getDate(domainList[position].expires_on)
        holder.itemView.setOnClickListener {
            myListener .onItemClick(domainList[position])
        }
    }

    override fun getItemCount(): Int {
        return domainList.size
    }

    class MyViewHolder1(itemView: View, listener: MyAdapter1.onItemClicklistener) : RecyclerView.ViewHolder(itemView) {

       var title:TextView
     var sub_title:TextView
        init {
            title = itemView.findViewById(R.id.item_title2)
           sub_title = itemView.findViewById(R.id.expDisplay1)
        }

    }
    fun getDate(timestamp: Long): String {
        val time = Date(timestamp * 1000)
        val sdf = SimpleDateFormat("d MMM yyyy")
        return sdf.format(time)
    }
}