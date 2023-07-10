package com.example.imokapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.database.Cursor
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_medical_contacts.*
import java.util.ArrayList


class PersonContacts : AppCompatActivity() {
    var doctor = arrayListOf("Cardiologist", "Dermatologist", "Neurologist", "Pediatrician", "Surgeon")
    var relative = arrayListOf("Brother", "Cousin", "Aunt", "Uncle", "Grandmother")
    var doctorAdapter: ArrayAdapter<String>? = null
    var relativeAdapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_contacts)
        val paths = getPaths()
        hideFields(paths.selectedDoctor)
        hideFields(paths.selectedRelative)
        showFields(paths.unselectedDoctor)
        showFields(paths.unselectedRelative)
        retrieveHardcodedContacts()
    }
    private fun retrieveContacts() {
//        val contactList: List<String>
//        val inst = ImOKApp.getInstance()
//        contactList = inst.personContactsRetrieveAll()
//        contactsAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, contactList)
    }
    private fun retrieveHardcodedContacts(){
        var paths = getPaths()
        medicalSelectionLeft.setOnClickListener{
            hideFields(paths.unselectedDoctor)
            showFields(paths.selectedDoctor)
            displayToast("You have selected your main doctor")
            val selectedDoctor = "General Doctor"
            var doctorInfo = getDoctorInfo(selectedDoctor)
            selectedDoctorNickname.text = selectedDoctor
            selectedDoctorName.text = doctorInfo.name
            selectedDoctorTitle.text = doctorInfo.title
            medicalProfileWebsite.text = doctorInfo.website
            selectedDoctorEmail.text = doctorInfo.email
            selectedDoctorPhoneNumber.text = doctorInfo.phoneNumber
            selectedDoctorStreet.text = doctorInfo.street
            selectedDoctorUnit.text = doctorInfo.unit
            selectedDoctorPostal.text = doctorInfo.postal
        }
        doctorAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, doctor)
        medicalSelectionRight.adapter = doctorAdapter
        medicalSelectionRight.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            hideFields(paths.unselectedDoctor)
            showFields(paths.selectedDoctor)
            val selectedDoctor = parent?.adapter?.getItem(position).toString()
            val doctorInfo = getDoctorInfo(selectedDoctor)
            displayToast("You have selected " + parent?.adapter?.getItem(position) + " of doctor")
            selectedDoctorNickname.text = selectedDoctor
            selectedDoctorName.text = doctorInfo.name
            selectedDoctorTitle.text = doctorInfo.title
            medicalProfileWebsite.text = doctorInfo.website
            selectedDoctorEmail.text = doctorInfo.email
            selectedDoctorPhoneNumber.text = doctorInfo.phoneNumber
            selectedDoctorStreet.text = doctorInfo.street
            selectedDoctorUnit.text = doctorInfo.unit
            selectedDoctorPostal.text = doctorInfo.postal
        }
        relativeSelectionLeft.setOnClickListener{
            hideFields(paths.unselectedRelative)
            showFields(paths.selectedRelative)
            val selectedRelative = "Mother"
            val relativeInfo = getRelativeInfo(selectedRelative)
            selectedRelativeNickname.text = selectedRelative
            selectedRelativeName.text = relativeInfo.name
            selectedRelativeNRIC.text = relativeInfo.nric
            selectedRelativeTitle.text = relativeInfo.title
            selectedRelativeEmail.text = relativeInfo.email
            selectedRelativePhoneNumber.text = relativeInfo.phoneNumber
            selectedRelativeStreet.text = relativeInfo.street
            selectedRelativeUnit.text = relativeInfo.unit
            selectedRelativePostal.text = relativeInfo.postal
            displayToast("You have selected your $selectedRelative")
        }
        relativeAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, relative)
        relativeSelectionRight.adapter = relativeAdapter
        relativeSelectionRight.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            hideFields(paths.unselectedRelative)
            showFields(paths.selectedRelative)
            val selectedRelative = parent?.adapter?.getItem(position).toString()
            val relativeInfo = getRelativeInfo(selectedRelative)
            selectedRelativeNickname.text = selectedRelative
            selectedRelativeName.text = relativeInfo.name
            selectedRelativeNRIC.text = relativeInfo.nric
            selectedRelativeTitle.text = relativeInfo.title
            selectedRelativeEmail.text = relativeInfo.email
            selectedRelativePhoneNumber.text = relativeInfo.phoneNumber
            selectedRelativeStreet.text = relativeInfo.street
            selectedRelativeUnit.text = relativeInfo.unit
            selectedRelativePostal.text = relativeInfo.postal
            displayToast("You have selected your " + parent?.adapter?.getItem(position))
        }
    }
    private fun getDoctorInfo(doctorName: String): DoctorInfo {
        return when (doctorName) {
            "General Doctor" -> DoctorInfo(
                name = "Dr. John Smith",
                title = "General Doctor",
                website = "https://example.com/doctors/johnsmith",
                email = "johnsmith@example.com",
                phoneNumber = "+65 12345678",
                street = "123 Main Street",
                unit = "Unit 1",
                postal = "123456"
            )
            "Cardiologist" -> DoctorInfo(
                name = "Dr. John",
                title = "Cardiologist",
                website = "www.cardiologyclinic.com",
                email = "drjohn@example.com",
                phoneNumber = "+1234567890",
                street = "456 Heart Avenue",
                unit = "Unit 2",
                postal = "67890"
            )
            "Dermatologist" -> DoctorInfo(
                name = "Dr. Emily",
                title = "Dermatologist",
                website = "www.dermatologyclinic.com",
                email = "dremily@example.com",
                phoneNumber = "+0987654321",
                street = "789 Skin Street",
                unit = "Unit 3",
                postal = "54321"
            )
            "Neurologist" -> DoctorInfo(
                name = "Dr. Michael",
                title = "Neurologist",
                website = "www.neurologyclinic.com",
                email = "drmichael@example.com",
                phoneNumber = "+1112223333",
                street = "321 Brain Avenue",
                unit = "Unit 5",
                postal = "98765"
            )
            "Pediatrician" -> DoctorInfo(
                name = "Dr. Sarah",
                title = "Pediatrician",
                website = "www.pediatricsclinic.com",
                email = "drsarah@example.com",
                phoneNumber = "+4445556666",
                street = "234 Kids Street",
                unit = "Unit 1",
                postal = "34567"
            )
            "Surgeon" -> DoctorInfo(
                name = "Dr. David",
                title = "Surgeon",
                website = "www.surgeryclinic.com",
                email = "drdavid@example.com",
                phoneNumber = "+7778889999",
                street = "876 Operation Avenue",
                unit = "Unit 7",
                postal = "12345"
            )
            "Gastroenterologist" -> DoctorInfo(
                name = "Dr. Samantha",
                title = "Gastroenterologist",
                website = "www.gastroclinic.com",
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
                name = "Jane Smith",
                nric = "S9876543B",
                title = "Mother",
                email = "janesmith@example.com",
                phoneNumber = "+65 87654321",
                street = "123 Main Street",
                unit = "#12-34",
                postal = "654321"
            )
            "Brother" -> RelativeInfo(
                name = "John",
                nric = "1234567890",
                title = "Brother",
                email = "john@example.com",
                phoneNumber = "+1234567890",
                street = "123 Brother Street",
                unit = "Unit 1",
                postal = "12345"
            )
            "Cousin" -> RelativeInfo(
                name = "Emily",
                nric = "0987654321",
                title = "Cousin",
                email = "emily@example.com",
                phoneNumber = "+0987654321",
                street = "456 Cousin Street",
                unit = "Unit 2",
                postal = "54321"
            )
            "Aunt" -> RelativeInfo(
                name = "Samantha",
                nric = "6789012345",
                title = "Aunt",
                email = "samantha@example.com",
                phoneNumber = "+1112223333",
                street = "789 Aunt Street",
                unit = "Unit 3",
                postal = "67890"
            )
            "Uncle" -> RelativeInfo(
                name = "Michael",
                nric = "5432109876",
                title = "Uncle",
                email = "michael@example.com",
                phoneNumber = "+4445556666",
                street = "234 Uncle Street",
                unit = "Unit 4",
                postal = "34567"
            )
            "Grandmother" -> RelativeInfo(
                name = "Sarah",
                nric = "9876543210",
                title = "Grandmother",
                email = "sarah@example.com",
                phoneNumber = "+7778889999",
                street = "567 Grandmother Street",
                unit = "Unit 5",
                postal = "98765"
            )
            else -> RelativeInfo(
                name = "Unknown Relative",
                nric = "",
                title = "",
                email = "",
                phoneNumber = "",
                street = "",
                unit = "",
                postal = ""
            )
        }
    }
    private fun hideFields(layout: LinearLayout) {
        for (i in 0 until layout.childCount) {
            val child = layout.getChildAt(i)
            child.visibility = View.GONE
        }
    }
    private fun showFields(layout: LinearLayout) {
        for (i in 0 until layout.childCount) {
            val child = layout.getChildAt(i)
            child.visibility = View.VISIBLE
        }
    }
    fun displayToast(message:String){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }
    data class Paths(
        val selectedDoctor: LinearLayout,
        val selectedRelative: LinearLayout,
        val unselectedDoctor: LinearLayout,
        val unselectedRelative: LinearLayout
    )

    fun getPaths(): Paths {
        val selectedDoctor = findViewById<LinearLayout>(R.id.selectedMedical)
        val selectedRelative = findViewById<LinearLayout>(R.id.selectedRelative)
        val unselectedDoctor = findViewById<LinearLayout>(R.id.unselectedDoctorLL)
        val unselectedRelative = findViewById<LinearLayout>(R.id.unselectedRelativeLL)
        return Paths(selectedDoctor, selectedRelative, unselectedDoctor, unselectedRelative)
    }

}

data class DoctorInfo(
    val name: String,
    val title: String,
    val website: String,
    val email: String,
    val phoneNumber: String,
    val street: String,
    val unit: String,
    val postal: String
)
data class RelativeInfo(
    val name: String,
    val nric: String,
    val title: String,
    val email: String,
    val phoneNumber: String,
    val street: String,
    val unit: String,
    val postal: String
)
