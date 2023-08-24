package com.example.imokapp

import android.app.Person
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.imokapp.ImOKApp.Companion.highBP
import com.example.imokapp.ImOKApp.Companion.highRiskBmi
import com.example.imokapp.ImOKApp.Companion.isolatedDiastolic
import com.example.imokapp.ImOKApp.Companion.isolatedSystolic
import com.example.imokapp.ImOKApp.Companion.lowBP
import com.example.imokapp.ImOKApp.Companion.lowRiskBmi
import com.example.imokapp.ImOKApp.Companion.moderateRiskBmi
import com.example.imokapp.ImOKApp.Companion.takeCareNotif
import com.example.imokapp.ImOKApp.Companion.uWeight
import kotlinx.android.synthetic.main.activity_survey.*

class Survey : AppCompatActivity() {

    private lateinit var c1q1RadioGroup: RadioGroup
    private lateinit var c1q2RadioGroup: RadioGroup
    private lateinit var c1q3RadioGroup: RadioGroup
    private lateinit var c1q4RadioGroup: RadioGroup
    private lateinit var c1q5RadioGroup: RadioGroup
    private lateinit var c1q6RadioGroup: RadioGroup


    private lateinit var c2q1RadioGroup: RadioGroup
    private lateinit var c2q2RadioGroup: RadioGroup
    private lateinit var c2q3RadioGroup: RadioGroup
    private lateinit var c2q4RadioGroup: RadioGroup
    private lateinit var c2q5RadioGroup: RadioGroup

    private lateinit var c3q1RadioGroup: RadioGroup
    private lateinit var c3q2RadioGroup: RadioGroup
    private lateinit var c3q3RadioGroup: RadioGroup
    private lateinit var c3q4RadioGroup: RadioGroup
    private lateinit var c3q5RadioGroup: RadioGroup
    private lateinit var c3q6RadioGroup: RadioGroup
    private lateinit var c3q7RadioGroup: RadioGroup
    private lateinit var c3q8RadioGroup: RadioGroup
    private lateinit var c3q9RadioGroup: RadioGroup
    private lateinit var c3q10RadioGroup: RadioGroup

    private lateinit var c4q1RadioGroup: RadioGroup
    private lateinit var c4q2RadioGroup: RadioGroup
    private lateinit var c4q3RadioGroup: RadioGroup
    private lateinit var c4q4RadioGroup: RadioGroup
    private lateinit var c4q5RadioGroup: RadioGroup

    private lateinit var c5q1RadioGroup: RadioGroup
    private lateinit var c5q2RadioGroup: RadioGroup
    private lateinit var c5q3RadioGroup: RadioGroup
    private lateinit var c5q4RadioGroup: RadioGroup
    private lateinit var c5q5RadioGroup: RadioGroup
    private lateinit var c5q1Question: TextView
    private lateinit var c5q2Question: TextView
    private lateinit var c5q3Question: TextView
    private lateinit var c5q4Question: TextView
    private lateinit var c5q5Question: TextView

    private var hbp = false
    private var lbp = false
    private var wl = false
    private var iS = false
    private var iD = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        category1.isGone = true
        category2.isGone = true
        category3.isGone = true
        category4.isGone = true
        category5.isGone = true

        //for isolatedSystolic and isolatedDiastolic for now we just put both highBP and lowBP fields but most likely is inaccurate "subject to change"

        // Category 1 questions
        if (highBP) {
            category1.isVisible = true
            c1q1RadioGroup = findViewById(R.id.c1q1_radio_group)
            c1q2RadioGroup = findViewById(R.id.c1q2_radio_group)
            c1q3RadioGroup = findViewById(R.id.c1q3_radio_group)
            c1q4RadioGroup = findViewById(R.id.c1q4_radio_group)
            c1q5RadioGroup = findViewById(R.id.c1q5_radio_group)
            c1q6RadioGroup = findViewById(R.id.c1q6_radio_group)
        }

        // Category 2 questions
        if(lowBP) {
            category2.isVisible = true
            c2q1RadioGroup = findViewById(R.id.c2q1_radio_group)
            c2q2RadioGroup = findViewById(R.id.c2q2_radio_group)
            c2q3RadioGroup = findViewById(R.id.c2q3_radio_group)
            c2q4RadioGroup = findViewById(R.id.c2q4_radio_group)
            c2q5RadioGroup = findViewById(R.id.c2q5_radio_group)
        }
        // Category 3 questions
        if(uWeight) {
            category3.isVisible = true
            c3q1RadioGroup = findViewById(R.id.c3q1_radio_group)
            c3q2RadioGroup = findViewById(R.id.c3q2_radio_group)
            c3q3RadioGroup = findViewById(R.id.c3q3_radio_group)
            c3q4RadioGroup = findViewById(R.id.c3q4_radio_group)
            c3q5RadioGroup = findViewById(R.id.c3q5_radio_group)
            c3q6RadioGroup = findViewById(R.id.c3q6_radio_group)
            c3q7RadioGroup = findViewById(R.id.c3q7_radio_group)
            c3q8RadioGroup = findViewById(R.id.c3q8_radio_group)
            c3q9RadioGroup = findViewById(R.id.c3q9_radio_group)
            c3q10RadioGroup = findViewById(R.id.c3q10_radio_group)
        }

