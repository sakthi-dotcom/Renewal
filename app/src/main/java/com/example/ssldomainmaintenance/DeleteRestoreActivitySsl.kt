package com.example.ssldomainmaintenance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ssldomainmaintenance.activity.*
import com.example.ssldomainmaintenance.harddelete.permanentDelete
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class DeleteRestoreActivitySsl : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_restore_ssl)

        var pass: String = ""
        val restore: Button = findViewById(R.id.ssl_restore_btn)
        val clearSsl:Button = findViewById(R.id.harddltssl)
        val subtit: TextView = findViewById(R.id.rssl_issuer)
        val is_to: TextView = findViewById(R.id.rssl_issued)
        val is_on: TextView = findViewById(R.id.rssl_isson)
        val e_on: TextView = findViewById(R.id.rssl_exon)
        val auto_renew1: TextView = findViewById(R.id.rssl_arenew)


        val bundle: Bundle? = intent.extras
        val final = intent.getStringExtra("data4")
        val con_final = final.toString()

        bundle?.let {
            val dataItem = it.getSerializable("data1") as sslRestoreItem
            supportActionBar?.title = dataItem.name
            pass = dataItem.name
            subtit.text = dataItem.issued_by
            is_to.text = dataItem.issued_to
            is_on.text = getDate(dataItem.issued_on)
            e_on.text = getDate(dataItem.expires_on)
            if (dataItem.auto_renewal_enabled == true) {
                auto_renew1.setText("Yes")
            } else
                auto_renew1.setText("No")

        }
        restore.setOnClickListener {
            val eBuilder = AlertDialog.Builder(this)
            eBuilder.setTitle("Restore")
            eBuilder.setMessage("Are you sure want to Restore? ")
            eBuilder.setPositiveButton("Yes"){
                    Dialog,which->restoreData(con_final)
                Toast.makeText(this,"Restored Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            eBuilder.setNegativeButton("No"){
                    Dialog,which->
            }
            val createBuild: AlertDialog = eBuilder.create()
            createBuild.show()
        }
        clearSsl.setOnClickListener {
            hardDeleteSsl(con_final)
        }


    }

    private fun hardDeleteSsl(con_final: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.172:5001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(permanentDelete::class.java)
        val call:Call<sslDelete> = retrofitAPI.ssl_hard(Name = con_final)
        call.enqueue(object : Callback<sslDelete?> {
            override fun onResponse(call: Call<sslDelete?>, response: Response<sslDelete?>) {
                Toast.makeText(this@DeleteRestoreActivitySsl,"Deleted Successfully!!",Toast.LENGTH_SHORT).show()
                val kout = Intent(this@DeleteRestoreActivitySsl,MainActivity::class.java)
                startActivity(kout )
            }

            override fun onFailure(call: Call<sslDelete?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })


    }

    private fun restoreData(con_final: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.172:5001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(delete::class.java)
        val call: Call<domainDelete> = retrofitAPI.modify_ssl(Name = con_final)
        call.enqueue(object : Callback<domainDelete?> {
            override fun onResponse(call: Call<domainDelete?>, response: Response<domainDelete?>) {
                val out = Intent(this@DeleteRestoreActivitySsl, MainActivity::class.java)
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

