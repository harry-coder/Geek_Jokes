package com.think.searchimage.locationutils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import com.think.searchimage.R
import java.util.*


object LocationUtils {

    var currentLocation: Location? = null
    private var locationTask: Task<Location?>? = null
   private lateinit var mFusedLocationClient: FusedLocationProviderClient
   private lateinit var cancellationTokenSource: CancellationTokenSource

    fun isLocationEnabled(context: Context): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    fun showGPSNotEnabledDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.enable_gps))
            .setMessage(context.getString(R.string.required_for_this_app))
            .setCancelable(false)

            .setPositiveButton(context.getString(R.string.enable_now)) { view_,_ ->
                view_.dismiss()

                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))

            }
            .show()
    }




    @SuppressLint("MissingPermission")
    fun getUserLocation(context: Context,location:(location:Location?)->Unit):Task<Location?>? {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        mFusedLocationClient.lastLocation
            .addOnCompleteListener{
                if (it.result != null) {
                    this.currentLocation = it.result
                    locationTask=it
                } else {

                    cancellationTokenSource= CancellationTokenSource()
                    locationTask= mFusedLocationClient.getCurrentLocation(
                        locationRequest(),
                        cancellationTokenSource.token
                    )
                }
                location.invoke(locationTask?.result)
            }
        return locationTask
    }







   private fun locationRequest(): CurrentLocationRequest =
        CurrentLocationRequest.Builder().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setDurationMillis(1000)
            .build()


    @Suppress("DEPRECATION")
    fun Geocoder.getAddress(
        latitude: Double,
        longitude: Double,
        address: (android.location.Address?) -> Unit
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getFromLocation(latitude, longitude, 1) { address(it.firstOrNull()) }
            return
        }

        try {
            address(getFromLocation(latitude, longitude, 1)?.firstOrNull())
        } catch(e: Exception) {
            //will catch if there is an internet problem
            address(null)
        }
    }


    fun getLocation(): Location? {
        return this.currentLocation
    }
    fun getLocationTask(): Task<Location?>? {
        return this.locationTask
    }

}
