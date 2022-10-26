package com.ergea.latihan_firebase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ergea.latihan_firebase.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
    }
}