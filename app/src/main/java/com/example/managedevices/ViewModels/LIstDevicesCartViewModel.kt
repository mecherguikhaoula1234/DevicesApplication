package com.example.managedevices.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managedevices.Data.db.DeviceEntity
import com.example.managedevices.Repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LIstDevicesCartViewModel@Inject constructor(private val homeRepository: HomeRepository): ViewModel() {

    fun getListDevicesFromDatabase() {
      listDeviceCart = homeRepository.getAllFromRoom()
    }

    fun getListDevicesOfCart(): LiveData<List<DeviceEntity>> {
        return this.listDeviceCart
    }

    fun insertDevice(deviceCartModel: DeviceEntity) {
        viewModelScope.launch {
            homeRepository.insert(deviceCartModel)
        }
    }

    fun deleteDevices() {
        viewModelScope.launch {
            homeRepository.deleteAll()
        }
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    var listDeviceCart: LiveData<List<DeviceEntity>> = MutableLiveData()
}