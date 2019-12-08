package com.example.mobilearcanoid

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback ,GoogleMap.OnMarkerClickListener,AttemptListView{
    private lateinit var dataAll:List<Attempt>
    override fun setupScreenForAttemptList(attempts: List<Pair<String, String>>) {
        GlobalScope.launch {
            withContext(Dispatchers.Default){
                presenter.repository.getAllAttempts()
                {
                    dataAll=it.sortedBy { it.score };
                    GlobalScope.launch {
                        withContext(Dispatchers.Main)
                        {
                            dataAll.map {
                                var cur = LatLng(it.lat, it.lon);
                                map.addMarker(MarkerOptions().position(cur).title(it.date+" "+it.score.toString()+" points"))
                            }
                        }
                    }
                }

            }
        }
    }

    override fun onMarkerClick(p0: Marker?) = false
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var presenter:LeaderboardListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        presenter = LeaderboardListPresenter(this, applicationContext)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        Toast.makeText(this,"To restart long click the map",Toast.LENGTH_LONG).show()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        setUpMap()
        map = googleMap

        // Add a marker in Sydney and move the camera
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
        map.isMyLocationEnabled = true

        map.setOnMapLongClickListener {
            var int= Intent(this,MainActivity::class.java)
            startActivity(int)
        }
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                var score=intent.getIntExtra("score",0)
                if (score>0) {
                    GlobalScope.launch {
                        withContext(Dispatchers.Default)
                        {
                            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                            val currentDate = sdf.format(Date())
                            presenter.repository.saveNewAttempt(Attempt(currentDate, score, location.latitude, location.longitude), {})
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onActivityResume()
    }

    override fun onDestroy() {
        presenter.onDestroyActivity()
        super.onDestroy()
    }
}
