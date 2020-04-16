package com.wesdewess

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class About : AppCompatActivity() {

    var dogname: String? = null
    var dogage: String? = null
    var dograce: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        dogname = PreferenceManager.getDefaultSharedPreferences(this).getString("dogname", "")
        dogage = PreferenceManager.getDefaultSharedPreferences(this).getString("age", "")
        dograce = PreferenceManager.getDefaultSharedPreferences(this).getString("race", "")

        //find views
        val name = findViewById<TextView>(R.id.dogNameText)
        val age = findViewById<TextView>(R.id.dogAgeText)
        val race = findViewById<TextView>(R.id.dogRaceText)

        //change text to value provided by user in settings
        name.text = dogname
        age.text = dogage
        race.text = dograce

        //find and/or update the dog image
        findImage()
    }

    //fetch a random image from dog api
    private fun findImage(){
        //request image from dog api
        Log.d(LOG_TAG, "finding image...")
        val queue = Volley.newRequestQueue(this)
        if(dograce.equals("")){
            val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            RANDOM_URI,
            null,
            Response.Listener { response -> // TODO: Handle response
                Log.d(LOG_TAG, "Success fetching JSON")
                loadImageToView(response["message"] as String, findViewById(R.id.imageView))
            },
            Response.ErrorListener { // TODO: Handle error
                Log.d(LOG_TAG, "Error fetching JSON")
            })
            queue.add(jsonObjectRequest);
        }else{
            val breedURL = "https://dog.ceo/api/breed/$dograce/images/random"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                breedURL,
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



    }

    //load an image onto an imageview
    private fun loadImageToView(url:String, iView: ImageView){
        Log.d(LOG_TAG, url) //log the url
        Picasso.get().load(url).into(iView) //Load image using Picasso
    }
}
