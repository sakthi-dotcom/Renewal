package com.example.ssldomainmaintenance.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.ssldomainmaintenance.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var pass:String =""
        val button:Button = findViewById(R.id.updateButton1)
        val dButton:Button = findViewById(R.id.deleteButton1)
       // val heading:TextView = findViewById(R.id.topic)
        val subtit:TextView = findViewById(R.id.ssl_issuer)
        val is_to:TextView =findViewById(R.id.ssl_issued)
        val c_type:TextView =findViewById(R.id.ssl_certi)
        val auto_renew:TextView =findViewById(R.id.ssl_renew)
        val is_on:TextView =findViewById(R.id.ssl_issdon)
        val ex_on:TextView =findViewById(R.id.ssl_expon)
        val bundle:Bundle? = intent.extras

        val final = intent.getStringExtra("data5")
        //  Log.d("passing 4",final.toString())
        val con_final = final.toString()
       bundle?.let {
            val dataItem = it.getSerializable("data") as sslDataItem
            supportActionBar?.title = dataItem.name
         //   heading.text = dataItem.name
            pass  = dataItem.name
            subtit.text = dataItem.issued_by
            is_to.text = dataItem.issued_to
            c_type.text = dataItem.certificate_type
            is_on.text =getDate(dataItem.issued_on)
            ex_on.text =getDate(dataItem.expires_on)

            if (dataItem.auto_renewal_enabled == true ){
                auto_renew.setText("Yes")
            }
            else
                auto_renew.setText("No")
        }

        button.setOnClickListener {
            val intent = Intent(this, MainActivity8::class.java)
            intent.putExtra("data3",pass)
            startActivity(intent)
        }
        dButton.setOnClickListener {
            val eBuilder = AlertDialog.Builder(this)
            eBuilder.setTitle("Delete")
            eBuilder.setIcon(R.drawable.ic_baseline_warning_24)
            eBuilder.setMessage("Are you sure want to delete? ")
            eBuilder.setPositiveButton("Yes"){
                    Dialog,which->deleteData1(con_final)

                Toast.makeText(this,"Deleted Successfully",Toast.LENGTH_SHORT).show()
            }
            eBuilder.setNegativeButton("No"){
                    Dialog,which->
            }
            val createBuild: AlertDialog = eBuilder.create()
            createBuild.show()
        }

    }

    private fun deleteData1(con_final:String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.172:5001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(AddSsl::class.java)
        val call: Call<sslDelete> = retrofitAPI.deleteSsl(Name = con_final)
        call.enqueue(object : Callback<sslDelete?> {
            override fun onResponse(call: Call<sslDelete?>, response: Response<sslDelete?>) {
                Toast.makeText(this@MainActivity2,"Deleted Successfully!!",Toast.LENGTH_SHORT).show()
                val out = Intent(this@MainActivity2,MainActivity::class.java)
                startActivity(out)
            }
            override fun onFailure(call: Call<sslDelete?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getDate(timestamp: Long) :String {
        val  time =  Date(timestamp * 1000)
        val sdf =  SimpleDateFormat("dd/MM/yyyy")
        return sdf.format(time)
    }
}