package com.example.tabianconsulting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class updateCategoryDialog extends AppCompatDialogFragment {
private EditText cat;
private CategoryDialogData listener;
Category category;
GalleryViewModel galleryViewModel;

    public updateCategoryDialog(Category category,GalleryViewModel galleryViewModel) {
        this.category=category;
        this.galleryViewModel=galleryViewModel;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CategoryDialogData) getTargetFragment();
        }catch (ClassCastException e){
        throw new ClassCastException(context.toString()+" Must Implement CategoryDialogData listener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_category_dialog_layout,null);
        cat = view.findViewById(R.id.updated_cat);
        builder.setView(view)
                .setTitle("Edit Category Name")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().dismiss();
                    }
                }).setPositiveButton("update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("UPDATE DIALOG TEXT",cat.getText().toString());

                category.setName(cat.getText().toString());
                galleryViewModel.updateCategory(category);
            }
        });

       return builder.create();
    }
    public interface CategoryDialogData{
        void updateCategory(String name);
    }
}
