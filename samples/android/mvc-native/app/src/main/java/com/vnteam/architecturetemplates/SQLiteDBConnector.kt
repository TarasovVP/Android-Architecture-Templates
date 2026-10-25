package com.vnteam.architecturetemplates

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Handler
import com.vnteam.architecturetemplates.MainActivity.Companion.DATABASE_NAME
import com.vnteam.architecturetemplates.MainActivity.Companion.TABLE_NAME

class SQLiteDBConnector(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME (id LONG,name VARCHAR(256),full_name VARCHAR(256), owner_login VARCHAR(256), owner_id LONG, owner_avatar_url VARCHAR(256), html_url VARCHAR(256), description VARCHAR(256))"
        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertData(handler: Handler, demoObjects: ArrayList<DemoObject>) {
        val database = this.writableDatabase
        val successMessages = mutableListOf<String>()
        val errorMessages = mutableListOf<String>()

        for (demoObject in demoObjects) {
            val contentValues = ContentValues()
            contentValues.put("id", demoObject.id ?: 0)
            contentValues.put("name", demoObject.name)
            contentValues.put("full_name", demoObject.full_name)
            contentValues.put("owner_login", demoObject.owner?.login)
            contentValues.put("owner_id", demoObject.owner?.id ?: 0)
            contentValues.put("owner_avatar_url", demoObject.owner?.avatar_url)
            contentValues.put("html_url", demoObject.html_url)
            contentValues.put("description", demoObject.description)

            val result = database.insert(TABLE_NAME, null, contentValues)
            if (result == (0).toLong()) {
                errorMessages.add("Error inserting for demoObject with id ${demoObject.id}")
            } else {
                successMessages.add("Success inserting for demoObject with id ${demoObject.id}")
            }
        }

        if (errorMessages.isNotEmpty()) {
            val errorMessage = handler.obtainMessage(MainActivity.ERROR, errorMessages.joinToString("\n"))
            handler.sendMessage(errorMessage)
        }
        if (successMessages.isNotEmpty()) {
            val forkList = handler.obtainMessage(MainActivity.SUCCESS_SQLITE_CONNECTION, demoObjects)
            handler.sendMessage(forkList)
        }
    }

    fun readData(): MutableList<DemoObject> {
        val demoObjects: MutableList<DemoObject> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val demoObject = DemoObject()
                demoObject.id = result.getLong(result.getColumnIndex("id") ?: 0)
                demoObject.name = result.getString(result.getColumnIndex("name") ?: 0)
                demoObject.full_name = result.getString(result.getColumnIndex("full_name") ?: 0)
                demoObject.owner?.login = result.getString(result.getColumnIndex("owner_login") ?: 0)
                demoObject.owner?.id = result.getLong(result.getColumnIndex("owner_id") ?: 0)
                demoObject.owner?.avatar_url = result.getString(result.getColumnIndex("owner_avatar_url") ?: 0)
                demoObject.html_url = result.getString(result.getColumnIndex("html_url") ?: 0)
                demoObject.description = result.getString(result.getColumnIndex("description") ?: 0)
                demoObjects.add(demoObject)
            }
            while (result.moveToNext())
        }
        return demoObjects
    }
}