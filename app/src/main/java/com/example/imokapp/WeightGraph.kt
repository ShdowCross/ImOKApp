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
import com.example.imokapp.ImOKApp.Companion.calculateWeightThreshold
import com.example.imokapp.ImOKApp.Companion.diastolic
import com.example.imokapp.ImOKApp.Companion.weight
import com.example.imokapp.ImOKApp.Companion.generateTimeLabels
import com.example.imokapp.ImOKApp.Companion.heightMeter
import com.example.imokapp.ImOKApp.Companion.highRiskBmi
import com.example.imokapp.ImOKApp.Companion.lowRiskBmi
import com.example.imokapp.ImOKApp.Companion.moderateRiskBmi
import com.example.imokapp.ImOKApp.Companion.systolic
import com.example.imokapp.ImOKApp.Companion.timeList
import com.example.imokapp.ImOKApp.Companion.uWeight
import com.example.imokapp.ImOKApp.Companion.weightArray
import com.example.imokapp.ImOKApp.Companion.weightAverage
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
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_weight_graph.*
import java.io.File
import java.io.IOException

class WeightGraph : AppCompatActivity() {
    private var surveyClassName = "com.example.imokapp.Survey"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_graph)

        val lowColor = ContextCompat.getColor(this, R.color.blue)
        val normalColor = ContextCompat.getColor(this, R.color.green)
        val highColor = ContextCompat.getColor(this, R.color.red)
        val highRiskBmiColor = ContextCompat.getColor(this, R.color.highRiskBmi)
        val moderateRiskBmiColor = ContextCompat.getColor(this, R.color.moderateRiskBmi)

        val warningLL = findViewById<LinearLayout>(R.id.warningLL)
        val warningView1 = findViewById<TextView>(R.id.warningView1)
        val warningView2 = findViewById<TextView>(R.id.warningView2)
        val warningSurveyView = findViewById<TextView>(R.id.warningSurveyView)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Alert")
        var message = ""

        // TODO make the normal threshold for the person
        weightAverage = weightValues.average().toFloat()

        if(weightValues.isNotEmpty()) {
            var weightSize = weightValues.size
            lastReadingWeightTV.text = weightValues[weightSize - 1].toString()
            if (highRiskBmi) {
                warningView1.text = "High Risk BMI"
                warningView1.setTextColor(highRiskBmiColor)
                warningSurveyView.text = "Tell us how you're feeling!"
                warningLL.isVisible = true
                if (weightNotificationOn) {
                    message += "You're BMI is becoming high risk, go see a doctor for medical advice. \n" + " "
                    weightNotificationOn = true
                }
            } else if (moderateRiskBmi) {
                warningView1.text = "Moderate Risk BMI"
                warningView1.setTextColor(moderateRiskBmiColor)
                warningSurveyView.text = "Click here to tell us how you're feeling!"
                warningLL.isVisible = true
                if (weightNotificationOn) {
                    message += "You're BMI is getting a little high, which resulted in an increase in BMI. \n What changed? \n"
                    weightNotificationOn = false
                }
            }
            else if (lowRiskBmi) {
                warningView1.text = "Normal BMI"
                warningView1.setTextColor(normalColor)
                warningLL.isInvisible = true
            }
            else if (uWeight) {
                warningView1.text = "Underweight"
                warningView1.setTextColor(lowColor)
                warningSurveyView.text = "Click here to tell us how you're feeling!"
                warningLL.isVisible = true
                if (weightNotificationOn) {
                    message += "Your BMI is going a little low \n"
                    weightNotificationOn = false
                }
            }
            else {
                warningView1.text = "Unspecified"
                warningView1.setTextColor(normalColor)
                warningLL.isInvisible = true
            }
            if (weightValues.size >= 5){
                var wThreshold = calculateWeightThreshold(weightAverage, 5f)
                if (weight >= weightAverage + wThreshold) {
                    // The weight is far above the person's norm
                    warningView2.text = "Warning: Weight Above Norm"
                    warningView2.setTextColor(highColor)
                    if (weightNotificationOn) {
                        message += "Your weight is significantly higher than your norm. \n If this is not normal, go see a doctor. \n"
                        if (weightValues.size < 30){
                            message += "\n If this is normal, don't be alarmed. \n Sudden spikes or drops could have a larger impact on your norm and will go away over time. \n"
                        }
                        weightNotificationOn = true
                    }
                }
                else if (weight <= weightAverage - wThreshold) {
                    // The weight is far below the person's norm
                    warningView2.text = "Warning: Weight Below Norm"
                    warningView2.setTextColor(lowColor)
                    if (weightNotificationOn) {
                        message += "Your weight is significantly lower than your norm. \n If this is not normal, go see a doctor. \n!"
                        if (weightValues.size < 30){
                            message += "\n If this is normal, don't be alarmed. \n Sudden spikes or drops could have a larger impact on your norm and will go away over time. \n"
                        }
                        weightNotificationOn = true
                    }
                }
                else{
                    warningView2.text = "Within Norm"
                    warningView2.setTextColor(normalColor)
                    weightNotificationOn = false
                }
            }
            alertDialogBuilder.setPositiveButton("Ok for now") { dialog, _ ->
                dialog.dismiss()
            }
            if (uWeight){
                alertDialogBuilder.setNegativeButton("Take Survey") { _, _ ->
                    val surveyClass = Class.forName(surveyClassName)
                    val toSurvey = Intent(this, surveyClass)
                    startActivity(toSurvey)
                }
                message += "Take our survey to get some recommendations \n"
            }
            alertDialogBuilder.setMessage(message)
            if (message != "") {
                alertDialogBuilder.show()
            }
            setUpWeightChart()
            setDataToWeightChart()
        }

        warningLL.setOnClickListener{
            if (surveyClassName.isNotEmpty()) {
                val surveyClass = Class.forName(surveyClassName)
                val toSurvey = Intent(this, surveyClass)
                startActivity(toSurvey)
            } else {
              Log.d("surveyClassName", "empty")
            }
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
                // Writing data to a file
                val gson = Gson()

// Convert ArrayList to regular List before writing
                val systolicList = systolic.toList()
                val diastolicList = diastolic.toList()
                val weightList = weightArray.toList()

                val data = mapOf(
                    "systolic" to systolicList,
                    "diastolic" to diastolicList,
                    "weight" to weightList,
                    "timeList" to timeList
                )

                val json = gson.toJson(data)

                try {
                    val file = File(filesDir, "data.json")
                    file.writeText(json)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

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

    fun setUpWeightChart() {
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

    fun setDataToWeightChart() {
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
