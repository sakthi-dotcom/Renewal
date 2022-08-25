package com.example.ssldomainmaintenance.activity

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.ssldomainmaintenance.R
import kotlinx.android.synthetic.main.activity_add_new_ssl.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity6 : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_ssl)

        val name: EditText = findViewById(R.id.userName)
        val issuedBy: EditText = findViewById(R.id.issuedBy)
        val issuedTo: EditText = findViewById(R.id.issuedTo)
        val issuedOn: EditText = findViewById(R.id.issuedOn)
        val expiresOn: EditText = findViewById(R.id.expires)
        val certificate_type:Spinner = findViewById(R.id.certiTypeSpinner)
        val auto_renewal_enabled1: EditText = findViewById(R.id.renewal)
        val btn: Button = findViewById(R.id.postBtn1)

        btn.setOnClickListener {

            val field1 = name.text.toString().trim()
            val field2 = issuedBy.text.toString().trim()
            val field3 = issuedTo.text.toString().trim()
            val field4 = issuedOn.text.toString().trim()
            val field5 = expiresOn.text.toString().trim()
            val field6 = auto_renewal_enabled1.text.toString().trim()

            if (field1.isEmpty()){
                name.error = "Field can't be empty"
                return@setOnClickListener
            }
            else if (field2.isEmpty()){
                issuedBy.error = "Field can't be empty"
                return@setOnClickListener
            }
            else if (field3.isEmpty()){
                issuedTo.error = "Field can't be empty"
                return@setOnClickListener
            }
            else if (field4.isEmpty()){
                issuedOn.error = "Field can't be empty"
                return@setOnClickListener
            }
            else if (field5.isEmpty()){
                expiresOn.error = "Field can't be empty"
                return@setOnClickListener
            }

            else if (field6.isEmpty()){
                auto_renewal_enabled1.error = "Field can't be empty"
                return@setOnClickListener
            }


            val pickedDate = issuedOn.getText().toString().replace("/", "-")
            val date = SimpleDateFormat("dd-MM-yyyy").parse(pickedDate)
            val temp = date.time
            val epochIss = temp / 1000


            val expiredDate = expiresOn.getText().toString().replace("/", "-")
            val expdate = SimpleDateFormat("dd-MM-yyyy").parse(expiredDate)
            val temp1 = expdate.time
            val epochExp = temp1 / 1000

            if (auto_renewal_enabled1.getText().toString() == "Yes" || auto_renewal_enabled1.getText().toString() == "yes"){
                auto_renewal_enabled1.setText(true.toString())
            }
            else
                auto_renewal_enabled1.setText(false.toString())

            postData1(
                name.getText().toString(),
                issuedBy.getText().toString(),
                issuedTo.getText().toString(),
                epochIss.toLong(),
                epochExp.toLong(),
                certificate_type.getSelectedItem().toString(),
                auto_renewal_enabled1.getText().toString().toBoolean()
            )

        }
        val is_new_ssl = Calendar.getInstance()
        val day = is_new_ssl.get(Calendar.DAY_OF_MONTH)
        val month = is_new_ssl.get(Calendar.MONTH)
        val year = is_new_ssl.get(Calendar.YEAR)

        issuedOn.setOnClickListener {
            val addSsl_isdate = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                    issuedOn.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
                },
                year,
                month,
                day
            )
            addSsl_isdate.show()
        }

        val ex_new_ssl = Calendar.getInstance()
        val day1 = ex_new_ssl.get(Calendar.DAY_OF_MONTH)
        val month1 = ex_new_ssl.get(Calendar.MONTH)
        val year1 = ex_new_ssl.get(Calendar.YEAR)

        expires.setOnClickListener {
            val addSsl_exdate = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                    expires.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
                },
                year1,
                month1,
                day1
            )
            addSsl_exdate.show()
        }

       setupSimpleSpinner()

    }

    private fun setupSimpleSpinner() {
        val adapter = ArrayAdapter.createFromResource(this,R.array.CertificateType,android.R.layout.simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        certiTypeSpinner.adapter = adapter
    }

    private fun postData1(name:String,
                              issued_by: String,
                              issued_to: String,
                              issued_on: Long,
                              expires_on: Long,
                              certificate_type:String,
                              auto_renewal_enabled: Boolean)
    {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.172:5001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(AddSsl::class.java)

        val mod = AddSslDataModel(
            name,
            issued_by,
            issued_to,
            issued_on,
            expires_on,
            certificate_type,
            auto_renewal_enabled
        )
        val call: Call<AddSslDataModel?>? = retrofitAPI.createPost1(mod)

        call?.enqueue(object : Callback<AddSslDataModel?> {
            override fun onResponse(call: Call<AddSslDataModel?>, response: Response<AddSslDataModel?>) {

                Toast.makeText(this@MainActivity6, "New SSL Added", Toast.LENGTH_SHORT).show()
                val out = Intent(this@MainActivity6,MainActivity::class.java)
                startActivity(out)

                val responseFromAPI= response.body()!!
                val responseString = """
                  Response Code : ${response.code()}
                    name : ${responseFromAPI.name.toString()}
                    issued_by : ${responseFromAPI.issued_by.toString()}
                    issued_to:${responseFromAPI.issued_to.toString()}
                    issued_on:${responseFromAPI.issued_on.toLong()}
                    expires_on:${responseFromAPI.expires_on.toLong()}
                    certificate_type:${responseFromAPI.certificate_type.toString()}
                    auto_renewal_enabled:${responseFromAPI.auto_renewal_enabled.toString().toBoolean()}
                    """.trimIndent()
            }

            override fun onFailure(call: Call<AddSslDataModel?>, t: Throwable) {

            }
        })
    }
}