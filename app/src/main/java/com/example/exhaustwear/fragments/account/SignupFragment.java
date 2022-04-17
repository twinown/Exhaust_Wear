package com.example.exhaustwear.fragments.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.exhaustwear.R;
import com.example.exhaustwear.Model.User;
import com.example.exhaustwear.navigationn.FragmentUpdateCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;


public class SignupFragment extends Fragment {

    private   View view;
    private   EditText name, regPhone, regEmail, regPassword, regConfPassword;
    private   FirebaseAuth firebaseAuth;
    private   ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        name = view.findViewById(R.id.editTextRegName);
        regPhone = view.findViewById(R.id.editTextRegNum);
        regEmail = view.findViewById(R.id.editTextRegEmailAddress);
        regPassword = view.findViewById(R.id.editTextPasswordRegOne);
        regConfPassword = view.findViewById(R.id.editTextTextPasswordRegTwo);
        firebaseAuth = FirebaseAuth.getInstance();

        //loading bar
        progressDialog = new ProgressDialog(getActivity());

        //for "зарегистрироваться" button
        Button regButton = view.findViewById(R.id.regButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              checkUserExists();
            }
        });

        //for "войти" text
        TextView login = view.findViewById(R.id.sign_up_text);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                Navigation.findNavController(view).navigate(R.id.action_signupFragment2_to_loginFragment);
            }
        });
        return view;
    }

    //registration occurs here
    public void createAccount() {
        //getting symbols from edits
        String userName = name.getText().toString().trim();
        String userPhone = regPhone.getText().toString().trim();
        String userEmail = regEmail.getText().toString().trim();
        String userPassword = regPassword.getText().toString().trim();
        String userConfPassword = regConfPassword.getText().toString().trim();

        //checking the empty strings
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getActivity(), "Введите имя", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(userPhone)) {
            Toast.makeText(getActivity(), "Введите номер телефона", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(getActivity(), "Введите E-mail", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(userPassword)) {
            Toast.makeText(getActivity(), "Введите пароль", Toast.LENGTH_SHORT).show();
        }else if(userPassword.length() < 6){
            regPassword.setError("Пароль не должен быть короче шести символов");
            regPassword.requestFocus();
        } else if (TextUtils.isEmpty(userConfPassword)) {
            Toast.makeText(getActivity(), "Подтвердите пароль", Toast.LENGTH_SHORT).show();
        }else if(!userPassword.equals(userConfPassword)){
            Toast.makeText(getActivity(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
        } else{
            progressDialog.setTitle("Создание аккаунта");
            progressDialog.setMessage("Пожалуйста, подождите...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        //   making auth in firebase and
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    User user = new User(userName, userPhone, userEmail, userPassword);
                    //creating database and threads there
                    FirebaseDatabase.getInstance().getReference("Users")
                            //additional thread here
                           .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Navigation.findNavController(view).popBackStack();
                                Toast.makeText(getActivity(), "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(getActivity(), "Ошибка", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    if (firebaseAuth.getCurrentUser() != null){
                        firebaseAuth.signOut();
                    }
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Ошибка", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    //what happens after pushing the "зарегистрироваться" button with checking of the user`s existing
    public void checkUserExists(){
        try {
            if(regEmail.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Заполните ВСЕ поля!", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(regEmail.getText().toString()).matches()){
                regEmail.setError("Используйте корректный E-mail без пробелов");
                regEmail.requestFocus();
            } else if (firebaseAuth != null && !regEmail.getText().toString().isEmpty()) {
            firebaseAuth.fetchSignInMethodsForEmail(regEmail.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            boolean checkResult = !Objects.requireNonNull(task.getResult().getSignInMethods()).isEmpty();
                            if (!checkResult){
                                createAccount();
                            }else {
                                Toast.makeText(getActivity(), "E-mail уже зарегистрирован", Toast.LENGTH_SHORT).show();
                              //  regButton.setEnabled(true);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                          //  regButton.setEnabled(true);
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
          } catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}


//old code (log in using phone) without email auth
//only database
/*

    //creating objects in the database
    public void registration( String userEmail,  String userPassword) {






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

*/