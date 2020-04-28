package com.example.tabianconsulting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Register extends Fragment {

    private OnTextEntered ontext;
    EditText email,pass,con_pass;

    public interface OnTextEntered {
         void OntextInput(String mail, String pass, String repass);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnTextEntered) {
            ontext = (OnTextEntered) context;
        } else {
            throw new ClassCastException(context.getClass().toString() + "Must implement OnTextEntered");
        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_register, container, false);
        email = view.findViewById(R.id.input_email);
        pass = view.findViewById(R.id.input_password);
        con_pass = view.findViewById(R.id.input_confirm_password);
//        return super.onCreateView(inflater, container, savedInstanceState);

        final Button button =view.findViewById(R.id.btn_register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonclicked(v);
            }
        });
        return view;
    }
    public void buttonclicked(View view){
        ontext.OntextInput(email.getText().toString(),pass.getText().toString(),con_pass.getText().toString());
    }
}
