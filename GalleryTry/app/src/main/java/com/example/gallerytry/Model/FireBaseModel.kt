package com.example.gallerytry.Model

import android.net.Uri
import android.util.Log
import com.example.gallerytry.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class FireBaseModel {
    private val auth = FirebaseAuth.getInstance()
    private lateinit var storageRef: StorageReference
    private lateinit var firestore: FirebaseFirestore
    private lateinit var currentUID: String

    fun checkUserLogin(): Boolean {
        val currentUser = auth.currentUser
        var loggedIn = false
        if (currentUser != null) {
            loggedIn = true
        }
        Log.i("checkUserLogin", "Executed")
        return loggedIn
    }

    fun login(email: String, password: String): Task<AuthResult> {
        val fAuth = auth.signInWithEmailAndPassword(email, password)
        return fAuth
    }

    fun signup(Name: String, Email: String, Password: String, uri: Uri?): Boolean {
        auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                Log.i("Signup", "Executed")
                currentUID = auth.currentUser!!.uid
                uploadImage(Name, Email, uri)
            } else {
                Log.e("Error", "" + task.exception)
            }
        }
        return true
    }

    private fun uploadImage(Name: String, Email: String, uri: Uri?) {
        var uri1 = uri
        if (uri == null) {
            uri1 = Uri.parse("android.resource://com.example.gallerytry/" + R.drawable.user1)
            Log.e("URI IS", "$uri1")
        }
        val filename = UUID.randomUUID().toString()
        storageRef = FirebaseStorage.getInstance().getReference("/images/$filename")
        storageRef.putFile(uri1!!).addOnSuccessListener {
            Log.i("Profile Image Upload", "Executed")
            storageRef.downloadUrl.addOnSuccessListener {
                Log.d("Location", "$it")
                savetodatabase(Name, Email, it.toString())
            }
        }.addOnFailureListener {
            Log.e("Unable to upload", "$it")
        }
    }

    private fun savetodatabase(sName: String, sEmail: String, imageID: String) {
        val uID = auth.uid.toString()
        Log.e("IMAGE URI", imageID)
        firestore = FirebaseFirestore.getInstance()
        val collection = firestore.collection("UsersDetails").document(uID)
        val userModel = UserDataModel(sName, sEmail, imageID)
        collection.set(userModel).addOnSuccessListener {
            Log.i("Save to database  ", "Executed")
        }.addOnFailureListener() {
            Log.e("Error", "$it")
        }

    }

    fun loadData(): CollectionReference {
        val userID = auth.currentUser!!.uid.toString()
        firestore = FirebaseFirestore.getInstance()
        return firestore.collection("UsersDetails").document(userID).collection("Categories")

    }

    fun addCategory(uri: Uri, categoryName: String): Boolean {
        val filename = UUID.randomUUID().toString()
        firestore = FirebaseFirestore.getInstance()
        storageRef = FirebaseStorage.getInstance().getReference("CategoryImages/$filename")
        storageRef.putFile(uri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener {
                Log.d("Location", "$it")
                uploadCategory(categoryName, it.toString())
            }
        }.addOnFailureListener {
            Log.e("Unable to upload", "$it")
        }
        return true
    }

    private fun uploadCategory(categoryName: String, categoryImageID: String) {
        val userId = auth.uid.toString()

        val categoryInfo =
            AddCategoryModel(
                categoryName,
                categoryImageID
            )

        firestore.collection("UsersDetails").document(userId).collection("Categories")
            .document(categoryName)
            .set(categoryInfo)
            .addOnSuccessListener {
                Log.d("Firebasemodel ", "Category uploaded successfully")
            }.addOnFailureListener {
                Log.e("Failed", "$it")
            }
    }

    fun loadImages(categoryName: String): CollectionReference {
        val uId = auth.uid.toString()
        firestore = FirebaseFirestore.getInstance()
        val reference = firestore.collection("UsersDetails").document(uId)
            .collection("Categories").document(categoryName)
            .collection("Category images")
        return reference
    }

    fun storeImages(categoryName: String, uri: Uri) {
        val user = auth.uid.toString()
        val filename = UUID.randomUUID().toString()
        storageRef = FirebaseStorage.getInstance().getReference("CategoryImages/$user/$filename")
        storageRef.putFile(uri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener {
                Log.d("Location", "$it")
                //Toast.makeText(context,"Uploaded successfully",Toast.LENGTH_SHORT).show()
                uploadToDatabase(it.toString(), categoryName)
            }
        }.addOnFailureListener {
            Log.e("Unable to upload", "$it")
        }
    }

    private fun uploadToDatabase(file: String, categoryName: String) {
        val userId = auth.uid.toString()
        val calendar = Calendar.getInstance()
        val timestamp = calendar.timeInMillis.toString()

        val imageInfo = ImageModel(
            file,
            timestamp,
            categoryName
        )


        firestore.collection("UsersDetails").document(userId)
            .collection("Categories").document(categoryName)
            .collection("Category images").document(timestamp)
            .set(imageInfo)
            .addOnSuccessListener {
                Log.d("Firebasemodel", "Successfully uploaded.")
                //Toast.makeText(context,"Image added successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Log.e("Failed", "$it")
            }

    }

    fun deleteImage(imageUrl: String, category: String, timestamp: String): Boolean {
        firestore = FirebaseFirestore.getInstance()
        val uId = auth.uid.toString()
        var v = true


        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl)
        storageReference.delete().addOnSuccessListener {
            Log.d("Deletion", "delete success")
        }
            .addOnFailureListener {
                Log.d("FAILED", "${it.message}")
            }

        firestore.collection("UsersDetails").document(uId)
            .collection("Categories").document(category)
            .collection("Category images").document(timestamp).delete()
            .addOnCompleteListener {
                Log.d("Firebasemodel", "Deleted from db")
                v = true

            }.addOnFailureListener {
                Log.e("error", "$it.exception")
                v = false
            }


        return v

    }


    fun getUserDetails(): Task<DocumentSnapshot> {
        Log.i("Getting user details", "Executed ")
        firestore = FirebaseFirestore.getInstance()
        val documentID = auth.uid.toString()
        Log.e("USERID", documentID)
        val docref = firestore.collection("UsersDetails").document(documentID).get()
        return docref
    }

    fun logout() {
        auth.signOut()
    }

    fun updateUserImage(uri: Uri) {
        val filename = UUID.randomUUID().toString()
        storageRef = FirebaseStorage.getInstance().getReference("/images/$filename")
        storageRef.putFile(uri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener {
                Log.d("Location", "$it")
                updateDatabase(it.toString())
            }
        }.addOnFailureListener {
            Log.e("Unable to upload", "$it")
        }
    }

    private fun updateDatabase(newImage: String) {
        firestore = FirebaseFirestore.getInstance()
        val docID = auth.uid.toString()
        firestore.collection("UsersDetails").document(docID).update("imageID", newImage)
            .addOnSuccessListener {
                Log.i("Firebasemodel", "Update successful!")
            }.addOnFailureListener {
            Log.e("Failure", "$it")
        }
    }

    fun getTimeline(): StorageReference {
        val userID = auth.uid
        storageRef = FirebaseStorage.getInstance().getReference("CategoryImages/$userID")
        return storageRef

    }


}

