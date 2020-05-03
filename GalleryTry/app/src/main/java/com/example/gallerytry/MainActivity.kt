package com.example.gallerytry

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewmodel: GalleryViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var email:String
    private lateinit var password:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            startActivity(
                Intent(this,
                    GalleryActivity::class.java)
            )
        }
    }


    private fun validate():Boolean{
        var valid = true
        email = login_email.text.toString()
        password = login_password.text.toString()

        if (TextUtils.isEmpty(email)){
            login_email.error = "Required field"
            valid = false
        }
        else if (TextUtils.isEmpty(password)){
            login_password.error = "Please enter password"
            valid = false
        }
        return valid
    }

    fun login(view: View) {
        if (!validate()){
            return
        }
        viewmodel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        viewmodel.login(email,password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this,"Signed in successfully", Toast.LENGTH_SHORT).show()
                startActivity(
                    Intent(this,
                        GalleryActivity::class.java)
                )
            } else {
                Toast.makeText(this,"Email id and password do not match", Toast.LENGTH_SHORT).show()
                Log.e("ERROR", "signInWithEmail:failure", task.exception)

            }
        }



    }

    fun signup(view: View) {
        val manager = supportFragmentManager
        val transact = manager.beginTransaction()
        val sFragment = Signup()
        transact.replace(R.id.main_activity_container,sFragment)
        transact.commit()
    }

}
