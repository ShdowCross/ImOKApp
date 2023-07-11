package com.example.imokapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_manual_input.*

class ManualInput : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_input)

        saveBtn.setOnClickListener() {
            var myIntent = Intent(this, HealthMetrics::class.java)
            startActivity(myIntent)
        }

        cancelBtn.setOnClickListener() {
            var myIntent = Intent(this, HealthMetrics::class.java)
            startActivity(myIntent)
        }
    }
}