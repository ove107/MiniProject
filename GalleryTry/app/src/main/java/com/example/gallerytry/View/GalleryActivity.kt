package com.example.gallerytry.View

import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.gallerytry.R
import com.example.gallerytry.View.Category.AddCategoryFragmnet
import com.example.gallerytry.View.Category.CategoryFragment
import com.example.gallerytry.View.Timeline.TimelineFragment
import com.example.gallerytry.View.User.UserProfileFragment
import kotlinx.android.synthetic.main.activity_gallery.*


class GalleryActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        //val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        //val toolbar:Toolbar = (Toolbar)findViewById(R.id.my_toolbar)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        val categoryFragment =
            CategoryFragment()
        supportFragmentManager.
        beginTransaction().
        replace(R.id.container,categoryFragment)
            .commit()

        navigationView.setOnNavigationItemSelectedListener {
                item ->
            when(item.itemId){

                R.id.profile_item -> {
                    actionBar?.title="Profile"
                    val profileFragment =
                        UserProfileFragment()
                    supportFragmentManager.
                    beginTransaction().
                    replace(R.id.container,profileFragment)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.gallery_item -> {
                    actionBar?.title="Category"
                    val categoryFragment =
                        CategoryFragment()
                    supportFragmentManager.
                    beginTransaction().
                    replace(R.id.container,categoryFragment)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.add_item -> {
                    actionBar?.title="Add Category"
                    val addFragment =
                        AddCategoryFragmnet()
                    supportFragmentManager.
                    beginTransaction().
                    replace(R.id.container,addFragment)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.timeline_item -> {
                    actionBar?.title="Timeline"
                    val timelineFragment =
                        TimelineFragment()
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





}
