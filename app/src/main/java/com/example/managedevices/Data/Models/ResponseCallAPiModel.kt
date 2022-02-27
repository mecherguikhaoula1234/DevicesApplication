package com.example.managedevices.Data.Models

import com.google.gson.annotations.SerializedName

data class ResponseCallAPiModel(
    @SerializedName("devices")
    var listDevice: List<DeviceModel>
)