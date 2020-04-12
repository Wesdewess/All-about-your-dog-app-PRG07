package com.wesdewess


import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ontworming.*
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class Ontworming : AppCompatActivity() {

    private var dayaDatabase: DatabaseHelper? = null //define which databaseHelper to use
    private val list = ArrayList<String?>()
    private lateinit var adapter: ArrayAdapter<String?>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ontworming)
        setSupportActionBar(toolbar)
        fab.setOnClickListener {
            showForm()
        }
        dayaDatabase = DatabaseHelper(this)

        val listView = findViewById<ListView>(R.id.ListView)

        val values = dayaDatabase!!.getDates()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        if (values != null) {
            for (element in values) {
                list.add(0,element["DATE"])
            }
        }

        listView.adapter = adapter
    }

    private fun showForm() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.form_ontworming_toevoegen,null)
        builder.setView(dialogView)

        // Add action buttons
        builder.apply {
            setPositiveButton(R.string.add
            ) { dialog, id ->
                val input = dialogView.findViewById<DatePicker>(R.id.dateInput)

                addEntry(input.dayOfMonth.toString()+"-"+(input.month+1).toString()+"-"+input.year)
                list.add(0,input.dayOfMonth.toString()+"-"+(input.month+1).toString()+"-"+input.year)
                adapter.notifyDataSetChanged()
            }
            setNegativeButton(R.string.cancel
            ) { dialog, id ->
                // User cancelled the dialog
            }
        }
        builder.create().show()
    }

    //insert user given value into database
    private fun addEntry(value1:String) {
            //insert data
            val isInserted: Boolean = dayaDatabase!!.insertData(value1)

            //show a toast
            if (isInserted) Toast.makeText(
                this@Ontworming,
                "Data Inserted",
                Toast.LENGTH_LONG
            ).show() else Toast.makeText(
                this@Ontworming,
                "Failed to add data",
                Toast.LENGTH_LONG
            ).show()
    }
}
