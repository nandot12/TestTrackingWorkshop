package com.nandohusni.testtrackingworkshop

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login_register.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class LoginRegisterActivity : AppCompatActivity() {

    private var database: DatabaseReference? = null

    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        database = FirebaseDatabase.getInstance().reference.child("user")



        mAuth = FirebaseAuth.getInstance()


        userRegister.setOnClickListener {




            mAuth?.createUserWithEmailAndPassword(userEmail.text.toString(), userPassword.text.toString())?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val user = mAuth?.currentUser

                    saveDatabase(user)

                    startActivity(Intent(this@LoginRegisterActivity,LoginActivity::class.java))

                    userConfirmPassword.visibility = View.GONE
                    userName.visibility = View.GONE

                } else {
                    // If sign in fails, display a message to the user.


                    // updateUI(null)
                }

                // ...
            }

        }


    }

    private fun saveDatabase(people: FirebaseUser?) {

        var users = Users()
        users.name = userName.text.toString()
        users.email = userEmail.text.toString()
        users.password = userPassword.text.toString()
        users.uuid = people?.uid!!



        database?.child(people?.uid)?.setValue(users)


    }
}
