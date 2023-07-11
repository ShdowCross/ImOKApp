package com.example.imokapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class PatientProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patientprofile)

        val homeButton = findViewById<Button>(R.id.home_button)
        homeButton.setOnClickListener {
            val intent = Intent(this, HealthMetrics::class.java)
            startActivity(intent)
        }

        val medicalButton = findViewById<Button>(R.id.medical_button)
        medicalButton.setOnClickListener {
            val intent = Intent(this, PersonContacts::class.java)
            startActivity(intent)
        }

        var personInfoList = retrievePersonInfo()
        if(personInfoList.isNotEmpty()){
            val personInfo = personInfoList[0]
            val userName = personInfo.userName
            val nric = personInfo.nric
            val gender = personInfo.gender
            val dob = personInfo.dob
            val personAge = age(dob)
            val bloodType = personInfo.bloodType
            val bmi = personInfo.bmi
            val allergies = personInfo.allergies
            val medicalHistory = personInfo.medicalHistory
            val vaccinationHistory = personInfo.vaccinationHistory
            println(userName)
            val userNamePath = findViewById<TextView>(R.id.userNameData)
            val nricPath = findViewById<TextView>(R.id.userNRICData)
            val genderPath = findViewById<TextView>(R.id.userGenderData)
            val dobPath = findViewById<TextView>(R.id.userAgeData)
            val bloodTypePath = findViewById<TextView>(R.id.userBloodTypeData)
            val bmiPath = findViewById<TextView>(R.id.userBMIData)
            val allergiesPath = findViewById<TextView>(R.id.userAllergiesData)
            val medicalHistoryPath = findViewById<TextView>(R.id.userMedicalHistoryData)
            val vaccinationHistoryPath = findViewById<TextView>(R.id.userVaccinationHistoryData)

            userNamePath.text = userName
            nricPath.text = nric
            genderPath.text = gender
            dobPath.text = personAge.toString()
            bloodTypePath.text = bloodType
            bmiPath.text = bmi
            allergiesPath.text = allergies
            medicalHistoryPath.text = medicalHistory
            vaccinationHistoryPath.text = vaccinationHistory
        }
        else{
            println("List Empty")
        }
    }

    fun retrievePersonInfo(): List<ImOKApp.Companion.PersonInfo> {
        val personInfoList = mutableListOf<ImOKApp.Companion.PersonInfo>()
        val db = MyDBAdapter(applicationContext)
        db.open()
        val cursor = db.personTableRetrieveAllEntriesCursor()

        cursor?.let {
            while (cursor.moveToNext()) {
                val personId = cursor.getLong(db.COLUMN_PERSON_ID)
                val userName = cursor.getString(db.COLUMN_USER_NAME)
                val nric = cursor.getString(db.COLUMN_NRIC)
                val gender = cursor.getString(db.COLUMN_GENDER)
                val dob = cursor.getString(db.COLUMN_DOB)
                val bloodType = cursor.getString(db.COLUMN_BLOOD_TYPE)
                val bmi = cursor.getString(db.COLUMN_BMI)
                val allergies = cursor.getString(db.COLUMN_ALLERGIES)

                val cursor2 = db.medicalHistoryTableRetrieveAllEntriesCursor()
                var medicalHistory = ""
                var vaccinationHistory = ""

                cursor2?.let {
                    while (cursor2.moveToNext()) {
                        if (cursor2.getLong(db.COLUMN_PERSON_ID) == personId) {
                            medicalHistory = cursor2.getString(db.COLUMN_MEDICAL_HISTORY)
                            vaccinationHistory = cursor2.getString(db.COLUMN_VACCINATION_HISTORY)
                            break
                        }
                    }
                    cursor2.close()
                }

                val personInfo = ImOKApp.Companion.PersonInfo(
                    personId,
                    userName,
                    nric,
                    gender,
                    dob,
                    bloodType,
                    bmi,
                    allergies,
                    medicalHistory,
                    vaccinationHistory
                )
                if (personId.toInt() == 0) {
                    personInfoList.add(personInfo)
                }
            }
        }

        cursor?.close()
        db.close()

        return personInfoList
    }
    fun age(dob: String) : Int {
        val today = Calendar.getInstance()
        val dobDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dob)

    // Create a Calendar instance for the DOB
        val dobCalendar = Calendar.getInstance()
        dobCalendar.time = dobDate

    // Calculate the difference in years
        var age = today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR)

    // Check if the current date is before the birth date
        if (today.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        return age
    }
}