package com.example.ssldomainmaintenance.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.ssldomainmaintenance.R
import com.example.ssldomainmaintenance.databinding.ActivityMainBinding
import com.example.ssldomainmaintenance.fragments.DomainFragment
import com.example.ssldomainmaintenance.fragments.SslFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(SslFragment())

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ssl -> replaceFragment(SslFragment())
                R.id.domain -> replaceFragment(DomainFragment())
                else ->{
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logOut -> {
                val eBuilder = AlertDialog.Builder(this)
                    eBuilder.setTitle("Exit")
                    eBuilder.setMessage("Are you sure want to exit? ")
                        eBuilder.setPositiveButton("Yes"){
                            Dialog,which->
                             val intent = Intent(this@MainActivity,SigninActivity::class.java)
                             startActivity(intent)
                             finish()
                            Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show()
                        }
                eBuilder.setNegativeButton("No"){
                        Dialog,which->
                }
                val createBuild:AlertDialog = eBuilder.create()
                createBuild.show()
            }
        }
        return true
    }

    // to avoid back activity
    override fun onBackPressed() {}
}