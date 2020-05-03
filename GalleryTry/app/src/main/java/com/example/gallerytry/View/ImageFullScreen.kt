package com.example.gallerytry.View

import android.os.Bundle
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
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.full_screen.view.*

class ImageFullScreen : Fragment() {
    private lateinit var viewmodel: GalleryViewModel
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var category:String
    private lateinit var timeStamp:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.full_screen, container, false)
        val bundle = this.arguments
        val mImage = bundle?.getString("ImageURL")
        category = bundle!!.getString("CategoryName")!!
        timeStamp = bundle.getString("TimeStamp")!!

        Log.i("Fetched", category)
        Log.i("Fetched", timeStamp)
        Picasso.get().load(mImage).into(view.expandedImageView)

        view.deleteImage.setOnClickListener {
            deleteImage()
        }
        return view
    }

    private fun deleteImage() {
        viewmodel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        if (viewmodel.deleteImage(category,timeStamp)){
            Toast.makeText(context,"Deleted successfully", Toast.LENGTH_SHORT).show()
            val manager = activity!!.supportFragmentManager
            manager.popBackStack()
        }

    }
}

