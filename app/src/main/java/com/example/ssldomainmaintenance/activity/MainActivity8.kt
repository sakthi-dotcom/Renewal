package com.example.ssldomainmaintenance.activity

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import com.example.ssldomainmaintenance.R
import kotlinx.android.synthetic.main.activity_main5.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity8 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main8)

        val but: Button = findViewById(R.id.doneBtn2)
        val isDate: EditText = findViewById(R.id.issuedOnEdit2)
        val exDate: EditText = findViewById(R.id.expiresOnEdit2)

        val final1=intent.getStringExtra("data3")
        supportActionBar?.title = final1
        val con_final1 = final1.toString()

        val c1 = Calendar.getInstance()
        val d1 = c1.get(Calendar.DAY_OF_MONTH)
        val m1 = c1.get(Calendar.MONTH)
        val y1 = c1.get(Calendar.YEAR)

        val c2 = Calendar.getInstance()
        val d2 = c2.get(Calendar.DAY_OF_MONTH)
        val m2 = c2.get(Calendar.MONTH)
        val y2 = c2.get(Calendar.YEAR)

        but.setOnClickListener {


            val pickedDate = isDate.getText().toString().replace("/", "-")
            val date = SimpleDateFormat("dd-MM-yyyy").parse(pickedDate)
            val temp = date.time
            val epoch = temp / 1000

            val expiredDate = exDate.getText().toString().replace("/", "-")
            val expdate = SimpleDateFormat("dd-MM-yyyy").parse(expiredDate)
            val temp2 = expdate.time
            val expepoch = temp2 / 1000

            putData1(con_final1, epoch, expepoch)
        }
        isDate.setOnClickListener {
            val dp1 = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, myear: Int, mMonth: Int, mDay ->
                    isDate.setText("" + mDay + "/" + (mMonth + 1) + "/" + myear)
                },
                y1,
                m1,
                d1
            )
            dp1.show()
        }
        exDate.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, mYear, mMonth, mDay ->
                    exDate.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
                },
                y2,
                m2,
                d2
            )
            dpd.show()

        }
    }

    private fun putData1(con_final1:String, epoch: Long, expepoch: Long) {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.172:5001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(AddSsl::class.java)
        val final=intent.getStringExtra("data3")
        val call: Call<update1> =  retrofitAPI.updateDate1(Name = con_final1 , issued_on = epoch, expires_on = expepoch)
        call.enqueue(object : Callback<update1?> {
            override fun onResponse(call: Call<update1?>, response: Response<update1?>) {
                Toast.makeText(this@MainActivity8,"Date Changed!!",Toast.LENGTH_SHORT).show()
                val out = Intent(this@MainActivity8,MainActivity::class.java)
                startActivity(out)
            }

            override fun onFailure(call: Call<update1?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}