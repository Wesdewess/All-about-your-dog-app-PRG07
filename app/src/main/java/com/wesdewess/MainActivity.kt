package com.wesdewess

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
    //open the map activity
    fun openMap(v: View?) {
        startActivity(Intent(this@MainActivity, MapsActivity::class.java))
    }
    //open the ontworming activity
    fun openOntworming(v: View?) {
        startActivity(Intent(this@MainActivity, Ontworming::class.java))
    }
}
