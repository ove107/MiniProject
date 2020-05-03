package com.example.gallerytry

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.gallerytry.GalleryViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.category_image.view.*
import kotlinx.android.synthetic.main.image.view.*
import java.io.ByteArrayOutputStream
import kotlin.collections.ArrayList

class addImageToCategory  : Fragment() {
    private lateinit var viewmodel: GalleryViewModel
    private lateinit var uri:Uri
    private lateinit var storageReference: StorageReference
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var categoryName:String
    private lateinit var imagesAdapter: AdapterImage
    private lateinit var imageList:ArrayList<String>
    private lateinit var iList : ArrayList<ImageModel>
    private lateinit var recycler : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.category_image,container,false)
        val bundle = this.arguments
        categoryName = bundle?.getString("catName").toString()
        view.textView.text = categoryName

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        recycler = view.findViewById(R.id.recyclerImages) as RecyclerView
        recycler.layoutManager = GridLayoutManager(this.context,3)
        imageList = arrayListOf()
        iList = arrayListOf()

        loadImages()

        view.chooseGallery.setOnClickListener{
            val galleryIntent = Intent()
            galleryIntent.type = "image/*"
            galleryIntent.action = Intent.ACTION_PICK
            startActivityForResult(galleryIntent,3)
        }

        view.chooseCamera.setOnClickListener{
            @RequiresApi(Build.VERSION_CODES.M)
            if (context?.checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.CAMERA
                    ),2
                )
            }
            else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 4)
            }
        }


        return view
    }

    private fun loadImages() {

        viewmodel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        viewmodel.loadImages(categoryName).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            imagesAdapter =
               AdapterImage(context)
            imagesAdapter.listChange(it)
            //imagesAdapter.notifyDataSetChanged()
            recycler.adapter = imagesAdapter
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data!=null && requestCode == 3){
            uri = data.data!!
            Log.e("URI GALLERY: ","$uri")
            //Toast.makeText(context,"Gallery",Toast.LENGTH_SHORT).show()
            storeImage()
        }
        if (data!= null && requestCode == 4 ){
            val photo: Bitmap = data.extras?.get("data") as Bitmap
            uri = getImageUri(context,photo)
            Log.e("URI CAMERA: ","$uri")
            //Toast.makeText(context,"camera",Toast.LENGTH_SHORT).show()
            storeImage()
        }
    }

    private fun getImageUri(context: Context?, photo: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.JPEG,100,bytes)
        val path: String = MediaStore.Images.Media.insertImage(
            context?.getContentResolver(),
            photo,
            "Title",
            null
        )
        val image = Uri.parse(path)
        return image


    }

    private fun storeImage() {
        viewmodel.storeImages(categoryName,uri)
        Toast.makeText(context,"Successfully uploaded.",Toast.LENGTH_SHORT).show()
    }
}