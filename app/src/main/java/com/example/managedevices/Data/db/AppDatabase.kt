package com.example.managedevices.Data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [DeviceEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDeviceDao(): DeviceDao
    companion object {
        // Singleton prevents multiple
        // instances of database opening at the
        // same time.
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return instance ?: synchronized(this) {
                var instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "devicesDatabase.db"
                ).build()
                instance = instance
                // return instance
                instance
            }
        }
    }
}