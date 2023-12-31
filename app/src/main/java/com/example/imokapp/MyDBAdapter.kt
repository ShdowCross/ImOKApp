package com.example.imokapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyDBAdapter(c : Context){


    private val DATABASE_NAME = "ImOKApp.db"
    private val personInfoTable = "personInfo"
    private val healthMetricsTable = "medicalHistory"
    private val medicalHistoryTable = "healthMetrics"
    private val surveyQuestionsTable = "surveyQuestions"
    private val surveyAnswersTable = "surveyAnswers"
    private val recommendationsTable = "recommendations"
    private val complicationsTable = "complications"
    private val personContactsTable = "personContacts"

    private val DATABASE_VERSION = 1
    private var _db: SQLiteDatabase? = null
    private val context: Context?= null

    //personInfo
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

    //medicalHistoryTable
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
    val SURVEY_QUESTION_ID = "_survey_question_id"
    val COLUMN_SURVEY_QUESTION_ID = 16
    val SURVEY_QUESTION_TEXT = "question_text"
    val COLUMN_SURVEY_QUESTION_TEXT = 17

    // Survey Answers
    val SURVEY_ANSWER_ID = "_survey_answer_id"
    val COLUMN_SURVEY_ANSWER_ID = 18
    val SURVEY_ANSWER_TEXT = "answer_text"
    val COLUMN_SURVEY_ANSWER_TEXT = 19

    // Recommendations
    val RECOMMENDATION_ID = "_recommendation_id"
    val COLUMN_RECOMMENDATION_ID = 20
    val RECOMMENDATION_TEXT = "recommendation_text"
    val COLUMN_RECOMMENDATION_TEXT = 21

    // Complications
    val COMPLICATION_ID = "_complication_id"
    val COLUMN_COMPLICATION_ID = 22
    val COMPLICATION_TEXT = "complication_text"
    val COLUMN_COMPLICATION_TEXT = 23

    //personContacts
    val CONTACT_ID = "_contact_id"
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

    protected val personInfoTable_CREATE = ("create table " + personInfoTable + " " + "(" + PERSON_ID + " integer primary key autoincrement, " + USER_NAME + " Text, "
            + NRIC + " text not null, " + GENDER + " text, " + DOB + " text, " + BLOOD_TYPE + " text, " + BMI + " text, " + ALLERGIES + " text);")

    protected val healthMetricsTable_CREATE = ("create table " + healthMetricsTable + " " + "(" + ENTRY_ID + " integer primary key autoincrement, " + PERSON_ID + " integer, "
            + MEDICAL_HISTORY + " text, " + VACCINATION_HISTORY + " text, "
            + "FOREIGN KEY(" + PERSON_ID + ") REFERENCES " + personInfoTable + "(" + PERSON_ID + "));")

    protected val medicalHistoryTable_CREATE = ("create table " + medicalHistoryTable + " " + "(" + PERSON_ID + " integer, " + MUSCLE_MASS + " text, "
            + HEIGHT + " text, " + WEIGHT + " text, " + HEART_RATE + " text, " + SYSTOLIC_BLOOD_PRESSURE + " text, " + DIASTOLIC_BLOOD_PRESSURE + " text, "
            + "FOREIGN KEY(" + PERSON_ID + ") REFERENCES " + personInfoTable + "(" + PERSON_ID + "));")

    protected val surveyQuestionsTable_CREATE = ("create table " + surveyQuestionsTable + " " + "(" + SURVEY_QUESTION_ID + " integer primary key autoincrement, " + SURVEY_QUESTION_TEXT + " text);")

    protected val surveyAnswersTable_CREATE = ("create table " + surveyAnswersTable + " " + "(" + SURVEY_ANSWER_ID + " integer primary key autoincrement, " + SURVEY_QUESTION_ID + " integer, "
            + SURVEY_ANSWER_TEXT + " text, "
            + "FOREIGN KEY(" + SURVEY_QUESTION_ID + ") REFERENCES " + surveyQuestionsTable + "(" + SURVEY_QUESTION_ID + "));")

    protected val recommendationsTable_CREATE = ("create table " + recommendationsTable + " " + "(" + RECOMMENDATION_ID + " integer primary key autoincrement, " + RECOMMENDATION_TEXT + " text);")

    protected val complicationsTable_CREATE = ("create table " + complicationsTable + " " + "(" + COMPLICATION_ID + " integer primary key autoincrement, " + COMPLICATION_TEXT + " text);")

    protected val personContactsTable_CREATE = ("create table " + personContactsTable + " " + "(" + CONTACT_ID + " integer primary key autoincrement, "
            + PERSON_ID + " integer, " + PHOTO + " text, " + NICKNAME + " text, " + NAME + " text, " + TITLE + " text, "
            + EMAIL + " text, " + PHONE_NUMBER + " text, " + ADDRESS_STREET + " text, " + ADDRESS_UNIT + " text, " + ADDRESS_POSTAL + " text, " +WEBSITE + " text, "
            + "FOREIGN KEY(" + PERSON_ID + ") REFERENCES " + personInfoTable + "(" + PERSON_ID + "));")

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

    // Insert a new person's details into the personInfo table
    fun insertPersonInfo( newValues:ContentValues): Long? {
        var rowId: Long? = null
        try {
            _db?.beginTransaction()
            rowId = _db?.insert(personInfoTable, null, newValues)
            _db?.setTransactionSuccessful()
        } catch (e: Exception) {
            Log.e(MYDBADAPTER_LOG_CAT, "Error inserting data into $personInfoTable: ${e.message}")
        } finally {
            _db?.endTransaction()
        }
        return rowId
    }

    // Insert a new entry into the medicalHistory table
    fun insertMedicalHistory(personId: Long, medicalHistory: String, vaccinationHistory: String): Long? {
        val newMedicalHistoryValues = ContentValues()
        newMedicalHistoryValues.put(PERSON_ID, personId)
        newMedicalHistoryValues.put(MEDICAL_HISTORY, medicalHistory)
        newMedicalHistoryValues.put(VACCINATION_HISTORY, vaccinationHistory)
        return _db?.insert(healthMetricsTable, null, newMedicalHistoryValues)

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

        return _db?.insert(medicalHistoryTable, null, newHealthMetricsValues)
    }

    // Insert a new survey question into the surveyQuestions table
    fun insertSurveyQuestion(questionText: String): Long? {
        val newSurveyQuestionValues = ContentValues()
        newSurveyQuestionValues.put(SURVEY_QUESTION_TEXT, questionText)

        return _db?.insert(surveyQuestionsTable, null, newSurveyQuestionValues)
    }

    // Insert a new survey answer into the surveyAnswers table
    fun insertSurveyAnswer(questionId: Long, answerText: String): Long? {
        val newSurveyAnswerValues = ContentValues()
        newSurveyAnswerValues.put(SURVEY_QUESTION_ID, questionId)
        newSurveyAnswerValues.put(SURVEY_ANSWER_TEXT, answerText)

        return _db?.insert(surveyAnswersTable, null, newSurveyAnswerValues)
    }

    // Insert a new recommendation into the recommendations table
    fun insertRecommendation(recommendationText: String): Long? {
        val newRecommendationValues = ContentValues()
        newRecommendationValues.put(RECOMMENDATION_TEXT, recommendationText)

        return _db?.insert(recommendationsTable, null, newRecommendationValues)
    }

    // Insert a new complication into the complications table
    fun insertComplication(complicationText: String): Long? {
        val newComplicationValues = ContentValues()
        newComplicationValues.put(COMPLICATION_TEXT, complicationText)

        return _db?.insert(complicationsTable, null, newComplicationValues)
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
        return _db?.insert(personContactsTable, null, newContactValues)
    }
    fun removePerson(_rowIndex: Int): Boolean {
        // Remove record from personInfo table
        if (_db!!.delete(personInfoTable, PERSON_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }

    fun removeMedicalHistory(_rowIndex: Int): Boolean {
        // Remove record from medicalHistory table
        if (_db!!.delete(healthMetricsTable, ENTRY_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }

    fun removeHealthMetrics(_rowIndex: Int): Boolean {
        // Remove record from healthMetrics table
        if (_db!!.delete(medicalHistoryTable, PERSON_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }

    fun removeSurveyQuestion(_rowIndex: Int): Boolean {
        // Remove record from surveyQuestions table
        if (_db!!.delete(surveyQuestionsTable, SURVEY_QUESTION_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }

    fun removeSurveyAnswer(_rowIndex: Int): Boolean {
        // Remove record from surveyAnswers table
        if (_db!!.delete(surveyAnswersTable, SURVEY_ANSWER_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }

    fun removeRecommendation(_rowIndex: Int): Boolean {
        // Remove record from recommendations table
        if (_db!!.delete(recommendationsTable, RECOMMENDATION_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }

    fun removeComplication(_rowIndex: Int): Boolean {
        // Remove record from complications table
        if (_db!!.delete(complicationsTable, COMPLICATION_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }

    fun removePersonContact(_rowIndex: Int): Boolean {
        // Remove record from personContacts table
        if (_db!!.delete(personContactsTable, CONTACT_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }


    fun updateEntry(_rowIndex: Int, entryName: String, entryTel: String): Boolean {


        return false
    }

    fun personTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from personInfo table
        var c: Cursor? = null
        try {
            c = _db?.query(personInfoTable, arrayOf(PERSON_ID, USER_NAME, NRIC, GENDER, DOB, BLOOD_TYPE, BMI, ALLERGIES), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(personInfoTable, "Failed to retrieve all entries")
        }
        return c
    }

    fun medicalHistoryTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from medicalHistory table
        var c: Cursor? = null
        try {
            c = _db?.query(healthMetricsTable, arrayOf(ENTRY_ID, PERSON_ID, MEDICAL_HISTORY, VACCINATION_HISTORY), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(healthMetricsTable, "Failed to retrieve all entries")
        }
        return c
    }

    fun healthMetricsTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from healthMetrics table
        var c: Cursor? = null
        try {
            c = _db?.query(medicalHistoryTable, arrayOf(PERSON_ID, MUSCLE_MASS, HEIGHT, WEIGHT, HEART_RATE, SYSTOLIC_BLOOD_PRESSURE, DIASTOLIC_BLOOD_PRESSURE), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(medicalHistoryTable, "Failed to retrieve all entries")
        }
        return c
    }

    fun surveyQuestionsTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from surveyQuestions table
        var c: Cursor? = null
        try {
            c = _db?.query(surveyQuestionsTable, arrayOf(SURVEY_QUESTION_ID, SURVEY_QUESTION_TEXT), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(surveyQuestionsTable, "Failed to retrieve all entries")
        }
        return c
    }

    fun surveyAnswersTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from surveyAnswers table
        var c: Cursor? = null
        try {
            c = _db?.query(surveyAnswersTable, arrayOf(SURVEY_ANSWER_ID, SURVEY_QUESTION_ID, SURVEY_ANSWER_TEXT), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(surveyAnswersTable, "Failed to retrieve all entries")
        }
        return c
    }

    fun recommendationsTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from recommendations table
        var c: Cursor? = null
        try {
            c = _db?.query(recommendationsTable, arrayOf(RECOMMENDATION_ID, RECOMMENDATION_TEXT), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(recommendationsTable, "Failed to retrieve all entries")
        }
        return c
    }

    fun complicationsTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from complications table
        var c: Cursor? = null
        try {
            c = _db?.query(complicationsTable, arrayOf(COMPLICATION_ID, COMPLICATION_TEXT), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(complicationsTable, "Failed to retrieve all entries")
        }
        return c
    }


    fun personContactsTableRetrieveAllEntriesCursor(): Cursor? {
        // Retrieve all records from personContacts table
        var c: Cursor? = null
        try {
            c = _db?.query(personContactsTable, arrayOf(CONTACT_ID, PERSON_ID, PHOTO, NICKNAME, NAME, TITLE, EMAIL, PHONE_NUMBER, ADDRESS_STREET, ADDRESS_UNIT, ADDRESS_POSTAL, WEBSITE), null, null, null, null, null)
        } catch (e: SQLiteException) {
            Log.w(personContactsTable, "Failed to retrieve all entries")
        }
        return c
    }


    inner class MyDBOpenHelper(c: Context, db_name : String, ver_no : Int ): SQLiteOpenHelper(c, db_name, null, ver_no){

        override fun onCreate(db: SQLiteDatabase?) {
            Log.d(MYDBADAPTER_LOG_CAT, "onCreate")

            db!!.execSQL(personInfoTable_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $personInfoTable created!")

            db!!.execSQL(medicalHistoryTable_CREATE)
            db!!.execSQL("PRAGMA foreign_keys=ON;")
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $medicalHistoryTable created!")

            db!!.execSQL(healthMetricsTable_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $healthMetricsTable created!")

            db!!.execSQL(surveyQuestionsTable_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $surveyQuestionsTable created!")

            db!!.execSQL(surveyAnswersTable_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $surveyAnswersTable created!")

            db!!.execSQL(recommendationsTable_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $recommendationsTable created!")

            db!!.execSQL(complicationsTable_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $complicationsTable created!")

            db!!.execSQL(personContactsTable_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $personContactsTable created!")
        }
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        }
    }
}