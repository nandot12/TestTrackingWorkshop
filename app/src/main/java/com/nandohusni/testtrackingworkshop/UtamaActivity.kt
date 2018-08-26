package com.nandohusni.testtrackingworkshop

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_utama.*
import com.google.firebase.database.DataSnapshot



class UtamaActivity : AppCompatActivity() {


    private var database: DatabaseReference? = null

    var datas = arrayListOf<Users>()


    private val PERMISSIONS_REQUEST = 100
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {


        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utama)

        database = FirebaseDatabase.getInstance().reference.child("user")

        database?.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {

                datas.clear()





                for (postSnapshot in p0?.getChildren()!!) {

                    var user = Users()
                    //getting artist
                    val artist = postSnapshot.getValue(Users::class.java)

                   var name = artist?.name
                    var email = artist?.email
                    var id = artist?.uuid
                    user.email = email!!
                    user.name = name!!
                    user.uuid = id!!

                    datas.add(user)

                    Toast.makeText(this@UtamaActivity,datas.toString(),Toast.LENGTH_SHORT).show()

                    idrecyclerview.adapter = ItemRecyclerview(datas)
                    idrecyclerview.layoutManager = LinearLayoutManager(this@UtamaActivity)
                    
                    //adding artist to the list

                }





            }
        })





        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        setUp()



//Check whether this app has access to the location permission//

        val permission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)

//If the location permission has been granted, then start the TrackerService//

        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService()
        } else {

            //If the app doesn’t currently have access to the user’s location, then request access//

            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST)
        }
    }

    private fun setUp() {


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        //If the permission has been granted...//

        if (requestCode == PERMISSIONS_REQUEST && grantResults.size == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            //...then start the GPS tracking service//

            startTrackerService()
        } else {

            //If the user denies the permission request, then display a toast with some more information//

            Toast.makeText(this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startTrackerService() {
        startService(Intent(this, TrackingService::class.java))

        //Notify the user that tracking has been enabled//

        //Toast.makeText(this, "GPS tracking enabled", Toast.LENGTH_SHORT).show()

        //Close MainActivity//

        // finish()
    }
}
