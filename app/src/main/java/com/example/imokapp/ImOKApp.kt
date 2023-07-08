package com.example.imokapp

import android.app.Application
import android.content.Context
import android.database.Cursor
import java.util.ArrayList


//TODO 7 :
// - Modify MyContacts class to extend application
// - Declare the two list objects that will hold the id and contact information
// - Create a static instance of MyContacts using the constructor
class ImOKApp() : Application(){

    //Calling of classes - Person Contacts
    private var contactList: ArrayList<String> = ArrayList<String>()
    private var contactIdList: ArrayList<Int> = ArrayList<Int>()

    companion object {
        val ourInstance = ImOKApp()
    }

    //Adding to DB - Person Contacts
    fun personContactsAddToDatabase(personId: Long, photo: String?, nickname: String?, name: String?, title: String?,
                                    email: String?, phoneNumber: String?, address_street: String?, address_unit: String?, address_postal: String?, website: String?, c: Context): Long? {

        val db = MyDBAdapter(c)
        db.open()

        val rowIDofInsertedEntry = db.insertPersonContact(personId, photo, nickname, name, title, email, phoneNumber, address_street, address_unit, address_postal, website)
        db.close()

        return rowIDofInsertedEntry
    }

    //Deleting from DB - Person Contacts
    fun personContactsDeleteFrmDatabase(contactID: Int, c: Context): Boolean {

        val db = MyDBAdapter(c)
        db.open()

        val id = contactIdList[contactID]

        val updateStatus = db.removePersonContact(id)
        db.close()

        return updateStatus
    }

    //Retrieve All from DB - Person Contacts
    fun personContactsRetrieveAll(c: Context): List<String> {

        val myCursor: Cursor?
        var contactName = ""
        val db = MyDBAdapter(c)
        db.open()
        contactIdList.clear()
        contactList.clear()
        myCursor = db.personContactsTableRetrieveAllEntriesCursor()
        if (myCursor != null && myCursor!!.count > 0){
            myCursor!!.moveToFirst()
            do{
                contactIdList.add(myCursor.getInt(db.COLUMN_CONTACT_ID)) // need to take based on personID
                contactName = myCursor.getString(db.COLUMN_NAME) // retrieve name fields need to put the classes into the contactList to extract all the features
                contactList.add(contactName)
            } while(myCursor.moveToNext())
        }
        db.close()
        return contactList
    }
}