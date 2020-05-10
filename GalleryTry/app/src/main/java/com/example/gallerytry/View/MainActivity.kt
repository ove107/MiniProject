package com.example.gallerytry.View

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.gallerytry.R
import com.example.gallerytry.View.Signup.SignupFragment
import com.example.gallerytry.View.User.ProgressDisplay
import com.example.gallerytry.ViewModel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var email:String ?= null
    private var password:String?=null
    private lateinit var viewmodel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewmodel = ViewModelProvider(this).get(UserViewModel::class.java)
        if (viewmodel.checkUserLogin()){
            startActivity(Intent(this,
                GalleryActivity::class.java))
        }
      login_signup.setOnClickListener {
            signup()
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
        val loadingDialog = ProgressDisplay(this)
        loadingDialog.startLoadingDialog("Signing in, please wait.")
        viewmodel.login(email!!,password!!).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                loadingDialog.dismissDialog()
                Toast.makeText(this,"Signed in successfully",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,
                    GalleryActivity::class.java))
            } else {
                Toast.makeText(this,"Email id and password do not match",Toast.LENGTH_SHORT).show()
                Log.e("ERROR", "signInWithEmail:failure", task.exception)

            }
        }



    }

    fun signup( ) {
        val manager = supportFragmentManager
        val transact = manager.beginTransaction()
        val sFragment = SignupFragment()
        transact.replace(R.id.main_activity_container,sFragment)
                .addToBackStack(null)
                .commit()
    }

}
