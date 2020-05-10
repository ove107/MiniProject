package com.example.gallerytry.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallerytry.Model.AddToTimeline
import com.example.gallerytry.Repository.Repository

class TimelineViewModel:ViewModel() {
    private val repository = Repository()
    private var mTimeList: MutableLiveData<List<AddToTimeline>> = MutableLiveData()
    private lateinit var tList:List<AddToTimeline>
    fun getTimeline(): LiveData<List<AddToTimeline>> {
        repository.getTimeline().listAll().addOnSuccessListener {
            val timeList = mutableListOf<AddToTimeline>()
            for (i in it.items) {
                i.metadata.addOnSuccessListener {
                    val AddToTimeline =
                        AddToTimeline(
                            i.downloadUrl,
                            it.creationTimeMillis

                        )

                    timeList.add(AddToTimeline)

                    tList = timeList.sortedByDescending {
                        it.timestamp as Long
                    }

                    mTimeList.value = tList
                }
            }
        }
        return mTimeList
    }

}