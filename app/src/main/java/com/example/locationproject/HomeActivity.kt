package com.example.locationproject

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.locationproject.databinding.ActivityHomeBinding
import com.google.android.gms.maps.model.Marker

class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityHomeBinding
    private lateinit var geoCoder: Geocoder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        geoCoder = Geocoder(this)
    }

    private val mapDragListener = object:GoogleMap.OnMarkerDragListener {
        override fun onMarkerDrag(p0: Marker) {
        }

        override fun onMarkerDragEnd(p0: Marker) {
        }

        override fun onMarkerDragStart(p0: Marker) {
            val it = p0.position
            try {
                val addresses1 = geoCoder.getFromLocation(it.latitude,it.longitude,1)
                if(addresses1.size > 0) {
                    val addres = addresses1[0]
                    val streetAddress = addres.getAddressLine(0)
                    mMap.addMarker(MarkerOptions().position(it).title(streetAddress))
                }
            }catch(e: Exception) {
                e.printStackTrace()
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener {
            Log.i("TAG", "onMapReady: $it")
            try {
                val addresses1 = geoCoder.getFromLocation(it.latitude,it.longitude,1)
                if(addresses1.size > 0) {
                    val addres = addresses1.get(0)
                    val streetAddress = addres.getAddressLine(0)
                    mMap.addMarker(MarkerOptions().position(it).title(streetAddress).draggable(true))
                }
            }catch(e: Exception) {
                e.printStackTrace()
            }
        }
        mMap.setOnMarkerDragListener(mapDragListener)
//        val latLong = LatLng(-14.0, 11.0)
//        val markerOption =
//            MarkerOptions().position(latLong).title("New Area Zone").snippet("Area 21 ")
//        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLong, 16.0f)
//        mMap.addMarker(markerOption)
//        mMap.animateCamera(cameraUpdate)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Add Loction").snippet("Subtitle for Title"))
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,16.0f))

        val address = geoCoder.getFromLocationName("noida",1)
        val add = address[0]
        val latLong = LatLng(add.latitude,add.longitude)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLong,16.0f)
        val marker = MarkerOptions().position(latLong).title(add.locality)
        mMap.addMarker(marker)
        mMap.moveCamera(cameraUpdate)

    }
}