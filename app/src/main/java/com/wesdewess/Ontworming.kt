package com.wesdewess

import android.app.AlertDialog
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ontworming.*


class Ontworming : AppCompatActivity() {
    var dayaDatabase: DatabaseHelper? = null //define which databaseHelper to use
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ontworming)
        setSupportActionBar(toolbar)

        dayaDatabase = DatabaseHelper(this);

        val res: Cursor = dayaDatabase!!.allData
        if (res.count == 0) {
            // show message
            showMessage("Error", "Nothing found")
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
            buffer.append(
                """
                    Marks :${res.getString(3).toString()}
                    
                    
                    """.trimIndent()
            )
        }

        // Show all data
        showMessage("Data", buffer.toString())
    }
    private fun showMessage(title: String?, Message: String?) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = layoutInflater

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.form_ontworming_toevoegen, null))
        // Add action buttons
        builder.apply {
            setPositiveButton(R.string.add
            ) { dialog, id ->
                addEntry()
            }
            setNegativeButton(R.string.cancel
            ) { dialog, id ->
                // User cancelled the dialog
            }
        }
        builder.show()
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
