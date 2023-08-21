package com.example.imokapp

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_medical_contacts.*
import kotlinx.android.synthetic.main.add_family_friends.*
import java.io.File
import java.util.ArrayList


class PersonContacts : AppCompatActivity() {
    var doctor = arrayListOf<String>()
    var relative = arrayListOf<String>()
    var doctorAdapter: ArrayAdapter<String>? = null
    var relativeAdapter: ArrayAdapter<String>? = null
    val personInfo = ImOKApp.Companion.PersonInfo()
    private val defaultImageResourceId = R.drawable.profile // Replace with the actual resource ID of your default image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_contacts)

        val back = findViewById<Button>(R.id.backBtn)
        back.setOnClickListener {
            val intent = Intent(this, PatientProfile::class.java)
            startActivity(intent)
        }

        for (entry in personInfo.doctors){
            if (entry.key != "General Doctor"){
                doctor.add(entry.key)
            }
        }
        for (entry in personInfo.relatives){
            if (entry.key != "Mother"){
                relative.add(entry.key)
            }
        }

        val paths = getPaths()
        val mainDoctor = "General Doctor"
        val mainDoctorInfo = personInfo.doctors[mainDoctor]
        val mainRelative = "Mother"
        val mainRelativeInfo = personInfo.relatives[mainRelative]
        Picasso.get().load(resources.getIdentifier(mainDoctorInfo?.imageUrl, "drawable", packageName))
        Picasso.get().load(resources.getIdentifier(mainRelativeInfo?.imageUrl, "drawable", packageName))
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

    private fun retrieveHardcodedContacts() {
        var paths = getPaths()
        medicalSelectionLeft.setOnClickListener {
            hideFields(paths.unselectedDoctor)
            showFields(paths.selectedDoctor)
            val selectedDoctor = "General Doctor"
            displayToast("You have selected your main doctor")
            var doctorInfo = personInfo.doctors[selectedDoctor]
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
                val doctorInfo = personInfo.doctors[selectedDoctor]
                val imageResourceId = resources.getIdentifier(doctorInfo?.imageUrl, "drawable", packageName)
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
            val relativeInfo = personInfo.relatives[selectedRelative]
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
                val relativeInfo = personInfo.relatives[selectedRelative]
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
            personInfo.relatives[formTitle.text.toString()] = newRelativeData
            val gson = Gson()
            val json = gson.toJson(personInfo.relatives)
            val file = File(this.filesDir, "addedRelatives.json")
            val typeToken = object : TypeToken<MutableMap<String, RelativeInfo>>() {}.type
            val deserializedRelatives: MutableMap<String, RelativeInfo> = gson.fromJson(json, typeToken)
            for ((key, value) in deserializedRelatives){
                Log.d("addedRelativesContentWrite", "Key: $key, Value: $value")
            }
            file.writeText(json)
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
