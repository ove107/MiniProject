package com.example.gallerytry

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentSnapshot

class GalleryViewModel: ViewModel() {
    private val repository = Repository()
    private var mCategoryList: MutableLiveData<List<addCategoryModel>> = MutableLiveData()
    private var mImageList: MutableLiveData<List<ImageModel>> = MutableLiveData()
    private var mTimeList: MutableLiveData<List<addToTimeline>> = MutableLiveData()
    private lateinit var tList:List<addToTimeline>
    var imageList: MutableList<ImageModel> = mutableListOf()
    val categoryList = mutableListOf<addCategoryModel>()

    fun login(email: String, password: String): Task<AuthResult> {
        return repository.login(email, password)
    }

    fun signup(Name: String, Email: String, Password: String, uri: Uri?): Boolean {
        return repository.signup(Name, Email, Password, uri)

    }

    fun loadData(): LiveData<List<addCategoryModel>> {
        if (categoryList.size>0){
            categoryList.clear()
        }
        repository.loadData().addSnapshotListener{ snapshot, exception ->
            if (exception!=null){
                return@addSnapshotListener
            }
            if (snapshot!=null){
                for(doc in snapshot.documentChanges){
                    val fetchedCategory =
                        addCategoryModel(
                            doc.document.getString("categoryName")!!,
                            doc.document.getString("categoryImage")!!
                        )
                    categoryList.add(fetchedCategory)
                }
            }
            mCategoryList.value = categoryList

        }
        return mCategoryList
    }

    fun addCategory(uri: Uri, categoryName: String): Boolean {
        return repository.addCategory(uri, categoryName)
    }

    fun loadImages(categoryName: String): LiveData<List<ImageModel>> {
        if (imageList.size > 0) {
            imageList.clear()
        }
        repository.loadImages(categoryName).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }
            if (snapshot != null) {

                for (doc in snapshot.documentChanges) {
//                        val imageModel = doc.document.toObject(ImageModel::class.java)
                    val fetched = ImageModel(
                        doc.document.getString("downloadURL")!!,
                        doc.document.getString("timestamp")!!,
                        doc.document.getString("categoryName")!!
                    )
                    imageList.add(fetched)


                }
                mImageList.value = imageList
                Log.i("List of images", imageList.toString())
                Log.i("Live data list ", mImageList.toString())
            }

        }
        return mImageList
    }

    fun storeImages(categoryName: String, uri: Uri) {
        return repository.storeImages(categoryName, uri)
    }

    fun deleteImage(category: String, timestamp: String): Boolean {
        return repository.deleteImage(category, timestamp)
    }

    fun getUserDetails(): Task<DocumentSnapshot> {
        return repository.getUserDetails()
    }

    fun logout() {
        return repository.logout()
    }


    fun updateUserImage(uri: Uri) {
        return repository.updateUserImage(uri)
    }


    fun getTimeline(): LiveData<List<addToTimeline>> {
        repository.getTimeline().listAll().addOnSuccessListener {
            val timeList = mutableListOf<addToTimeline>()
            for (i in it.items) {
                i.metadata.addOnSuccessListener {
                    val timelineModel =
                        addToTimeline(
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
        }
        return mTimeList
    }

}




