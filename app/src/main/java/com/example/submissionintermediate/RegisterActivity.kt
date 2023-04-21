package com.example.submissionintermediate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.submissionintermediate.DataStore.UserRepository
import com.example.submissionintermediate.ViewModel.AuthViewModel
import com.example.submissionintermediate.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModel.Factory(UserRepository(this))
    }
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
                viewModel.userRegister(name, email, password)
                Intent (this, LoginActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }

    }
}