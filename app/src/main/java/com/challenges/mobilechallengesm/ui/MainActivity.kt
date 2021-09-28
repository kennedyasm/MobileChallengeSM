package com.challenges.mobilechallengesm.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.challenges.mobilechallengesm.R
import com.challenges.mobilechallengesm.ui.viewmodel.BeersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: BeersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initObservers()
    }

    private fun initObservers() {
        viewModel.beersItems.observe(::getLifecycle) {
            Log.d("kTest -> ", "SUCCESS_DATA :: $it")
        }
    }
}