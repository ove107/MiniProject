package com.example.tabianconsulting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class updateCategoryDialog extends AppCompatDialogFragment {
private EditText cat;
private CategoryDialogData listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CategoryDialogData) context;
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
        builder.setView(view)
                .setTitle("Edit Category Name")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    listener.updateCategory(cat.getText().toString());
            }
        });
        cat = view.findViewById(R.id.updated_cat);
       return builder.create();
    }
    public interface CategoryDialogData{
        void updateCategory(String name);
    }
}
