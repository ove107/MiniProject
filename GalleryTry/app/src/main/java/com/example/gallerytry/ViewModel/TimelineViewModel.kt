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
    private var timeLineStatus = MutableLiveData<TimelineProgress>()

    fun getTimelineStatus(): LiveData<TimelineProgress> {
        return timeLineStatus
    }

    fun getTimeline(): LiveData<List<AddToTimeline>> {
        timeLineStatus.value = TimelineProgress.SHOW_PROGRESS
        repository.getTimeline().listAll().addOnSuccessListener {
            if (it != null) {
                timeLineStatus.value = TimelineProgress.HIDE_PROGRESS
                val timeList = mutableListOf<AddToTimeline>()
                for (i in it.items) {
                    i.metadata.addOnSuccessListener {
                        val timelineModel =
                            AddToTimeline(
                                i.downloadUrl,
                                it.creationTimeMillis

                            )

                        timeList.add(timelineModel)

                        tList = timeList.sortedByDescending {
                            it.timestamp as Long
                        }

                        mTimeList.value = tList
                    }
                }
            } else{

                timeLineStatus.value = TimelineProgress.HIDE_PROGRESS
            }
        }
        return mTimeList
    }

    enum class TimelineProgress{
        SHOW_PROGRESS,
        HIDE_PROGRESS
    }
}
