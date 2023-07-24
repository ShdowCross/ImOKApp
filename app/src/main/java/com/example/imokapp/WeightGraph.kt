package com.example.imokapp

import android.content.Intent
import android.graphics.Color.green
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.imokapp.ImOKApp.Companion.BMI
import com.example.imokapp.ImOKApp.Companion.addWeightData
import com.example.imokapp.ImOKApp.Companion.calculateBMI
import com.example.imokapp.ImOKApp.Companion.weight
import com.example.imokapp.ImOKApp.Companion.generateTimeLabels
import com.example.imokapp.ImOKApp.Companion.heightMeter
import com.example.imokapp.ImOKApp.Companion.highRiskBmi
import com.example.imokapp.ImOKApp.Companion.lowRiskBmi
import com.example.imokapp.ImOKApp.Companion.moderateRiskBmi
import com.example.imokapp.ImOKApp.Companion.timeList
import com.example.imokapp.ImOKApp.Companion.uWeight
import com.example.imokapp.ImOKApp.Companion.weightArray
import com.example.imokapp.ImOKApp.Companion.weightNotificationOn
import com.example.imokapp.ImOKApp.Companion.weightValues
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_weight_graph.*

class WeightGraph : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_graph)

        var warningLL = findViewById<LinearLayout>(R.id.warningLL)
        var warningView = findViewById<TextView>(R.id.warningView)
        var warningSurveyView = findViewById<TextView>(R.id.warningSurveyView)

        val lowColor = ContextCompat.getColor(this, R.color.blue)
        val normalColor = ContextCompat.getColor(this, R.color.green)
        val highColor = ContextCompat.getColor(this, R.color.red)
        val highRiskBmiColor = ContextCompat.getColor(this, R.color.highRiskBmi)
        val moderateRiskBmiColor = ContextCompat.getColor(this, R.color.moderateRiskBmi)
        val lowRiskBmiColor = ContextCompat.getColor(this, R.color.lowRiskBmi)
        val uWeightColor = ContextCompat.getColor(this, R.color.uWeight)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Alert")
        var message = ""
        var surveyClassName = ""

        // TODO make the normal threshold for the person
        // TODO try to correlate this to the height value as the actual app takes from that
        if(weightValues.isNotEmpty()) {
            var weightSize = weightValues.size
            lastReadingWeightTV.text = weightValues[weightSize - 1].toString()
            if (highRiskBmi) {
                warningView.text = "High Risk BMI"
                warningView.setTextColor(highRiskBmiColor)
                warningSurveyView.text = "Click here to tell us how you're feeling!"
                warningLL.isVisible = true
                if (weightNotificationOn) {
                    message += "You're weight is extremely high from your norm, if this is not normal, go see a doctor. \n Take our survey as well to get some recommendations!"
                    surveyClassName = "com.example.imokapp.Survey"
                    weightNotificationOn = true
                }
            } else if (moderateRiskBmi) {
                warningView.text = "Moderate Risk BMI"
                warningView.setTextColor(moderateRiskBmiColor)
                warningSurveyView.text = "Click here to tell us how you're feeling!"
                warningLL.isVisible = true
                if (weightNotificationOn) {
                    message += "You're weight is getting a little high, which resulted in an increase in BMI. What changed? \n Take our survey to get some recommendations"
                    surveyClassName = "com.example.imokapp.Survey"
                    weightNotificationOn = false
                }
            }
            else if (lowRiskBmi) {
                warningView.text = "Normal BMI"
                warningView.setTextColor(lowRiskBmiColor)
                warningLL.isInvisible = true
            }
            else if (uWeight) {
                warningView.text = "Underweight"
                warningView.setTextColor(lowColor)
                warningSurveyView.text = "Click here to tell us how you're feeling!"
                warningLL.isVisible = true
                if (weightNotificationOn) {
                    message += "Your BMI is a little low, is this normal? "
                    surveyClassName = "com.example.imokapp.Survey"
                    weightNotificationOn = false
                }
            }
            else {
                warningView.text = "Unspecified"
                warningView.setTextColor(normalColor)
                warningLL.isInvisible = true
            }
            alertDialogBuilder.setMessage(message)
            alertDialogBuilder.setPositiveButton("Ok for now") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton("Take Survey") { _, _ ->
                val surveyClass = Class.forName(surveyClassName)
                val toSurvey = Intent(this, surveyClass)
                startActivity(toSurvey)
            }
            if (message != "") {
                alertDialogBuilder.show()
            }
            setUpWeightChart()
            setDataToWeightChart()
        }

        warningLL.setOnClickListener{
            val surveyClass = Class.forName(surveyClassName)
            val toSurvey = Intent(this, surveyClass)
            startActivity(toSurvey)
        }

        var submitBtn = findViewById<Button>(R.id.weightSubmitBtn)
        submitBtn.setOnClickListener {
            var weightET = findViewById<EditText>(R.id.weightEntryET)
            var weightText = weightET.text.toString()
            weightET.setText("")
            weightET.clearFocus()

            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(submitBtn.windowToken, 0)

            if (weightText.isEmpty()) {
                weightET.error = "Weight Value is Empty"
            }
            else {
                weight = weightText.toFloat()
                var bmiValue = calculateBMI(weight, heightMeter)
                if (bmiValue >= "27.5".toFloat()) {
                    highRiskBmi = true
                    moderateRiskBmi = false
                    lowRiskBmi = false
                    uWeight = false
                }
                else if (bmiValue.toInt() >= 23 && bmiValue.toInt() < 27.5) {
                    highRiskBmi = false
                    moderateRiskBmi = true
                    lowRiskBmi = false
                    uWeight = false
                }
                else if (bmiValue.toInt() >= 18.5 && bmiValue.toInt() < 23) {
                    highRiskBmi = false
                    moderateRiskBmi = false
                    lowRiskBmi = true
                    uWeight = false
                }
                else{
                    highRiskBmi = false
                    moderateRiskBmi = false
                    lowRiskBmi = false
                    uWeight = true
                }
                addWeightData(weight)
                weightValues.add(weight)
                weightNotificationOn = true
                timeList.add(generateTimeLabels())
                recreate()
            }
        }
        var back = findViewById<Button>(R.id.backBtn)
        back.setOnClickListener{
            var backToDashboard = Intent(this,HealthMetrics::class.java)
            startActivity(backToDashboard)
        }
        var profile = findViewById<ImageView>(R.id.profilePictureIV)
        profile.setOnClickListener{
            var toProfile = Intent(this, PatientProfile::class.java)
            startActivity(toProfile)
        }
    }

    private fun setUpWeightChart() {
        with(WeightChart) {

            axisRight.isEnabled = true
            animateX(1200, Easing.EaseInSine)

            description.isEnabled = false

            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.valueFormatter = MyAxisFormatter()
            xAxis.granularity = 1F
            xAxis.setDrawGridLines(true)
            xAxis.setDrawAxisLine(true)
            axisLeft.setDrawGridLines(true)
            extraRightOffset = 30f

            legend.isEnabled = true
            legend.textSize = 15f
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            legend.form = Legend.LegendForm.LINE

        }
    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            val index = value.toInt()
            return if (index in timeList.indices) {
                timeList[index]
            } else {
                ""
            }
        }
    }

    private fun setDataToWeightChart() {
        val weightDataset = LineDataSet(weightArray, "Weight")
        weightDataset.lineWidth = 3f
        weightDataset.valueTextSize = 12f
        weightDataset.mode = LineDataSet.Mode.CUBIC_BEZIER
        weightDataset.color = ContextCompat.getColor(this, R.color.green)
        weightDataset.valueTextColor = ContextCompat.getColor(this, R.color.green)
//    weightDataset.enableDashedLine(20F, 10F, 0F)
//    weightDataset.disableDashedLine()

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(weightDataset)

        val lineData = LineData(dataSet)
        WeightChart.data = lineData

        WeightChart.invalidate()
    }
}
