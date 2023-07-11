package com.example.imokapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_health_metrics.*

class HealthMetrics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_metrics)

        manualInputBtn.setOnClickListener() {
            var myIntent = Intent(this, ManualInput::class.java)
            startActivity(myIntent)
        }

        bloodPressureIV.setOnClickListener() {
            var myIntent = Intent(this, BpGraph::class.java)
            startActivity(myIntent)
        }

        weightIV.setOnClickListener() {
            var myIntent = Intent(this, WeightGraph::class.java)
            startActivity(myIntent)
        }

//        bpGraphBtn.setOnClickListener() {
//            var myIntent = Intent(this, BpGraph::class.java)
//            startActivity(myIntent)
//        }
//
//        weightGraphBtn.setOnClickListener() {
//            var myIntent = Intent(this, WeightGraph::class.java)
//            startActivity(myIntent)
//        }
    }
}