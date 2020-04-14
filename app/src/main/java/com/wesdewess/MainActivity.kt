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
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    val LOG_TAG = "wessel"
    private val DOG_URI = "https://dog.ceo/api/breeds/image/random"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val queue = Volley.newRequestQueue(this)

        // make request
        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            DOG_URI,
            null,
            Response.Listener { response -> // TODO: Handle response
                Log.d(LOG_TAG, "Success fetching JSON")
                processData(response!!)
            },
            Response.ErrorListener { // TODO: Handle error
                Log.d(LOG_TAG, "Error fetching JSON")
            })
        queue.add(jsonObjectRequest);
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        val dogname = PreferenceManager.getDefaultSharedPreferences(this).getString("dogname", "")
        if (!dogname.equals("")) {
            findViewById<TextView>(R.id.textView).text = resources.getString(R.string.titlepart) + " " + dogname
        } else {
            findViewById<TextView>(R.id.textView).text = resources.getString(R.string.title)
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

    fun processData(data: JSONObject) {
        try {
            val resultURL = data["message"] as String

            Log.d(LOG_TAG, resultURL)

            loadImage(resultURL)

        } catch (e: JSONException) {
            e.printStackTrace()
            Log.d(LOG_TAG, "Error processing JSON")
        }
    }

    private fun loadImage(url: String?) {
        val imageView: ImageView = findViewById(R.id.imageView)

        //Loading image using Picasso
        Picasso.get().load(url).into(imageView)
    }
}
