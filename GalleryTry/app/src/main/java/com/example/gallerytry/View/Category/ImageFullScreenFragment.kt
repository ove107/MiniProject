package com.example.gallerytry.View.Category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gallerytry.R
import com.example.gallerytry.ViewModel.CategoryViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.full_screen.view.*

class ImageFullScreenFragment : Fragment() {
    private var viewmodel = CategoryViewModel()
    private var category:String ?= null
    private var timeStamp:String?= null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.full_screen, container, false)
        val bundle = this.arguments
        val mImage = bundle?.getString("ImageURL")
        category = bundle!!.getString("CategoryName")
        timeStamp = bundle.getString("TimeStamp")
        Picasso.get().load(mImage).into(view.expandedImageView)

        view.deleteImage.setOnClickListener {
            deleteImage(mImage.toString())
        }
        return view
    }

    private fun deleteImage(imageUrl: String) {
        viewmodel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        if (viewmodel.deleteImage(imageUrl,category!!,timeStamp!!)){
            Toast.makeText(context,"Deleted successfully",Toast.LENGTH_SHORT).show()
            val manager = activity!!.supportFragmentManager
            manager.popBackStack()
        }

    }
}

