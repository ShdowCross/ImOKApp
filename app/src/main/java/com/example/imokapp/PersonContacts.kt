package com.example.imokapp

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.imokapp.ImOKApp.Companion.addedRelatives
import com.example.imokapp.ImOKApp.Companion.doctors
import com.example.imokapp.ImOKApp.Companion.relatives
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_medical_contacts.*
import java.util.ArrayList


class PersonContacts : AppCompatActivity() {
    var doctor = arrayListOf<String>()
    var relative = arrayListOf<String>()
    var doctorAdapter: ArrayAdapter<String>? = null
    var relativeAdapter: ArrayAdapter<String>? = null
    val defaultImageResourceId = R.drawable.profile // Replace with the actual resource ID of your default image


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_contacts)

        val back = findViewById<Button>(R.id.backBtn)
        back.setOnClickListener {
            val intent = Intent(this, PatientProfile::class.java)
            startActivity(intent)
        }
        for (entry in doctors){
            if (entry.key != "General Doctor"){
                doctor.add(entry.key)
            }
        }
        for (entry in relatives){
            if (entry.key != "Mother"){
                relative.add(entry.key)
            }
        }
        val paths = getPaths()
        val main1Doctor = "General Doctor"
        val main1Info = getDoctorInfo(main1Doctor)
        val main2Doctor = "Mother"
        val main2Info = getRelativeInfo(main2Doctor)
        Picasso.get().load(resources.getIdentifier(main1Info.imageUrl, "drawable", packageName))
        Picasso.get().load(resources.getIdentifier(main2Info.imageUrl, "drawable", packageName))
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

    private fun retrieveHardcodedContacts() {
        var paths = getPaths()
        medicalSelectionLeft.setOnClickListener {
            hideFields(paths.unselectedDoctor)
            showFields(paths.selectedDoctor)
            val selectedDoctor = "General Doctor"
            displayToast("You have selected your main doctor")
            var doctorInfo = doctors[selectedDoctor]
            val imageResourceId =
                resources.getIdentifier(doctorInfo?.imageUrl, "drawable", packageName)
            selectedDoctorNickname.text = selectedDoctor
            selectedDoctorName.text = doctorInfo?.name
            selectedDoctorTitle.text = doctorInfo?.title
            medicalProfileWebsite.text = doctorInfo?.website
            val imageRequest = if (imageResourceId != 0) {
                Picasso.get().load(imageResourceId)
            } else {
                Picasso.get().load(defaultImageResourceId)
            }
            imageRequest.error(defaultImageResourceId).into(paths.selectedDoctorProfileImg)
            selectedDoctorEmail.text = doctorInfo?.email
            selectedDoctorPhoneNumber.text = doctorInfo?.phoneNumber
            selectedDoctorStreet.text = doctorInfo?.street
            selectedDoctorUnit.text = doctorInfo?.unit
            selectedDoctorPostal.text = doctorInfo?.postal
        }
        doctorAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, doctor)
        medicalSelectionRight.adapter = doctorAdapter
        medicalSelectionRight.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                hideFields(paths.unselectedDoctor)
                showFields(paths.selectedDoctor)
                val selectedDoctor = parent?.adapter?.getItem(position).toString()
                val doctorInfo = doctors[selectedDoctor]
                val imageResourceId =
                    resources.getIdentifier(doctorInfo?.imageUrl, "drawable", packageName)
                displayToast("You have selected " + parent?.adapter?.getItem(position) + " of doctor")
                selectedDoctorNickname.text = selectedDoctor
                selectedDoctorName.text = doctorInfo?.name
                selectedDoctorTitle.text = doctorInfo?.title
                medicalProfileWebsite.text = doctorInfo?.website
                val imageRequest = if (imageResourceId != 0) {
                    Picasso.get().load(imageResourceId)
                } else {
                    Picasso.get().load(defaultImageResourceId)
                }
                imageRequest.error(defaultImageResourceId).into(paths.selectedDoctorProfileImg)
                selectedDoctorEmail.text = doctorInfo?.email
                selectedDoctorPhoneNumber.text = doctorInfo?.phoneNumber
                selectedDoctorStreet.text = doctorInfo?.street
                selectedDoctorUnit.text = doctorInfo?.unit
                selectedDoctorPostal.text = doctorInfo?.postal
            }
        relativeSelectionLeft.setOnClickListener {
            hideFields(paths.unselectedRelative)
            showFields(paths.selectedRelative)
            val selectedRelative = "Mother"
            val relativeInfo = relatives[selectedRelative]
            val imageResourceId =
                resources.getIdentifier(relativeInfo?.imageUrl, "drawable", packageName)
            selectedRelativeNickname.text = selectedRelative
            selectedRelativeName.text = relativeInfo?.name
            selectedRelativeTitle.text = relativeInfo?.title
            val imageRequest = if (imageResourceId != 0) {
                Picasso.get().load(imageResourceId)
            } else {
                Picasso.get().load(defaultImageResourceId)
            }
            imageRequest.error(defaultImageResourceId).into(paths.selectedRelativeProfileImg)
            selectedRelativeEmail.text = relativeInfo?.email
            selectedRelativePhoneNumber.text = relativeInfo?.phoneNumber
            selectedRelativeStreet.text = relativeInfo?.street
            selectedRelativeUnit.text = relativeInfo?.unit
            selectedRelativePostal.text = relativeInfo?.postal
            displayToast("You have selected your $selectedRelative")
        }
        relativeAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, relative)
        relativeSelectionRight.adapter = relativeAdapter
        relativeSelectionRight.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                hideFields(paths.unselectedRelative)
                showFields(paths.selectedRelative)
                val selectedRelative = parent?.adapter?.getItem(position).toString()
                val relativeInfo = relatives[selectedRelative]
                val imageResourceId =
                    resources.getIdentifier(relativeInfo?.imageUrl, "drawable", packageName)
                selectedRelativeNickname.text = selectedRelative
                selectedRelativeName.text = relativeInfo?.name
                selectedRelativeTitle.text = relativeInfo?.title
                val imageRequest = if (imageResourceId != 0) {
                    Picasso.get().load(imageResourceId)
                } else {
                    Picasso.get().load(defaultImageResourceId)
                }
                imageRequest.error(defaultImageResourceId).into(paths.selectedRelativeProfileImg)
                selectedRelativeEmail.text = relativeInfo?.email
                selectedRelativePhoneNumber.text = relativeInfo?.phoneNumber
                selectedRelativeStreet.text = relativeInfo?.street
                selectedRelativeUnit.text = relativeInfo?.unit
                selectedRelativePostal.text = relativeInfo?.postal
                displayToast("You have selected your " + parent?.adapter?.getItem(position))
            }
        addPeopleBtn.setOnClickListener {
            showFormDialog()
        }
    }

    private fun showFormDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.add_family_friends, null)

        // Create the dialog builder
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Modal Form") // Set the title of the dialog

        dialogBuilder.setPositiveButton("Submit", DialogInterface.OnClickListener { dialog, which ->
            var formName = dialogView.findViewById<EditText>(R.id.editTextName)
            var formTitle = dialogView.findViewById<EditText>(R.id.editTextTitle)
            var formPhoneNumber = dialogView.findViewById<EditText>(R.id.editTextPhoneNumber)
            var formEmail = dialogView.findViewById<EditText>(R.id.editTextEmail)
            var formStreet = dialogView.findViewById<EditText>(R.id.editTextStreet)
            var formUnit = dialogView.findViewById<EditText>(R.id.editTextUnit)
            var formPostal = dialogView.findViewById<EditText>(R.id.editTextPostal)
            val newRelativeData = RelativeInfo(
                name = formName.text.toString(),
                title = formTitle.text.toString(),
                imageUrl = "null",
                phoneNumber = formPhoneNumber.text.toString(),
                email = formEmail.text.toString(),
                street = formStreet.text.toString(),
                unit = formUnit.text.toString(),
                postal = formPostal.text.toString()
            )
            relatives[formTitle.text.toString()] = newRelativeData
            addedRelatives[formTitle.text.toString()] = newRelativeData
            recreate()
        })

        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            // Handle cancel button click if needed
            dialog.dismiss()
        })

        // Create and show the dialog
        val dialog = dialogBuilder.create()
        dialog.show()
    }
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
    class Paths(
        val selectedDoctor: LinearLayout,
        val selectedRelative: LinearLayout,
        val unselectedDoctor: LinearLayout,
        val unselectedRelative: LinearLayout,
        val selectedDoctorProfileImg: ImageView,
        val selectedRelativeProfileImg: ImageView
    )
    fun getPaths(): Paths {
        val selectedDoctor = findViewById<LinearLayout>(R.id.selectedMedical)
        val selectedRelative = findViewById<LinearLayout>(R.id.selectedRelative)
        val unselectedDoctor = findViewById<LinearLayout>(R.id.unselectedDoctorLL)
        val unselectedRelative = findViewById<LinearLayout>(R.id.unselectedRelativeLL)
        val selectedDoctorProfileImg = findViewById<ImageView>(R.id.chosenMedicalProfileImg)
        val selectedRelativeProfileImg = findViewById<ImageView>(R.id.chosenRelativeProfileImg)
        return Paths(selectedDoctor, selectedRelative, unselectedDoctor, unselectedRelative, selectedDoctorProfileImg, selectedRelativeProfileImg)
    }
}

data class DoctorInfo(
    val name: String,
    val title: String,
    val website: String,
    val imageUrl : String,
    val email: String,
    val phoneNumber: String,
    val street: String,
    val unit: String,
    val postal: String
)
data class RelativeInfo(
    val name: String,
    val title: String,
    val imageUrl : String,
    val email: String,
    val phoneNumber: String,
    val street: String,
    val unit: String,
    val postal: String
)
