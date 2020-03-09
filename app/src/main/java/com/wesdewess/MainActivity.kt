package com.wesdewess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openMap(v: View?) {
        val toast = Toast.makeText(applicationContext, "Yo man", Toast.LENGTH_SHORT)
        toast.show()
        startActivity(Intent(this@MainActivity, MapsActivity::class.java))
        Log.d("user_action","maps button clicked")
    }
}
