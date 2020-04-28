package com.example.tabianconsulting;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Login extends Fragment {
    private OnLoginEntered onLogin;
    EditText mEmail,mPass;
    TextView register;
    public interface OnLoginEntered{
        public void onLoginPressed(String mail,String pass);
        public void onClickRegister(int id);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Register.OnTextEntered) {
            onLogin = (Login.OnLoginEntered) context;
        } else {
            throw new ClassCastException(context.getClass().toString() + "Must implement OnLoginEntered");
        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        mEmail = view.findViewById(R.id.email);
        mPass = view.findViewById(R.id.password);
         register = (TextView)view.findViewById(R.id.link_register);
        final Button button =view.findViewById(R.id.email_sign_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonclicked(v);
                //registerSelected();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSelected(v);
            }
        });
        return view;
}

    public void buttonclicked(View view){
        onLogin.onLoginPressed(mEmail.getText().toString(),mPass.getText().toString());
    }
    public void registerSelected(View v){
        onLogin.onClickRegister(R.layout.activity_register);
    }
}