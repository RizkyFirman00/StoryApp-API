package com.example.submissionintermediate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.submissionintermediate.DataStore.ViewModel.AuthViewModel
import com.example.submissionintermediate.databinding.ActivityRegisterBinding
import com.example.submissionintermediate.utils.Injection
import com.example.submissionintermediate.utils.ResultMain
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<AuthViewModel>(factoryProducer = { Injection.autViewModelFactory })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Register Starbags"

        binding.loginTxt.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.buttonRegister.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                binding.edRegisterName.error = "Nama tidak boleh kosong"
                binding.edRegisterEmail.error = "Email tidak boleh kosong"
                binding.edRegisterPassword.error = "Password tidak boleh kosong"
            } else {
                lifecycleScope.launch {
                    viewModel.register(name, email, password)
                    viewModel.registerResponse.collect {
                        when (it.resultVerifyUser) {
                            is ResultMain.Success<String> -> {
                                showLoading(false)
                                startActivity(
                                    Intent(this@RegisterActivity, LoginActivity::class.java)
                                )
                                finish()
                            }
                            is ResultMain.Loading -> showLoading(true)
                            is ResultMain.Error -> {
                                showLoading(false)
                                Toast.makeText(
                                    this@RegisterActivity,
                                    it.resultVerifyUser.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else -> Unit
                        }
                    }
                }
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}