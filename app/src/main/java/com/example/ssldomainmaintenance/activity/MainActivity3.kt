package com.example.ssldomainmaintenance.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.ssldomainmaintenance.R
import kotlinx.android.synthetic.main.activity_main3.*
import java.util.*

class MainActivity3 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val c1 = Calendar.getInstance()
        val day1 = c1.get(Calendar.DAY_OF_MONTH)
        val month1 = c1.get(Calendar.MONTH)
        val year1 = c1.get(Calendar.YEAR)

        issuer.setOnClickListener {

            val dp = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view:DatePicker,mYear:Int,mMonth:Int,mDay:Int ->
                issuer.setText(""+mDay +"/" +(mMonth+1) +"/" + mYear)
            },year,month,day)
            dp.show()

        }
        issuedTo.setOnClickListener {
            val dp1 = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view:DatePicker,mYear:Int,mMonth:Int,mDay:Int ->
                issuedTo.setText(""+mDay+"/"+(mMonth+1)+"/"+mYear)
            },year1,month1,day1)
            dp1.show()
        }
    }
}