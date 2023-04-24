package com.example.submissionintermediate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionintermediate.DataClass.Model.StoryModelEntity
import com.example.submissionintermediate.DataClass.StoryModel
import com.example.submissionintermediate.DataStore.UserAuthRepository
import com.example.submissionintermediate.DataStore.ViewModel.MainViewModel
import com.example.submissionintermediate.databinding.ActivityMainBinding
import com.example.submissionintermediate.utils.Injection
import com.example.submissionintermediate.utils.ResultMain
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {StoriesAdapter()}
    private val viewModel by viewModels<MainViewModel>(factoryProducer = {Injection.storyViewModelFactory})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "Stories"

        binding.rvStories.layoutManager = LinearLayoutManager(this)
        binding.rvStories.setHasFixedSize(true)
        binding.rvStories.adapter = adapter

        lifecycleScope.launch {
            viewModel.storyListState.collect {
                when (it.resultStories) {
                    is ResultMain.Success -> {
                        it.resultStories.value?.let { stories ->
                            adapter.dataSet(stories)
                        }
                        showLoading(false)
                    }
                    is ResultMain.Loading -> showLoading(true)
                    is ResultMain.Error -> {
                        showLoading(false)
                        Log.d("Error", it.resultStories.message)
                        Toast.makeText(
                            this@MainActivity,
                            it.resultStories.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> Unit
                }
                binding.tvUsername.text = it.name
            }
        }
    }

    override fun onResume() {
            super.onResume()
            viewModel.getListStory()
            viewModel.getUserData()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}