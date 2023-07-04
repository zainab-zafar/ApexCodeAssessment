package com.apex.codeassesment.ui.details

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.apex.codeassesment.R
import com.apex.codeassesment.data.model.Coordinates
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ActivityDetailsBinding
import com.apex.codeassesment.ui.location.LocationActivity
import com.bumptech.glide.Glide

// TODO (3 points): Convert to Kotlin
// TODO (3 points): Remove bugs or crashes if any
// TODO (1 point): Add content description to images
// TODO (2 points): Add tests
class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    private lateinit var coordinates: Coordinates

    private val user by lazy {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                intent.getParcelableExtra<User>("saved-user-key", User::class.java)
            }
            else -> {
                intent.getParcelableExtra<User>("saved-user-key")
            }
        } as User
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        setActionListeners()


        // TODO (1 point): Use Glide to load images
        user.picture?.let {
            Glide.with(this)
                .load(it.large)
                .fitCenter()
                .into(binding.detailsImage)
        }

//    binding.detailsImage = user.getPicture().getLarge();

    }

    fun initData() {
        val name = user.name
        binding.detailsName.text = this.getString(R.string.details_name, name!!.first, name.last)
        binding.detailsEmail.text = getString(R.string.details_email, user.gender)
        binding.detailsAge.text = user.dob!!.age.toString()
        user.location!!.coordinates?.let {
            coordinates = it
        }
        binding.detailsLocation.text = getString(R.string.details_location, coordinates.latitude, coordinates.longitude)
    }

    private fun setActionListeners() {
        binding.detailsLocationButton.setOnClickListener { x: View? -> navigateLocation(coordinates) }
    }

    private fun navigateLocation(coordinates: Coordinates) {
        val intent = Intent(this, LocationActivity::class.java)
            .putExtra("user-latitude-key", coordinates.latitude)
            .putExtra("user-longitude-key", coordinates.longitude)
        startActivity(intent)
    }
}