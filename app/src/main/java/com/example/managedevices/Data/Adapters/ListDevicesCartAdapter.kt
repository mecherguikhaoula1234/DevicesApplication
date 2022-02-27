package com.example.managedevices.Data.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.managedevices.Data.Constantes
import com.example.managedevices.Data.db.DeviceEntity
import com.example.managedevices.R
import com.google.android.material.textview.MaterialTextView

class ListDevicesCartAdapter(private var context: Context, private var listDevicesCart: ArrayList<DeviceEntity>): BaseAdapter() {

    class ViewHolder(row: View?) {
        var typeDeviceTextView: MaterialTextView? = null
        var numberDeviceTextView: MaterialTextView? = null
        var priceDeviceTextView: MaterialTextView? = null

        init {
            if (row != null) {
                this.typeDeviceTextView = row.findViewById(R.id.type_device_materialTextView)
                this.numberDeviceTextView = row.findViewById(R.id.number_device_materialTextView)
                this.priceDeviceTextView = row.findViewById(R.id.price_device_materialTextView)
            }
        }
    }

    override fun getCount(): Int {
        return listDevicesCart.size
    }

    override fun getItem(position: Int): DeviceEntity {
        return listDevicesCart[position]
    }

    override fun getItemId(position: Int): Long {
        return listDevicesCart[position].id.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            view = inflater.inflate(R.layout.item_device_cart, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val deviceOfCart = listDevicesCart[position]

        viewHolder.typeDeviceTextView!!.text = deviceOfCart.type
        viewHolder.numberDeviceTextView!!.text = "${context.getString(R.string.numberDevices_text)} ${deviceOfCart.number}"
        viewHolder.priceDeviceTextView!!.text = "${context.getString(R.string.price_text)} ${Constantes.CONST_SEPARATOR} ${deviceOfCart.price} ${deviceOfCart.currency}"

        return view as View
    }
}