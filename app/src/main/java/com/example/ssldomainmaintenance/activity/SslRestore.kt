package com.example.ssldomainmaintenance.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ssldomainmaintenance.DeleteRestoreActivitySsl
import com.example.ssldomainmaintenance.R
import com.example.ssldomainmaintenance.first.RestoreAdapter
import kotlinx.android.synthetic.main.activity_domain_restore.*
import kotlinx.android.synthetic.main.activity_ssl_restore.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val url = "http://192.168.1.172:5001/"
class SslRestore : AppCompatActivity() {

    lateinit var ssl_adapter :SSLAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ssl_restore)
        sslRecycler.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        sslRecycler.layoutManager = linearLayoutManager
        getmydata()


    }

    private fun getmydata() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
            .create(delete::class.java)
        val retrofitData = retrofitBuilder.getData1()
        retrofitData.enqueue(object : Callback<List<sslRestoreItem>?> {
            override fun onResponse(
                call: Call<List<sslRestoreItem>?>,
                response: Response<List<sslRestoreItem>?>
            ) {
                val responseBody = response.body()!!
                ssl_adapter = SSLAdapter(baseContext,responseBody)
                ssl_adapter.notifyDataSetChanged()
                sslRecycler.adapter = ssl_adapter
                ssl_adapter.setOnClicklistener(object :SSLAdapter.onItemClicklistener{
                    override fun onItemClick(dataItem: sslRestoreItem) {
                        val intent = Intent(this@SslRestore,DeleteRestoreActivitySsl::class.java)
                        startActivity(intent)
                        intent.putExtra("data1",dataItem)
                        intent.putExtra("data4",dataItem.name)
                        startActivity(intent)
                    }
                })
            }

            override fun onFailure(call: Call<List<sslRestoreItem>?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }


}