package com.example.gallerytry.View.User

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gallerytry.R
import com.example.gallerytry.View.MainActivity
import com.example.gallerytry.ViewModel.UserViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_profile.*
import kotlinx.android.synthetic.main.user_profile.view.*


class UserProfileFragment : Fragment() {
    private var viewmodel= UserViewModel()
    private lateinit var uri: Uri
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.user_profile,container,false)
        viewmodel = ViewModelProvider(this).get(UserViewModel::class.java)
        userDetails()
        view.user_profile_logout.setOnClickListener {
            viewmodel.logout()
            startActivity(Intent(context,
                MainActivity::class.java))
        }

        view.user_profile_image.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent,"Upload image"),1)

        }

        return view
    }

    private lateinit var imageProgress:ProgressDialog

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && data!= null){
            uri = data.data!!

            imageProgress = ProgressDialog(context)
            imageProgress.setTitle("Updating profile")
            imageProgress.setMessage("Please wait")
            imageProgress.show()

            user_profile_image.setImageURI(uri)
            updateImage()
        }
    }

    private fun updateImage() {
        viewmodel.updateUserImage(uri)
        imageProgress.dismiss()
    }






    private fun userDetails() {
        val loadingDialog = ProgressDisplay(activity!!)
        loadingDialog.startLoadingDialog("Fetching user details")
        viewmodel.getUserDetails()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.e("DATA", "DocumentSnapshot data: ${document.data}")
                    Log.e("DATA", "User name: ${document.getString("username")}")
                    Log.e("DATA", "User email: ${document.getString("emailID")}")
                    user_profile_name.text = document.getString("username")
                    user_profile_email.text = document.getString("emailID")
                    Picasso.get().load(document.getString("imageID")).into(user_profile_image)
                    loadingDialog.dismissDialog()
                    user_progress_image.visibility = View.GONE
                } else {
                    Log.e("NO DOCUMENT", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Failed", "get failed with ", exception)
                Toast.makeText(context,"Unable to fetch user details, please try again later.",Toast.LENGTH_SHORT).show()
            }
    }
}