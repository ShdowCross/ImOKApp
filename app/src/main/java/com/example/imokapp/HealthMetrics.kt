package com.example.imokapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.activity_health_metrics.*

class HealthMetrics : AppCompatActivity() {

//    val HEALTH_METRICS_RESULT_CODE = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_metrics)

        var weightFromManualInput = intent.getStringExtra("enter_weight")
        var heartRateFromManualInput = intent.getStringExtra("enter_heart_rate")
        var bloodPressureFromManualInput = intent.getStringExtra("enter_blood_pressure")

        weightKgTV.text = "$weightFromManualInput"
        hrBPMTV.text = "$heartRateFromManualInput bpm"
        bpmmHgTV.text = "$bloodPressureFromManualInput mmHg"


        var weightForBMI = weightFromManualInput?.toFloat()
        var heightForBMI = (heightMTV.text as String).toFloat()

        var calculatedBMI = weightForBMI?.div((heightForBMI * heightForBMI))

        bmiMessurementTV.text = String.format("%.2f",calculatedBMI) + " kg/m\u00B2"

    }

    fun manualInput(v:View) {
        var myIntent = Intent(this, ManualInput::class.java)
        startActivity(myIntent)
    }

    fun weightGraph(v:View) {
        var myIntent = Intent(this, WeightGraph::class.java)
        startActivity(myIntent)
    }

    fun BpGraph(v:View) {
        var myIntent = Intent(this, BpGraph::class.java)
        startActivity(myIntent)
    }
}