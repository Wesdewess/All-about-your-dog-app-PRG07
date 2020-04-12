package com.wesdewess

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context?) :SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase){
        db.execSQL("create table $TABLE_NAME (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase,oldVersion: Int,newVersion: Int){
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(date: String?): Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, date)
        val result = db.insert(TABLE_NAME, null, contentValues)
        return result != -1L
    }

    val allData: Cursor
        get(){
            val db = this.writableDatabase
            return db.rawQuery("select * from $TABLE_NAME", null)
        }
    fun getDates(): ArrayList<HashMap<String, String>>? {
        val db = this.writableDatabase
        val dateList: ArrayList<HashMap<String, String>> = ArrayList()
        val cursor = db.rawQuery("select * from $TABLE_NAME", null)
        while (cursor.moveToNext()) {
            val date: HashMap<String, String> = HashMap()
            date["ID"] = cursor.getString(cursor.getColumnIndex(COL_1))
            date["DATE"] = cursor.getString(cursor.getColumnIndex(COL_2))
            dateList.add(date)
        }

        return dateList
    }
    fun updateData(id: String,date: String?): Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, id)
        contentValues.put(COL_2, date)
        db.update(TABLE_NAME, contentValues,"ID = ?", arrayOf(id))
        return true
    }

    fun deleteData(id: String): Int{
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "ID = ?", arrayOf(id))
    }

    companion object {
        const val DATABASE_NAME = "Daya.db"
        const val TABLE_NAME = "ontworming_dates"
        const val COL_1 = "ID"
        const val COL_2 = "DATE"
    }
}