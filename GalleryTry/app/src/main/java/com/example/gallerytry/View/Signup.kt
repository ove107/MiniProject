package com.example.gallerytry.View

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gallerytry.R
import com.example.gallerytry.ViewModel.GalleryViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.signup.*
import kotlinx.android.synthetic.main.signup.view.*

class Signup : Fragment(){
    private lateinit var viewmodel: GalleryViewModel
    private lateinit var sName:String
    private lateinit var sEmail:String
    private lateinit var sPass:String
    private lateinit var auth: FirebaseAuth
    private var uri: Uri?= null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.signup,container,false)
        viewmodel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        auth = FirebaseAuth.getInstance()
        view.signup_button.setOnClickListener {
            if (!sValidate()){
               sValidate()
            }
            else {
                if (viewmodel.signup(sName,sEmail,sPass,uri)) {
                    val intent = Intent(context,
                        MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(context,"Sign up successful", Toast.LENGTH_SHORT).show()
                }

            }
        }

        view.sign_up_image.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent,"Pick image"),0)
        }


        return view

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && data != null){
            uri = data.data
            Log.e("URI",uri.toString())
            sign_up_image.setImageURI(uri)
            Log.d("Image display","working")
        }
    }

    private fun sValidate():Boolean {
        sName = signup_name.text.toString()
        sEmail = signup_email.text.toString()
        sPass = signup_password.text.toString()
        var v = true
        if (TextUtils.isEmpty(sName)) {
            signup_name.error = "Required Field"
            v = false
        }
        if (TextUtils.isEmpty(sEmail)) {
            signup_email.error = "Required Field"
            v = false
        }
        if (TextUtils.isEmpty(sPass)) {
            signup_password.error = "Required Field"
            v = false
        }
        if (!sEmail.contains("@") || !sEmail.contains(".com")){
            signup_email.error = "Enter a valid email address"
            v = false
        }
        if (sPass.length < 6) {
            signup_password.error = "Password is too short"
            v = false
        }
        if (sName.length < 4) {
            signup_name.error = "Name is too short"
            v = false
        }
        return v
    }

}

