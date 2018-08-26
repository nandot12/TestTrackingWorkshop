package com.nandohusni.testtrackingworkshop

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()


        linkSignup.setOnClickListener {

            startActivity(Intent(this@LoginActivity, LoginRegisterActivity::class.java))
        }


        userSignUp.setOnClickListener {


            mAuth?.signInWithEmailAndPassword(userEmail.text.toString(), userPassword.text.toString())?.addOnCompleteListener { tast ->

                if (tast.isSuccessful) {


                    startActivity(Intent(this@LoginActivity, UtamaActivity::class.java))


                } else {
                    Toast.makeText(this@LoginActivity, "failed signIn", Toast.LENGTH_SHORT).show()

                }

            }
        }
    }
}
