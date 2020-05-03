package com.example.gallerytry.Repository



import android.net.Uri
import com.example.gallerytry.Model.FireBaseModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.StorageReference

class Repository (){
    private var firebaseModel = FireBaseModel()

    fun login(email:String,password:String) =firebaseModel.login(email, password)
    fun signup(Name:String, Email:String,Password:String, uri: Uri?):Boolean = firebaseModel.signup(Name, Email,Password,uri)
    fun loadData():CollectionReference = firebaseModel.loadData()
    fun addCategory(uri: Uri,categoryName: String):Boolean = firebaseModel.addCategory(uri,categoryName)
    fun loadImages(categoryName: String) = firebaseModel.loadImages(categoryName)
    fun storeImages(categoryName: String,uri: Uri) = firebaseModel.storeImages(categoryName,uri)
    fun deleteImage(category:String,timestamp:String):Boolean = firebaseModel.deleteImage(category, timestamp)
    fun getUserDetails() = firebaseModel.getUserDetails()
    fun updateUserImage(uri: Uri) = firebaseModel.updateUserImage(uri)
    fun getTimeline() :StorageReference = firebaseModel.getTimeline()
    fun logout() = firebaseModel.logout()
}