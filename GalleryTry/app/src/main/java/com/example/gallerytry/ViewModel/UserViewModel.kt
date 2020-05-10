package com.example.gallerytry.ViewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.gallerytry.Repository.Repository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentSnapshot

class UserViewModel: ViewModel() {
    private val repository = Repository()
    fun checkUserLogin(): Boolean {
        return repository.checkUserLogin()
    }

    fun login(email:String, password:String): Task<AuthResult> {
        return repository.login(email, password)
    }
    fun signup(Name:String, Email:String, Password:String, uri: Uri?): Boolean {
        return repository.signup(Name, Email, Password, uri)

    }

    fun getUserDetails(): Task<DocumentSnapshot> {
        return repository.getUserDetails()
    }

    fun logout(){
        return repository.logout()
    }


    fun updateUserImage(uri: Uri){
        return repository.updateUserImage(uri)
    }


}