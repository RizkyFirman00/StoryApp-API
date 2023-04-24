package com.example.submissionintermediate.utils

import android.app.Application

class App: Application(){
    override fun onCreate() {
        super.onCreate()
        Injection.initWith(this)
    }
}