package com.example.managedevices.Views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.managedevices.Data.Adapters.ListDevicesShopAdapter
import com.example.managedevices.Data.Models.DeviceModel
import com.example.managedevices.Data.Utils.Companion.displayAlert
import com.example.managedevices.R
import com.example.managedevices.ViewModels.ListDevicesShopViewModel
import com.example.managedevices.databinding.ActivityListDevicesShopBinding
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListDevicesShopActivity : AppCompatActivity(), ListDevicesShopAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        setEvent()
    }

    /**
     * Initialize the components of the view
     */
    private fun initComponents() {
        listDevicesShopBinding = ActivityListDevicesShopBinding.inflate(layoutInflater)
        setContentView(listDevicesShopBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.devices_title_text)
        searchDeviceTextInputLayout = listDevicesShopBinding.searchDeviceTextInputLayout
        listDevicesShopBinding.searchDeviceTextInputLayout.requestFocus()
        homeRecycleView = listDevicesShopBinding.listDevicesListview
        homeRecycleView.layoutManager = LinearLayoutManager(this)
        listDevicesShopAdapter = ListDevicesShopAdapter(this, this)
        homeRecycleView.adapter = listDevicesShopAdapter
        homeViewModelListDevices = ViewModelProvider(this).get(ListDevicesShopViewModel::class.java)
    }

    /**
     * Manage the listeners of the class HomeActivity
     */
    private fun setEvent() {
        homeViewModelListDevices.getDevicesFromApi()
        // Observe the list of devices from Api
        homeViewModelListDevices.getListDevices().observe(this, {
            if (homeViewModelListDevices.listDevicesFromApi.value != null) {
                listDevices = homeViewModelListDevices.listDevicesFromApi.value!!.toMutableList()
                listDevicesShopAdapter.setDevices(listDevices)
                listDevicesShopAdapter.notifyDataSetChanged()
            } else {
                displayAlert(
                    this,
                    null,
                    { finish() },
                    getString(R.string.call_api_error_message),
                    getString(R.string.ok_text),
                    null
                )
            }
        })

        // Observe the progress Bar
        homeViewModelListDevices.getIsProgressing().observe(this, {
            if (homeViewModelListDevices.isProgressingBar.value!!) {
                listDevicesShopBinding.progressBar.isVisible = true
                listDevicesShopBinding.loadingDataMaterialTextView.isVisible = true
            } else {
                listDevicesShopBinding.progressBar.visibility = View.GONE
                listDevicesShopBinding.loadingDataMaterialTextView.visibility = View.GONE
            }
        })

        // Handle the click on the button search
        searchDeviceTextInputLayout.setEndIconOnClickListener {
            listDevicesShopAdapter.filter.filter(listDevicesShopBinding.searchDeviceTextInputEditText.text)
            listDevicesShopAdapter.notifyDataSetChanged()
        }

        // Handle the listener change text
        listDevicesShopBinding.searchDeviceTextInputEditText.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable) {
                listDevicesShopAdapter.filter.filter(s)
                listDevicesShopAdapter.notifyDataSetChanged()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Add listener befor text changed
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Add action onTextChanged
            }
        })
    }

    /**
     * Manage the click of an item of the list of devices
     */
    override fun onItemClick(position: Int) {
        val intent = Intent(this, DeviceInformationActivity::class.java)

        intent.putExtra("deviceInformation", listDevicesShopAdapter.getDevice(position))
        startActivity(intent)
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

    private lateinit var homeRecycleView: RecyclerView
    private lateinit var listDevicesShopBinding: ActivityListDevicesShopBinding
    private lateinit var listDevicesShopAdapter: ListDevicesShopAdapter
    private lateinit var homeViewModelListDevices: ListDevicesShopViewModel
    private lateinit var searchDeviceTextInputLayout: TextInputLayout
    private var listDevices: MutableList<DeviceModel> = mutableListOf()
}