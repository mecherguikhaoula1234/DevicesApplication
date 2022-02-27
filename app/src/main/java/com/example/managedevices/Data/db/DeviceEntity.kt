package com.example.managedevices.Data.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "devicesTable")
@Parcelize
data class DeviceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "price") val price: Double?,
    @ColumnInfo(name = "currency") val currency: String?,
    @ColumnInfo(name = "number") val number: Int?
): Parcelable
