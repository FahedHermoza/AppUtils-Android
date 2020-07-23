package com.fahed.developer.bluetooharduinoandroid

import android.bluetooth.BluetoothSocket
import android.bluetooth.BluetoothDevice
import java.io.IOException
import java.util.*
import android.bluetooth.BluetoothA2dp
import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.os.Message
import android.util.Log
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception


class ConnectThread(private val addressDevice: String, private val bluetoohActivityFragment: BluetoohActivityFragment) : Thread() {
    private val mmSocket: BluetoothSocket?
    val myUUID = "00001101-0000-1000-8000-00805F9B34FB"
    var output: OutputStream? = null
    var input: InputStream? = null

    init {
        var socket: BluetoothSocket? = null
        try {
            sendMesageToHandler("STATE: Conectando...")
            var myBluetooth = BluetoothAdapter.getDefaultAdapter() //State Conectando...
            var device: BluetoothDevice = myBluetooth!!.getRemoteDevice(addressDevice)
            socket = device.createRfcommSocketToServiceRecord(UUID.fromString(myUUID))
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery()

        } catch (e: IOException) {
            Log.e("TAG","ERROR INIT Exception: "+e.toString())
            e.printStackTrace()
        }

        mmSocket = socket
    }

    override fun run() {
        try {
            mmSocket?.connect()
            output = mmSocket?.outputStream
            input = mmSocket?.inputStream
            Thread() {
                run() {
                    try {
                        var erro = false
                        while (mmSocket?.isConnected!!) {
                            val readMessage = input?.read() //State Read
                            val scanner= Scanner(input)
                            var scannerMessage = scanner.nextLine()
                            sendMesageToHandler("READ: " + scannerMessage)
                            Log.e("TAG", "Mensaje leido Bytes: " + scannerMessage)
                            if (readMessage == 0 && !erro) {
                                erro = true
                                sendMesageToHandler("ERROR: Recuperando menssage")
                                Log.e("TAG", "ERROR - Mensaje leido: " + readMessage)
                            } else if (readMessage != 0) {
                                erro = false
                            }

                        }

                    } catch (e: Exception) {
                        //Exception: bt socket closed, read return: -1
                        Log.e("TAG", "Exception Read Message: " + e.toString())
                    }
                }
            }.start()
            sendMesageToHandler( "STATE: Conectado") //State Conectado
        } catch (connectException: IOException) {
            Log.e("TAG","ERROR connection: "+connectException.toString())
            sendMesageToHandler("ERROR: "+connectException.toString())
            return
        }
    }

    fun sendMesageToHandler(content:String){
        val message = Message()
        val bundle = Bundle()
        bundle.putByteArray("data", content.toByteArray())
        message.setData(bundle)
        bluetoohActivityFragment.handler.sendMessage(message)
    }

    /** Will cancel an in-progress connection, and close the socket  */
    fun cancel() {
        try {
            mmSocket?.close()
        } catch (e: IOException) {
            //State Error
            Log.e("TAG","ERROR close: "+e.toString())
        }
    }


    fun senMsg(msg: String) {
        if (output != null) {
            try {
                var bytes = msg.toByteArray()
                output?.write(bytes)
                sendMesageToHandler( "WRITE: "+msg)

            } catch (e: Exception) {
                //State Error
                Log.e("TAG","ERROR write: "+e.toString())
                sendMesageToHandler( "ERROR: Enviando mensaje")
                e.printStackTrace()
            }
        }
    }


}