package com.example.tabianconsulting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class addCategoryDialog extends AppCompatDialogFragment {
    private EditText cat;
    private addCategoryInterface listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (addCategoryInterface)getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_category_dialog,null);
        builder.setView(view)
                .setTitle("Add Category")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().dismiss();
                    }
                }).setPositiveButton("add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               listener.addNewCategory(cat.getText().toString());
            }
        });
        cat = view.findViewById(R.id.new_category);
        return builder.create();
    }
    public interface addCategoryInterface{
       void addNewCategory(String name);
    }
}
