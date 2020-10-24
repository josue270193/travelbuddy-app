package com.app.travelbuddy.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.app.travelbuddy.R
import com.app.travelbuddy.ui.viewmodel.CountryViewModel
import com.app.travelbuddy.utils.Resource
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var toolbar: MaterialToolbar

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val countryViewModel: CountryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBar(navController, appBarConfiguration)
        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.main_home -> toolbar.visibility = View.GONE
                else -> toolbar.visibility = View.VISIBLE
            }
        }

        fetchCountryInformation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.setupWithNavController(navController)
    }

    private fun fetchCountryInformation() {
        countryViewModel.getCountryByIp().observe(this, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Timber.d("status: ${it.status}")
                }
                Resource.Status.ERROR -> {
                    Timber.d("status: ${it.status}")
                    Timber.d("message: ${it.message}")
                }
                Resource.Status.SUCCESS -> {
                    Timber.d("status: ${it.status}")
                    Timber.d("data: ${it.data}")
                }
            }
        })
    }
}