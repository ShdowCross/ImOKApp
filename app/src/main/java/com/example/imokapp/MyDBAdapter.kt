package com.example.imokapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyDBAdapter(c : Context){


    private val DATABASE_NAME = "test.db"
    private val DATABASE_TABLE1 = "personalInfo"
    private val DATABASE_TABLE2 = "medicalHistory"
    private val DATABASE_TABLE3 = "healthMetrics"
    private val DATABASE_TABLE4 = "surveyQuestions"
    private val DATABASE_TABLE5 = "surveyAnswers"
    private val DATABASE_TABLE6 = "recommendations"
    private val DATABASE_TABLE7 = "complications"
    private val DATABASE_TABLE8 = "personContacts"

    private val DATABASE_VERSION = 1
    private var _db: SQLiteDatabase? = null
    private val context: Context?= null

    //personalInfo
    val PERSON_ID = "_person_id"
    val COLUMN_PERSON_ID = 0
    val USER_NAME = "user_name"
    val COLUMN_USER_NAME = 1
    val NRIC = "nric"
    val COLUMN_NRIC = 2
    val GENDER = "gender"
    val COLUMN_GENDER = 3
    val DOB = "dob"
    val COLUMN_DOB = 4
    val BLOOD_TYPE = "blood_type"
    val COLUMN_BLOOD_TYPE = 5
    val BMI = "bmi"
    val COLUMN_BMI = 6
    val ALLERGIES = "allergies"
    val COLUMN_ALLERGIES = 7

    val ENTRY_ID = "_id"
    val COLUMN_ENTRY_ID = 8
    val MEDICAL_HISTORY = "medical_history"
    val COLUMN_MEDICAL_HISTORY = 9
    val VACCINATION_HISTORY = "vaccination_history"
    val COLUMN_VACCINATION_HISTORY = 10

    //healthMetrics
    val MUSCLE_MASS = "muscle_mass"
    val COLUMN_MUSCLE_MASS = 11
    val HEIGHT = "height"
    val COLUMN_HEIGHT = 12
    val WEIGHT = "weight"
    val COLUMN_WEIGHT = 13
    val HEART_RATE = "heart_rate"
    val COLUMN_HEART_RATE = 14
    val SYSTOLIC_BLOOD_PRESSURE = "systolic_blood_pressure"
    val COLUMN_SYSTOLIC_BLOOD_PRESSURE = 15
    val DIASTOLIC_BLOOD_PRESSURE = "diastolic_blood_pressure"
    val COLUMN_DIASTOLIC_BLOOD_PRESSURE = 15


    // Survey Questions
    val SURVEY_QUESTION_ID = "_id"
    val COLUMN_SURVEY_QUESTION_ID = 16
    val SURVEY_QUESTION_TEXT = "question_text"
    val COLUMN_SURVEY_QUESTION_TEXT = 17

    // Survey Answers
    val SURVEY_ANSWER_ID = "_id"
    val COLUMN_SURVEY_ANSWER_ID = 18
    val SURVEY_ANSWER_TEXT = "answer_text"
    val COLUMN_SURVEY_ANSWER_TEXT = 19

    // Recommendations
    val RECOMMENDATION_ID = "_id"
    val COLUMN_RECOMMENDATION_ID = 20
    val RECOMMENDATION_TEXT = "recommendation_text"
    val COLUMN_RECOMMENDATION_TEXT = 21

    // Complications
    val COMPLICATION_ID = "_id"
    val COLUMN_COMPLICATION_ID = 22
    val COMPLICATION_TEXT = "complication_text"
    val COLUMN_COMPLICATION_TEXT = 23

    //personContacts
    val CONTACT_ID = "_id"
    val COLUMN_CONTACT_ID = 24
    val PHOTO = "photo"
    val COLUMN_PHOTO = 25
    val NICKNAME = "nickname"
    val COLUMN_NICKNAME = 26
    val NAME = "name"
    val COLUMN_NAME = 27
    val TITLE = "title"
    val COLUMN_TITLE = 28
    val EMAIL = "email"
    val COLUMN_EMAIL = 29
    val PHONE_NUMBER = "phone_number"
    val COLUMN_PHONE_NUMBER = 30
    val ADDRESS_STREET = "address_street"
    val COLUMN_ADDRESS_STREET = 31
    val ADDRESS_UNIT = "address_unit"
    val COLUMN_ADDRESS_UNIT = 32
    val ADDRESS_POSTAL = "address_postal"
    val COLUMN_ADDRESS_POSTAL = 33
    val WEBSITE = "website"
    val COLUMN_WEBSITE = 32

    protected val DATABASE_TABLE1_CREATE = ("create table " + DATABASE_TABLE1 + " " + "(" + PERSON_ID + " integer primary key autoincrement, " + USER_NAME + " Text, "
            + NRIC + " text not null, " + GENDER + " text, " + DOB + " text, " + BLOOD_TYPE + " text, " + BMI + " text, " + ALLERGIES + " text);")

    protected val DATABASE_TABLE2_CREATE = ("create table " + DATABASE_TABLE2 + " " + "(" + ENTRY_ID + " integer primary key autoincrement, " + PERSON_ID + " integer, "
            + MEDICAL_HISTORY + " text, " + VACCINATION_HISTORY + " text, "
            + "FOREIGN KEY(" + PERSON_ID + ") REFERENCES " + DATABASE_TABLE1 + "(" + PERSON_ID + "));")

    protected val DATABASE_TABLE3_CREATE = ("create table " + DATABASE_TABLE3 + " " + "(" + PERSON_ID + " integer, " + MUSCLE_MASS + " text, "
            + HEIGHT + " text, " + WEIGHT + " text, " + HEART_RATE + " text, " + SYSTOLIC_BLOOD_PRESSURE + " text, " + DIASTOLIC_BLOOD_PRESSURE + " text, "
            + "FOREIGN KEY(" + PERSON_ID + ") REFERENCES " + DATABASE_TABLE1 + "(" + PERSON_ID + "));")

    protected val DATABASE_TABLE4_CREATE = ("create table " + DATABASE_TABLE4 + " " + "(" + SURVEY_QUESTION_ID + " integer primary key autoincrement, " + SURVEY_QUESTION_TEXT + " text);")

    protected val DATABASE_TABLE5_CREATE = ("create table " + DATABASE_TABLE5 + " " + "(" + SURVEY_ANSWER_ID + " integer primary key autoincrement, " + SURVEY_QUESTION_ID + " integer, "
            + SURVEY_ANSWER_TEXT + " text, "
            + "FOREIGN KEY(" + SURVEY_QUESTION_ID + ") REFERENCES " + DATABASE_TABLE4 + "(" + SURVEY_QUESTION_ID + "));")

    protected val DATABASE_TABLE6_CREATE = ("create table " + DATABASE_TABLE6 + " " + "(" + RECOMMENDATION_ID + " integer primary key autoincrement, " + RECOMMENDATION_TEXT + " text);")

    protected val DATABASE_TABLE7_CREATE = ("create table " + DATABASE_TABLE7 + " " + "(" + COMPLICATION_ID + " integer primary key autoincrement, " + COMPLICATION_TEXT + " text);")

    protected val DATABASE_TABLE8_CREATE = ("create table " + DATABASE_TABLE8 + " " + "(" + CONTACT_ID + " integer primary key autoincrement, "
            + PERSON_ID + " integer, " + PHOTO + " text, " + NICKNAME + " text, " + NAME + " text, " + TITLE + " text, "
            + EMAIL + " text, " + PHONE_NUMBER + " text, " + ADDRESS_STREET + " text, " + ADDRESS_UNIT + " text, " + ADDRESS_POSTAL + " text, " +WEBSITE + " text, "
            + "FOREIGN KEY(" + PERSON_ID + ") REFERENCES " + DATABASE_TABLE1 + "(" + PERSON_ID + "));")






    private val MYDBADAPTER_LOG_CAT = "MY_LOG"

    private var dbHelper: MyDBOpenHelper? = null

    init {
        //TODO 1 : Create a MyDBOpenHelper object
        dbHelper = MyDBOpenHelper(c, DATABASE_NAME, DATABASE_VERSION)
    }

    fun close() {
        //TODO 2 : close the table using the SQLite database handler
        _db?.close()
    }


    fun open() {
        //TODO 3 : Open DB using the appropriate methods
        try{
            _db = dbHelper?.writableDatabase
        } catch (e: SQLiteException){
            _db = dbHelper?.readableDatabase
        }
    }

    // Insert a new person's details into the personalInfo table
    fun insertPersonDetails(userName: String, nric: String, gender: String, dob: String, bloodType: String, bmi: String, allergies: String): Long? {
        val newPersonValues = ContentValues()
        newPersonValues.put(USER_NAME, userName)
        newPersonValues.put(NRIC, nric)
        newPersonValues.put(GENDER, gender)
        newPersonValues.put(DOB, dob)
        newPersonValues.put(BLOOD_TYPE, bloodType)
        newPersonValues.put(BMI, bmi)
        newPersonValues.put(ALLERGIES, allergies)

        return _db?.insert(DATABASE_TABLE1, null, newPersonValues)
    }

    // Insert a new entry into the medicalHistory table
    fun insertMedicalHistory(personId: Long, medicalHistory: String, vaccinationHistory: String): Long? {
        val newMedicalHistoryValues = ContentValues()
        newMedicalHistoryValues.put(PERSON_ID, personId)
        newMedicalHistoryValues.put(MEDICAL_HISTORY, medicalHistory)
        newMedicalHistoryValues.put(VACCINATION_HISTORY, vaccinationHistory)
        return _db?.insert(DATABASE_TABLE2, null, newMedicalHistoryValues)

    }
    // Insert a new set of health metrics into the healthMetrics table
    fun insertHealthMetrics(personId: Long, muscleMass: String, height: String, weight: String, heartRate: String,
                            systolicBloodPressure: String, diastolicBloodPressure: String): Long? {
        val newHealthMetricsValues = ContentValues()
        newHealthMetricsValues.put(PERSON_ID, personId)
        newHealthMetricsValues.put(MUSCLE_MASS, muscleMass)
        newHealthMetricsValues.put(HEIGHT, height)
        newHealthMetricsValues.put(WEIGHT, weight)
        newHealthMetricsValues.put(HEART_RATE, heartRate)
        newHealthMetricsValues.put(SYSTOLIC_BLOOD_PRESSURE, systolicBloodPressure)
        newHealthMetricsValues.put(DIASTOLIC_BLOOD_PRESSURE, diastolicBloodPressure)

        return _db?.insert(DATABASE_TABLE3, null, newHealthMetricsValues)
    }

    // Insert a new survey question into the surveyQuestions table
    fun insertSurveyQuestion(questionText: String): Long? {
        val newSurveyQuestionValues = ContentValues()
        newSurveyQuestionValues.put(SURVEY_QUESTION_TEXT, questionText)

        return _db?.insert(DATABASE_TABLE4, null, newSurveyQuestionValues)
    }

    // Insert a new survey answer into the surveyAnswers table
    fun insertSurveyAnswer(questionId: Long, answerText: String): Long? {
        val newSurveyAnswerValues = ContentValues()
        newSurveyAnswerValues.put(SURVEY_QUESTION_ID, questionId)
        newSurveyAnswerValues.put(SURVEY_ANSWER_TEXT, answerText)

        return _db?.insert(DATABASE_TABLE5, null, newSurveyAnswerValues)
    }

    // Insert a new recommendation into the recommendations table
    fun insertRecommendation(recommendationText: String): Long? {
        val newRecommendationValues = ContentValues()
        newRecommendationValues.put(RECOMMENDATION_TEXT, recommendationText)

        return _db?.insert(DATABASE_TABLE6, null, newRecommendationValues)
    }

    // Insert a new complication into the complications table
    fun insertComplication(complicationText: String): Long? {
        val newComplicationValues = ContentValues()
        newComplicationValues.put(COMPLICATION_TEXT, complicationText)

        return _db?.insert(DATABASE_TABLE7, null, newComplicationValues)
    }

    fun insertPersonContact(personId: Long, photo: String?, nickname: String?, name: String?, title: String?,
                            email: String?, phoneNumber: String?, address_street: String?, address_unit: String?, address_postal: String?, website: String?): Long? {
        val newContactValues = ContentValues()
        newContactValues.put(PERSON_ID, personId)
        newContactValues.put(PHOTO, photo)
        newContactValues.put(NICKNAME, nickname)
        newContactValues.put(NAME, name)
        newContactValues.put(TITLE, title)
        newContactValues.put(EMAIL, email)
        newContactValues.put(PHONE_NUMBER, phoneNumber)
        newContactValues.put(ADDRESS_STREET, address_street)
        newContactValues.put(ADDRESS_UNIT, address_unit)
        newContactValues.put(ADDRESS_POSTAL, address_postal)
        newContactValues.put(WEBSITE, website)
        return _db?.insert(DATABASE_TABLE8, null, newContactValues)
    }
    fun removePerson(_rowIndex: Int): Boolean {
        // Remove record from personalInfo table
        if (_db!!.delete(DATABASE_TABLE1, PERSON_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }

    fun removeMedicalHistory(_rowIndex: Int): Boolean {
        // Remove record from medicalHistory table
        if (_db!!.delete(DATABASE_TABLE2, ENTRY_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }

    fun removeHealthMetrics(_rowIndex: Int): Boolean {
        // Remove record from healthMetrics table
        if (_db!!.delete(DATABASE_TABLE3, PERSON_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }

    fun removeSurveyQuestion(_rowIndex: Int): Boolean {
        // Remove record from surveyQuestions table
        if (_db!!.delete(DATABASE_TABLE4, SURVEY_QUESTION_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }

    fun removeSurveyAnswer(_rowIndex: Int): Boolean {
        // Remove record from surveyAnswers table
        if (_db!!.delete(DATABASE_TABLE5, SURVEY_ANSWER_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }

    fun removeRecommendation(_rowIndex: Int): Boolean {
        // Remove record from recommendations table
        if (_db!!.delete(DATABASE_TABLE6, RECOMMENDATION_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }

    fun removeComplication(_rowIndex: Int): Boolean {
        // Remove record from complications table
        if (_db!!.delete(DATABASE_TABLE7, COMPLICATION_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }

    fun removePersonContact(_rowIndex: Int): Boolean {
        // Remove record from personContacts table
        if (_db!!.delete(DATABASE_TABLE8, CONTACT_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }


    fun updateEntry(_rowIndex: Int, entryName: String, entryTel: String): Boolean {


        return false
    }

    fun personTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from personalInfo table
        var c: Cursor? = null
        try {
            c = _db?.query(DATABASE_TABLE1, arrayOf(PERSON_ID, USER_NAME, NRIC, GENDER, DOB, BLOOD_TYPE, BMI, ALLERGIES), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(DATABASE_TABLE1, "Failed to retrieve all entries")
        }
        return c
    }

    fun medicalHistoryTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from medicalHistory table
        var c: Cursor? = null
        try {
            c = _db?.query(DATABASE_TABLE2, arrayOf(ENTRY_ID, PERSON_ID, MEDICAL_HISTORY, VACCINATION_HISTORY), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(DATABASE_TABLE2, "Failed to retrieve all entries")
        }
        return c
    }

    fun healthMetricsTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from healthMetrics table
        var c: Cursor? = null
        try {
            c = _db?.query(DATABASE_TABLE3, arrayOf(PERSON_ID, MUSCLE_MASS, HEIGHT, WEIGHT, HEART_RATE, SYSTOLIC_BLOOD_PRESSURE, DIASTOLIC_BLOOD_PRESSURE), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(DATABASE_TABLE3, "Failed to retrieve all entries")
        }
        return c
    }

    fun surveyQuestionsTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from surveyQuestions table
        var c: Cursor? = null
        try {
            c = _db?.query(DATABASE_TABLE4, arrayOf(SURVEY_QUESTION_ID, SURVEY_QUESTION_TEXT), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(DATABASE_TABLE4, "Failed to retrieve all entries")
        }
        return c
    }

    fun surveyAnswersTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from surveyAnswers table
        var c: Cursor? = null
        try {
            c = _db?.query(DATABASE_TABLE5, arrayOf(SURVEY_ANSWER_ID, SURVEY_QUESTION_ID, SURVEY_ANSWER_TEXT), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(DATABASE_TABLE5, "Failed to retrieve all entries")
        }
        return c
    }

    fun recommendationsTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from recommendations table
        var c: Cursor? = null
        try {
            c = _db?.query(DATABASE_TABLE6, arrayOf(RECOMMENDATION_ID, RECOMMENDATION_TEXT), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(DATABASE_TABLE6, "Failed to retrieve all entries")
        }
        return c
    }

    fun complicationsTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from complications table
        var c: Cursor? = null
        try {
            c = _db?.query(DATABASE_TABLE7, arrayOf(COMPLICATION_ID, COMPLICATION_TEXT), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(DATABASE_TABLE7, "Failed to retrieve all entries")
        }
        return c
    }

    fun personContactsTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from personContacts table
        var c: Cursor? = null
        try {
            c = _db?.query(DATABASE_TABLE8, arrayOf(CONTACT_ID, PERSON_ID, PHOTO, NICKNAME, NAME, TITLE, EMAIL, PHONE_NUMBER, ADDRESS_STREET, ADDRESS_UNIT, ADDRESS_POSTAL, WEBSITE), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(DATABASE_TABLE8, "Failed to retrieve all entries")
        }
        return c
    }


    inner class MyDBOpenHelper(c: Context, db_name : String, ver_no : Int ): SQLiteOpenHelper(c, db_name, null, ver_no){


        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(DATABASE_TABLE1_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $DATABASE_TABLE1 created!")

            db!!.execSQL(DATABASE_TABLE2_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $DATABASE_TABLE2 created!")

            db!!.execSQL(DATABASE_TABLE3_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $DATABASE_TABLE3 created!")

            db!!.execSQL(DATABASE_TABLE4_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $DATABASE_TABLE4 created!")

            db!!.execSQL(DATABASE_TABLE5_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $DATABASE_TABLE5 created!")

            db!!.execSQL(DATABASE_TABLE6_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $DATABASE_TABLE6 created!")

            db!!.execSQL(DATABASE_TABLE7_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $DATABASE_TABLE7 created!")

            db!!.execSQL(DATABASE_TABLE8_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $DATABASE_TABLE8 created!")

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        }
    }

}