package com.example.ssldomainmaintenance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ssldomainmaintenance.activity.SigninActivity
import com.example.ssldomainmaintenance.databinding.ActivityRegisterBinding
import com.example.ssldomainmaintenance.register.RApi
import com.example.ssldomainmaintenance.register.RegisterData
import com.example.ssldomainmaintenance.register.registerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL1 = "http://192.168.1.172:5001/"

class RegisterActivity : AppCompatActivity(), View.OnClickListener,View.OnFocusChangeListener,View.OnKeyListener {

    private lateinit var mBinding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.emailEt1.onFocusChangeListener = this
        mBinding.passET2.onFocusChangeListener = this
        mBinding.confPass.onFocusChangeListener = this



        setContentView(R.layout.activity_register)
        val et: EditText = findViewById(R.id.emailEt1)
        val pass: EditText = findViewById(R.id.passET2)
        val pass1: EditText = findViewById(R.id.confPass)
        val submit: Button = findViewById(R.id.register)

        submit.setOnClickListener {
            val email = et.text.toString()
            val password = pass.text.toString()

            signup(email, password)
        }
    }
    private fun  validateEmail():Boolean{
        var errorMessage:String?= null
        val value:String = mBinding.emailEt1.text.toString()
        if(value.isEmpty()){
            errorMessage = "Email is required"
        }else if (Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            errorMessage = "Email address is invalid"
        }
       if (errorMessage!=null){
           mBinding.emailLayout1.apply {
               isErrorEnabled = true
               error = errorMessage
           }
       }
        return errorMessage == null
    }
    private fun validatePassword():Boolean{
    var errorMessage:String?= null
    val value:String = mBinding.passET2.text.toString()
    if(value.isEmpty()){
        errorMessage = "Password is required"
    }else if (value.length < 8){
        errorMessage = "Password must be 8 characters long"
    }
        if (errorMessage!=null){
            mBinding.passwordLayout1.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
    return errorMessage == null
}
    private fun validateConfirmPassword():Boolean{
        var errorMessage:String?= null
        val value:String = mBinding.confPass.text.toString()
        if(value.isEmpty()){
            errorMessage = "Confirm Password is required"
        }else if (value.length < 8){
            errorMessage = "Confirm Password must be 8 characters long"
        }
        if (errorMessage!=null){
            mBinding.passwordLayout1.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }
    private fun validatePasswordAndConfirmPassword():Boolean{
        var errorMessage:String?= null
        val password = mBinding.passET2.text.toString()
        val confirmPass = mBinding.confPass.text.toString()
        if (password!=confirmPass){
            errorMessage = "Password mismatch"
        }
        if (errorMessage!=null){
            mBinding.passwordLayout1.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun signup(email: String, password: String) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL1)
            .build()
            .create(RApi::class.java)
        val mod = RegisterData(email,password)
        val retrofitData = retrofitBuilder.userRegister(mod)
        retrofitData.enqueue(object : Callback<registerResponse?> {
            override fun onResponse(
                call: Call<registerResponse?>,
                response: Response<registerResponse?>
            ) {
                if (response.body()?.statusCode == 200){
                    val intent = Intent(this@RegisterActivity, SigninActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@RegisterActivity, "Registered", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@RegisterActivity, "User already exist" +
                            "", Toast.LENGTH_SHORT).show()

                }


            }

            override fun onFailure(call: Call<registerResponse?>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()            }
        })
    }

    override fun onClick(v: View?) {

    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (v!= null){
            when (v.id){
                R.id.emailEt1 ->{
                    if (hasFocus){
                        if (mBinding.emailLayout1.isCounterEnabled){
                            mBinding.emailLayout1.isErrorEnabled = false}

                    }else{
                        validateEmail()
                    }

                }
                R.id.passET2 ->{
                    if (hasFocus){
                        if (mBinding.passwordLayout2.isCounterEnabled){
                            mBinding.passwordLayout2.isErrorEnabled = false}

                    }else{
                        validatePassword()
                    }
                }
                R.id.confPass ->{

                    if (hasFocus){
                        if (mBinding.passwordLayout1.isCounterEnabled){
                            mBinding.passwordLayout1.isErrorEnabled = false}

                    }else{
                        validateConfirmPassword()
                    }
                }

                }
            }
        }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {

        return false
    }
}


