package com.example.managedevices.Data.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.managedevices.Data.Models.DeviceModel
import com.example.managedevices.Data.Utils.Companion.loadImageWithGlide
import com.example.managedevices.R

class ListDevicesShopAdapter(private val listener: OnItemClickListener, private var context: Context): RecyclerView.Adapter<ListDevicesShopAdapter.DeviceViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val deviceItemView = LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)

        return DeviceViewHolder(deviceItemView)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val currentDevice: DeviceModel = listDevices[position]

        loadImageWithGlide(context, currentDevice.imageUrl, holder.deviceImageview!!)
        holder.titleDeviceTextview?.text = currentDevice.title
        if (currentDevice.isFavorite) {
            holder.descriptionTextview?.text = context.getString(R.string.status_available_text)
        } else {
            holder.descriptionTextview?.text = context.getString(R.string.status_unavailable_text)
        }
    }

    override fun getItemCount(): Int {
        return listDevices.size
    }

    fun setDevices(devices: List<DeviceModel>) {
        this.listDevices = devices
        this.listFiltered = devices
    }

    fun getDevice(position: Int): DeviceModel {
        return listDevices[position]
    }

    inner class DeviceViewHolder(itemView: View) : ViewHolder(itemView), View.OnClickListener {
        var deviceImageview: ImageView? = itemView.findViewById(R.id.device_imageview)
        var titleDeviceTextview: TextView? = itemView.findViewById(R.id.title_device_textview)
        var descriptionTextview: TextView? = itemView.findViewById(R.id.description_materialTextView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

            if (position != NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()

                if (constraint == null || constraint.isEmpty()) {
                    //no constraint given, just return all the data. (no search)
                    results.count = listFiltered.size
                    results.values = listFiltered
                } else {
                    val resultsData = ArrayList<DeviceModel>()
                    val searchStr = constraint.toString().lowercase()

                    for (s in listFiltered)
                        if (s.title.lowercase().contains(searchStr) || s.description.lowercase().contains(searchStr)) resultsData.add(s)
                    results.count = resultsData.size
                    results.values = resultsData
                }
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                listDevices = results.values as ArrayList<DeviceModel>
            }
        }
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    private var listDevices: List<DeviceModel> = arrayListOf()
    private var listFiltered = listDevices
}