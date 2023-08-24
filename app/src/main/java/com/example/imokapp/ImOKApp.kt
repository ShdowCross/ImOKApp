package com.example.imokapp

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.imokapp.ImOKApp.Companion.COLUMN_ALLERGIES
import com.example.imokapp.ImOKApp.Companion.COLUMN_BLOOD_TYPE
import com.example.imokapp.ImOKApp.Companion.COLUMN_BMI
import com.example.imokapp.ImOKApp.Companion.COLUMN_DOB
import com.example.imokapp.ImOKApp.Companion.COLUMN_GENDER
import com.example.imokapp.ImOKApp.Companion.COLUMN_PERSON_ID
import com.example.imokapp.ImOKApp.Companion.COLUMN_USER_NAME
import com.github.mikephil.charting.data.Entry
import java.util.*


//TODO 7 :
// - Modify MyContacts class to extend application
// - Declare the two list objects that will hold the id and contact information
// - Create a static instance of MyContacts using the constructor
class ImOKApp() : Application(){
    //Calling of classes - Person Contacts
    private var contactList: ArrayList<String> = ArrayList<String>()
    private var contactIdList: ArrayList<Int> = ArrayList<Int>()

    companion object {
        const val COLUMN_PERSON_ID = "person_id"
        const val COLUMN_USER_NAME = "user_name"
        const val COLUMN_GENDER = "gender"
        const val COLUMN_DOB = "dob"
        const val COLUMN_BLOOD_TYPE = "blood_type"
        const val COLUMN_BMI = "bmi"
        const val COLUMN_ALLERGIES = "allergies"
        private const val MYDBADAPTER_LOG_CAT = "MyDBAdapter"
        data class PersonInfo(
            val personId: Long,
            val userName: String,
            val gender: String,
            val dob: String,
            val bloodType: String,
            val bmi: String,
            val allergies: String,
            val medicalHistory: String,
            val vaccinationHistory: String
        )

        private lateinit var instance: ImOKApp

        fun getInstance(): ImOKApp {
            return instance
        }
        // person data
        var hardCodedDoctors: MutableMap<String , DoctorInfo> = mutableMapOf(
            "General Doctor" to getDoctorInfo("General Doctor"),
//            "Cardiologist" to getDoctorInfo("Cardiologist"),
//            "Dermatologist" to getDoctorInfo("Dermatologist"),
//            "Neurologist" to getDoctorInfo("Neurologist"),
//            "Pediatrician" to getDoctorInfo("Pediatrician"),
//            "Gastroenterologist" to getDoctorInfo("Gastroenterologist")
        )
        var hardCodedRelatives: MutableMap<String , RelativeInfo> = mutableMapOf(
            "Mother" to getRelativeInfo("Mother"),
            "Brother" to getRelativeInfo("Brother"),
            "Cousin" to getRelativeInfo("Cousin"),
            "Aunt" to getRelativeInfo("Aunt"),
            "Uncle" to getRelativeInfo("Uncle")
        )
        private fun getDoctorInfo(doctorName: String): DoctorInfo {
            return when (doctorName) {
                "General Doctor" -> DoctorInfo(
                    name = "Dr. Smith",
                    title = "General Doctor",
                    website = "https://example.com/doctors/johnsmith",
                    imageUrl = "doctor1",
                    email = "johnsmith@example.com",
                    phoneNumber = "+65 12345678",
                    street = "123 Main Street",
                    unit = "Unit 1",
                    postal = "123456"
                )
                "Cardiologist" -> DoctorInfo(
                    name = "Dr. Don",
                    title = "Cardiologist",
                    website = "www.cardiologyclinic.com",
                    imageUrl = "doctor5",
                    email = "drjohn@example.com",
                    phoneNumber = "+1234567890",
                    street = "456 Heart Avenue",
                    unit = "Unit 2",
                    postal = "67890"
                )
                "Dermatologist" -> DoctorInfo(
                    name = "Dr. Perkins",
                    title = "Dermatologist",
                    website = "www.dermatologyclinic.com",
                    imageUrl = "doctor3",
                    email = "dremily@example.com",
                    phoneNumber = "+0987654321",
                    street = "789 Skin Street",
                    unit = "Unit 3",
                    postal = "54321"
                )
                "Neurologist" -> DoctorInfo(
                    name = "Dr. Nash",
                    title = "Neurologist",
                    website = "www.neurologyclinic.com",
                    imageUrl = "doctor2",
                    email = "drmichael@example.com",
                    phoneNumber = "+1112223333",
                    street = "321 Brain Avenue",
                    unit = "Unit 5",
                    postal = "98765"
                )
                "Pediatrician" -> DoctorInfo(
                    name = "Dr. Phua",
                    title = "Pediatrician",
                    website = "www.pediatricsclinic.com",
                    imageUrl = "doctor4",
                    email = "drsarah@example.com",
                    phoneNumber = "+4445556666",
                    street = "234 Kids Street",
                    unit = "Unit 1",
                    postal = "34567"
                )
                "Gastroenterologist" -> DoctorInfo(
                    name = "Dr. Theresa",
                    title = "Gastroenterologist",
                    website = "www.gastroclinic.com",
                    imageUrl = "doctor6",
                    email = "drsamantha@example.com",
                    phoneNumber = "+2223334444",
                    street = "567 Stomach Street",
                    unit = "Unit 6",
                    postal = "98765"
                )
                else -> DoctorInfo(
                    name = "Unknown Doctor",
                    title = "Unknown Title",
                    website = "",
                    imageUrl = "",
                    email = "",
                    phoneNumber = "",
                    street = "",
                    unit = "",
                    postal = ""
                )
            }
        }
        private fun getRelativeInfo(relativeName: String): RelativeInfo {
            return when (relativeName) {
                "Mother" -> RelativeInfo(
                    name = "Elderly Mother",
                    title = "Mother",
                    imageUrl = "janedoh",
                    email = "elderlymother@example.com",
                    phoneNumber = "+65 87654321",
                    street = "123 Main Street",
                    unit = "#12-34",
                    postal = "654321"
                )
                "Brother" -> RelativeInfo(
                    name = "Supportive Brother",
                    title = "Brother",
                    imageUrl = "brother",
                    email = "supportivebrother@example.com",
                    phoneNumber = "+1234567890",
                    street = "123 Brother Street",
                    unit = "Unit 1",
                    postal = "12345"
                )
                "Cousin" -> RelativeInfo(
                    name = "Caring Cousin",
                    title = "Cousin",
                    imageUrl = "cousin",
                    email = "caringcousin@example.com",
                    phoneNumber = "+0987654321",
                    street = "456 Cousin Street",
                    unit = "Unit 2",
                    postal = "54321"
                )
                "Aunt" -> RelativeInfo(
                    name = "Supportive Aunt",
                    title = "Aunt",
                    imageUrl = "aunt",
                    email = "supportiveaunt@example.com",
                    phoneNumber = "+1112223333",
                    street = "789 Aunt Street",
                    unit = "Unit 3",
                    postal = "67890"
                )
                "Uncle" -> RelativeInfo(
                    name = "Helpful Uncle",
                    title = "Uncle",
                    imageUrl = "uncle",
                    email = "helpfuluncle@example.com",
                    phoneNumber = "+4445556666",
                    street = "234 Uncle Street",
                    unit = "Unit 4",
                    postal = "34567"
                )
                else -> RelativeInfo(
                    name = "Unknown Relative",
                    title = "",
                    imageUrl = "",
                    email = "",
                    phoneNumber = "",
                    street = "",
                    unit = "",
                    postal = ""
                )
            }
        }

        var addedDoctors: MutableMap<String , DoctorInfo> = mutableMapOf()
        var addedRelatives: MutableMap<String , RelativeInfo> = mutableMapOf()

        var doctors: MutableMap<String , DoctorInfo> = (hardCodedDoctors + addedDoctors).toMutableMap()
        var relatives: MutableMap<String , RelativeInfo> = (hardCodedRelatives + addedRelatives).toMutableMap()

        var muscleMass: Float = 35.8F
        var weight: Float = 0F
        var weightAverage: Float = 0F
        var weightThreshold: Float = 0f
        var heightCM: Float = 169.50F
        var heightMeter: Float = (heightCM / 100)
        var BMI: Float = 0F
        var bloodPressureSystolic: Int = 0
        var bloodPressureDiastolic: Int = 0
        var heartRate: Float = 0F

        var firstRun: Boolean = true
        var bpNotificationOn: Boolean = true
        var weightNotificationOn: Boolean = true
        var hrNotificationOn: Boolean = true
        var muscleMassNotificationOn: Boolean = true

        var highBP: Boolean = false
        var grade1Hypertension: Boolean = false
        var grade2Hypertension: Boolean = false
        var grade3Hypertension: Boolean = false
        var grade4Hypertension: Boolean = false
        var lowBP : Boolean = false
        var isolatedSystolic: Boolean = false
        var isolatedDiastolic: Boolean = false
        var highRiskBmi: Boolean = false
        var moderateRiskBmi: Boolean = true
        var lowRiskBmi: Boolean = false
        var uWeight: Boolean = false
        var takeCareNotif: Boolean = false
        var hbpAnswered = false
        var lbpAnswered = false
        var wlAnswered = false
        var iSAnswered = false
        var iDAnswered = false

        var systolic = ArrayList<Entry>()
        var systolicValues = ArrayList<Int>()
        var diastolic = ArrayList<Entry>()
        var diastolicValues = ArrayList<Int>()
        var weightArray = ArrayList<Entry>()
        var weightValues = ArrayList<Float>()
        var timeList = ArrayList<String>()

        fun addBpData(systolicValue: Int, diastolicValue: Int) {
            val systolicIndex = systolic.size.toFloat()
            systolic.add(Entry(systolicIndex, systolicValue.toFloat()))

            val diastolicIndex = diastolic.size.toFloat()
            diastolic.add(Entry(diastolicIndex, diastolicValue.toFloat()))
        }
        fun addWeightData(weight:Float){
            val weightIndex = weightArray.size.toFloat()
            weightArray.add(Entry(weightIndex, weight))
        }
        fun generateTimeLabels(): String {
            val currentTime = Calendar.getInstance()
            val hour = currentTime.get(Calendar.HOUR_OF_DAY)
            val minute = currentTime.get(Calendar.MINUTE)
            val seconds = currentTime.get(Calendar.SECOND)
            val labelHour = (hour) % 24
            val label = String.format("%02d:%02d:%02d", labelHour, minute, seconds)
            return label
        }
        fun calculateBMI(w: Float, h: Float): Float{
            BMI = w / (h * h)
            return BMI
        }
        fun calculateWeightThreshold(wA: Float, wT: Float): Float {
            weightThreshold = wA * (wT / 100.0f)
            return weightThreshold
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
//        val dbAdapter = MyDBAdapter(this)
//        dbAdapter.open()
//        personInfoHardcodedValues()
//        dbAdapter.close()
    }

    fun personContactsAddToDatabase(
        personId: Long, photo: String?, nickname: String?, name: String?, title: String?,
        email: String?, phoneNumber: String?, addressStreet: String?, addressUnit: String?,
        addressPostal: String?, website: String?
    ): Long? {
        val db = MyDBAdapter(applicationContext)
        db.open()
        val rowIDofInsertedEntry =
            db.insertPersonContact(personId, photo, nickname, name, title, email, phoneNumber, addressStreet, addressUnit, addressPostal, website)
        db.close()
        return rowIDofInsertedEntry
    }
    fun personInfoHardcodedValues() {
        val dbAdapter = MyDBAdapter(applicationContext)
        dbAdapter.open()
        val personInfoList: List<ContentValues> = listOf(
            ContentValues().apply {
                put(COLUMN_PERSON_ID, 1)
                put(COLUMN_USER_NAME, "John Doe")
                put(COLUMN_GENDER, "Male")
                put(COLUMN_DOB, "1990-01-01")
                put(COLUMN_BLOOD_TYPE, "O+")
                put(COLUMN_BMI, "22.5")
                put(COLUMN_ALLERGIES, "None")
            },
            ContentValues().apply {
                put(COLUMN_PERSON_ID, 2)
                put(COLUMN_USER_NAME, "Jane Smith")
                put(COLUMN_GENDER, "Female")
                put(COLUMN_DOB, "1995-05-10")
                put(COLUMN_BLOOD_TYPE, "A+")
                put(COLUMN_BMI, "20.2")
                put(COLUMN_ALLERGIES, "Pollen")
            },
            ContentValues().apply {
                put(COLUMN_PERSON_ID, 3)
                put(COLUMN_USER_NAME, "Mike Johnson")
                put(COLUMN_GENDER, "Male")
                put(COLUMN_DOB, "1988-09-22")
                put(COLUMN_BLOOD_TYPE, "B+")
                put(COLUMN_BMI, "25.1")
                put(COLUMN_ALLERGIES, "Dairy")
            },
            ContentValues().apply {
                put(COLUMN_PERSON_ID, 4)
                put(COLUMN_USER_NAME, "Emily Davis")
                put(COLUMN_GENDER, "Female")
                put(COLUMN_DOB, "1992-03-15")
                put(COLUMN_BLOOD_TYPE, "AB+")
                put(COLUMN_BMI, "23.7")
                put(COLUMN_ALLERGIES, "Peanuts")
            },
            ContentValues().apply {
                put(COLUMN_PERSON_ID, 5)
                put(COLUMN_USER_NAME, "David Wilson")
                put(COLUMN_GENDER, "Male")
                put(COLUMN_DOB, "1993-11-18")
                put(COLUMN_BLOOD_TYPE, "O-")
                put(COLUMN_BMI, "24.5")
                put(COLUMN_ALLERGIES, "Shellfish")
            })
            for (item in personInfoList) {
            val newPersonInfoRowId = dbAdapter.insertPersonInfo(item)
            Log.d(MYDBADAPTER_LOG_CAT, "Inserted row with ID: $newPersonInfoRowId")
            val valueSet = item.valueSet()
            if (valueSet.isNotEmpty()) {
                for ((key, value) in valueSet) {
                    Log.d(MYDBADAPTER_LOG_CAT, "Column: $key, Value: $value")
                }
            } else {
                Log.d(MYDBADAPTER_LOG_CAT, "Empty valueSet for item: $item")
            }
            Log.d(MYDBADAPTER_LOG_CAT, "Data inserted: ${item.toString()}")
        }
        dbAdapter.close()
    }


    fun personContactsDeleteFromDatabase(contactID: Int): Boolean {
        val db = MyDBAdapter(applicationContext)
        db.open()
        val id = contactIdList[contactID]
        val updateStatus = db.removePersonContact(id)
        db.close()
        return updateStatus
    }

    fun personContactsRetrieveAll(): List<String> {
        val db = MyDBAdapter(applicationContext)
        db.open()
        contactIdList.clear()
        contactList.clear()
        val myCursor = db.personContactsTableRetrieveAllEntriesCursor()
        myCursor?.let {
            if (myCursor.count > 0) {
                myCursor.moveToFirst()
                do {
                    contactIdList.add(myCursor.getInt(db.COLUMN_CONTACT_ID))
                    val contactName = myCursor.getString(db.COLUMN_NAME)
                    contactList.add(contactName)
                } while (myCursor.moveToNext())
            }
        }
        myCursor?.close()
        db.close()
        return contactList
    }
}
