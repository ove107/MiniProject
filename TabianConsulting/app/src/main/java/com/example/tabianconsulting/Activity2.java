package com.example.tabianconsulting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Activity2 extends AppCompatActivity implements CategoryAdapter.MyClickListener,updateCategoryDialog.CategoryDialogData,addCategoryDialog.addCategoryInterface{
RecyclerView recyclerView;
CategoryAdapter categoryAdapter;
GalleryViewModel galleryViewModel;
private Category cat;
//GalleryRepository galleryRepository;
FloatingActionButton fab;
CardView category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        galleryViewModel.getmAllCategory().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {

                categoryAdapter.setCategories(categories);
                categoryAdapter.notifyDataSetChanged();

            }
        });


        recyclerView = findViewById(R.id.category_container);
        categoryAdapter= new CategoryAdapter(this,this);
        recyclerView.setAdapter(categoryAdapter);
//       // GridLayoutManager grid = new GridLayoutManager()
        recyclerView.setLayoutManager(new GridLayoutManager(this,3, GridLayoutManager.VERTICAL,false));
        fab = findViewById(R.id.add_category);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            addCategoryDialog addCategoryDialog = new addCategoryDialog();
            addCategoryDialog.show(getSupportFragmentManager(),"Add Category");
            }
        });
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onMyItemclicked(View view,Category cat) {
        this.cat=cat;
        updateCategoryDialog update = new updateCategoryDialog();
        update.show(getSupportFragmentManager(),"Update Category");
    }

    @Override
    public void updateCategory(String name) {

        Toast.makeText(this,"\""+"+cat.getName()\""+" Changed To \""+name+"\" Successfuly",Toast.LENGTH_LONG).show();
        cat.setName(name);
        galleryViewModel.updateCategory(cat);
        categoryAdapter.notifyDataSetChanged();

    }

    @Override
    public void addNewCategory(String name) {
    galleryViewModel.insert(new Category(name));
    categoryAdapter.notifyDataSetChanged();
    Toast.makeText(this,"\""+name+"\"Added Successfully",Toast.LENGTH_LONG).show();

    }
}
