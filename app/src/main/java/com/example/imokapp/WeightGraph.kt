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
import kotlinx.android.synthetic.main.activity_weight_graph.*

class WeightGraph : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_graph)

        setUpWeightChart()
        setDataToWeightChart()
    }

    private fun setUpWeightChart() {
        with(WeightChart) {

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

    private fun setDataToWeightChart() {

        val weight = LineDataSet(weight(), "Weight")
        weight.lineWidth = 3f
        weight.valueTextSize = 15f
        weight.mode = LineDataSet.Mode.CUBIC_BEZIER
        weight.color = ContextCompat.getColor(this, R.color.yellow_orange)
        weight.valueTextColor = ContextCompat.getColor(this, R.color.yellow_orange)
        weight.enableDashedLine(20F, 10F, 0F)
        weight.disableDashedLine()

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(weight)

        val lineData = LineData(dataSet)
        WeightChart.data = lineData

        WeightChart.invalidate()
    }

    private fun weight(): ArrayList<Entry> {
        val weight = ArrayList<Entry>()
        weight.add(Entry(0f, 74.20f))
        weight.add(Entry(1f, 75.00f))
        weight.add(Entry(2f, 75.10f))
        weight.add(Entry(3f, 74.00f))
        return weight
    }

}