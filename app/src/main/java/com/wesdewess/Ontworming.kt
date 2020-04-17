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
    //define date handler to manage date formats
    private var dateHandler: DateHandler = DateHandler()
    //which database to use
    private var dayaDatabase: DatabaseHelper? = null //define which databaseHelper to use
    //this list will be used to show dates in
    private val list = ArrayList<String?>()
    //adapter for the listview
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

    //build dialog containing a date-picker. date will be stored in SQLite database
    private fun showForm() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.form_ontworming_toevoegen,null)
        builder.setView(dialogView)

        // Add action buttons
        builder.apply {
            setPositiveButton(R.string.add
            ) { _, _ ->
                //user confirmed dialog
                val input = dialogView.findViewById<DatePicker>(R.id.dateInput)
                val addedDate = input.dayOfMonth.toString()+"-"+(input.month+1).toString()+"-"+input.year

                addEntry(addedDate)
                list.add(1,addedDate + "  -  " + dateHandler.compareToCurrentDate(addedDate).toString() + " " + resources.getString(R.string.days_ago))
                list.removeAt(0)
                list.add(0, resources.getString(R.string.days_since_last_ontw) + " " + dateHandler.compareToCurrentDate(addedDate))
                adapter.notifyDataSetChanged()
            }
            setNegativeButton(R.string.cancel
            ) { _, _ ->
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
        //find the correct view to add the list to
        val listView = findViewById<ListView>(R.id.ListView)
        //get data from database
        val values = dayaDatabase!!.getDates()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        list.add(0, resources.getString(R.string.days_since_last_ontw) + " " + dateHandler.compareToCurrentDate(values))

        //add all items to list and compare dates to current date
        if (values != null) {
            for (element in values) {
                list.add(1,element["DATE"] + "  -  " + dateHandler.compareToCurrentDate(element["DATE"]).toString() + " " + resources.getString(R.string.days_ago))
            }
        }
        listView.adapter = adapter
    }
}
