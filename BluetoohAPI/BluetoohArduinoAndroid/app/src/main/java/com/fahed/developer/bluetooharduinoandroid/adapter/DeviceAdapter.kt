package com.fahed.developer.bluetooharduinoandroid.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fahed.developer.bluetooharduinoandroid.BluetoohActivity
import com.fahed.developer.bluetooharduinoandroid.R
import com.fahed.developer.bluetooharduinoandroid.model.Device
import com.fahed.developer.bluetooharduinoandroid.utils.inflate
import kotlinx.android.synthetic.main.item_device_adapter.view.*

class DeviceAdapter(private var listDevices: MutableList<Device>) : RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_device_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listDevices[position])
    }

    override fun getItemCount() = listDevices.size

    fun setDevices(noteList: List<Device>) {
        this.listDevices.clear()
        this.listDevices.addAll(noteList)
        notifyDataSetChanged()
    }

    fun updateDevices(courses: MutableList<Device>) {
        for(publication in courses){
            this.listDevices.add(publication)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(device: Device) {
            itemView.textViewTitle.text = device.name
            itemView.textViewStateBluetooh.text = device.address

            itemView.textViewTitle.setOnClickListener {
                goToBluetoohActivity(device)
            }

        }

        fun goToBluetoohActivity(device: Device) {
            val myIntent = Intent(itemView.context, BluetoohActivity::class.java)
            myIntent.putExtra("name",device.name)
            myIntent.putExtra("address",device.address)
            myIntent.putExtra("uuid",device.uuid)
            itemView.context.startActivity(myIntent)
        }
    }
}