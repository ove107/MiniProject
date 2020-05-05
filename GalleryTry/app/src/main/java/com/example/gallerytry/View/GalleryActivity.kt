package com.example.gallerytry.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.gallerytry.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_gallery.*


class GalleryActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)


        val categoryFragment = Category()
        supportFragmentManager.
        beginTransaction().
        replace(R.id.container,categoryFragment)
            .commit()

        navigationView.setOnNavigationItemSelectedListener {
                item ->
            when(item.itemId){

                R.id.profile_item -> {
                    val profileFragment =
                        userProfile()
                    supportFragmentManager.
                    beginTransaction().
                    replace(R.id.container,profileFragment)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.gallery_item -> {
                  val categoryFragment =
                      Category()
                    supportFragmentManager.
                    beginTransaction().
                    replace(R.id.container,categoryFragment)
                        .addToBackStack(null)
                        .commit()
               }
                R.id.add_item -> {
                    val addFragment =
                        addCategory()
                    supportFragmentManager.
                    beginTransaction().
                    replace(R.id.container,addFragment)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.timeline_item -> {
                    val timelineFragment =
                        Timeline()
                    supportFragmentManager.
                    beginTransaction().
                    replace(R.id.container,timelineFragment)
                        .addToBackStack(null)
                        .commit()
                }
           }
            true
        }
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser == null){
           logout()
        }
    }
    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>0)
        {
            for(i in 1..supportFragmentManager.backStackEntryCount)
            supportFragmentManager.popBackStackImmediate()
        }
        else {
            val dialog = DialogLogout()
            supportFragmentManager.beginTransaction().add(dialog, "dialog fragment").commit()
        }
    }

    fun logout() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        startActivity(Intent(this, MainActivity::class.java))
    }


}
