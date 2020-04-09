package com.wesdewess

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase){
        db.execSQL("create table $TABLE_NAME (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase,oldVersion: Int,newVersion: Int){
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(name: String?,surname: String?): Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, surname)
        val result = db.insert(TABLE_NAME, null, contentValues)
        return result != -1L
    }

    val allData: Cursor
        get(){
            val db = this.writableDatabase
            return db.rawQuery("select * from $TABLE_NAME", null)
        }

    fun updateData(id: String,name: String?,surname: String?): Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, id)
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, surname)
        db.update(TABLE_NAME, contentValues,"ID = ?", arrayOf(id))
        return true
    }

    fun deleteData(id: String): Int{
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "ID = ?", arrayOf(id))
    }

    companion object {
        const val DATABASE_NAME = "Student.db"
        const val TABLE_NAME = "student_table"
        const val COL_1 = "ID"
        const val COL_2 = "NAME"
        const val COL_3 = "SURNAME"
    }
}