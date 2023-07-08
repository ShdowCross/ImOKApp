package com.example.imokapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Survey : AppCompatActivity() {

    private lateinit var c1q1RadioGroup: RadioGroup
    private lateinit var c1q2RadioGroup: RadioGroup
    private lateinit var c1q3RadioGroup: RadioGroup
    private lateinit var c1q4RadioGroup: RadioGroup
    private lateinit var c1q5RadioGroup: RadioGroup

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        // Category 1 questions
        c1q1RadioGroup = findViewById(R.id.c1q1_radio_group)
        c1q2RadioGroup = findViewById(R.id.c1q2_text_view)
        c1q3RadioGroup = findViewById(R.id.category_1_question_3_radio_group)
        c1q4RadioGroup = findViewById(R.id.category_1_question_4_radio_group)
        c1q5RadioGroup = findViewById(R.id.category_1_question_5_radio_group)

        // Category 2 questions
        c2q1RadioGroup = findViewById(R.id.c2q1_radio_group)
        c2q2RadioGroup = findViewById(R.id.category_2_question_2_radio_group)
        c2q3RadioGroup = findViewById(R.id.category_2_question_3_radio_group)
        c2q4RadioGroup = findViewById(R.id.category_2_question_4_radio_group)
        c2q5RadioGroup = findViewById(R.id.category_2_question_5_radio_group)

        // Category 3 questions
        c3q1RadioGroup = findViewById(R.id.category_3_question_1_radio_group)
        c3q2RadioGroup = findViewById(R.id.category_3_question_2_radio_group)
        c3q3RadioGroup = findViewById(R.id.category_3_question_3_radio_group)
        c3q4RadioGroup = findViewById(R.id.category_3_question_4_radio_group)
        c3q5RadioGroup = findViewById(R.id.category_3_question_5_radio_group)

        val submitButton = findViewById<Button>(R.id.submit_button)

        submitButton.setOnClickListener {
            val allAnswers = mutableListOf<String>()

            // Category 1 answers
            for (i in 0 until c1q1RadioGroup.childCount) {
                val radioButton = c1q1RadioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    allAnswers.add(radioButton.text.toString())
                }
            }

            for (i in 0 until c1q2RadioGroup.childCount) {
                val radioButton = c1q2RadioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    allAnswers.add(radioButton.text.toString())
                }
            }

            for (i in 0 until c1q3RadioGroup.childCount) {
                val radioButton = c1q3RadioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    allAnswers.add(radioButton.text.toString())
                }
            }

            for (i in 0 until c1q4RadioGroup.childCount) {
                val radioButton = c1q4RadioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    allAnswers.add(radioButton.text.toString())
                }
            }

            for (i in 0 until c1q5RadioGroup.childCount) {
                val radioButton = c1q5RadioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    allAnswers.add(radioButton.text.toString())
                }
            }

            // Category 2 answers
            for (i in 0 until c2q1RadioGroup.childCount) {
                val radioButton = c2q1RadioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    allAnswers.add(radioButton.text.toString())
                }
            }

            for (i in 0 until c2q2RadioGroup.childCount) {
                val radioButton = c2q2RadioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    allAnswers.add(radioButton.text.toString())
                }
            }

            for (i in 0 until c2q3RadioGroup.childCount) {
                val radioButton = c2q3RadioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    allAnswers.add(radioButton.text.toString())
                }
            }

            for (i in 0 until c2q4RadioGroup.childCount) {
                val radioButton = c2q4RadioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    allAnswers.add(radioButton.text.toString())
                }
            }

            for (i in 0 until c2q5RadioGroup.childCount) {
                val radioButton = c2q5RadioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    allAnswers.add(radioButton.text.toString())
                }
            }

            // Category 3 answers
            for (i in 0 until c3q1RadioGroup.childCount) {
                val radioButton = c3q1RadioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    allAnswers.add(radioButton.text.toString())
                }
            }

            for (i in 0 until c3q2RadioGroup.childCount) {
                val radioButton = c3q2RadioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    allAnswers.add(radioButton.text.toString())
                }
            }

            for (i in 0 until c3q3RadioGroup.childCount) {
                val radioButton = c3q3RadioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    allAnswers.add(radioButton.text.toString())
                }
            }

            for (i in 0 until c3q4RadioGroup.childCount) {
                val radioButton = c3q4RadioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    allAnswers.add(radioButton.text.toString())
                }
            }

            for (i in 0 until c3q5RadioGroup.childCount) {
                val radioButton = c3q5RadioGroup.getChildAt(i) as RadioButton
                if (radioButton.isChecked) {
                    allAnswers.add(radioButton.text.toString())
                }
            }

            val yesAnswers = allAnswers.filter { it == "Yes" }.count()

            if (yesAnswers > allAnswers.size / 2) {
                // Redirect to recommendations page
                //val intent = Intent(this, RecommendationsActivity::class.java)
                //                startActivity(intent)
            }
        }
    }
}
