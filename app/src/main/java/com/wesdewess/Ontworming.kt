package com.wesdewess

import android.app.AlertDialog
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ontworming.*


class Ontworming : AppCompatActivity() {
    var dayaDatabase: DatabaseHelper? = null //define which databaseHelper to use
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ontworming)
        setSupportActionBar(toolbar)
        fab.setOnClickListener {
            showForm()
        }

        dayaDatabase = DatabaseHelper(this);

        val res: Cursor = dayaDatabase!!.allData
        if (res.count == 0) {
            // show message
            showList("Error", "Nothing found")
            return
        }

        val buffer = StringBuffer()
        while (res.moveToNext()) {
            buffer.append(
                """
                    Id :${res.getString(0).toString()}
                    
                    """.trimIndent()
            )
            buffer.append(
                """
                    Name :${res.getString(1).toString()}
                    
                    """.trimIndent()
            )
            buffer.append(
                """
                    Surname :${res.getString(2).toString()}
                    
                    """.trimIndent()
            )
        }

        // Show all data
        showList("Items in database",buffer.toString())
    }
    private fun showList(title:String,data:String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(data)
        builder.show()
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
                addEntry(dialogView.findViewById<EditText>(R.id.username).text.toString(), dialogView.findViewById<EditText>(R.id.password).text.toString())

            }
            setNegativeButton(R.string.cancel
            ) { dialog, id ->
                // User cancelled the dialog
            }
        }
        builder.create().show()
    }

    private fun addEntry(value1:String, value2:String) {
            val isInserted: Boolean = dayaDatabase!!.insertData(value1,value2)
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
