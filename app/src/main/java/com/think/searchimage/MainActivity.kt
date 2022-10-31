package com.think.searchimage

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.think.searchimage.database.entity.LocationEntity
import com.think.searchimage.databinding.ActivityMainBinding
import com.think.searchimage.extentions.showToastLong
import com.think.searchimage.locationutils.LocationUtils
import com.think.searchimage.locationutils.LocationUtils.getAddress
import com.think.searchimage.viewmodel.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_coordinates_bottom_sheet.view.*
import kotlinx.android.synthetic.main.shimmer_layout.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: LocationViewModel by viewModels()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private lateinit var multiplePermissionsContract: ActivityResultLauncher<Array<String>>
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.root.also {
            setContentView(it)
        }
        setListeners()
        setupPermissions()
        askPermissionsForLocation()
    }

    private fun setupPermissions() {
        var allPermissionGranted=false
        multiplePermissionsContract =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionsStatusMap ->
                permissionsStatusMap.entries.forEach {
                    val isGranted = it.value
                    if (isGranted) {
                        allPermissionGranted=true
                    } else {
                        allPermissionGranted=false
                        showToastLong(getString(R.string.no_permission))
                        // Permission is denied
                    }
                }
                if(allPermissionGranted)getLastLocation()
            }

    }

    private fun askPermissionsForLocation() {
        multiplePermissionsContract.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )
    }

    private fun setListeners() {

        bottomSheetBehavior = BottomSheetBehavior.from(binding.includeLayout)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.fbAdd.setImageDrawable(resources.getDrawable(R.drawable.ic_add))

                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {

                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.fbAdd.setImageDrawable(resources.getDrawable(R.drawable.ic_close))

                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {

                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {

                    }
                    BottomSheetBehavior.STATE_SETTLING -> {

                    }
                }
            }


            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

        })

        fb_add.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                addMarker()
                setValuesToBottomSheetFields()

            } else {
                mMap?.clear()
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }


        }

        binding.includeLayout.bt_submit.setOnClickListener {
            saveLocationData()
        }


    }

    private fun saveLocationData() {
        val name = binding.includeLayout.et_name.text.toString()
        val coordinates = binding.includeLayout.et_coordinates.text.toString()
        if (name.isEmpty()) {
            binding.includeLayout.et_name.error = getString(R.string.enter_name)
        } else if (coordinates.isEmpty()) {
            binding.includeLayout.et_name.error = getString(R.string.enter_coor)
        } else {
            val location = LocationEntity()
            location.propertyName = name
            location.propertyCoordinates = coordinates

            viewModel.saveLocationData(location).observe(this) {
                if (it.getData() != null) {
                    showToastLong(getString(R.string.data_saved))
                }
            }
        }

    }

    private fun addMarker() {
        val markerOption = MarkerOptions().position(viewModel.centerLatLng)
        val icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_event)
        markerOption.icon(icon)
        val cameraPosition: CameraPosition =
            CameraPosition.Builder().target(viewModel.centerLatLng).zoom(12F).build()
        mMap?.addMarker(markerOption)
        mMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun setValuesToBottomSheetFields() {
        binding.includeLayout.et_coordinates.setText("${viewModel.centerLatLng.latitude},${viewModel.centerLatLng.longitude}");

        retrieveAddressFromLocation(viewModel.centerLatLng)
    }

    private fun retrieveAddressFromLocation(location: LatLng?) {
        location?.let {
            Geocoder(this, Locale("in"))
                .getAddress(location.latitude, location.longitude) {
                    binding.includeLayout.et_name.setText("${it?.featureName}, ${it?.locality}, ${it?.adminArea}, ${it?.countryName}")
                }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (LocationUtils.isLocationEnabled(this)) {

            LocationUtils.getUserLocation(this) {
                viewModel.centerLatLng = LatLng(
                    LocationUtils.currentLocation?.latitude!!,
                    LocationUtils.currentLocation?.longitude!!
                )
                mMap?.isMyLocationEnabled = true
                locationFetched()
            }
        } else {
            LocationUtils.showGPSNotEnabledDialog(this)
        }
    }

    private fun locationFetched() {
        val cameraPosition: CameraPosition =
            CameraPosition.Builder().target(viewModel.centerLatLng).zoom(12F).build()
        mMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }


}