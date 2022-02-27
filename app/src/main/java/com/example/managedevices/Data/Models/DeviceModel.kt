package com.example.managedevices.Data.Models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * POJO class for the model Device for shop
 */
@Parcelize
data class DeviceModel(
    @SerializedName("Id")
    var id: String,
    @SerializedName("Type")
    var type: String,
    @SerializedName("Price")
    var price: Double,
    @SerializedName("Currency")
    var currency: String,
    @SerializedName("isFavorite")
    var isFavorite: Boolean,
    @SerializedName("imageUrl")
    var imageUrl: String,
    @SerializedName("Title")
    var title: String,
    @SerializedName("Description")
    var description: String
): Parcelable
