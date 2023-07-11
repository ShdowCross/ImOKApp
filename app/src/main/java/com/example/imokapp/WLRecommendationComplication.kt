package com.example.imokapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WLRecommendationComplication : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wl_rc)

        val medicalButton = findViewById<Button>(R.id.medical_button)
        medicalButton.setOnClickListener {
            val intent = Intent(this, PersonContacts::class.java)
            startActivity(intent)
        }
    }

}