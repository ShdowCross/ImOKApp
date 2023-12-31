package com.example.imokapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        val healthMetrics = ImOKApp.Companion.HealthMetrics()
        val healthStatus = ImOKApp.Companion.HealthStatus()
        val healthNotification = ImOKApp.Companion.HealthNotifications()
        val graphData = ImOKApp.Companion.GraphData()
        category1.isGone = true
        category2.isGone = true
        category3.isGone = true
        category4.isGone = true
        category5.isGone = true

        //for isolatedSystolic and isolatedDiastolic for now we just put both highBP and lowBP fields but most likely is inaccurate "subject to change"

        // Category 1 questions
        if (healthStatus.highBP) {
            category1.isVisible = true
            c1q1RadioGroup = findViewById(R.id.c1q1_radio_group)
            c1q2RadioGroup = findViewById(R.id.c1q2_radio_group)
            c1q3RadioGroup = findViewById(R.id.c1q3_radio_group)
            c1q4RadioGroup = findViewById(R.id.c1q4_radio_group)
            c1q5RadioGroup = findViewById(R.id.c1q5_radio_group)
            c1q6RadioGroup = findViewById(R.id.c1q6_radio_group)
        }

        // Category 2 questions
        if(healthStatus.lowBP) {
            category2.isVisible = true
            c2q1RadioGroup = findViewById(R.id.c2q1_radio_group)
            c2q2RadioGroup = findViewById(R.id.c2q2_radio_group)
            c2q3RadioGroup = findViewById(R.id.c2q3_radio_group)
            c2q4RadioGroup = findViewById(R.id.c2q4_radio_group)
            c2q5RadioGroup = findViewById(R.id.c2q5_radio_group)
        }
        // Category 3 questions
        if(healthStatus.uWeight) {
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

        if(healthStatus.isolatedSystolic){
            category4.isVisible = true
        }
        if(healthStatus.isolatedDiastolic){
            category5.isVisible = true
        }

        val submitButton = findViewById<Button>(R.id.submit_button)
        submitButton.setOnClickListener {
            if (healthStatus.highBP){
                val category1YesCount = countYesAnswers(R.id.c1q1_yes, R.id.c1q2_yes, R.id.c1q3_yes, R.id.c1q4_yes, R.id.c1q5_yes, R.id.c1q6_yes)
                if (moreThanHalfQuestionsAnsweredYes(category1YesCount)) {
                    // Redirect users to a recommendation page for category 1
                    val intent = Intent(this, HBPRecommendationComplication::class.java)
                    startActivity(intent)
                }
                else{
                    healthNotification.takeCareNotif = true
                }
            }
            if(healthStatus.lowBP){
                val category2YesCount = countYesAnswers(R.id.c2q1_yes, R.id.c2q2_yes, R.id.c2q3_yes, R.id.c2q4_yes, R.id.c2q5_yes)
                if (moreThanHalfQuestionsAnsweredYes(category2YesCount)) {
                    // Redirect users to a recommendation page for category 2
                    val intent = Intent(this, LBPRecommendationComplication::class.java)
                    startActivity(intent)
                }
                else{
                    healthNotification.takeCareNotif = true
                }
            }
            if(healthStatus.uWeight){
                val category3YesCount = countYesAnswers(R.id.c3q1_yes, R.id.c3q2_yes, R.id.c3q3_yes, R.id.c3q4_yes, R.id.c3q5_yes, R.id.c3q6_yes, R.id.c3q7_yes, R.id.c3q8_yes, R.id.c3q9_yes, R.id.c3q10_yes)
                if (moreThanHalfQuestionsAnsweredYes(category3YesCount)) {
                    // Redirect users to a recommendation page for category 3
                    val intent = Intent(this, WLRecommendationComplication::class.java)
                    startActivity(intent)
                }
                else{
                    healthNotification.takeCareNotif = true
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

