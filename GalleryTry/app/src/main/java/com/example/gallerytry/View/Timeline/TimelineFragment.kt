package com.example.gallerytry.View.Timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallerytry.Adapter.AdapterTimeline
import com.example.gallerytry.R
import com.example.gallerytry.View.User.ProgressDisplay
import com.example.gallerytry.ViewModel.TimelineViewModel


class TimelineFragment: Fragment() {
    private lateinit var viewmodel: TimelineViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.timeline,container,false)
        val recyclerView = view.findViewById(R.id.timeline_recycler) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(context,3)
        val loadingDialog = ProgressDisplay(activity!!)
        loadingDialog.startLoadingDialog("Loading Images")

        viewmodel = ViewModelProvider(this).get(TimelineViewModel::class.java)
        viewmodel.getTimeline().observe(viewLifecycleOwner, Observer {
            val tAdapter =
                AdapterTimeline(it, context)
            recyclerView.adapter = tAdapter
            loadingDialog.dismissDialog()
        })

        return view
    }
}