package com.example.gallerytry.ViewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallerytry.Model.AddCategoryModel
import com.example.gallerytry.Model.ImageModel
import com.example.gallerytry.Repository.Repository
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot

class CategoryViewModel:ViewModel() {

    val categoryList = mutableListOf<AddCategoryModel>()
    private val repository = Repository()
    private var mImageList : MutableLiveData<List<ImageModel>> = MutableLiveData()
    private var mCategoryList: MutableLiveData<List<AddCategoryModel>> = MutableLiveData()


    fun loadData(): LiveData<List<AddCategoryModel>> {
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
                        AddCategoryModel(
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

    fun addCategory(uri: Uri, categoryName: String):Boolean{
        return repository.addCategory(uri,categoryName)
    }

    fun loadImages(categoryName: String):LiveData<List<ImageModel>>{

        repository.loadImages(categoryName)
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    //Log.w(TAG, "Listen Fail", e)
                    mImageList.value = null
                    return@EventListener
                }
                val savedImagesList: MutableList<ImageModel> = mutableListOf()
                for (doc in value!!) {
                    val imageItem =
                        ImageModel(
                            doc.getString("downloadURL")!!,
                            doc.getString("timestamp")!!,
                            doc.getString("categoryName")!!
                        )
                    savedImagesList.add(imageItem)
                }
                mImageList.value = savedImagesList
                //Log.d(TAG, savedImagesList.toString())
            })
        return mImageList
    }

    fun storeImages(categoryName: String,uri: Uri){
        return repository.storeImages(categoryName, uri)
    }

    fun deleteImage(imageUrl: String,category:String,timestamp:String):Boolean{
        return repository.deleteImage(imageUrl, category, timestamp)
    }


}