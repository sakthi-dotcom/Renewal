package com.example.ssldomainmaintenance.fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ssldomainmaintenance.*
import com.example.ssldomainmaintenance.activity.*
import kotlinx.android.synthetic.main.fragment_domain.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL1 = "http://192.168.1.172:5001/"
class DomainFragment : Fragment() {
    lateinit var mAdapter: MyAdapter1
    lateinit var linearLayoutManager:  LinearLayoutManager
    lateinit var recyclerView: RecyclerView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view1 = inflater.inflate(R.layout.fragment_domain, container, false)
        //var deleteButton = view1.findViewById<ImageButton>(R.id.imageBut)

        view1.floatBtn2.setOnClickListener {
            val intent = Intent(requireActivity(),MainActivity7::class.java)
            startActivity(intent)
        }

        view1.floatBtn3.setOnClickListener {
            val i = Intent(requireActivity(),DomainRestore::class.java)
            startActivity(i)
        }
        return view1


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.domain_recyc)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
        getMyData()
    }


    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL1)
            .build()
            .create(domainInterface::class.java)
        val retrofitData = retrofitBuilder.getdata()

        retrofitData.enqueue(object : Callback <List<domainDataItem>?> {
            override fun onResponse(call: Call<List<domainDataItem>?>, response: Response<List<domainDataItem>?>)
            {
                val responseBody = response.body()!!
                mAdapter = MyAdapter1(requireContext(),responseBody)
                recyclerView.adapter = mAdapter
                mAdapter.setOnClicklistener(object :MyAdapter1.onItemClicklistener{
                    override fun onItemClick(dataItem: domainDataItem) {
                        val intent = Intent(requireContext(),MainActivity4::class.java)
                        intent.putExtra("data1",dataItem)
                        intent.putExtra("data4",dataItem.domain_name)
                        startActivity(intent)
                    }
                })
            }
            override fun onFailure(call: Call<List<domainDataItem>?>, t: Throwable) {
                Log.d(ContentValues.TAG, "onFailure :" + t.message)
            }
        })
    }
}