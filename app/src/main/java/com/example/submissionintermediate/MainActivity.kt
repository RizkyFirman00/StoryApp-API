package com.example.submissionintermediate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionintermediate.DataStore.UserRepository
import com.example.submissionintermediate.ViewModel.MainViewModel
import com.example.submissionintermediate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<MainViewModel> {
        MainViewModel.Factory(UserRepository(this))
    }
    private val adapter by lazy {
        StoriesAdapter() { story ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", story)
                startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvStories.layoutManager = LinearLayoutManager(this)
        binding.rvStories.setHasFixedSize(true)
        binding.rvStories.adapter = adapter

        viewModel.getUserToken().observe(this) { token ->
            if (token != null) {
                val token = token
                Log.d("Token Home Menu", token)
                viewModel.getStories(token)
                adapter.setData(viewModel.storiesUser.value!!)
            }
        }
    }
}