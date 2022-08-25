package com.example.ssldomainmaintenance.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.example.ssldomainmaintenance.R

@Suppress("DEPRECATION")
class splashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME: Long =1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this, SigninActivity::class.java))
            finish()
        },SPLASH_TIME)
    }
}