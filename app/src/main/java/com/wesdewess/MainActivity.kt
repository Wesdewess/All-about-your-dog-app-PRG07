package com.wesdewess

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

const val LOG_TAG = "dog_app"
const val DOG_URI = "https://dog.ceo/api/breeds/image/random"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findImage()
        Log.d(LOG_TAG, "created main activity")
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        //change title to dogname set by user in the settings
        val dogname = PreferenceManager.getDefaultSharedPreferences(this).getString("dogname", "") //find value set in preferences
        if (!dogname.equals("")) { //if dogname is set
            findViewById<TextView>(R.id.textView).text = resources.getString(R.string.titlepart) + " " + dogname //custom title
        } else {
            findViewById<TextView>(R.id.textView).text = resources.getString(R.string.title) //default title
        }
    }

    //open the map activity
    fun openMap(v: View?) {
        startActivity(Intent(this@MainActivity, MapsActivity::class.java))
    }
    //open the ontworming activity
    fun openOntworming(v: View?) {
        startActivity(Intent(this@MainActivity, Ontworming::class.java))
    }
    //open the Settings activity
    fun openSettings(v: View?) {
        startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
    }

    //fetch a random image from dog api
    private fun findImage(){
        //request image from dog api
        Log.d(LOG_TAG, "finding image...")
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            DOG_URI,
            null,
            Response.Listener { response -> // TODO: Handle response
                Log.d(LOG_TAG, "Success fetching JSON")
                loadImageToView(response["message"] as String, findViewById(R.id.imageView))
            },
            Response.ErrorListener { // TODO: Handle error
                Log.d(LOG_TAG, "Error fetching JSON")
            })
        queue.add(jsonObjectRequest);
    }

    //load an image onto an imageview
    private fun loadImageToView(url:String, iView: ImageView){
            Log.d(LOG_TAG, url) //log the url
            Picasso.get().load(url).into(iView) //Load image using Picasso
    }
}
