package com.example.exhaustwear;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exhaustwear.Model.Users;

import com.example.exhaustwear.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class LoginFragment extends Fragment {
    ActivityMainBinding binding2;
    View view;
    EditText logPhone, logPassword;
    TextView forgotPass, registrationText;
    Button logButton;
    ProgressDialog loadingBar;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_login, container, false);

        logPhone = view.findViewById(R.id.editTextPhone);
        logPassword = view.findViewById(R.id.editTextPassword);
        loadingBar = new ProgressDialog(getActivity());
        logButton = view.findViewById(R.id.loginButton);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });


        forgotPass = view.findViewById(R.id.forgotPassword);
        //


        // click the text Регистрация
        registrationText = view.findViewById(R.id.sign_up_text);
        registrationText.setOnClickListener(new View.OnClickListener() {
            //transition to the fragment_login
            //using getActivity()!!!
            @Override
            public void onClick(View view) {
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_layout, AccountFragment.class, null).commit();
            }
        });


        return view;

    }

    private void loginUser() {

        String loginPhone = logPhone.getText().toString();
        String loginPassword = logPassword.getText().toString();

        if (TextUtils.isEmpty(loginPhone)) {
            Toast.makeText(getActivity(), "Введите номер телефона", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(loginPassword)) {
            Toast.makeText(getActivity(), "Введите пароль", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Вход в приложение");
            loadingBar.setMessage("Пожалуйста, подождите...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
    //if user exists, login to the app
            validateUser(loginPhone, loginPassword);
        }

    }

    private void validateUser(String loginPhone, String loginPassword) {

        //A Firebase reference represents a particular location in your Database
        //and can be used for reading or writing data to that Database location
        // getInstance() to get an instance
        //use getReference() to access a location in the database and read or write data
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        //Add a listener for a single change in the data at this location
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(loginPhone).exists()){
                    //get value of the concrete user
                    Users usersData = snapshot.child("Users").child(loginPhone).getValue(Users.class);
                    //does our introduced data coincide with the data from the firebase
                    assert usersData != null;
                    //it`s very important to specify that  the "usersData.getPhone()" is NOT NULL
                    //you can hardcode it or just use the YODA conditions (like done here)
                    //equalsIgnoreCase <---- case is important too !!!
                    if(loginPhone.equalsIgnoreCase(usersData.getPhone())){
                        if(loginPassword.equalsIgnoreCase(usersData.getPassword())){
                            loadingBar.dismiss();
                            Toast.makeText(getActivity(), "Успешный вход!", Toast.LENGTH_SHORT).show();
                            FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                            fm.replace(R.id.frame_layout,CatalogFragment.class, null ).commit();
                        }
                    else {
                        loadingBar.dismiss();
                            Toast.makeText(getActivity(), "Неверный пароль", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    loadingBar.dismiss();
                    Toast.makeText(getActivity(), "Аккаунта с номером " + loginPhone + " не существует", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.frame_layout, SignupFragment.class, null).commit();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}