package com.example.ssldomainmaintenance.activity

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import com.example.ssldomainmaintenance.R
import kotlinx.android.synthetic.main.activity_add_new_domain.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity7 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_domain)

        val domain_name: EditText = findViewById(R.id.userName1)
        val issued_by: EditText = findViewById(R.id.issuedBy1)
        val issued_to: EditText = findViewById(R.id.issuedTo2)
        val issued_on: EditText = findViewById(R.id.issuedOn1)
        val expires_on: EditText = findViewById(R.id.expires1)
        val auto_renewal_enabled:SwitchCompat = findViewById(R.id.autoRenew1)
        val btn: Button = findViewById(R.id.postBtn2)

        btn.setOnClickListener {
            val field1 = domain_name.text.toString().trim()
            val field2 = issued_by.text.toString().trim()
            val field3 = issued_to.text.toString().trim()
            val field4 = issued_on.text.toString().trim()
            val field5 = expires_on.text.toString().trim()


            if (field1.isEmpty()){
                domain_name.error = "Field can't be empty"
                return@setOnClickListener
            }
            else if (field2.isEmpty()){
                issued_by.error = "Field can't be empty"
                return@setOnClickListener
            }
            else if (field3.isEmpty()){
                issued_to.error = "Field can't be empty"
                return@setOnClickListener
            }
            else if (field4.isEmpty()){
                issued_on.error = "Field can't be empty"
                return@setOnClickListener
            }
            else if (field5.isEmpty()){
                expires_on.error = "Field can't be empty"
                return@setOnClickListener
            }


            val pickedDate = issued_on.getText().toString().replace("/", "-")
            val date = SimpleDateFormat("dd-MM-yyyy").parse(pickedDate)
            val temp = date.time
            val epoch = temp / 1000

            val expiredDate = expires_on.getText().toString().replace("/", "-")
            val expdate = SimpleDateFormat("dd-MM-yyyy").parse(expiredDate)
            val temp2 = expdate.time
            val expepoch = temp2 / 1000



            if (auto_renewal_enabled.isChecked()){
                auto_renewal_enabled.setText(true.toString())
            }
            else
                auto_renewal_enabled.setText(false.toString())

            postData(
                domain_name.getText().toString(),
                issued_by.getText().toString(),
                issued_to.getText().toString(),
                epoch.toLong(),
                expepoch.toLong(),
                auto_renewal_enabled.getText().toString().toBoolean()
            )

        }


        val is_new_domain = Calendar.getInstance()
        val day = is_new_domain.get(Calendar.DAY_OF_MONTH)
        val month = is_new_domain.get(Calendar.MONTH)
        val year = is_new_domain.get(Calendar.YEAR)

        issuedOn1.setOnClickListener {
            val add_domain_isdate = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                    issuedOn1.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
                },
                year,
                month,
                day
            )
            add_domain_isdate.show()
        }

        val ex_new_domain = Calendar.getInstance()
        val day1 = ex_new_domain.get(Calendar.DAY_OF_MONTH)
        val month1 = ex_new_domain.get(Calendar.MONTH)
        val year1 = ex_new_domain.get(Calendar.YEAR)
        expires1.setOnClickListener {
            val add_domain_exdate = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                    expires1.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
                },
                year1,
                month1,
                day1
            )
            add_domain_exdate.show()
        }
    }

    private fun postData(
        domain_name: String,
        issued_by: String,
        issued_to: String,
        issued_on: Long,
        expires_on: Long,
        auto_renewal_enabled: Boolean
    ) {
       val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.172:5001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(AddDomain::class.java)
        val model = AddDomainDataModel(
            domain_name,
            issued_by,
            issued_to,
            issued_on,
            expires_on,
            auto_renewal_enabled
        )
        val call: Call<AddDomainDataModel?>? = retrofitAPI.createPost(model)

        call?.enqueue(object : Callback<AddDomainDataModel?> {
            override fun onResponse(call: Call<AddDomainDataModel?>, response: Response<AddDomainDataModel?>) {

                Toast.makeText(this@MainActivity7, "New Domain Added", Toast.LENGTH_SHORT).show()
                val out = Intent(this@MainActivity7,MainActivity::class.java)
                startActivity(out)

                val responseFromAPI = response.body()!!
                val responseString = """
                  Response Code : ${response.code()}
                    domain_name : ${responseFromAPI.domain_name.toString()}
                    issued_by : ${responseFromAPI.issued_by.toString()}
                    issued_to:${responseFromAPI.issued_to.toString()}
                    issued_on:${responseFromAPI.issued_on.toLong()}
                    expires_on:${responseFromAPI.expires_on.toLong()}
                    auto_renewal_enabled:${responseFromAPI.auto_renewal_enabled.toString().toBoolean()}
                    """.trimIndent()
            }

            override fun onFailure(call: Call<AddDomainDataModel?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}