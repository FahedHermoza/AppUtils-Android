package com.x.developer.ciudadesdepartamento

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_location.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class LocationActivityFragment : Fragment(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    LocationListener, EasyPermissions.PermissionCallbacks {
    private lateinit var  mGoogleApiClient: GoogleApiClient
    private lateinit var  mLocationRequest: LocationRequest

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonPermissions.setOnClickListener {
            if(checkSDKMinorsToMarshmallow()){
                startGoogleLocationServices()
            }else{
                if(checkPermissionsGranted()){ startGoogleLocationServices()}
            }
        }


        buttonGeocode.setOnClickListener {
            if(textViewLatitude.text.toString() != "0" && textViewLongitude.text.toString() != "0"){
                getGeocodeData(textViewLatitude.text.toString().toDouble(), textViewLongitude.text.toString().toDouble())
            }
        }
    }

    //Flow Google Location Services
    fun startGoogleLocationServices(){
        mGoogleApiClient = GoogleApiClient.Builder(context!!)
            .addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this)
            .build()

        mGoogleApiClient.connect()
    }

    override fun onStop() {
        super.onStop()
        mGoogleApiClient.disconnect()
    }

    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest.create()
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest.setInterval(3000) //refresh every 3 second

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.i("LOG_TAG", "GoogleApiClient connection has been suspended")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("LOG_TAG", "GoogleApiClient connection has failed")
    }

    override fun onLocationChanged(p0: Location?) {
        Log.e("TAG","Location: "+p0?.latitude +" - "+p0?.longitude)
        textViewLatitude.text = p0?.latitude?.toString()
        textViewLongitude.text = p0?.longitude?.toString()
    }

    //Method using Geocoder
    private fun getGeocodeData( latitude: Double, longitud: Double){
        var gcd = Geocoder(context, Locale.getDefault())
        var addresses: List<Address>? = null

        try {
            addresses = gcd.getFromLocation(latitude, longitud, 1)
        } catch ( e: IOException) {
            e.printStackTrace()
        }
        if (addresses != null && addresses.size > 0) {

            var firstAddress = addresses.get(0)

            var countryNameCode = firstAddress.countryName.plus(" / ").plus(firstAddress.countryCode)
            textViewGeocodeCountry.text = countryNameCode

            var address = firstAddress.getAddressLine(0)
            textViewGeocodeAddress.text = address

            var adminArea = firstAddress.adminArea
            textViewGeocodeDepartamento.text = adminArea

            var subadminArea = firstAddress.subAdminArea
            textViewGeocodeProvincia.text =  subadminArea

            var locality = firstAddress.locality
            textViewGeocodeDistrito.text = locality

            var sublocality = firstAddress.subLocality
            textViewGeocodeSubLocality.text = sublocality

            var postalCode = firstAddress.postalCode
            textViewGeocodePostalCode.text = postalCode
        }

    }

    // Flow EasyPermissions
    override fun onStart() {
        super.onStart()
        checkSdkAndPermissions()
    }

    companion object {
        private const val  RC_CAMERA_LOCATION_AND_STORAGE_PERM = 124
        private var CAMERA_LOCATION_AND_STORAGE = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun checkPermissionsGranted(): Boolean{
        if (ActivityCompat.checkSelfPermission(activity!!.applicationContext, CAMERA_LOCATION_AND_STORAGE[0]) == PackageManager.PERMISSION_GRANTED ) {
            return true
        }
        return false
    }

    private fun checkSdkAndPermissions() {
        if(checkSDKMinorsToMarshmallow())
            startGoogleLocationServices()
        else
            permissionTask()
    }

    private fun checkSDKMinorsToMarshmallow(): Boolean{
        var check = true
        val myVersion = Build.VERSION.SDK_INT
        val versionMarshmallow = Build.VERSION_CODES.M
        if(myVersion >= versionMarshmallow)
            check = false
        return check
    }

    @AfterPermissionGranted(RC_CAMERA_LOCATION_AND_STORAGE_PERM)
    fun permissionTask() {
        if (hasPermissions()) {
            startGoogleLocationServices()
        } else {
            EasyPermissions.requestPermissions(
                activity!!,
                getString(R.string.need_permissions_introduction),
                RC_CAMERA_LOCATION_AND_STORAGE_PERM ,
                CAMERA_LOCATION_AND_STORAGE[0])
        }
    }

    private fun hasPermissions(): Boolean {
        return EasyPermissions.hasPermissions(context!!, CAMERA_LOCATION_AND_STORAGE[0])
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        startGoogleLocationServices()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(context, getString(R.string.without_permissions_introduction), Toast.LENGTH_LONG).show();
    }

}
