package com.example.cleanarchitecturebasicnewsapp.newsList

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cleanarchitecturebasicnewsapp.R
import com.example.cleanarchitecturebasicnewsapp.states.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class NewsListActivity : AppCompatActivity() {
    private val viewModel: NewsListViewModel by viewModels()

    companion object {
        const val ACTIVITY_TAG = "NewsListActivityss"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        viewModel.getTopHeadLInes("us")
        lifecycleScope.launch {

            viewModel.newsState.collect { state ->
                Log.d(ACTIVITY_TAG, "states: $state")
                when (state) {
                    is UiState.Error -> {
                        Log.d(ACTIVITY_TAG, "onCreate: ${state.message}")
                    }

                    UiState.Loading -> {
                        Log.d(ACTIVITY_TAG, "onCreate: Loading")
                    }

                    is UiState.Success -> {
                        Log.d(ACTIVITY_TAG, "onCreate: ${state.data}")
                    }
                }

            }
        }

    }
}