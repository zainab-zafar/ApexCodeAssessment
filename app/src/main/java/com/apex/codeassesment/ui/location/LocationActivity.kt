package com.apex.codeassesment.ui.location

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.apex.codeassesment.R
import com.apex.codeassesment.databinding.ActivityLocationBinding
import com.apex.codeassesment.utils.locationFinder.GPSHelper
import com.apex.codeassesment.utils.locationFinder.IGPSActivity
import com.apex.codeassesment.utils.locationFinder.PermissionUtils


// TODO (Optional Bonus 8 points): Calculate distance between 2 coordinates using phone's location
class LocationActivity : AppCompatActivity(), IGPSActivity {
    private var permissionArray = ArrayList<String>()
    private var PERMISSION_REQUEST_CODE = 101

    lateinit var userLatitude: String
    lateinit var userLongitude: String
    lateinit var latitudeRandomUser: String
    lateinit var longitudeRandomUser: String

    lateinit var binding: ActivityLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissions()

        latitudeRandomUser = intent.getStringExtra("user-latitude-key").toString()
        longitudeRandomUser = intent.getStringExtra("user-longitude-key").toString()

        binding.locationRandomUser.text = getString(R.string.location_random_user, latitudeRandomUser, longitudeRandomUser)
        binding.locationCalculateButton.setOnClickListener {
            // Toast.makeText(this, "TODO (8): Bonus - Calculate distance between 2 coordinates using phone's location", Toast.LENGTH_SHORT).show()
            calculateDistance()
        }
    }

    fun checkPermissions() {
        permissionArray.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        permissionArray.add(android.Manifest.permission.ACCESS_FINE_LOCATION)

        if (PermissionUtils.hasPermissionGranted(this, permissionArray)) {
            accessUserLocation()
        } else
            requestRequiredPermissions()
    }

    private fun requestRequiredPermissions() {
        PermissionUtils.checkAndRequestPermissions(
            this,
            permissionArray,
            PERMISSION_REQUEST_CODE
        )
    }

    private fun accessUserLocation() {
        if (isLocationEnabled(this)) {
            findLocation()
        } else {
            AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Location!!")
                .setMessage("For more accurate scanning to LE bluetooth devices, please turn on GPS positioning")
                .setPositiveButton("Ok") { dialog, which ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    locationResult.launch(intent)
                }
                .create()
                .show()
        }
    }

    private val locationResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            findLocation()
        } else if (result.resultCode == RESULT_CANCELED) {
            accessUserLocation()
        }
    }

    private fun isLocationEnabled(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // This is a new method provided in API 28
            val lm = context.getSystemService(LOCATION_SERVICE) as LocationManager
            lm.isLocationEnabled
        } else {
            // This was deprecated in API 28
            val mode = Settings.Secure.getInt(
                context.contentResolver, Settings.Secure.LOCATION_MODE,
                Settings.Secure.LOCATION_MODE_OFF
            )
            mode != Settings.Secure.LOCATION_MODE_OFF
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && permissions.isNotEmpty()) {
            var hasPermissionsGranted = true

            for (res in grantResults) {
                if (res < 0) {
                    hasPermissionsGranted = false
                    break
                }
            }

            if (grantResults.isNotEmpty() && hasPermissionsGranted) {
                accessUserLocation()

            } else {
                val builder = AlertDialog.Builder(this)
                builder.setCancelable(false)
                builder.setTitle("Grant Permission")
                builder.setMessage("Please allow permissions to continue.")
                builder.setPositiveButton(
                    "Ok"
                ) { dialogInterface, i ->
                    checkPermissions()
                }
                builder.show()
            }
        }
    }

    private fun findLocation() {
        GPSHelper(this@LocationActivity as IGPSActivity)
    }

    override fun locationChanged(location: Location?) {
        userLongitude = location?.longitude.toString()
        userLatitude = location?.latitude.toString()
        binding.locationPhone.text = getString(R.string.location_phone, userLatitude, userLongitude)
    }

    private fun calculateDistance() {
        binding.locationDistance.text = "\n\n" +
                "Distance: \n* ${DistanceHelper.distance(userLatitude.toDouble(), userLongitude.toDouble(), latitudeRandomUser.toDouble(), longitudeRandomUser.toDouble(), 'K')} in unit K\n" +
                "** ${DistanceHelper.distance(userLatitude.toDouble(), userLongitude.toDouble(), latitudeRandomUser.toDouble(), longitudeRandomUser.toDouble(), 'N')} in unit N\n" +
                "*** ${DistanceHelper.distance(userLatitude.toDouble(), userLongitude.toDouble(), latitudeRandomUser.toDouble(), longitudeRandomUser.toDouble(), 'N')} with no unit."
    }

}
