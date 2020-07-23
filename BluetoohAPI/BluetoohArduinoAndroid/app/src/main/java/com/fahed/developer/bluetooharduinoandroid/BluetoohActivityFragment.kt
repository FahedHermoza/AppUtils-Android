package com.fahed.developer.bluetooharduinoandroid

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_bluetooh.*
import java.lang.Exception

/**
 * A placeholder fragment containing a simple view.
 */
class BluetoohActivityFragment : Fragment() {

    //private var myBluetooth: BluetoothAdapter? = null
    private var connectThread: ConnectThread? = null
    private lateinit var nameDevice: String
    private lateinit var addresDevice: String
    private lateinit var uuidDevice: String

    private fun getAnswer() {
        nameDevice = activity!!.intent.getStringExtra("name")
        addresDevice = activity!!.intent.getStringExtra("address")
        uuidDevice = activity!!.intent.getStringExtra("uuid")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bluetooh, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAnswer()
        connectBluetoohThread()
        configButtonSend()
        configButtonClose()
    }

    fun connectBluetoohThread() {
        connectThread = ConnectThread(addresDevice, this)
        connectThread?.start()
    }

    fun configButtonSend(){
        buttonSendBluetooh.setOnClickListener {
            if(editTextMessageBluetooh.text.isNotEmpty())
                sendMessage(editTextMessageBluetooh.getText().toString())
            else
                Toast.makeText(context, "Error...Texto vacio", Toast.LENGTH_LONG).show()
        }
    }

    fun sendMessage(message:String){
        Log.e("TAG","SEND MESSAGE: "+message)
        connectThread?.senMsg(message)
    }

    private fun configButtonClose() {
        buttonCloseBluetooh.setOnClickListener {
            Log.e("TAG","CLOSE: ")
            connectThread?.cancel()
            activity?.finish()
        }
    }

    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            try {
                val bundle = msg.getData()
                val data = bundle.getByteArray("data")

                val dataString = String(data!!)

                if (dataString.contains("ERROR:")) {
                    textViewStateBluetooh.text = dataString
                }

                if (dataString.contains("STATE:")) {
                    textViewStateBluetooh.text = dataString.split(" ")[1]
                }

                if (dataString.contains("WRITE:")) {
                    var message = dataString.split(" ")[1]
                    var contentTextView = textViewLogBluetooh.text.toString()
                    var newMessage = "Enviado: "+message
                    textViewLogBluetooh.text = newMessage +"\n" + contentTextView
                    editTextMessageBluetooh.setText("")
                }

                if (dataString.contains("READ:")) {
                    var message = dataString.split(" ")[1]
                    var contentTextView = textViewLogBluetooh.text.toString()
                    var newMessage = "Recibido: "+message
                    textViewLogBluetooh.text =  newMessage +"\n" + contentTextView
                }


            }catch (e: Exception){
                Log.e("TAG","Exception: "+e.toString())
            }


        }

    }


}
