package com.example.managedevices.Repository

import androidx.lifecycle.LiveData
import com.example.managedevices.Data.Models.DeviceModel
import com.example.managedevices.Data.db.DeviceDao
import com.example.managedevices.Data.db.DeviceEntity
import com.example.managedevices.Network.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

/**
 * Class to bind between the data and the ViewModel
 */
class HomeRepository@Inject constructor(private val devicesDao: DeviceDao, private val deviceApi: RetrofitService) {

    // Get devices from the api
     suspend fun getAllDevices(): List<DeviceModel>? {
         var listDevice:  List<DeviceModel>? = null

         CoroutineScope(Dispatchers.IO).async {
             val response = deviceApi.getAllDevices()

             listDevice = if (response != null) {
                 response.body()!!.listDevice
             } else {
                 null
             }
         }.await()
        return  listDevice
    }

    suspend fun insert(device: DeviceEntity) {
        devicesDao.insertDevice(device)
    }

    fun getAllFromRoom(): LiveData<List<DeviceEntity>> {
        return devicesDao.getAll()
    }

    suspend fun deleteAll(){
        devicesDao.deleteAll()
    }
}