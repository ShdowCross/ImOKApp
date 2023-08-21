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
import com.example.imokapp.ImOKApp.Companion.addWeightData
import com.example.imokapp.ImOKApp.Companion.calculateBMI
import com.example.imokapp.ImOKApp.Companion.calculateWeightThreshold
import com.example.imokapp.ImOKApp.Companion.generateTimeLabels
import com.example.imokapp.ImOKApp.Companion.graphData
import com.example.imokapp.ImOKApp.Companion.healthMetrics
import com.example.imokapp.ImOKApp.Companion.healthNotification
import com.example.imokapp.ImOKApp.Companion.healthStatus
import com.example.imokapp.ImOKApp.Companion.personInfo
import com.example.imokapp.ImOKApp.Companion.writeGraphDataJson
import com.example.imokapp.ImOKApp.Companion.writeHealthMetricsJson
import com.example.imokapp.ImOKApp.Companion.writeHealthNotificationsJson
import com.example.imokapp.ImOKApp.Companion.writeHealthStatusJson
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

        val infoData = ImOKApp.pullInfo(filesDir)
        personInfo = infoData?.personInfo ?: ImOKApp.Companion.PersonInfo()
        healthMetrics = infoData?.healthMetrics ?: ImOKApp.Companion.HealthMetrics()
        healthStatus = infoData?.healthStatus ?: ImOKApp.Companion.HealthStatus()
        healthNotification = infoData?.healthNotifications ?: ImOKApp.Companion.HealthNotifications()
        graphData = infoData?.graphData ?: ImOKApp.Companion.GraphData()

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

        val healthMetrics = ImOKApp.healthMetrics
        val healthStatus = ImOKApp.healthStatus
        val healthNotification = ImOKApp.healthNotification
        val graphData = ImOKApp.graphData

        // TODO make the normal threshold for the person
        healthMetrics.weightAverage = graphData.weightArray.map { it.y }.average().toFloat()
        
        if((graphData.weightArray).isNotEmpty()) {
            var weightSize = graphData.weightArray.size
            lastReadingWeightTV.text = (graphData.weightArray).last().y.toString()
            if (healthStatus.highRiskBmi) {
                warningView1.text = "High Risk BMI"
                warningView1.setTextColor(highRiskBmiColor)
                warningSurveyView.text = "Tell us how you're feeling!"
                warningLL.isVisible = true
                if (healthNotification.weightNotificationOn) {
                    message += "You're BMI is becoming high risk, go see a doctor for medical advice. \n" + " "
                    healthNotification.weightNotificationOn = true
                }
            } else if (healthStatus.moderateRiskBmi) {
                warningView1.text = "Moderate Risk BMI"
                warningView1.setTextColor(moderateRiskBmiColor)
                warningSurveyView.text = "Click here to tell us how you're feeling!"
                warningLL.isVisible = true
                if (healthNotification.weightNotificationOn) {
                    message += "You're BMI is getting a little high, which resulted in an increase in BMI. \n What changed? \n"
                    healthNotification.weightNotificationOn = false
                }
            }
            else if (healthStatus.lowRiskBmi) {
                warningView1.text = "Normal BMI"
                warningView1.setTextColor(normalColor)
                warningLL.isInvisible = true
            }
            else if (healthStatus.uWeight) {
                warningView1.text = "Underweight"
                warningView1.setTextColor(lowColor)
                warningSurveyView.text = "Click here to tell us how you're feeling!"
                warningLL.isVisible = true
                if (healthNotification.weightNotificationOn) {
                    message += "Your BMI is going a little low \n"
                    healthNotification.weightNotificationOn = false
                }
            }
            else {
                warningView1.text = "Unspecified"
                warningView1.setTextColor(normalColor)
                warningLL.isInvisible = true
            }
            if ((graphData.weightArray).size >= 5){
                var wThreshold = calculateWeightThreshold(healthMetrics.weightAverage, 5f, healthMetrics)
                if (healthMetrics.weight >= healthMetrics.weightAverage + wThreshold) {
                    // The weight is far above the person's norm
                    warningView2.text = "Warning: Weight Above Norm"
                    warningView2.setTextColor(highColor)
                    if (healthNotification.weightNotificationOn) {
                        message += "Your weight is significantly higher than your norm. \n If this is not normal, go see a doctor. \n"
                        if ((graphData.weightArray).size < 30){
                            message += "\n If this is normal, don't be alarmed. \n Sudden spikes or drops could have a larger impact on your norm and will go away over time. \n"
                        }
                        healthNotification.weightNotificationOn = true
                    }
                }
                else if (healthMetrics.weight <= healthMetrics.weightAverage - wThreshold) {
                    // The weight is far below the person's norm
                    warningView2.text = "Warning: Weight Below Norm"
                    warningView2.setTextColor(lowColor)
                    if (healthNotification.weightNotificationOn) {
                        message += "Your weight is significantly lower than your norm. \n If this is not normal, go see a doctor. \n!"
                        if ((graphData.weightArray).size < 30){
                            message += "\n If this is normal, don't be alarmed. \n Sudden spikes or drops could have a larger impact on your norm and will go away over time. \n"
                        }
                        healthNotification.weightNotificationOn = true
                    }
                }
                else{
                    warningView2.text = "Within Norm"
                    warningView2.setTextColor(normalColor)
                    healthNotification.weightNotificationOn = false
                }
            }
            alertDialogBuilder.setPositiveButton("Ok for now") { dialog, _ ->
                dialog.dismiss()
            }
            if (healthStatus.uWeight){
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
                healthMetrics.weight = weightText.toFloat()
                var bmiValue = calculateBMI(healthMetrics.weight, healthMetrics.heightMeter, healthMetrics)
                if (bmiValue >= "27.5".toFloat()) {
                    healthStatus.highRiskBmi = true
                    healthStatus.moderateRiskBmi = false
                    healthStatus.lowRiskBmi = false
                    healthStatus.uWeight = false
                }
                else if (bmiValue.toInt() >= 23 && bmiValue.toInt() < 27.5) {
                    healthStatus.highRiskBmi = false
                    healthStatus.moderateRiskBmi = true
                    healthStatus.lowRiskBmi = false
                    healthStatus.uWeight = false
                }
                else if (bmiValue.toInt() >= 18.5 && bmiValue.toInt() < 23) {
                    healthStatus.highRiskBmi = false
                    healthStatus.moderateRiskBmi = false
                    healthStatus.lowRiskBmi = true
                    healthStatus.uWeight = false
                }
                else{
                    healthStatus.highRiskBmi = false
                    healthStatus.moderateRiskBmi = false
                    healthStatus.lowRiskBmi = false
                    healthStatus.uWeight = true
                }
                addWeightData(healthMetrics.weight, graphData)
                healthNotification.weightNotificationOn = true
                graphData.timeList.add(generateTimeLabels())
                writeHealthMetricsJson(
                    weightThreshold = healthMetrics.weightThreshold,
                    weight = healthMetrics.weight,
                    weightAverage = healthMetrics.weightAverage,
                    filesDir = filesDir
                )
                writeHealthStatusJson(
                    uWeight = healthStatus.uWeight,
                    filesDir = filesDir
                )
                writeHealthNotificationsJson(
                    weightNotificationOn = healthNotification.weightNotificationOn,
                    filesDir = filesDir
                )
                writeGraphDataJson(
                    systolic = graphData.weightArray,
                    timeList = graphData.timeList,
                    filesDir = filesDir
                )

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
        val graphData = ImOKApp.Companion.GraphData()
        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            val index = value.toInt()
            return if (index in graphData.timeList.indices) {
                graphData.timeList[index]
            } else {
                ""
            }
        }
    }

    fun setDataToWeightChart() {
        val graphData = ImOKApp.Companion.GraphData()
        val weightDataset = LineDataSet(graphData.weightArray, "Weight")
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
