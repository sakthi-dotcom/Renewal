package com.example.ssldomainmaintenance.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ssldomainmaintenance.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        var pass: String = ""
        val up_button: Button = findViewById(R.id.updateButton2)
        val del_button: Button = findViewById(R.id.deleteButton)
        //  val heading:TextView = findViewById(R.id.topic1)
        val subtit: TextView = findViewById(R.id.domain_issuer)
        val is_to: TextView = findViewById(R.id.domain_issued)
        val auto_renew1: TextView = findViewById(R.id.domain_renew)
        val i_on: TextView = findViewById(R.id.domain_issdon)
        val e_on: TextView = findViewById(R.id.doamin_expon)
        val bundle: Bundle? = intent.extras

        val final = intent.getStringExtra("data4")
        val con_final = final.toString()


        bundle?.let {
            val dataItem = it.getSerializable("data1") as domainDataItem
            supportActionBar?.title = dataItem.domain_name
            pass = dataItem.domain_name
            subtit.text = dataItem.issued_by
            is_to.text = dataItem.issued_to
            i_on.text = getDate(dataItem.issued_on)
            e_on.text = getDate(dataItem.expires_on)


            if (dataItem.auto_renewal_enabled == true) {
                auto_renew1.setText("Yes")
            } else
                auto_renew1.setText("No")

        }

        up_button.setOnClickListener() {
            val intent = Intent(this, MainActivity5::class.java)
            intent.putExtra("data2", pass)
            startActivity(intent)
        }
        del_button.setOnClickListener {
            val eBuilder = AlertDialog.Builder(this)
            eBuilder.setTitle("Delete")
            eBuilder.setIcon(R.drawable.ic_baseline_warning_24)
            eBuilder.setMessage("Are you sure want to delete? ")
            eBuilder.setPositiveButton("Yes"){
                    Dialog,which->deleteData(con_final)
                Toast.makeText(this,"Deleted Successfully",Toast.LENGTH_SHORT).show()
                finish()
            }
            eBuilder.setNegativeButton("No"){
                    Dialog,which->
            }
            val createBuild:AlertDialog = eBuilder.create()
            createBuild.show()
        }

    }

    private fun deleteData(con_final: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.172:5001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(AddDomain::class.java)
        val call: Call<domainDelete> = retrofitAPI.deleteDomain(Name = con_final)
        call.enqueue(object : Callback<domainDelete?> {
            override fun onResponse(call: Call<domainDelete?>, response: Response<domainDelete?>) {
                val out = Intent(this@MainActivity4,MainActivity::class.java)
                startActivity(out)
            }
            override fun onFailure(call: Call<domainDelete?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getDate(timestamp: Long): String {
        val time = Date(timestamp * 1000)
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return sdf.format(time)
    }
}