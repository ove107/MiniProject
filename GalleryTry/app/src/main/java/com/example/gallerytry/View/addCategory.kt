package com.example.gallerytry.View

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gallerytry.R
import com.example.gallerytry.ViewModel.GalleryViewModel
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.add_category.*
import kotlinx.android.synthetic.main.add_category.view.*


class addCategory : Fragment(){
    private lateinit var viewmodel: GalleryViewModel
    private var uri: Uri?= null
    private lateinit var progressDialog : ProgressDialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.add_category,container,false)

        view.categoryImage.setOnClickListener{
            val categoryImg = Intent()
            categoryImg.type = "image/*"
            categoryImg.action = Intent.ACTION_PICK
            startActivityForResult(categoryImg,2)
        }

        view.addCategoryBtn.setOnClickListener {
            if (uri == null){
                Toast.makeText(context,"Please select an image for the category", Toast.LENGTH_SHORT).show()
            }
            else if (TextUtils.isEmpty(categoryEdit.text)){
                Toast.makeText(context,"Please enter name of the category", Toast.LENGTH_SHORT).show()
            }
            else {
                progressDialog = ProgressDialog(context)
                progressDialog.setTitle("Adding category")
                progressDialog.setMessage("Please wait")
                progressDialog.show()
                val categoryName = categoryEdit.text.toString()
                viewmodel = ViewModelProvider(this).get(GalleryViewModel::class.java)
                if (viewmodel.addCategory(uri!!,categoryName)){
                    progressDialog.dismiss()
                    activity!!.navigationView.menu.getItem(0).setChecked(true)
                    val category = Category()
                    val transaction = activity!!.supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container,category)
                    transaction.commit()
                    Toast.makeText(context,"Category added successfully", Toast.LENGTH_SHORT).show()
                }



            }
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data!= null && requestCode == 2){
            uri = data.data!!
            categoryImage.setImageURI(uri)
        }
    }
}