package com.example.managedevices.Network

import com.example.managedevices.Data.Models.ResponseCallAPiModel
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {
    @GET("home/getDevices")
    suspend fun getAllDevices(): Response<ResponseCallAPiModel>
}