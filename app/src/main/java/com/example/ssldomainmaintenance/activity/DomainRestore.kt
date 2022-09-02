package com.example.ssldomainmaintenance.activity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ssldomainmaintenance.MyAdapter1
import com.example.ssldomainmaintenance.R
import com.example.ssldomainmaintenance.first.RestoreAdapter
import kotlinx.android.synthetic.main.activity_domain_restore.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://192.168.1.172:5001/"
class DomainRestore : AppCompatActivity() {

    lateinit var myAdapter:RestoreAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_domain_restore)


        restore_recycler.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        restore_recycler.layoutManager = linearLayoutManager
        getMyData()
    }


    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(delete::class.java)
        val retrofitData = retrofitBuilder.getdata()
        retrofitData.enqueue(object : Callback<List<restoreItem>?> {
            override fun onResponse(
                call: Call<List<restoreItem>?>,
                response: Response<List<restoreItem>?>
            ) {
                val responseBody = response.body()!!
                myAdapter= RestoreAdapter(baseContext,responseBody)
                myAdapter.notifyDataSetChanged()
                restore_recycler.adapter = myAdapter
                myAdapter.setOnClicklistener(object :RestoreAdapter.onItemClicklistener{
                    override fun onItemClick(dataItem: restoreItem) {
                        val intent = Intent(this@DomainRestore,DeleteRestoreActivity::class.java)
                        intent.putExtra("data1",dataItem)
                        intent.putExtra("data4",dataItem.domain_name)
                        startActivity(intent)
                    }
                })
            }

            override fun onFailure(call: Call<List<restoreItem>?>, t: Throwable) {
                Log.d(ContentValues.TAG,"onFailure"+t.message)
            }
        })



}
}