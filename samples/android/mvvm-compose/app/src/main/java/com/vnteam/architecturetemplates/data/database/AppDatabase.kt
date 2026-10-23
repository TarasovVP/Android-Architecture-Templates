package com.vnteam.architecturetemplates.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vnteam.architecturetemplates.data.database.entities.DemoObjectDB

@Database(
    entities = [DemoObjectDB::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun demoObjectDao(): DemoObjectDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, appContext.packageName)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}