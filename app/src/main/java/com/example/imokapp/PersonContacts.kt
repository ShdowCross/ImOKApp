package com.example.imokapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.database.Cursor
import android.widget.ArrayAdapter
import java.util.ArrayList


class PersonContacts : AppCompatActivity() {

    var contactsAdapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_contacts)
    }
    private fun retrieveContacts(){
        val contactList: List<String>
        val inst = ImOKApp.ourInstance
        contactList = inst.personContactsRetrieveAll(applicationContext)
        contactsAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, contactList)
        //ArrayAdapterStuff
    }
}