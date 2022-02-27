package com.example.managedevices.Data

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.managedevices.R
import com.example.managedevices.Views.DeviceInformationActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * class to define generic methods
 */
class Utils {
    companion object {
        /**
         * Load image with Glide
         */
        fun loadImageWithGlide(context: Context, urlImage: String, imageView: ImageView) {
            Glide.with(context)
                .load(urlImage) // image url
                .placeholder(R.drawable.ic_baseline_wait_24) // any placeholder to load at start
                .error(R.drawable.ic_baseline_error_outline_24)  // any image in case of error
                .override(200, 200) // resizing
                .centerCrop()
                .into(imageView)
        }

        /**
         * Display a personilezed Alert
         */
        fun displayAlert(
            context: Context,
            customview: View?,
            method: () -> Unit,
            title: String?,
            positiveBotton: String,
            negativButton: String?
        ) {
            var validButton: Button
            var cancelButton: Button
            val customAlertDialog = MaterialAlertDialogBuilder(context)

            if (customview != null) {
                customAlertDialog.setView(customview)
            }
            customAlertDialog.setMessage(title)
            customAlertDialog.setNegativeButton(negativButton, null)
            customAlertDialog.setPositiveButton(positiveBotton, null)
            customAlertDialog.setCancelable(false)
            alertDialog = customAlertDialog.create()
            alertDialog.setOnShowListener {
                validButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                cancelButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                validButton.setOnClickListener {
                    if (context is DeviceInformationActivity) {
                        method()
                    } else {
                        method()
                        alertDialog.dismiss()
                    }
                }
                cancelButton.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
            alertDialog.show()
        }

        lateinit var alertDialog: AlertDialog
    }
}