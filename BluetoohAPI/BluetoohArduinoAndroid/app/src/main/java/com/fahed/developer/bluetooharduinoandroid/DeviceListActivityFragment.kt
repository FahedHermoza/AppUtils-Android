package com.fahed.developer.bluetooharduinoandroid

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahed.developer.bluetooharduinoandroid.adapter.DeviceAdapter
import com.fahed.developer.bluetooharduinoandroid.model.Device
import kotlinx.android.synthetic.main.fragment_device_list.*

/**
 * A placeholder fragment containing a simple view.
 */
class DeviceListActivityFragment : Fragment() {

    //Bluetooth
    private var bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var pairedDevices: Set<BluetoothDevice>

    private lateinit var adapter : DeviceAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_device_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(bluetoothAdapter == null)
        {
            Toast.makeText(context, "Bluetooth Device Not Available", Toast.LENGTH_LONG).show()
            activity?.finish()
        } else{
            if(!bluetoothAdapter!!.isEnabled) {
                var intentEnableBluetooh: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intentEnableBluetooh,1)
            }else{
                pairedDevicesList()
            }
        }

        buttonPairedDeviceList.setOnClickListener {
            pairedDevicesList()
        }

    }

    private fun pairedDevicesList() {
        pairedDevices = bluetoothAdapter!!.bondedDevices
        var listDevice = mutableListOf<Device>()

        if (pairedDevices.isNotEmpty())
        {
            for(elementDevice in pairedDevices)
            {
                listDevice.add(Device(elementDevice.name,  elementDevice.address, elementDevice.uuids[0].toString()))
            }
            displayNotes(listDevice)
        }
        else
            Toast.makeText(context, "No se encontraron dispositivos Bluetooth emparejados.", Toast.LENGTH_LONG).show()

    }

    private fun displayNotes(deviceList: MutableList<Device>?) {
        adapter = DeviceAdapter(deviceList!!)
        recyclerViewDeviceList.adapter = adapter
        recyclerViewDeviceList.visibility = View.VISIBLE

    }
}
