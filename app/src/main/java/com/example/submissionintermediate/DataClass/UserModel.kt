package com.example.submissionintermediate.DataClass

data class UserModel(
    val userId: String,
    val name: String,
    val email: String?,
    val password: String,
    val token: String
)
