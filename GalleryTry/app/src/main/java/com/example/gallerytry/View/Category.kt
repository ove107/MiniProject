package com.example.gallerytry.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallerytry.Adapter.CategoryAdapter
import com.example.gallerytry.Model.addCategoryModel
import com.example.gallerytry.R
import com.example.gallerytry.ViewModel.GalleryViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Category: Fragment() {
    private lateinit var viewmodel: GalleryViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var firestoreRef: FirebaseFirestore
    private lateinit var cAdapter: CategoryAdapter
    private lateinit var categoryList: ArrayList<addCategoryModel>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.category, container, false)
        recyclerView = view.findViewById(R.id.recycler) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(context,3)
        auth = FirebaseAuth.getInstance()
        firestoreRef = FirebaseFirestore.getInstance()



        categoryList = arrayListOf()
        viewmodel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        viewmodel.loadData().observe(viewLifecycleOwner, Observer {
            cAdapter =
                CategoryAdapter(it, context)
            recyclerView.adapter = cAdapter

        })


        return view
    }


}

