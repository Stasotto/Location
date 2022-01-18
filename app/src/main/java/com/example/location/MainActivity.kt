package com.example.location

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private var latitude: Double? = null
    private var longitude: Double? = null

    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkLocationPermission()
        findViewById<Button>(R.id.button).setOnClickListener {
            starMapActivity(latitude!!, longitude!!)
        }

    }

    private fun starMapActivity(latitude: Double, longitude: Double) {
        val geoUri = "geo:$latitude,$longitude"
        val geo = Uri.parse(geoUri)
        val intent = Intent(Intent.ACTION_VIEW, geo)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun checkLocationPermission() {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        task.addOnSuccessListener {location ->
            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
                Toast.makeText(
                    applicationContext,
                    "${location.latitude} ${location.longitude}",
                    Toast.LENGTH_LONG
                ).show()

            }
        }

    }
}