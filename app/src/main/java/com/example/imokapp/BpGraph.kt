package com.example.imokapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_bp_graph.*

class BpGraph : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bp_graph)

        setUpBpChart()
        setDataToBpChart()
    }

    private fun setUpBpChart() {
        with(BpChart) {

            axisRight.isEnabled = false
            animateX(1200, Easing.EaseInSine)

            description.isEnabled = false

            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.valueFormatter = MyAxisFormatter()
            xAxis.granularity = 1F
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)
            axisLeft.setDrawGridLines(false)
            extraRightOffset = 30f

            legend.isEnabled = true
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.form = Legend.LegendForm.LINE

        }
    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        private var time = arrayListOf("0700", "1200", "1700", "2200")

        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            val index = value.toInt()
            return if (index < time.size) {
                time[index]
            } else {
                null
            }
        }
    }

    private fun setDataToBpChart() {

        val systolic = LineDataSet(systolic(), "Systolic")
        systolic.lineWidth = 3f
        systolic.valueTextSize = 15f
        systolic.mode = LineDataSet.Mode.CUBIC_BEZIER
        systolic.color = ContextCompat.getColor(this, R.color.red)
        systolic.valueTextColor = ContextCompat.getColor(this, R.color.red)
        systolic.enableDashedLine(20F, 10F, 0F)
        systolic.disableDashedLine()

        val diastolic = LineDataSet(diastolic(), "Diastolic")
        diastolic.lineWidth = 3f
        diastolic.valueTextSize = 15f
        diastolic.mode = LineDataSet.Mode.CUBIC_BEZIER
        diastolic.color = ContextCompat.getColor(this, R.color.blue)
        diastolic.valueTextColor = ContextCompat.getColor(this, R.color.blue)
        diastolic.enableDashedLine(20F, 10F, 0F)
        diastolic.disableDashedLine()

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(systolic)
        dataSet.add(diastolic)

        val lineData = LineData(dataSet)
        BpChart.data = lineData

        BpChart.invalidate()
    }

    private fun systolic(): ArrayList<Entry> {
        val systolic = ArrayList<Entry>()
        systolic.add(Entry(0f, 126f))
        systolic.add(Entry(1f, 127f))
        systolic.add(Entry(2f, 126f))
        systolic.add(Entry(3f, 125f))
        return systolic
    }

    private fun diastolic(): ArrayList<Entry> {
        val diastolic = ArrayList<Entry>()
        diastolic.add(Entry(0f, 82f))
        diastolic.add(Entry(1f, 80f))
        diastolic.add(Entry(2f, 81f))
        diastolic.add(Entry(3f, 82f))
        return diastolic
    }
}