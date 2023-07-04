package com.apex.codeassesment.ui.main

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apex.codeassesment.R
import com.apex.codeassesment.RandomUserApplication
import com.apex.codeassesment.data.UserRepository
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ActivityMainBinding
import com.apex.codeassesment.di.MainComponent
import com.apex.codeassesment.di.ViewModelFactory
import com.apex.codeassesment.ui.details.DetailsActivity
import com.apex.codeassesment.ui.main.adapter.MainActivityAdapter
import com.apex.codeassesment.ui.main.callbacks.MainCallbacks
import com.apex.codeassesment.vm.MainViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


// TODO (5 points): Move calls to repository to Presenter or ViewModel.
// TODO (5 points): Use combination of sealed/Dataclasses for exposing the data required by the view from viewModel .
// TODO (3 points): Add tests for viewmodel or presenter.
// TODO (1 point): Add content description to images
// TODO (3 points): Add tests
// TODO (Optional Bonus 10 points): Make a copy of this activity with different name and convert the current layout it is using in
//  Jetpack Compose.

class MainActivity : AppCompatActivity(), MainCallbacks {

    // TODO (2 points): Convert to view binding


    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var userRepository: UserRepository


    private var randomUser: User = User()
        set(value) {
            // TODO (1 point): Use Glide to load images after getting the data from endpoints mentioned in RemoteDataSource
            // userImageView.setImageBitmap(refreshedUser.image)

            value.picture?.thumbnail?.let {

                Glide.with(this@MainActivity)
                    .load(it)
                    .dontTransform()
                    .fitCenter()
                    .into(binding.mainImage)
            }

            binding.mainName.text = value.name!!.first
            binding.mainEmail.text = value.email
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedContext = this
        (applicationContext as MainComponent.Injector).mainComponent.inject(this)

        getViewModel()
        initData()
        setRecyclerAdapter()
        setActionListeners()
        setDataObserver()
        showProgress()
        hideProgress()
    }

    private fun initData() {
        showProgress()
        viewModel.getUser()
    }

    private fun getViewModel() {
        /* Viewmodel object creation with custom ViewModelFactory */
        val viewModelFactory = ViewModelFactory(MainViewModel(RandomUserApplication.getInstance()?.applicationContext as Application, userRepository = userRepository))
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

    }

    private fun setActionListeners() {
        binding.mainUserListButton.setOnClickListener {
            showProgress()
            viewModel.getUsers()
        }

        binding.mainSeeDetailsButton.setOnClickListener {
            navigateDetails(DetailsActivity::class.java) {
                putSerializable("saved-user-key", randomUser)
            }
        }

        binding.mainRefreshButton.setOnClickListener {
            showProgress()
            viewModel.getUser()
        }
    }

    private fun showProgress() {
        Glide.with(this).load(R.raw.progress_loader).into(binding.imageProgress)
        binding.imageProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.imageProgress.visibility = View.GONE
    }

    private fun setRecyclerAdapter() {
        binding.mainUserList.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        binding.mainUserList.adapter = MainActivityAdapter(this, this@MainActivity as MainCallbacks)
    }

    private fun getRecyclerAdapter(): MainActivityAdapter {
        return (binding.mainUserList.adapter as MainActivityAdapter)
    }

    private fun setDataObserver() {
        lifecycleScope.launch {
            viewModel.observeUserData.collectLatest {
                hideProgress()
                randomUser = it.results!![0]
            }
        }

        lifecycleScope.launch {
            viewModel.observeUsersDataList.collectLatest {
                hideProgress()
                getRecyclerAdapter().addData(it.results!!)
            }
        }
    }

    companion object {
        var sharedContext: Context? = null
    }

    override fun onUserItemClick(userItem: User) {
        navigateDetails(DetailsActivity::class.java) {
            putParcelable("saved-user-key", userItem)
        }
    }

}

// TODO (2 points): Convert to extenstion function.
fun <T> Context.navigateDetails(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}
