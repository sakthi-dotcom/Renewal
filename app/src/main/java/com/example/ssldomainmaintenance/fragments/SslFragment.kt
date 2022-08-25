package com.example.ssldomainmaintenance.fragments


import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ssldomainmaintenance.MyAdapter
import com.example.ssldomainmaintenance.R
import com.example.ssldomainmaintenance.activity.MainActivity2
import com.example.ssldomainmaintenance.activity.MainActivity6
import com.example.ssldomainmaintenance.activity.sslDataItem
import com.example.ssldomainmaintenance.activity.sslInterface
import kotlinx.android.synthetic.main.fragment_ssl.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "http://192.168.1.172:5001/"


class SslFragment : Fragment() {

    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager:  LinearLayoutManager
    lateinit var recyclerView: RecyclerView



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_ssl, container, false)
        view.floatBtn1.setOnClickListener {
            startActivity(Intent(requireActivity(),MainActivity6::class.java))
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.ssl_recyc)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
        getMyData()

    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(sslInterface::class.java)
        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<sslDataItem>?> {
            override fun onResponse(call: Call<List<sslDataItem>?>, response: Response<List<sslDataItem>?>) {

                    val responseBody = response.body()!!
                    myAdapter = MyAdapter(requireContext(), responseBody)
                    recyclerView.adapter = myAdapter
                    myAdapter.setOnItemClickListener(object : MyAdapter.onItemClickListener {
                        override fun onItemClick(dataItem: sslDataItem) {
                            val intent = Intent(requireContext(), MainActivity2::class.java)
                            intent.putExtra("data", dataItem)
                            intent.putExtra("data5",dataItem.name)
                            startActivity(intent)
                        }
                    })
                }

            override fun onFailure(call: Call<List<sslDataItem>?>, t: Throwable) {
                d(ContentValues.TAG, "onFailure :" + t.message)
            }
        })
    }

}