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
                        .commit()
                }
                R.id.gallery_item -> {
                  val categoryFragment =
                      Category()
                    supportFragmentManager.
                    beginTransaction().
                    replace(R.id.container,categoryFragment)
                        .commit()
               }
                R.id.add_item -> {
                    val addFragment =
                        addCategory()
                    supportFragmentManager.
                    beginTransaction().
                    replace(R.id.container,addFragment)
                        .commit()
                }
                R.id.timeline_item -> {
                    val timelineFragment =
                        Timeline()
                    supportFragmentManager.
                    beginTransaction().
                    replace(R.id.container,timelineFragment)
                        .commit()
                }
           }
            true
        }
    }

    fun logout(view: View) {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        startActivity(Intent(this, MainActivity::class.java))
    }


}
