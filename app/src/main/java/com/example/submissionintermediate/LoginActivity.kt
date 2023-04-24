package com.example.submissionintermediate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.submissionintermediate.DataStore.ViewModel.AuthViewModel
import com.example.submissionintermediate.databinding.ActivityLoginBinding
import com.example.submissionintermediate.utils.Injection
import com.example.submissionintermediate.utils.ResultMain
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<AuthViewModel>(factoryProducer = { Injection.autViewModelFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Login Starbags"

        lifecycleScope.launch {
            viewModel.loginResponse.collect {
                when (it.resultVerifyUser) {
                    is ResultMain.Success<String> -> {
                        startActivity(
                            Intent(this@LoginActivity, MainActivity::class.java)
                        )
                        finish()}
                    is ResultMain.Loading -> showLoading(true)
                    is ResultMain.Error -> {
                        showLoading(false)
                        Toast.makeText(
                            this@LoginActivity,
                            it.resultVerifyUser.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> Unit
                }
            }
        }

        //Button Login untuk login
        binding.buttonLogin.setOnClickListener {
            val emailUser = binding.emailUser.text.toString()
            val passwordUser = binding.passwordUser.text.toString()
            if (emailUser.isEmpty() || passwordUser.isEmpty()) {
                Toast.makeText(this, "Email atau password tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.login(emailUser, passwordUser)
            }
        }

        //Button pindah Ke halaman Register
        binding.registerTxt.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}