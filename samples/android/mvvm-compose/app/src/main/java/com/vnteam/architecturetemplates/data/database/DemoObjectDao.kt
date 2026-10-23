package com.vnteam.architecturetemplates.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vnteam.architecturetemplates.data.database.entities.DemoObjectDB

@Dao
interface DemoObjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDemoObjects(demoObjects: List<DemoObjectDB>)

    @Query("SELECT * FROM demoObjects")
    fun getDemoObjects(): List<DemoObjectDB>

    @Query("SELECT * FROM demoObjects WHERE :id = id")
    fun getDemoObjectById(id: Long): DemoObjectDB
}