package com.example.gallerytry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageReference


class Timeline: Fragment() {
    private lateinit var viewmodel: GalleryViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private lateinit var timeList: ArrayList<addToTimeline>
    private lateinit var recyclerView: RecyclerView
    private lateinit var tAdapter: AdapterTimeline
    //private lateinit var uri: Uri
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.timeline,container,false)
        timeList = arrayListOf()
        recyclerView = view.findViewById(R.id.timeline_recycler) as RecyclerView

        recyclerView.layoutManager = GridLayoutManager(context,3)
        auth = FirebaseAuth.getInstance()
        val userID = auth.uid
        viewmodel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        viewmodel.getTimeline().observe(viewLifecycleOwner, Observer {
            tAdapter =
                AdapterTimeline(it, context)
            recyclerView.adapter = tAdapter
        })

        return view
    }
}