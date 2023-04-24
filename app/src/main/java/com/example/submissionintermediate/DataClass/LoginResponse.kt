package com.example.submissionintermediate.DataClass

data class LoginResponse(
    val error: Boolean,
    val loginResult: LoginResult,
    val message: String
)

data class LoginResult(
    val userId: String,
    val name: String,
    val email: String,
    val token: String
)