        if(isolatedSystolic){
            category4.isVisible = true
            c4q1RadioGroup = findViewById(R.id.c4q1_radio_group)
            c4q2RadioGroup = findViewById(R.id.c4q2_radio_group)
            c4q3RadioGroup = findViewById(R.id.c4q3_radio_group)
            c4q4RadioGroup = findViewById(R.id.c4q4_radio_group)
            c4q5RadioGroup = findViewById(R.id.c4q5_radio_group)
        }
        if(isolatedDiastolic){
            category5.isVisible = true
            c5q1RadioGroup = findViewById(R.id.c5q1_radio_group)
            c5q2RadioGroup = findViewById(R.id.c5q2_radio_group)
            c5q3RadioGroup = findViewById(R.id.c5q3_radio_group)
            c5q4RadioGroup = findViewById(R.id.c5q4_radio_group)
            c5q5RadioGroup = findViewById(R.id.c5q5_radio_group)
            c5q1Question = findViewById<TextView>(R.id.c5q1_text_view)
            c5q2RadioGroup = findViewById(R.id.c5q2_radio_group)
            c5q3RadioGroup = findViewById(R.id.c5q3_radio_group)
            c5q4RadioGroup = findViewById(R.id.c5q4_radio_group)
            c5q5RadioGroup = findViewById(R.id.c5q5_radio_group)
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Alert")
        var message = ""

        val submitButton = findViewById<Button>(R.id.submit_button)
        submitButton.setOnClickListener {
            if(!highBP and !lowBP and !uWeight and !isolatedSystolic and !isolatedDiastolic){
                takeCareNotif = true
                val intent = Intent(this, HealthMetrics::class.java)
                startActivity(intent)
            }
            else if(isolatedSystolic or isolatedDiastolic){
                val category4YesCount = countYesAnswers(R.id.c4q1_yes, R.id.c4q2_yes, R.id.c4q3_yes, R.id.c4q4_yes, R.id.c4q5_yes)
                val category5YesCount = countYesAnswers(R.id.c5q1_yes, R.id.c5q2_yes, R.id.c5q3_yes, R.id.c5q4_yes, R.id.c5q5_yes)
                if (moreThanHalfQuestionsAnsweredYes(category4YesCount)) {
                    iS = true
                    message += "\n Your Blood Pressure contains unusual readings, please visit a doctor to identify the cause. \n"
                }
                else{
                    if (moreThanHalfQuestionsAnsweredYes(category5YesCount)) {
                        iD = true
                        message += "\n Your Blood Pressure contains unusual readings, please visit a doctor to identify the cause. \n"
                    }
                    else{
                        takeCareNotif = true
                    }
                }
                if (iS or iD){
                    alertDialogBuilder.setMessage(message)
                    if (message != "") {
                        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
                            val intent = Intent(this, PersonContacts::class.java)
                            startActivity(intent)
                        }
                        val alertDialog = alertDialogBuilder.create()
                        alertDialog.setCancelable(false) // Prevent tapping outside of the dialog to dismiss it
                        alertDialog.show()
                    }
                }
                else{
                    val intent = Intent(this, HealthMetrics::class.java)
                    startActivity(intent)
                }
            } else{
                if (highBP){
                    val category1YesCount = countYesAnswers(R.id.c1q1_yes, R.id.c1q2_yes, R.id.c1q3_yes, R.id.c1q4_yes, R.id.c1q5_yes, R.id.c1q6_yes)
                    if (moreThanHalfQuestionsAnsweredYes(category1YesCount)) {
                        hbp = true
                    }
                }
                if(lowBP){
                    val category2YesCount = countYesAnswers(R.id.c2q1_yes, R.id.c2q2_yes, R.id.c2q3_yes, R.id.c2q4_yes, R.id.c2q5_yes)
                    if (moreThanHalfQuestionsAnsweredYes(category2YesCount)) {
                        lbp = true
                    }
                }
                if(uWeight){
                    val category3YesCount = countYesAnswers(R.id.c3q1_yes, R.id.c3q2_yes, R.id.c3q3_yes, R.id.c3q4_yes, R.id.c3q5_yes, R.id.c3q6_yes, R.id.c3q7_yes, R.id.c3q8_yes, R.id.c3q9_yes, R.id.c3q10_yes)
                    if (moreThanHalfQuestionsAnsweredYes(category3YesCount)) {
                        wl = true
                    }
                }
                if(highRiskBmi){

                }
                if(moderateRiskBmi){

                }
                if(lowRiskBmi){
                    takeCareNotif = true
                }
                else{
                    val intent = Intent(this, Recommendations::class.java)
                    intent.putExtra("hbp", hbp)
                    intent.putExtra("lbp", lbp)
                    intent.putExtra("wl", wl)
                    startActivity(intent)
                }
            }
        }
    }

    private fun countYesAnswers(vararg radioButtonIds: Int): Int {
        var yesCount = 0
        for (radioButtonId in radioButtonIds) {
            val radioButton = findViewById<RadioButton>(radioButtonId)
            if (radioButton.isChecked && radioButton.text == "Yes") {
                yesCount++
            }
        }
        return yesCount
    }

    private fun moreThanHalfQuestionsAnsweredYes(yesCount: Int): Boolean {
        return yesCount > 1
    }
}

