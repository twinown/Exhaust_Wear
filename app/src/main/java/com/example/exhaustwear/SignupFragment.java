package com.example.exhaustwear;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class SignupFragment extends Fragment {
    View view;
    EditText name, regPhone,regEmail, regPassword, regConfPassword;
    Button regButton;
    TextView login;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_signup, container, false);

        name = view.findViewById(R.id.editTextRegName);
        regPhone = view.findViewById(R.id.editTextRegNum);
        regPassword = view.findViewById(R.id.editTextPasswordRegOne);
        regConfPassword = view.findViewById(R.id.editTextTextPasswordRegTwo);
        regEmail = view.findViewById(R.id.editTextEmailAddress);
        //loading circle
        progressDialog = new ProgressDialog(getActivity());

        //for button
        regButton = view.findViewById(R.id.regButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        //for signup text
        login = view.findViewById(R.id.sign_up_text);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_layout, LoginFragment.class, null).commit();
            }
        });

        return view;
    }

    //push the "зарегистрироваться" button

    private void createAccount(){
        //getting symbols from edits
        String userName = name.getText().toString();
        String userPhone= regPhone.getText().toString();
        String userEmail = regEmail.getText().toString();
        String userPassword = regPassword.getText().toString();
        String userConfPassword = regConfPassword.getText().toString();

        //checking the empty strings

        if (TextUtils.isEmpty(userName)){
            Toast.makeText(getActivity(), "Введите имя", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userPhone)) {
            Toast.makeText(getActivity(), "Введите номер телефона", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(getActivity(), "Введите E-mail", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(getActivity(), "Введите пароль", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userConfPassword)) {
            Toast.makeText(getActivity(), "Подтвердите пароль", Toast.LENGTH_SHORT).show();
        }else if(!userPassword.equals(userConfPassword)){
            Toast.makeText(getActivity(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
        } else{
            progressDialog.setTitle("Создание аккаунта");
            progressDialog.setMessage("Пожалуйста, подождите...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
//   checking the phone for having been already added
            validatePhone(userName, userPhone, userEmail, userPassword);
        }
    }

    //creating objects in the database
    private void validatePhone(final String userName,  final String userPhone,final String userEmail, final String userPassword) {
        //A Firebase reference represents a particular location in your Database
        //and can be used for reading or writing data to that Database location
        final DatabaseReference rootRef;
        // getInstance() to get an instance
        //use getReference() to access a location in the database and read or write data
        rootRef = FirebaseDatabase.getInstance().getReference();
        //Add a listener for a single change in the data at this location
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //A DataSnapshot instance contains data from a Firebase Database location.
            // Any time you read Database data, you receive the data as a DataSnapshot.
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //comparing edited email with existed to add new user
                if (!(snapshot.child("Users").child(userPhone).exists())){
                    //locate this way
                    //how data will be located
                    HashMap<String,Object> userDataMap = new HashMap<>();
                    userDataMap.put("Name", userName);
                    userDataMap.put("Phone", userPhone);
                    userDataMap.put("Email", userEmail);
                    userDataMap.put("Password", userPassword);
                    rootRef.child("Users").child(userPhone).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                                fm.replace(R.id.frame_layout,LoginFragment.class, null ).commit();
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Ошибка", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    //or progressDialog will be spin infinitely
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Телефон " + userPhone +  " уже зарегистрирован", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.frame_layout,LoginFragment.class, null ).commit();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}