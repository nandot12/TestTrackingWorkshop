package com.nandohusni.testtrackingworkshop

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed(Runnable {

            mAuth = FirebaseAuth.getInstance()

            if (mAuth?.currentUser != null){

                startActivity(Intent(this@SplashActivity,UtamaActivity::class.java))
            }
            else{

                startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
            }
        },3000)




    }
}
