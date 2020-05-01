package com.example.tabianconsulting;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CategoryFragment extends Fragment implements addCategoryDialog.addCategoryInterface,CategoryAdapter.MyClickListener {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    CategoryAdapter.MyClickListener listener;
    CategoryFragmentInterface listen;
    GalleryViewModel galleryViewModel;
    Category cat;

    @Override
    public void onMyItemclicked(View view, Category cat) {
        this.cat=cat;
        updateCategoryDialog update = new updateCategoryDialog(cat,galleryViewModel);
        update.setTargetFragment(CategoryFragment.this,2);
        update.show(getFragmentManager(),"Update Category");
    }

    @Override
    public void onClickCategory(String name) {

    }



//    @Override
//    public void updateCategory(String name) {
//        cat.setName(name);
//        galleryViewModel.updateCategory(cat);
//        categoryAdapter.notifyDataSetChanged();
//        Toast.makeText(getContext(),"\""+cat.getName()+"\""+" Changed To \""+name+"\" Successfuly",Toast.LENGTH_LONG).show();
//        cat.setName(name);
//    }

    public interface CategoryFragmentInterface{
        void addNewCategory(String name);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listen = (CategoryFragmentInterface)context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.category_recycler_fragment,container,false);
        fab = view.findViewById(R.id.add_category);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategoryDialog addCategoryDialog = new addCategoryDialog();
                addCategoryDialog.setTargetFragment(CategoryFragment.this,1);
                addCategoryDialog.show(getFragmentManager(),"AddCategoryDialog");
                Log.d("Category_Fragment","fab clicked of category fragment");

            }
        });
        listener = (CategoryAdapter.MyClickListener) getContext();
        recyclerView =(RecyclerView) view.findViewById(R.id.category_container);
        categoryAdapter= new CategoryAdapter(listener,getContext());
        recyclerView.setAdapter(categoryAdapter);
//       // GridLayoutManager grid = new GridLayoutManager()
        RecyclerView.LayoutManager layout = new GridLayoutManager(getActivity(),3, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layout);
//        fab = findViewById(R.id.add_category);
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        galleryViewModel.getmAllCategory().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {

                categoryAdapter.setCategories(categories);
                categoryAdapter.notifyDataSetChanged();

            }
        });
        return  view;
    }

    @Override
    public void addNewCategory(String name) {
        galleryViewModel.insert(new Category(name));
        categoryAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(),"\""+name+"\"Added Successfully",Toast.LENGTH_LONG).show();

    }
}
