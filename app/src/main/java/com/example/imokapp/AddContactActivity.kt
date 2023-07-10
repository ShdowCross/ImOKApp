package com.example.imokapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_contact.*

class AddContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_contact)

        //TODO 13 : Add listener to add contact into database

        addRecordsBtn.setOnClickListener {
            val inst = ImOKApp.getInstance()

            val personId = addPersonIdET.text.toString()
            val name = addNameET.text.toString()
            val photo = addPhotoET.text.toString()
            val nickname = addNicknameET.text.toString()
            val title = addTitleET.text.toString()
            val email = addEmailET.text.toString()
            val phoneNumber = addPhoneNumberET.text.toString()
            val addressStreet = addAddressStreetET.text.toString()
            val addressUnit = addAddressUnitET.text.toString()
            val addressPostal = addAddressPostalET.text.toString()
            val website = addWebsiteET.text.toString()

            inst.personContactsAddToDatabase(
                personId.toLong(),
                photo,
                nickname,
                name,
                title,
                email,
                phoneNumber,
                addressStreet,
                addressUnit,
                addressPostal,
                website
            )

            finish()
        }
    }
}
