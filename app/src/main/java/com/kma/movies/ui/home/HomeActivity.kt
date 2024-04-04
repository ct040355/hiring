package com.kma.movies.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.kma.movies.R
import com.kma.movies.base.BaseActivity
import com.kma.movies.common.navigation.setupWithNavController
import com.kma.movies.common.utils.gone
import com.kma.movies.common.utils.invisible
import com.kma.movies.common.utils.visible
import com.kma.movies.databinding.ActivityHomeBinding


class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            setupBottomNavigation()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigation()
    }

    @SuppressLint("RestrictedApi")
    private fun setupBottomNavigation() {
        val controller = binding.bottomNavigation.setupWithNavController(
            listOf(
                R.navigation.navigation_popular,
                R.navigation.navigation_account
            ),
            supportFragmentManager,
            R.id.nav_host_container,
            intent
        )
//         Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            if (navController.graph.id == R.id.navigation_popular) {
                binding.appBar.gone()
            } else {
                binding.appBar.visible()
            }
            setupActionBarWithNavController(navController)
        })

        currentNavController = controller
    }




}
