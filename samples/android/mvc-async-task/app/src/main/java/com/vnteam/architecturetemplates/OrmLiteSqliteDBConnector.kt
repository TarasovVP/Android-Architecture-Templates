package com.vnteam.architecturetemplates

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vnteam.architecturetemplates.MainActivity.Companion.DATABASE_NAME

class OrmLiteSqliteDBConnector(context: Context) : OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, 1) {

    private var demoObjectDao: Dao<DemoObjectDB, Int>? = null

    override fun onCreate(database: SQLiteDatabase, connectionSource: ConnectionSource) {
        TableUtils.createTable(connectionSource, DemoObjectDB::class.java)
    }
    override fun onUpgrade(database: SQLiteDatabase, connectionSource: ConnectionSource, oldVersion: Int, newVersion: Int) {
        TableUtils.dropTable<DemoObjectDB, Int>(connectionSource, DemoObjectDB::class.java, true)
        onCreate(database, connectionSource)
    }

    fun getDemoObjectDao(): Dao<DemoObjectDB, Int>? {
        if (demoObjectDao == null) {
            demoObjectDao = DaoManager.createDao(connectionSource, DemoObjectDB::class.java)
        }
        return demoObjectDao
    }

    fun getTransformedDemoObjects(): List<DemoObject> {
        val demoObjectList = arrayListOf<DemoObject>()
        demoObjectDao?.queryForAll()?.forEach { demoObjectDB ->
            demoObjectList.add(DemoObject().apply {
                id = demoObjectDB.id
                name = demoObjectDB.name
                fullName = demoObjectDB.fullName
                htmlUrl = demoObjectDB.htmlUrl
                description = demoObjectDB.description
                owner = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(Owner::class.java).fromJson(demoObjectDB.owner.orEmpty())
            })
        }
        return demoObjectList
    }

    fun insertDataAsync(demoObjects: List<DemoObjectDB>, successResult: () -> Unit, errorResult: (String) -> Unit) {
        InsertDataTask(this, successResult, errorResult).execute(demoObjects)
    }

    private class InsertDataTask(
        private val dbConnector: OrmLiteSqliteDBConnector,
        private val successResult: () -> Unit,
        private val errorResult: (String) -> Unit
    ) : AsyncTask<List<DemoObjectDB>, Void, Pair<Unit, String>>() {

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: List<DemoObjectDB>): Pair<Unit, String> {
            var errorMessage = ""
            try {
                val demoObject = params[0]
                dbConnector.getDemoObjectDao()?.create(demoObject)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage.toString()
            }
            return Pair(Unit, errorMessage)
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Pair<Unit, String>) {
            successResult.invoke()
            if (result.second.isNotEmpty()) errorResult.invoke(result.second)
        }
    }
}