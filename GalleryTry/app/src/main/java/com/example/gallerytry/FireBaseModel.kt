package com.example.gallerytry
import android.net.Uri
import android.util.Log
import android.widget.Toast
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

    fun login(email: String, password: String): Task<AuthResult> {
        val fAuth = auth.signInWithEmailAndPassword(email, password)
        return fAuth
    }

    fun signup(Name: String, Email: String, Password: String, uri: Uri?): Boolean {
        auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener() { task ->
            if (task.isSuccessful) {


                //  Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());

                //send email verificaiton
                sendVerificationEmail()

                FirebaseAuth.getInstance().signOut()

                currentUID = auth.currentUser!!.uid
                uploadImage(Name, Email, uri)
                //startActivity(Intent(context, GalleryActivity::class.java))
            } else {
                Log.e("Error", "" + task.exception)
            }
        }
        return true
    }

    private fun sendVerificationEmail() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Verification", "Verfification sent")
            } else {
                Log.d("Verification", "Could not send verification mail ")
            }
        }
    }

    private fun uploadImage(Name: String, Email: String, uri: Uri?) {
        var uri1 = uri
        if (uri == null) {
            Toast.makeText(MainActivity(), "Please select image", Toast.LENGTH_SHORT).show()
            uri1 = Uri.parse("android.resource://com.example.gallerytry/" + R.drawable.user1)
            Log.e("URI IS", "$uri1")
        }
        val filename = UUID.randomUUID().toString()
        storageRef = FirebaseStorage.getInstance().getReference("/images/$filename")
        storageRef.putFile(uri1!!).addOnSuccessListener {
           Toast.makeText(MainActivity(), "Image uploaded successfully!", Toast.LENGTH_SHORT)
                .show()
            storageRef.downloadUrl.addOnSuccessListener {
                Log.d("Location", "$it")
                savetodatabase(Name, Email, it.toString())
            }
        }.addOnFailureListener {
            Log.e("Unable to upload", "$it")
        }
    }

    private fun savetodatabase(sName: String, sEmail: String, imageID: String) {
        //val uID = auth.uid.toString()
        Log.e("IMAGE URI", imageID)
        firestore = FirebaseFirestore.getInstance()
        val collection = firestore.collection("UsersDetails").document(currentUID)
        val userModel =
            userDataModel(sName, sEmail, imageID)
        collection.set(userModel).addOnSuccessListener {
            Log.e("FIREBASE MODEL ", "successful")
            //Toast.makeText(MainActivity(), "stored in db", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener() {
            Log.e("Error", "$it")
        }

    }

    fun loadData() : CollectionReference {
        val userID = auth.uid.toString()
        //val source = Source.CACHE
        firestore = FirebaseFirestore.getInstance()
        return firestore.collection("UsersDetails").document(userID).collection("Categories")

    }

    fun addCategory(uri: Uri, categoryName: String):Boolean{
        val filename = UUID.randomUUID().toString()
        firestore = FirebaseFirestore.getInstance()
        storageRef = FirebaseStorage.getInstance().getReference("CategoryImages/$filename")
        storageRef.putFile(uri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener {
                Log.d("Location", "$it")
                uploadCategory(categoryName,it.toString())
            }
        }.addOnFailureListener {
            Log.e("Unable to upload", "$it")
        }
        return true
    }
    private fun uploadCategory(categoryName: String, categoryImageID: String) {
        val userId = auth.uid.toString()

        val categoryInfo = addCategoryModel(
            categoryName,
            categoryImageID
        )

        firestore.collection("UsersDetails").document(userId).collection("Categories").document(categoryName)
            .set(categoryInfo)
            .addOnSuccessListener {
                Log.d("Firebasemodel ","Category uploaded successfully")
            }.addOnFailureListener{
                Log.e("Failed","$it")
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

    fun storeImages(categoryName: String,uri: Uri) {
        val user = auth.uid.toString()
        val filename = UUID.randomUUID().toString()
        storageRef = FirebaseStorage.getInstance().getReference("CategoryImages/$user/$filename")
        storageRef.putFile(uri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener {
                Log.d("Location", "$it")
                //Toast.makeText(context,"Uploaded successfully",Toast.LENGTH_SHORT).show()
                uploadToDatabase(it.toString(),categoryName)
            }
        }.addOnFailureListener{
            Log.e("Unable to upload","$it")
        }
    }

    private fun uploadToDatabase(file: String,categoryName: String) {
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
                Log.d("Firebasemodel","Successfully uploaded.")
                //Toast.makeText(context,"Image added successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Log.e("Failed","$it")
            }

    }

    fun deleteImage(category:String, timestamp: String):Boolean{
        firestore = FirebaseFirestore.getInstance()
        val uId = auth.uid.toString()
        var v = true

        firestore.collection("UsersDetails").document(uId)
            .collection("Categories").document(category)
            .collection("Category images").document(timestamp).delete()
            .addOnCompleteListener{
                //Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_SHORT).show()
                v = true

            }.addOnFailureListener{
                Log.e("error","$it.exception")
                v = false
            }
        return v

    }
    fun getUserDetails():Task<DocumentSnapshot>{
        firestore = FirebaseFirestore.getInstance()
        val documentID = auth.uid.toString()
        Log.e("USERID",documentID)
        val docref = firestore.collection("UsersDetails").document(documentID).get()
        return docref
    }

    fun logout(){
        auth.signOut()
    }

    fun updateUserImage(uri: Uri){
        val filename = UUID.randomUUID().toString()
        storageRef = FirebaseStorage.getInstance().getReference("/images/$filename")
        storageRef.putFile(uri).addOnSuccessListener {
            // Toast.makeText(context, "Image uploaded successfully!", Toast.LENGTH_SHORT).show()
            storageRef.downloadUrl.addOnSuccessListener {
                Log.d("Location", "$it")
                updateDatabase(it.toString())
            }
        }.addOnFailureListener{
            Log.e("Unable to upload","$it")
        }
    }

    private fun updateDatabase(newImage: String) {
        firestore = FirebaseFirestore.getInstance()
        val docID = auth.uid.toString()
        firestore.collection("UsersDetails").document(docID).update("imageID",newImage).addOnSuccessListener {
            Log.i("Firebasemodel","Update successful!")
        }.addOnFailureListener{
            Log.e("Failure","$it")
            //Toast.makeText(context,"Unable to update profile, please try again later.",Toast.LENGTH_SHORT).show()
        }
    }

    fun getTimeline(): StorageReference {
        val userID = auth.uid
        storageRef = FirebaseStorage.getInstance().getReference("CategoryImages/$userID")
        return storageRef

    }



}



