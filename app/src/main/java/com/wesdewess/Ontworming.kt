package com.wesdewess


import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ontworming.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList


class Ontworming : AppCompatActivity() {

    private var dateHandler: DateHandler = DateHandler()
    private var dayaDatabase: DatabaseHelper? = null //define which databaseHelper to use


    private val list = ArrayList<String?>()
    private lateinit var adapter: ArrayAdapter<String?>

    //all that happens when creating the Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ontworming)
        setSupportActionBar(toolbar)
        fab.setOnClickListener {
            showForm()
        }
        dayaDatabase = DatabaseHelper(this)

        makeList()
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
                val addedDate = input.dayOfMonth.toString()+"-"+(input.month+1).toString()+"-"+input.year

                addEntry(addedDate)
                list.add(1,addedDate + "  -  " + dateHandler.compareToCurrentDate(addedDate).toString() + " " + resources.getString(R.string.days_ago))

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

            //show a toast to indicate success or failure
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

    private fun makeList(){

        val listView = findViewById<ListView>(R.id.ListView)
        val values = dayaDatabase!!.getDates()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        list.add(0, resources.getString(R.string.days_since_last_ontw) + " " + dateHandler.compareToCurrentDate(values))

        if (values != null) {
            for (element in values) {
                list.add(1,element["DATE"] + "  -  " + dateHandler.compareToCurrentDate(element["DATE"]).toString() + " " + resources.getString(R.string.days_ago))
            }
        }

        listView.adapter = adapter

        Toast.makeText(
            this@Ontworming,
            "Days since last ontworming: " + dateHandler.compareToCurrentDate(values),
            Toast.LENGTH_LONG
        ).show()
    }
}
