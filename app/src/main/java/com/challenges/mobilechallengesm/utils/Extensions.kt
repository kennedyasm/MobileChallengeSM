package com.challenges.mobilechallengesm.utils

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

internal fun SearchView.queries(
    lifecycle: Lifecycle,
    mQuery: (textChange: String?) -> Unit
) {
    var stateJob: Job? = null
    val coroutineScope = lifecycle.coroutineScope
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            mQuery(query)
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            stateJob?.cancel()
            stateJob = coroutineScope.launchWhenStarted {
                newText?.let {
                    delay(500)
                    mQuery(it)
                }
            }
            return true
        }
    })
}