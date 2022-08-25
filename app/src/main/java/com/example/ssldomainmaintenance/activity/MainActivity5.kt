package com.example.ssldomainmaintenance.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ssldomainmaintenance.R
import kotlinx.android.synthetic.main.activity_main5.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


class MainActivity5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        val but:Button = findViewById(R.id.submitbtn1)
        val isDate:EditText = findViewById(R.id.issuer1)
        val exDate:EditText = findViewById(R.id.issuedTo1)

        val final=intent.getStringExtra("data2")
        supportActionBar?.title = final
        val con_final = final.toString()


        but.setOnClickListener {
            val pickedDate = isDate.getText().toString().replace("/", "-")
            val date = SimpleDateFormat("dd-MM-yyyy").parse(pickedDate)
            val temp = date.time
            val epoch = temp / 1000

            val expiredDate = exDate.getText().toString().replace("/", "-")
            val expdate = SimpleDateFormat("dd-MM-yyyy").parse(expiredDate)
            val temp2 = expdate.time
            val expepoch = temp2 / 1000

         putData(con_final,epoch,expepoch)

        }

        val c1 = Calendar.getInstance()
        val d1 = c1.get(Calendar.DAY_OF_MONTH)
        val m1 = c1.get(Calendar.MONTH)
        val y1 = c1.get(Calendar.YEAR)

        val c2 = Calendar.getInstance()
        val d2 = c2.get(Calendar.DAY_OF_MONTH)
        val m2 = c2.get(Calendar.MONTH)
        val y2 = c2.get(Calendar.YEAR)


        issuer1.setOnClickListener {
            val dp1 = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, myear: Int, mMonth: Int, mDay ->
                    issuer1.setText("" + mDay + "/" + (mMonth + 1) + "/" + myear)
                },
                y1,
                m1,
                d1
            )
            dp1.show()
        }
        issuedTo1.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, mYear, mMonth, mDay ->
                    issuedTo1.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
                },
                y2,
                m2,
                d2
            )
            dpd.show()

        }
    }

   private fun putData(con_final:String,epoch:Long,expepoch:Long) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.172:5001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(AddDomain::class.java)
        val final=intent.getStringExtra("data2")
       Log.d("final",final.toString())
           val call:Call<update> =  retrofitAPI.updateDate(Name = con_final , issued_on = epoch, expires_on = expepoch)
           call.enqueue(object : Callback<update?> {
               override fun onResponse(call: Call<update?>, response: Response<update?>) {
                   Toast.makeText(this@MainActivity5,"Date Changed",Toast.LENGTH_SHORT).show()
                   val out = Intent(this@MainActivity5,MainActivity::class.java)
                   startActivity(out)
               }

               override fun onFailure(call: Call<update?>, t: Throwable) {
                   TODO("Not yet implemented")
               }
           })
   }
}