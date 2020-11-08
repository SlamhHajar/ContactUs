package com.hajarslamah.intent

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


private const val REQUEST_DAIL = 0

class MainActivity : AppCompatActivity() {
    private lateinit var callPhone: ImageButton
    private lateinit var sendEmail: ImageButton
    private lateinit var openMap: ImageButton
    private lateinit var gitHub: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callPhone=findViewById(R.id.btn_call)
        sendEmail=findViewById(R.id.btn_gmail)
        openMap=findViewById(R.id.btn_google_map)
        gitHub=findViewById(R.id.btn_gthb)
        callPhone.apply {
            setOnClickListener {
                if (ActivityCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED) {
                    // ActivityCompat.shouldShowRequestPermissionRationale(this as Activity, Manifest.permission.CALL_PHONE)
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        REQUEST_DAIL
                    )
                } else {
                    if(contactExists(this@MainActivity ,"77777776")==false) {
                        addContact("Eng/Hajar Salamh", "programer@gmail.com", "77777776")
                    }else{
                            callRequst()
                        }

                }
            }
        }
        sendEmail.setOnClickListener{
            sendGmail()
        }
        openMap.setOnClickListener{
            openMap()
        }
        gitHub.setOnClickListener{
            gitHub()
        }
        gitHub.setOnClickListener{
            gitHub()
        }
    }
    fun callRequst(){
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:" + Uri.encode("77777776"))
        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode== REQUEST_DAIL){
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Call Phones permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Call Phones permission denied", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    fun sendGmail(){
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:programer4455@gmail.com")
        }
        if(emailIntent.resolveActivity(packageManager)!=null)
            startActivity(Intent.createChooser(emailIntent, "Send feedback Vai..."))
    }
    fun openMap(){
        val gmmIntentUri = Uri.parse("geo:15.441211,44.125283")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if(mapIntent.resolveActivity(packageManager)!=null)
            startActivity(mapIntent)
    }
    fun gitHub(){
        val gmmIntentUri = Uri.parse("https://github.com/SlamhHajar")
        val gitHubIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        if(gitHubIntent.resolveActivity(packageManager)!=null)
            startActivity(gitHubIntent)
    }

    fun faceBook(){
        val gmmIntentUri = Uri.parse("https://facebook.com")
        val faceIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        if(faceIntent.resolveActivity(packageManager)!=null)
            startActivity(faceIntent)
    }
    fun contactExists(context: Context, number: String?): Boolean {
        return if (number != null) {
            val cr: ContentResolver = context.getContentResolver()
            val curContacts: Cursor? =
                cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
            while (curContacts!!.moveToNext()) {
                val contactNumber: String =
                    curContacts.getString(curContacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                if (number == contactNumber) {
                    return true
                }
            }
            false
        } else {
            false
        }
    }
    fun addContact(contact_name:String,contact_email:String, contact_phone:String){

          val intent = Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI).apply {
            type = ContactsContract.RawContacts.CONTENT_TYPE
            putExtra(
                ContactsContract.Intents.Insert.NAME,
                contact_name
            )
            putExtra(
                ContactsContract.Intents.Insert.EMAIL,
                contact_email
            )
            putExtra(
                ContactsContract.Intents.Insert.EMAIL_TYPE,
                ContactsContract.CommonDataKinds.Email.TYPE_WORK
            )
            putExtra(
                ContactsContract.Intents.Insert.PHONE,
                contact_phone
            )
            putExtra(
                ContactsContract.Intents.Insert.PHONE_TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE_WORK
            )
        }
        startActivity(intent)
    }
    }




//            val intent = Intent(Intent.ACTION_CALL);
//            intent.data = Uri.parse("tel:777777777")
//            startActivity(intent)




