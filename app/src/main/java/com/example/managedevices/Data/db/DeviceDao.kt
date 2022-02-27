package com.example.managedevices.Data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Insert
import androidx.room.Update

@Dao
interface DeviceDao {
    @Query("SELECT * FROM devicesTable")
    fun getAll(): LiveData<List<DeviceEntity>>

    @Query("DELETE FROM devicesTable")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(user: DeviceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(device: DeviceEntity)

    @Update
    suspend fun update(device: DeviceEntity)
}