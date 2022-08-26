package com.example.ssldomainmaintenance

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
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

class RegisterActivity : AppCompatActivity(),View.OnClickListener,View.OnFocusChangeListener,View.OnKeyListener {
    private lateinit var mBinding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.emailEt.onFocusChangeListener = this
        mBinding.passwordEt.onFocusChangeListener = this
        mBinding.cPasswordEt.onFocusChangeListener = this
        mBinding.registerBtn.setOnClickListener {
            val email = mBinding.emailEt.text.toString().trim()
            val password = mBinding.passwordEt.text.toString().trim()
            val confpass = mBinding.cPasswordEt.text.toString().trim()  
            signup(email,confpass)
        }
    }



    private fun validateEmail():Boolean{
        var errorMessage:String? = null
        val value = mBinding.emailEt.text.toString()
        if (value.isEmpty()){
            errorMessage = "Email is required"
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            errorMessage = "Email address is invalid"
        }

        if(errorMessage != null){
            mBinding.emailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }
    private fun validatePassword():Boolean{
        var errorMessage:String? = null
        val value = mBinding.passwordEt.text.toString()
        if (value.isEmpty()){
            errorMessage = "Password is required"
        }else if (value.length< 8){
            errorMessage = "Password must be 8 characters"
        }
        if(errorMessage != null){
            mBinding.passwordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validateConfirmPassword():Boolean{
        var errorMessage:String? = null
        val value = mBinding.passwordEt.text.toString()
        if (value.isEmpty()){
            errorMessage = "Confirm password is required"
        }else if (value.length< 8){
            errorMessage = "Confirm password must be 8 characters"
        }
        if(errorMessage != null){
            mBinding.cPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }
    private fun validatePasswordAndConfirmPassword():Boolean{
        var errorMessage:String?= null
        val password = mBinding.passwordEt.text.toString()
        val confirmPassword = mBinding.cPasswordEt.text.toString()
        if (password != confirmPassword){
            errorMessage = "Password does not match"
        }
        if(errorMessage != null){
            mBinding.cPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null

    }
    override fun onClick(v: View?) {

    }
    override fun onFocusChange(v: View?, hasFocus: Boolean) {

        if (v != null) {
            when (v.id) {
                R.id.emailEt -> {
                    if (hasFocus) {
                        if (mBinding.emailTil.isErrorEnabled) {
                            mBinding.emailTil.isErrorEnabled = false
                        }
                    } else {
                        validateEmail()
                    }
                }
                R.id.passwordEt -> {
                    if (hasFocus) {
                        if (mBinding.passwordTil.isErrorEnabled) {
                            mBinding.passwordTil.isErrorEnabled = false
                        }
                    } else {
                        if (validatePassword() && mBinding.cPasswordEt.text!!.isNotEmpty() && validateConfirmPassword() && validatePasswordAndConfirmPassword()) {
                            if (mBinding.cPasswordTil.isErrorEnabled) {
                                mBinding.cPasswordTil.isErrorEnabled = false
                            }
                            mBinding.cPasswordTil.apply {
                                setStartIconDrawable(R.drawable.ic_baseline_check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }
                }
                R.id.cPasswordEt -> {
                    if (hasFocus) {
                        if (mBinding.cPasswordTil.isErrorEnabled) {
                            mBinding.cPasswordTil.isErrorEnabled = false
                        }
                    } else {
                        if (validateConfirmPassword() && validatePassword() && validatePasswordAndConfirmPassword()) {
                            if (mBinding.passwordTil.isErrorEnabled) {
                                mBinding.passwordTil.isErrorEnabled = false
                            }
                            mBinding.cPasswordTil.apply {
                                setStartIconDrawable(R.drawable.ic_baseline_check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))


                            }
                        }
                    }
                }
            }
        }
    }
    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }

    private fun signup(email: String, confpass: String) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL1)
            .build()
            .create(RApi::class.java)
        val mod = RegisterData(email,confpass)
        val retrofitData = retrofitBuilder.userRegister(mod)
        retrofitData.enqueue(object : Callback<registerResponse?> {
            override fun onResponse(
                call: Call<registerResponse?>,
                response: Response<registerResponse?>
            ) {
                if (response.body()?.statusCode == 200){
                    val intent = Intent(this@RegisterActivity,SigninActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@RegisterActivity, "User Registered", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@RegisterActivity, "User already exist" + "", Toast.LENGTH_SHORT).show()

                }
            }override fun onFailure(call: Call<registerResponse?>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()            }
        })
    }


    }





