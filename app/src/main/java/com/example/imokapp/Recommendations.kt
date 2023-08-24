package com.example.imokapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.imokapp.ImOKApp.Companion.hbpAnswered
import com.example.imokapp.ImOKApp.Companion.iDAnswered
import com.example.imokapp.ImOKApp.Companion.iSAnswered
import com.example.imokapp.ImOKApp.Companion.lbpAnswered
import com.example.imokapp.ImOKApp.Companion.wlAnswered

class Recommendations : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendations)
        val medicalButton = findViewById<Button>(R.id.medical_button)
        val hbpRecommendations = findViewById<LinearLayout>(R.id.HBP)
        val lowRecommendations = findViewById<LinearLayout>(R.id.LBP)
        val wlRecommendations = findViewById<LinearLayout>(R.id.WL)

        val intent = intent
        val highBloodPressure = intent.getBooleanExtra("hbp", false)
        val lowBloodPressure = intent.getBooleanExtra("lbp", false)
        val weightLoss = intent.getBooleanExtra("wl", false)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Alert")
        var message = ""
        if (highBloodPressure) {
            if (hbpAnswered){
                hbpRecommendations.visibility = View.VISIBLE
            }
            message += "You may have some high blood pressure.\n"
            hbpAnswered = true
        } else {
            hbpRecommendations.visibility = View.GONE
            hbpAnswered = false
        }
        if (lowBloodPressure) {
            if (lbpAnswered){
                lowRecommendations.visibility = View.VISIBLE
            }
            message += "You may have some low blood pressure.\n"
            lbpAnswered = true
        } else {
            lowRecommendations.visibility = View.GONE
            lbpAnswered = false
        }

        if (weightLoss) {
            if (wlAnswered){
                wlRecommendations.visibility = View.VISIBLE
            }
            message += "You may be a little underweight\n"
            wlAnswered = true
        } else {
            wlRecommendations.visibility = View.GONE
            wlAnswered = false
        }
        message += "Please click on the medical contacts for fast access to doctors and relatives"
        alertDialogBuilder.setMessage(message)
        if (message != "") {
            alertDialogBuilder.show()
        }
        if (iSAnswered or iDAnswered){
            iSAnswered = false
            iDAnswered = false
        }
        medicalButton.setOnClickListener {
            val intent = Intent(this, PersonContacts::class.java)
            startActivity(intent)
        }
    }
}