package com.example.managedevices.Data.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.managedevices.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest: TestCase() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: DeviceDao

    @Before
    override public fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        dao = database.getDeviceDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun selectDevicesFromDBTest() = runBlockingTest {
        val deviceToAdd1 = DeviceEntity(1, "Computer", 132.52, "EUR", 51)
        val deviceToAdd2 = DeviceEntity(2, "Mouse", 178.52, "EUR", 51)
        val deviceToAdd3 = DeviceEntity(3, "Keyboard", 132.2, "EUR", 51)
        val dataAdded = listOf(deviceToAdd1, deviceToAdd2, deviceToAdd3)

        dao.insertDevice(deviceToAdd1)
        dao.insertDevice(deviceToAdd2)
        dao.insertDevice(deviceToAdd3)

        val selectDevices = dao.getAll().getOrAwaitValue()

        assertThat(selectDevices).isEqualTo(dataAdded)
    }

    @Test
    fun insertDeviceToDBTest() = runBlockingTest {
        val deviceToAdd1 = DeviceEntity(1, "Computer", 132.52, "EUR", 51)

        dao.insertDevice(deviceToAdd1)
        val selectDevices = dao.getAll().getOrAwaitValue()

        assertThat(selectDevices).contains(deviceToAdd1)
    }
}