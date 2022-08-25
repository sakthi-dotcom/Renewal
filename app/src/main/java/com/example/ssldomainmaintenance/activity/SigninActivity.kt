package com.example.ssldomainmaintenance.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ssldomainmaintenance.R
import com.example.ssldomainmaintenance.RegisterActivity
import com.example.ssldomainmaintenance.databinding.ActivitySigninBinding
import com.example.ssldomainmaintenance.login.Api
import com.example.ssldomainmaintenance.login.LoginData
import com.example.ssldomainmaintenance.login.loginResponse
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL1 = "http://192.168.1.172:5001/"
class SigninActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        val et: EditText = findViewById(R.id.emailEt)
        val pass: EditText = findViewById(R.id.passET)
        val submit: Button = findViewById(R.id.button)
        val register:TextView = findViewById(R.id.next)

        register.setOnClickListener {
            val move = Intent(this, RegisterActivity::class.java)
            startActivity(move)
        }

        submit.setOnClickListener {
            val email = et.text.toString()
            val password = pass.text.toString()

            if (email.isEmpty()) {
                et.error = "Email Required"
                et.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                pass.error = "Password Required"
                pass.requestFocus()
                return@setOnClickListener
            }
            login(email, password)
        }
    }

    private fun login(email: String, password: String) {

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL1)
            .build()
            .create(Api::class.java)
        val model = LoginData(email, password)
        val retrofitData = retrofitBuilder.userLogin(model)
        retrofitData.enqueue(object : Callback<loginResponse?> {
            override fun onResponse(
                call: Call<loginResponse?>,
                response: Response<loginResponse?>
            ) {
                if (response.body()?.statusCode == 200) {
                    val intent = Intent(this@SigninActivity, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@SigninActivity, "Hi $email", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this@SigninActivity,
                        "Invalid email or password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<loginResponse?>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}