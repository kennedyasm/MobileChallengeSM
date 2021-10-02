package com.challenges.mobilechallengesm.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.challenges.mobilechallengesm.R
import com.challenges.mobilechallengesm.databinding.ActivityMainBinding
import com.challenges.mobilechallengesm.ui.viewmodel.BeersViewModel
import com.challenges.mobilechallengesm.utils.queries
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: BeersViewModel by viewModels()
    private lateinit var navController: NavController

    private val expandListener = object : MenuItem.OnActionExpandListener {

        override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
            navController.navigate(R.id.toSearch)
            return true
        }

        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
            navController.navigateUp()
            return true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) navController.navigateUp().also { return true }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavController()
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContent) as NavHostFragment
        navController = navHostFragment.findNavController()
        setupActionBarWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        findSearchActionView(menu)
        return true
    }

    private fun findSearchActionView(menu: Menu?) = menu?.findItem(R.id.mSearch)?.let { menuItem ->

        menuItem.setOnActionExpandListener(expandListener)?.also {

            (it.actionView as? SearchView)?.apply {
                queries(lifecycle) { query -> viewModel.searchBeersByQuery(query) }
            }

        }
    }
}
