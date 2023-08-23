package com.example.imokapp

import android.app.Application
import android.util.Log
import com.github.mikephil.charting.data.Entry
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
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
        //        const val COLUMN_PERSON_ID = "person_id"
//        const val COLUMN_USER_NAME = "user_name"
//        const val COLUMN_GENDER = "gender"
//        const val COLUMN_DOB = "dob"
//        const val COLUMN_BLOOD_TYPE = "blood_type"
//        const val COLUMN_BMI = "bmi"
//        const val COLUMN_ALLERGIES = "allergies"
//        private const val MYDBADAPTER_LOG_CAT = "MyDBAdapter"

        lateinit var personInfo: PersonInfo
        lateinit var healthMetrics: HealthMetrics
        lateinit var healthStatus: HealthStatus
        lateinit var healthNotification: HealthNotifications
        lateinit var graphData: GraphData

        data class PersonInfo(
            val userName: String = "Jonathan Ho",
            val gender: String = "Male",
            val dob: String = "16 July 1953",
            val bloodType: String = "O+",
            val allergies: String = "None",
            val medicalHistory: String = "No major medical history",
            val vaccinationHistory: String = "Up to Date",
            var doctors: MutableMap<String, DoctorInfo> = mutableMapOf(),
            var relatives: MutableMap<String, RelativeInfo> = mutableMapOf()
        )

        data class HealthMetrics(
            var weightThreshold: Float = 0f,
            var weight: Float = 0F,
            var weightAverage: Float = 0F,
            var muscleMass: Float = 35.8F,
            var heightCM: Float = 169.50F,
            var heightMeter: Float = (heightCM / 100),
            var bloodPressureSystolic: Int = 0,
            var bloodPressureDiastolic: Int = 0,
            var heartRate: Float = 0F,
            var BMI: Float = 0F
        )

        data class HealthStatus(
            var highBP: Boolean = false,
            var grade1Hypertension: Boolean = false,
            var grade2Hypertension: Boolean = false,
            var grade3Hypertension: Boolean = false,
            var grade4Hypertension: Boolean = false,
            var lowBP: Boolean = false,
            var isolatedSystolic: Boolean = false,
            var isolatedDiastolic: Boolean = false,
            var highRiskBmi: Boolean = false,
            var moderateRiskBmi: Boolean = true,
            var lowRiskBmi: Boolean = false,
            var uWeight: Boolean = false
        )

        data class HealthNotifications(
            var firstRun: Boolean = true,
            var bpNotificationOn: Boolean = true,
            var weightNotificationOn: Boolean = true,
            var hrNotificationOn: Boolean = true,
            var muscleMassNotificationOn: Boolean = true,
            var takeCareNotif: Boolean = false
        )

        data class GraphData(
            var systolic: ArrayList<Entry> = ArrayList(),
            var diastolic: ArrayList<Entry> = ArrayList(),
            var weightArray: ArrayList<Entry> = ArrayList(),
            var timeList: ArrayList<String> = ArrayList()
        )

        private lateinit var instance: ImOKApp

        fun getInstance(): ImOKApp {
            return instance
        }

        // person data
        var hardCodedDoctors: MutableMap<String, DoctorInfo> = mutableMapOf(
            "General Doctor" to getDoctorInfo("General Doctor"),
//            "Cardiologist" to getDoctorInfo("Cardiologist"),
//            "Dermatologist" to getDoctorInfo("Dermatologist"),
//            "Neurologist" to getDoctorInfo("Neurologist"),
//            "Pediatrician" to getDoctorInfo("Pediatrician"),
//            "Gastroenterologist" to getDoctorInfo("Gastroenterologist")
        )
        var hardCodedRelatives: MutableMap<String, RelativeInfo> = mutableMapOf(
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

        fun addBpData(systolicValue: Int, diastolicValue: Int, graphData: GraphData) {
            val systolicIndex = graphData.systolic.size.toFloat()
            graphData.systolic.add(Entry(systolicIndex, systolicValue.toFloat()))

            val diastolicIndex = graphData.diastolic.size.toFloat()
            graphData.diastolic.add(Entry(diastolicIndex, diastolicValue.toFloat()))
        }

        fun addWeightData(weight: Float, graphData: GraphData) {
            val weightIndex = graphData.weightArray.size.toFloat()
            graphData.weightArray.add(Entry(weightIndex, weight))
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

        fun calculateBMI(w: Float, h: Float, healthMetrics: HealthMetrics): Float {
            if (h <= 0.0F) {
                return 0.0F
            }
            healthMetrics.BMI = w / (h * h)
            return healthMetrics.BMI
        }

        fun calculateWeightThreshold(wA: Float, wT: Float, healthMetrics: HealthMetrics): Float {
            healthMetrics.weightThreshold = wA * (wT / 100.0f)
            return healthMetrics.weightThreshold
        }

        fun writePersonInfoJson(
            userName: String? = null,
            gender: String? = null,
            dob: String? = null,
            bloodType: String? = null,
            allergies: String? = null,
            medicalHistory: String? = null,
            vaccinationHistory: String? = null,
            doctors: Map<String, DoctorInfo>? = null,
            relatives: Map<String, RelativeInfo>? = null,
            filesDir: File
        ) {
            val gson = GsonBuilder().serializeSpecialFloatingPointValues().create()

            val personInfo = PersonInfo(
                userName ?: "Jonathan Ho",
                gender ?: "Male",
                dob ?: "16 July 1953",
                bloodType ?: "O+",
                allergies ?: "None",
                medicalHistory ?: "No major medical history",
                vaccinationHistory ?: "Up to Date",
                doctors?.toMutableMap() ?: mutableMapOf(),
                relatives?.toMutableMap() ?: mutableMapOf()
            )

            val json = gson.toJson(personInfo)

            try {
                val file = File(filesDir, "personInfo.json")
                file.writeText(json)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun writeHealthMetricsJson(
            weightThreshold: Float? = null,
            weight: Float? = null,
            weightAverage: Float? = null,
            muscleMass: Float? = null,
            heightCM: Float? = null,
            heightMeter: Float? = null,
            bloodPressureSystolic: Int? = null,
            bloodPressureDiastolic: Int? = null,
            heartRate: Float? = null,
            BMI: Float? = null,
            filesDir: File
        ) {
            val gson = GsonBuilder().serializeSpecialFloatingPointValues().create()

            val healthMetrics = HealthMetrics(
                weightThreshold ?: 5f,
                weight ?: 0F,
                weightAverage ?: 0F,
                muscleMass ?: 35.8F,
                heightCM ?: 169.50F,
                heightMeter ?:if (heightCM != null && heightCM > 0) heightCM / 100 else 0F,
                bloodPressureSystolic ?: 0,
                bloodPressureDiastolic ?: 0,
                heartRate ?: 0F,
                BMI ?: 0F
            )

            val json = gson.toJson(healthMetrics)

            try {
                val file = File(filesDir, "healthMetrics.json")
                Log.d("FileCreation", "File path: ${file.absolutePath}")
                file.writeText(json)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun writeHealthStatusJson(
            highBP: Boolean? = null,
            grade1Hypertension: Boolean? = null,
            grade2Hypertension: Boolean? = null,
            grade3Hypertension: Boolean? = null,
            grade4Hypertension: Boolean? = null,
            lowBP: Boolean? = null,
            isolatedSystolic: Boolean? = null,
            isolatedDiastolic: Boolean? = null,
            highRiskBmi: Boolean? = null,
            moderateRiskBmi: Boolean? = null,
            lowRiskBmi: Boolean? = null,
            uWeight: Boolean? = null,
            filesDir: File
        ) {
            val gson = GsonBuilder().serializeSpecialFloatingPointValues().create()

            val healthStatus = HealthStatus(
                highBP ?: false,
                grade1Hypertension ?: false,
                grade2Hypertension ?: false,
                grade3Hypertension ?: false,
                grade4Hypertension ?: false,
                lowBP ?: false,
                isolatedSystolic ?: false,
                isolatedDiastolic ?: false,
                highRiskBmi ?: false,
                moderateRiskBmi ?: true,
                lowRiskBmi ?: false,
                uWeight ?: false
            )

            val json = gson.toJson(healthStatus)

            try {
                val file = File(filesDir, "healthStatus.json")
                file.writeText(json)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun writeHealthNotificationsJson(
            firstRun: Boolean? = null,
            bpNotificationOn: Boolean? = null,
            weightNotificationOn: Boolean? = null,
            hrNotificationOn: Boolean? = null,
            muscleMassNotificationOn: Boolean? = null,
            takeCareNotif: Boolean? = null,
            filesDir: File
        ) {
            val gson = GsonBuilder().serializeSpecialFloatingPointValues().create()

            val healthNotifications = HealthNotifications(
                firstRun ?: true,
                bpNotificationOn ?: true,
                weightNotificationOn ?: true,
                hrNotificationOn ?: true,
                muscleMassNotificationOn ?: true,
                takeCareNotif ?: false
            )

            val json = gson.toJson(healthNotifications)

            try {
                val file = File(filesDir, "healthNotifications.json")
                file.writeText(json)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun writeGraphDataJson(
            systolic: List<Entry>? = null,
            diastolic: List<Entry>? = null,
            weightArray: List<Entry>? = null,
            timeList: List<String>? = null,
            filesDir: File
        ) {
            val gson = GsonBuilder().serializeSpecialFloatingPointValues().create()

            val systolicList = systolic?.toList()
            val diastolicList = diastolic?.toList()
            val weightList = weightArray?.toList()

            val data = mapOf(
                "systolic" to systolicList,
                "diastolic" to diastolicList,
                "weight" to weightList,
                "timeList" to timeList
            )

            val json = gson.toJson(data)

            try {
                val file = File(filesDir, "graphData.json")
                file.writeText(json)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


        fun readPersonInfoJson(filesDir: File): PersonInfo? {
            val gson = GsonBuilder().serializeSpecialFloatingPointValues().create()
            try {
                val file = File(filesDir, "personInfo.json")
                if (file.exists()) {
                    val json = file.readText()

                    // Deserialize PersonInfo object
                    val personInfo = gson.fromJson(json, PersonInfo::class.java)

                    // Read and deserialize relatives
                    try {
                        val relativesFile = File(filesDir, "personInfo.json")
                        if (relativesFile.exists()) {
                            val relativesJson = relativesFile.readText()
                            val typeToken = object : TypeToken<MutableMap<String, RelativeInfo>>() {}.type
                            val deserializedRelatives: MutableMap<String, RelativeInfo> = gson.fromJson(relativesJson, typeToken)
                            if (deserializedRelatives.isEmpty()) {
                                val hardcodedRelatives = listOf("Mother", "Brother", "Cousin", "Aunt", "Uncle")
                                val hardcodedRelativesMap = hardcodedRelatives.associateWith { relativeName -> getRelativeInfo(relativeName) }
                                personInfo.relatives.putAll(hardcodedRelativesMap)
                            } else {
                                personInfo.relatives.putAll(deserializedRelatives)
                            }
                        }
                    } catch (e: FileNotFoundException) {
                        Log.d("addedRelatives", "not found")
                    }

                    return personInfo
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        fun readHealthMetricsJson(filesDir: File): HealthMetrics? {
            val gson = GsonBuilder().serializeSpecialFloatingPointValues().create()
            try {
                val file = File(filesDir, "healthMetrics.json")
                val json = file.readText()
                return gson.fromJson(json, HealthMetrics::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        fun readHealthStatusJson(filesDir: File): HealthStatus? {
            val gson = GsonBuilder().serializeSpecialFloatingPointValues().create()
            try {
                val file = File(filesDir, "healthStatus.json")
                val json = file.readText()
                return gson.fromJson(json, HealthStatus::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        fun readHealthNotificationsJson(filesDir: File): HealthNotifications? {
            val gson = GsonBuilder().serializeSpecialFloatingPointValues().create()
            try {
                val file = File(filesDir, "healthNotifications.json")
                val json = file.readText()
                return gson.fromJson(json, HealthNotifications::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        fun readGraphDataJson(filesDir: File): GraphData? {
            val gson = GsonBuilder().serializeSpecialFloatingPointValues().create()
            try {
                val file = File(filesDir, "graphData.json")
                val json = file.readText()
                return gson.fromJson(json, GraphData::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
        data class InfoData(
            val personInfo: PersonInfo?,
            val healthMetrics: HealthMetrics?,
            val healthStatus: HealthStatus?,
            val healthNotifications: HealthNotifications?,
            val graphData: GraphData?
        )
        fun pullInfo(filesDir: File): InfoData?{
            val personInfo = readPersonInfoJson(filesDir)
            val healthMetrics = readHealthMetricsJson(filesDir)
            val healthStatus = readHealthStatusJson(filesDir)
            val healthNotifications = readHealthNotificationsJson(filesDir)
            val graphData = readGraphDataJson(filesDir)

            return InfoData(personInfo, healthMetrics, healthStatus, healthNotifications, graphData)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        val personInfo = PersonInfo()
        val healthMetrics = HealthMetrics()
        val healthStatus = HealthStatus()
        val notifications = HealthNotifications()
        val graphData = GraphData()
        if (personInfo == null) {
            writePersonInfoJson(filesDir = filesDir)
        }
        if (healthMetrics == null) {
            writeHealthMetricsJson(filesDir = filesDir)
        }
        if (healthStatus == null) {
            writeHealthStatusJson(filesDir = filesDir)
        }
        if (notifications == null) {
            writeHealthNotificationsJson(filesDir = filesDir)
        }
        if (graphData == null) {
            writeGraphDataJson(filesDir = filesDir)
        }
        readPersonInfoJson(filesDir)
        readHealthMetricsJson(filesDir)
        readHealthStatusJson(filesDir)
        readHealthNotificationsJson(filesDir)
        readGraphDataJson(filesDir)
//        try {
//            val file = File(filesDir, "healthMetrics.json")
//            val json = file.readText()
//
//            val typeToken = object : TypeToken<Map<String, List<Any>>>() {}.type
//            val data: Map<String, List<Any>> = gson.fromJson(json, typeToken)
//
//            // Populate the variables from the retrieved data
//            val systolicList = data["systolic"] as List<Map<String, Float>>
//            val diastolicList = data["diastolic"] as List<Map<String, Float>>
//            val weightList = data["weight"] as List<Map<String, Float>>
//
//            graphData.systolic = ArrayList<Entry>(systolicList.map { Entry(it["x"]!!, it["y"]!!) })
//            graphData.diastolic = ArrayList<Entry>(diastolicList.map { Entry(it["x"]!!, it["y"]!!) })
//            graphData.weightArray = ArrayList<Entry>(weightList.map { Entry(it["x"]!!, it["y"]!!) })
//            graphData.timeList = ArrayList(data["timeList"] as List<String>)
//
//
//            // Extract values for ArrayLists if necessary
//            var systolicValues = ArrayList(graphData.systolic.map { it.y.toInt() })
//            var diastolicValues = ArrayList(graphData.diastolic.map { it.y.toInt() })
//            var weightValues = ArrayList(graphData.weightArray.map { it.y })
//
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        val dbAdapter = MyDBAdapter(this)
//        dbAdapter.open()
//        personInfoHardcodedValues()
//        dbAdapter.close()
    }

//    fun personContactsAddToDatabase(
//        personId: Long, photo: String?, nickname: String?, name: String?, title: String?,
//        email: String?, phoneNumber: String?, addressStreet: String?, addressUnit: String?,
//        addressPostal: String?, website: String?
//    ): Long? {
//        val db = MyDBAdapter(applicationContext)
//        db.open()
//        val rowIDofInsertedEntry =
//            db.insertPersonContact(personId, photo, nickname, name, title, email, phoneNumber, addressStreet, addressUnit, addressPostal, website)
//        db.close()
//        return rowIDofInsertedEntry
//    }
//    fun personInfoHardcodedValues() {
//        val dbAdapter = MyDBAdapter(applicationContext)
//        dbAdapter.open()
//        val personInfoList: List<ContentValues> = listOf(
//            ContentValues().apply {
//                put(COLUMN_PERSON_ID, 1)
//                put(COLUMN_USER_NAME, "John Doe")
//                put(COLUMN_GENDER, "Male")
//                put(COLUMN_DOB, "1990-01-01")
//                put(COLUMN_BLOOD_TYPE, "O+")
//                put(COLUMN_BMI, "22.5")
//                put(COLUMN_ALLERGIES, "None")
//            },
//            ContentValues().apply {
//                put(COLUMN_PERSON_ID, 2)
//                put(COLUMN_USER_NAME, "Jane Smith")
//                put(COLUMN_GENDER, "Female")
//                put(COLUMN_DOB, "1995-05-10")
//                put(COLUMN_BLOOD_TYPE, "A+")
//                put(COLUMN_BMI, "20.2")
//                put(COLUMN_ALLERGIES, "Pollen")
//            },
//            ContentValues().apply {
//                put(COLUMN_PERSON_ID, 3)
//                put(COLUMN_USER_NAME, "Mike Johnson")
//                put(COLUMN_GENDER, "Male")
//                put(COLUMN_DOB, "1988-09-22")
//                put(COLUMN_BLOOD_TYPE, "B+")
//                put(COLUMN_BMI, "25.1")
//                put(COLUMN_ALLERGIES, "Dairy")
//            },
//            ContentValues().apply {
//                put(COLUMN_PERSON_ID, 4)
//                put(COLUMN_USER_NAME, "Emily Davis")
//                put(COLUMN_GENDER, "Female")
//                put(COLUMN_DOB, "1992-03-15")
//                put(COLUMN_BLOOD_TYPE, "AB+")
//                put(COLUMN_BMI, "23.7")
//                put(COLUMN_ALLERGIES, "Peanuts")
//            },
//            ContentValues().apply {
//                put(COLUMN_PERSON_ID, 5)
//                put(COLUMN_USER_NAME, "David Wilson")
//                put(COLUMN_GENDER, "Male")
//                put(COLUMN_DOB, "1993-11-18")
//                put(COLUMN_BLOOD_TYPE, "O-")
//                put(COLUMN_BMI, "24.5")
//                put(COLUMN_ALLERGIES, "Shellfish")
//            })
//            for (item in personInfoList) {
//            val newPersonInfoRowId = dbAdapter.insertPersonInfo(item)
//            Log.d(MYDBADAPTER_LOG_CAT, "Inserted row with ID: $newPersonInfoRowId")
//            val valueSet = item.valueSet()
//            if (valueSet.isNotEmpty()) {
//                for ((key, value) in valueSet) {
//                    Log.d(MYDBADAPTER_LOG_CAT, "Column: $key, Value: $value")
//                }
//            } else {
//                Log.d(MYDBADAPTER_LOG_CAT, "Empty valueSet for item: $item")
//            }
//            Log.d(MYDBADAPTER_LOG_CAT, "Data inserted: ${item.toString()}")
//        }
//        dbAdapter.close()
//    }


//    fun personContactsDeleteFromDatabase(contactID: Int): Boolean {
//        val db = MyDBAdapter(applicationContext)
//        db.open()
//        val id = contactIdList[contactID]
//        val updateStatus = db.removePersonContact(id)
//        db.close()
//        return updateStatus
//    }
//
//    fun personContactsRetrieveAll(): List<String> {
//        val db = MyDBAdapter(applicationContext)
//        db.open()
//        contactIdList.clear()
//        contactList.clear()
//        val myCursor = db.personContactsTableRetrieveAllEntriesCursor()
//        myCursor?.let {
//            if (myCursor.count > 0) {
//                myCursor.moveToFirst()
//                do {
//                    contactIdList.add(myCursor.getInt(db.COLUMN_CONTACT_ID))
//                    val contactName = myCursor.getString(db.COLUMN_NAME)
//                    contactList.add(contactName)
//                } while (myCursor.moveToNext())
//            }
//        }
//        myCursor?.close()
//        db.close()
//        return contactList
//    }
}
