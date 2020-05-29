package com.fahed.developer.permissions.view.activities.List

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fahed.developer.permissions.R
import com.fahed.developer.permissions.adapter.ClinicAdapter
import com.fahed.developer.permissions.view.activities.map.MapActivity
import com.fahed.developer.permissions.viewmodel.ClinicsListViewModel
import com.fahedhermoza.developer.examplenote01.Models.Clinic
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.ArrayList

/**
 * A placeholder fragment containing a simple view.
 */
class ListActivityFragment : Fragment(), ClinicAdapter.ClinicHandle {

    companion object{
        //Statics for Permission y startActivityForResult
        private const val PERMISSIONS_REQUEST_LOCATION = 1
        private const val PERMISSIONS_REQUEST_CALL_PHONE = 2
        private const val PERMISSIONS_REQUEST_CAMERA = 3

        private const val ACTIVITY_RESULT_CAMERA = 4
    }
    private lateinit var adapter: ClinicAdapter
    private lateinit var viewModel: ClinicsListViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ClinicsListViewModel::class.java)

        adapter = ClinicAdapter(mutableListOf(), this)
        recyclerViewClinics.adapter = adapter
        viewModel.getSavedNotes().observe(this, Observer { clinics ->
            clinics?.let {
                adapter.setClinics(clinics)
            }
        })

        setupViews()
        viewModel.insertAllItems(loadData())
    }

    private fun setupViews() {
        changeTitle()
    }

    private fun changeTitle(){
        activity?.title = getString(R.string.title_activity_list)
    }

    private fun loadData():MutableList<Clinic> {
        var clinics: MutableList<Clinic> = ArrayList()
        clinics.add(Clinic("Clínica Peruano Suiza", "Av. Peru Mz, Av. Perú 3", "(084) 237168"))
        clinics.add(Clinic("Clínica San Juan de Dios de Cusco", "Manzanares", "(084) 231340"))
        clinics.add(Clinic("Centro Médico CIMA", "Av. Pardo Paseo de los Héroes 978", "(084) 255550"))
        clinics.add(Clinic("Clinica MacSalud", "Av. La Cultura 1410", "(084) 582060"))
        clinics.add(Clinic("Clínica Pardo", "Av. de La Cultura 710", "(084) 231718"))
        return clinics
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.menu_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.locationMenuItem){
            //Permission in Fragment
            if(isPermissionGrantedLocation()){
                goToMapActivity()
            }
        }
        return false
    }

    private fun isPermissionGrantedLocation():Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionCheckFineLocation = checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            val permissionCheckCoarseLocation = checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION)
            if(permissionCheckFineLocation == PackageManager.PERMISSION_GRANTED &&
                permissionCheckCoarseLocation == PackageManager.PERMISSION_GRANTED){
                return true
            }else{
                requestPermissions( arrayOf(Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_LOCATION)
                return false
            }
        }
        return true
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.e("TAG","codigo:_"+requestCode)
        when(requestCode){

            PERMISSIONS_REQUEST_LOCATION -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    goToMapActivity()
                }else{
                    showToast("Necesita Permisos para continuar")
                }
            }

            PERMISSIONS_REQUEST_CALL_PHONE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else{
                    showToast("Necesita Permisos para continuar")
                }
            }


            PERMISSIONS_REQUEST_CAMERA -> {
                Log.e("TAG","REQUEST CAMERA")
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else{
                    showToast("Necesita Permisos para continuar")
                }
            }

        }
    }

    private fun showToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }

    private fun goToMapActivity() {
        val myIntent = Intent(context, MapActivity::class.java)
        startActivity(myIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode){
            ACTIVITY_RESULT_CAMERA -> {
                if(resultCode == Activity.RESULT_OK){
                    //Hacer algo despues de llamar a la camara
                }
            }
        }
    }

    //Permission in Adapter with interface Handler
    override fun bodyImageCamera() {
        if(isPermissionGrantedCamera()){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, ACTIVITY_RESULT_CAMERA)
        }
    }

    private fun isPermissionGrantedCamera():Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionCheckCamera = checkSelfPermission(context!!,Manifest.permission.CAMERA)
            if(permissionCheckCamera == PackageManager.PERMISSION_GRANTED){
                return true
            }else{
                requestPermissions( arrayOf(Manifest.permission.CAMERA),
                    PERMISSIONS_REQUEST_CAMERA
                )
                return false
            }
        }
        return true
    }

    //Permission in Adapter with interface Handler
    override fun bodyImageCall(phone: String) {
        if(isPermissionGrantedCallPhone()){
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:" + phone.trim())
            startActivity(intent)
        }
    }

    private fun isPermissionGrantedCallPhone():Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionCheckCallPhone = checkSelfPermission(context!!,Manifest.permission.CALL_PHONE)
            if(permissionCheckCallPhone == PackageManager.PERMISSION_GRANTED){
                return true
            }else{
                requestPermissions( arrayOf(Manifest.permission.CALL_PHONE),
                    PERMISSIONS_REQUEST_CALL_PHONE
                )
                return false
            }
        }
        return true
    }

}
