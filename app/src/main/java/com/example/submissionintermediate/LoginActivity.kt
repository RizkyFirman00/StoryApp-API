package com.example.submissionintermediate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.submissionintermediate.DataStore.UserRepository
import com.example.submissionintermediate.ViewModel.AuthViewModel
import com.example.submissionintermediate.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModel.Factory(UserRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Login Starbags"

        //Button pindah Ke halaman Register
        binding.registerTxt.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        //Button Login
        binding.buttonLogin.setOnClickListener {
            val emailUser = binding.emailUser.text.toString()
            val passwordUser = binding.passwordUser.text.toString()
            if (emailUser.isEmpty() || passwordUser.isEmpty()) {
                Toast.makeText(this, "Email atau password tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.userAuthLogin(emailUser, passwordUser)
                viewModel.user.observe(this) { event ->
                    event.error?.toBoolean().let { error ->
                        if (!error!!) {
                            viewModel.saveToken(event.loginResult.token)
                            val token = viewModel.getToken().toString()
                            Log.d("token", token)
                            event.loginResult.email?.let { it1 -> viewModel.saveEmail(it1) }
                            event.loginResult.name?.let { it1 -> viewModel.saveName(it1) }
                            Intent(this, MainActivity::class.java).also {
                                startActivity(it)
                                finish()
                            }
                        } else {
                            val msg = "Salah password atau email"
                            Toast.makeText(this, "$msg", Toast.LENGTH_SHORT).show()
                            binding.passwordUser.apply {
                                text?.clear()
                                requestFocus()
                                setError(null)
                            }
                        }
                    }
                }
            }
        }
    }
}