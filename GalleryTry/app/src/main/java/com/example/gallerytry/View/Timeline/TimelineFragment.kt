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
    private var viewModel = TimelineViewModel()
    private var loadingDialog:ProgressDisplay ?= null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.timeline,container,false)
        val recyclerView = view.findViewById(R.id.timeline_recycler) as RecyclerView
        viewModel = ViewModelProvider(this).get(TimelineViewModel::class.java)
        setObservers()
        loadingDialog = ProgressDisplay(activity!!)
        recyclerView.layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        viewModel.getTimeline().observe(viewLifecycleOwner, Observer {
            val tAdapter =
                AdapterTimeline(
                    it,
                    context
                )
            recyclerView.adapter = tAdapter
        })

        return view
    }

    private fun setObservers() {
        viewModel.getTimelineStatus().observe(viewLifecycleOwner, Observer {
            when(it){
                TimelineViewModel.TimelineProgress.SHOW_PROGRESS -> showProgress()
                TimelineViewModel.TimelineProgress.HIDE_PROGRESS -> hideProgress()
            }
        })
    }

    private fun showProgress() {
        loadingDialog!!.startLoadingDialog("Loading your timeline")
    }

    private fun hideProgress() {
        loadingDialog!!.dismissDialog()
    }
}