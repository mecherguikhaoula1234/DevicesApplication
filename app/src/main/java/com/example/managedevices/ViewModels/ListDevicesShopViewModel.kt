package com.example.managedevices.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managedevices.Data.Models.DeviceModel
import com.example.managedevices.Repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ListDevicesShopViewModel@Inject constructor(private val homeRepository: HomeRepository): ViewModel() {
    fun getListDevices(): LiveData<List<DeviceModel>> {
        return listDevicesFromApi
    }

    fun getDevicesFromApi() {
        var responseCallApi: List<DeviceModel>?

        isProgressingBar.value = true
        viewModelScope.launch(Main) {
            try {
                responseCallApi = homeRepository.getAllDevices()

                if (responseCallApi != null) {
                    listDevicesFromApi.postValue(responseCallApi)
                } else {
                    listDevicesFromApi.postValue(null)
                }
            } catch (e: Exception) {
                listDevicesFromApi.postValue(null)
            }
            isProgressingBar.postValue(false)
        }
    }

    fun getIsProgressing(): LiveData<Boolean> {
        return isProgressingBar
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    var listDevicesFromApi = MutableLiveData<List<DeviceModel>>()
    var isProgressingBar: MutableLiveData<Boolean> = MutableLiveData()
}