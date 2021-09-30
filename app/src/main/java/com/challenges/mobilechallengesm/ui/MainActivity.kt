package com.challenges.mobilechallengesm.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import com.challenges.mobilechallengesm.R
import com.challenges.mobilechallengesm.databinding.ActivityMainBinding
import com.challenges.mobilechallengesm.ui.viewmodel.BeersViewModel
import com.challenges.mobilechallengesm.utils.queries
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
        private set

    private val viewModel: BeersViewModel by viewModels()

    private val expandListener = object : MenuItem.OnActionExpandListener {

        override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
            showSearchBeersDialog()
            return true
        }

        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
            hideSearchBeersDialog()
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadMainFragment()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    private fun loadMainFragment() = supportFragmentManager.beginTransaction()
        .replace(binding.fragmentContent.id, BeersMainFragment())
        .commit()

    private fun showSearchBeersDialog() = with(supportFragmentManager.beginTransaction()) {
        add(binding.fragmentContent.id, SearchBeersDialogFragment(), SearchBeersDialogFragment.TAG)
        commit()
    }

    private fun hideSearchBeersDialog() = with(supportFragmentManager) {
        (findFragmentByTag(SearchBeersDialogFragment.TAG) as? DialogFragment)?.dismiss()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        findSearchActionView(menu)
        return true
    }

    private fun findSearchActionView(menu: Menu?) = menu?.findItem(R.id.mSearch)?.let { menuItem ->

        menuItem.setOnActionExpandListener(expandListener)?.also {

            (it.actionView as? SearchView)?.apply {
                queries(lifecycle) { query -> query?.let { viewModel.searchByQuery(query) } }
            }

        }
    }
}
