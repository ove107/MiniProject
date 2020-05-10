package com.example.gallerytry.View.Category

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
import com.example.gallerytry.Adapter.CategoryCallBackListener
import com.example.gallerytry.Model.AddCategoryModel
import com.example.gallerytry.R
import com.example.gallerytry.View.User.ProgressDisplay
import com.example.gallerytry.ViewModel.CategoryViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class CategoryFragment: Fragment(),CategoryCallBackListener {
    private lateinit var viewmodel: CategoryViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var firestoreRef: FirebaseFirestore
    private lateinit var cAdapter: CategoryAdapter
    private lateinit var categoryList: ArrayList<AddCategoryModel>
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
        val loadingDialog = ProgressDisplay(activity!!)
        loadingDialog.startLoadingDialog("Loading Images")
        viewmodel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewmodel.loadData().observe(viewLifecycleOwner, Observer {
            cAdapter =
                CategoryAdapter(it, context,this)
            recyclerView.adapter = cAdapter
            loadingDialog.dismissDialog()

        })
        return view
    }

    override fun onClickCategory(categoryName: String) {
        val fragment = AddImageToCategoryFragment()
        val bundle = Bundle()
        fragment.arguments = bundle
        bundle.putString("catName", categoryName)
        val transaction = activity!!.supportFragmentManager!!.beginTransaction()
        transaction.replace(R.id.container,fragment)
                    .addToBackStack(null)
                    .commit()
    }
}

