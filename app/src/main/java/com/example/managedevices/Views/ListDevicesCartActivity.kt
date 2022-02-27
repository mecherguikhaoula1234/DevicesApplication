package com.example.managedevices.Views

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.managedevices.Data.Adapters.ListDevicesCartAdapter
import com.example.managedevices.Data.Constantes
import com.example.managedevices.Data.Utils
import com.example.managedevices.Data.db.DeviceEntity
import com.example.managedevices.R
import com.example.managedevices.ViewModels.LIstDevicesCartViewModel
import com.example.managedevices.databinding.ActivityListDevicesCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListDevicesCartActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponenets()
        setEvent()
    }

    /**
     * Initialize the components of the view MainMenuActivity
     */
    @SuppressLint("SetTextI18n")
    private fun initComponenets() {
        listDevicesCartBinding = ActivityListDevicesCartBinding.inflate(layoutInflater)
        setContentView(listDevicesCartBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.list_devices_cart_text)
        listDevicesCart = intent.extras?.get("listDevices") as ArrayList<DeviceEntity>
        listOfDevicesAdapter = ListDevicesCartAdapter(this, listDevicesCart)
        listDevicesCartBinding.listDevicesListview.isClickable = false
        listDevicesCartBinding.listDevicesListview.adapter = listOfDevicesAdapter
        listDevicesCartViewModel = ViewModelProvider(this).get(LIstDevicesCartViewModel::class.java)
        listDevicesCartBinding.paymentValueTextview.text = "${getString(R.string.sum_payment_text)} ${Constantes.CONST_SEPARATOR} ${"%.2f".format(calculatePayment())} ${listDevicesCart[0].currency}"
    }

    /**
     * Manage the click on the listeners of the class MainMenuActivity
     */
    private fun setEvent() {
        configureBottomView()
    }

    /**
     * Configure the  BottomNavigationView Listener
     */
    private fun configureBottomView() {
        listDevicesCartBinding.payCancelBottomNavigationView.setOnItemSelectedListener { item -> addActionBottomViewClick(item.itemId) }
    }

    /**
     * Add the action of click on the differents menus of BottomNavigationView
     */
    private fun addActionBottomViewClick(item: Int): Boolean {
        when (item) {
            R.id.pay_button -> {
                Utils.displayAlert(
                    this,
                    null,
                    ::confirmCartAction,
                    getString(R.string.confirm_cart_text),
                    getString(R.string.yes_text),
                    getString(R.string.no_text)
                )
            }

            R.id.cancel_button -> {
                finish()
            }
        }
        return true
    }

    /**
     * Manage the click on back button
     */
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    /**
     * Handle the confirm of a cart
     */
    private fun confirmCartAction() {
        listDevicesCartViewModel.deleteDevices()
        finish()
        Toast.makeText(this, getString(R.string.cart_created_text), Toast.LENGTH_SHORT).show()
     }

    /**
     * Calculate the price of the cart
     */
    private fun calculatePayment(): Double {
        var sumPayment = 0.0

        for (i in 0 until listDevicesCart.size) {
            sumPayment += listDevicesCart[i].price!! * listDevicesCart[i].number!!
        }
        return sumPayment
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    private lateinit var listDevicesCartBinding: ActivityListDevicesCartBinding
    private lateinit var listOfDevicesAdapter: ListDevicesCartAdapter
    private lateinit var listDevicesCart: ArrayList<DeviceEntity>
    private lateinit var listDevicesCartViewModel: LIstDevicesCartViewModel

}