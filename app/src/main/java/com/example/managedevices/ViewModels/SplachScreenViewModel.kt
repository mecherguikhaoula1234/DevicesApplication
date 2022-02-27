package com.example.managedevices.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class SplachScreenViewModel: ViewModel() {

    fun getIsProgressing(): LiveData<Int> {
        return progressBarStatus
    }

    suspend fun updateProgressBar() {
        var progress = 0

        progressBarStatus.value = 0
        CoroutineScope(Dispatchers.IO).async {
            while (progress < 125) {
                progress +=25
                progressBarStatus.postValue(progress)
                delay(1000)
            }
        }.await()
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    var progressBarStatus: MutableLiveData<Int> = MutableLiveData()
}