package com.example.managedevices.Views

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.managedevices.Data.Constantes
import com.example.managedevices.Data.Models.DeviceModel
import com.example.managedevices.Data.Utils
import com.example.managedevices.Data.Utils.Companion.alertDialog
import com.example.managedevices.Data.Utils.Companion.displayAlert
import com.example.managedevices.Data.db.DeviceEntity
import com.example.managedevices.R
import com.example.managedevices.ViewModels.LIstDevicesCartViewModel
import com.example.managedevices.databinding.ActivityDeviceInformationBinding
import com.example.managedevices.databinding.CustomviewNumberDevicesBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * this class is to display the informations of a selected device
 */
@AndroidEntryPoint
class DeviceInformationActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        setEvent()
    }

    /**
     * Initialize the components of the view
     */
    @SuppressLint("SetTextI18n")
    private fun initComponents() {
        deviceInformationBinding = ActivityDeviceInformationBinding.inflate(layoutInflater)
        setContentView(deviceInformationBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        deviceInformation = intent.getParcelableExtra("deviceInformation")!!
        supportActionBar?.title =getString(R.string.details_text, deviceInformation.title)
        deviceInformationBinding.titleDeviceTextview.text = deviceInformation.title
        Utils.loadImageWithGlide(this, deviceInformation.imageUrl, deviceInformationBinding.deviceImageImageview)
        deviceInformationBinding.typeDeviceTextview.text = "${getString(R.string.type_text)} ${Constantes.CONST_HYPHEN} ${deviceInformation.type}"
        deviceInformationBinding.priceDeviceTextview.text = "${getString(R.string.price_text)} ${Constantes.CONST_HYPHEN} ${deviceInformation.price} ${deviceInformation.currency}"
        deviceInformationBinding.descriptionDeviceTextview.text = "${getString(R.string.description_text)} ${Constantes.CONST_HYPHEN} ${deviceInformation.description}"
        deviceInformationViewModel = ViewModelProvider(this).get(LIstDevicesCartViewModel::class.java)
    }

    /**
     * Manage the listeners of the class DeviceInformationActivity
     */
    private fun setEvent() {
        configureBottomView()
    }

    /**
     * Configurer BottomNavigationView Listener
     */
    private fun configureBottomView() {
        deviceInformationBinding.addCartCancelBottomnavigationview.setOnItemSelectedListener {
                item -> addActionBottomViewClick(item.itemId) }
    }

    /**
     * Add action for each menu from BottomNavigationView
     */
    private fun addActionBottomViewClick(item: Int): Boolean {
        when (item) {
            R.id.valid_button -> {
                val customviewNumberDevicesBinding = CustomviewNumberDevicesBinding.inflate(layoutInflater)

                customviewNumberDevicesBinding.numberDeviceTextInputEditText.setFocusableInTouchMode(true)
                customviewNumberDevicesBinding.numberDeviceTextInputEditText.requestFocus()
                displayAlert(
                    this,
                    customviewNumberDevicesBinding.root,
                    { manageTypingNumberDevice(customviewNumberDevicesBinding) },
                    null,
                    getString(R.string.valid_text),
                    getString(R.string.cancel_text)
                )            }

            R.id.cancel_button -> {
               finish()
            }
        }
        return true
    }

    /**
     * Manage the typing of number of devices
     */
    private fun manageTypingNumberDevice(view: CustomviewNumberDevicesBinding) {
        val numberOfDevicesTextInputEditText = view.numberDeviceTextInputEditText

        when {
            numberOfDevicesTextInputEditText.text.isNullOrEmpty() -> {
                numberOfDevicesTextInputEditText.error = getString(R.string.add_number_error_text)
            }
            numberOfDevicesTextInputEditText.text.toString().toInt() == 0 -> {
                numberOfDevicesTextInputEditText.error = getString(R.string.number_greater_than_zero_error_text)
            }
            else -> {
                deviceInformationViewModel.insertDevice(
                    DeviceEntity(
                        0,
                        deviceInformation.type,
                        deviceInformation.price,
                        deviceInformation.currency,
                        numberOfDevicesTextInputEditText.text.toString().toInt()
                    )
                )
                alertDialog.dismiss()
                Toast.makeText(this, getString(R.string.device_added_text), Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Manage the click on back button
     */
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    private lateinit var deviceInformationBinding: ActivityDeviceInformationBinding
    private lateinit var deviceInformation: DeviceModel
    private lateinit var deviceInformationViewModel: LIstDevicesCartViewModel
}
