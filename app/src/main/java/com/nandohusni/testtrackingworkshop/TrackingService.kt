package com.nandohusni.testtrackingworkshop

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.os.IBinder;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.Manifest;
import android.annotation.SuppressLint
import android.location.Location;
import android.app.Notification;
import android.content.pm.PackageManager;
import android.app.PendingIntent;
import android.app.Service;
import android.widget.Toast
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

/**
 * Created by nandoseptianhusni on 21/08/18.
 */
class TrackingService : Service() {


    private var mAuth: FirebaseAuth? = null
    protected var stopReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            //Unregister the BroadcastReceiver when the notification is tapped//

            unregisterReceiver(this)

            //Stop the Service//

            stopSelf()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        mAuth = FirebaseAuth.getInstance()


        requestLocationUpdates()
    }



    private fun requestLocationUpdates() {
        val request = LocationRequest()

        //Specify how often your app should request the device’s location//

        request.setInterval(1000)

        //Get the most accurate location data available//

        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        val client = LocationServices.getFusedLocationProviderClient(this)
        val path = "locations"
        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)

        //If the app currently has access to the location permission...//

        if (permission == PackageManager.PERMISSION_GRANTED) {

            //...then request location updates//

            client.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {

                    //Get a reference to the database, so your app can perform read and write operations//

                    val ref = FirebaseDatabase.getInstance().getReference(path)
                    val location = locationResult.getLastLocation()
                    if (location != null) {


                        //Save the location data to the database//

                        ref.child(mAuth?.currentUser?.uid).setValue(location)
                    }
                }
            }, null)
        }
    }


}