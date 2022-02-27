package com.example.managedevices.Views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.managedevices.Data.Utils.Companion.displayAlert
import com.example.managedevices.Data.db.DeviceEntity
import com.example.managedevices.R
import com.example.managedevices.ViewModels.LIstDevicesCartViewModel
import com.example.managedevices.databinding.ActivityMainmenuBinding
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.startActivity

@AndroidEntryPoint
class MainMenuActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        setEvent()
    }

    /**
     * Initialize the components of the view MainMenuActivity
     */
    private fun initComponents() {
        mainmenuBinding = ActivityMainmenuBinding.inflate(layoutInflater)
        setContentView(mainmenuBinding.root)
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainmenuViewModel = ViewModelProvider(this).get(LIstDevicesCartViewModel::class.java)
    }

    /**
     * Manage the click on the listeners of the class MainMenuActivity
     */
    private fun setEvent() {
        mainmenuBinding.shopDevicesButton.setOnClickListener {
            startActivity<ListDevicesShopActivity>()
        }

        mainmenuBinding.shoppinghartShopDevicesButton.setOnClickListener {
            mainmenuViewModel.getListDevicesFromDatabase()

            mainmenuViewModel.getListDevicesOfCart().observe(this, {
                val listDevicesCart = mainmenuViewModel.listDeviceCart.value!!

                if (!listDevicesCart.isNullOrEmpty()) {
                    val intent = Intent(this@MainMenuActivity, ListDevicesCartActivity::class.java)

                    intent.putExtra(
                        "listDevices",
                        mainmenuViewModel.listDeviceCart.value as ArrayList<DeviceEntity>
                    )
                    startActivity(intent)
                } else {
                    displayAlert(
                        this@MainMenuActivity,
                        null,
                        { },
                        getString(R.string.no_device_in_cart),
                        getString(R.string.ok_text),
                        null
                    )
                }
            })
        }
    }

    /**
     * Manage the click on the back button
     */
    override fun onSupportNavigateUp(): Boolean {
        displayAlert(this, null, {finishAffinity()}, getString(R.string.quit_app_text), getString(R.string.yes_text), getString(R.string.no_text))
        return true
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }

    override fun onPause() {
        mainmenuViewModel.getListDevicesOfCart().removeObservers(this)
        super.onPause()
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    private lateinit var mainmenuBinding: ActivityMainmenuBinding
    private lateinit var mainmenuViewModel: LIstDevicesCartViewModel
}