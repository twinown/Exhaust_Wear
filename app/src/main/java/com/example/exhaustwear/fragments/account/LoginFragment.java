package com.example.exhaustwear.fragments.account;

import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exhaustwear.R;
//import com.example.exhaustwear.activities.SignUpActivity;
import com.example.exhaustwear.navigation.FragmentUpdateCallback;
import com.example.exhaustwear.navigation.MainFragmentPagerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {
    MainFragmentPagerAdapter mPagerAdapter;
    private ViewPager2 mViewPager;
    public static final int TAB_POSITION = 2;
    private FirebaseAuth firebaseAuth;
    private View view;
    private EditText logEmail, logPassword;
    private TextView forgotPass, registrationText;
    private Button logButton, clearButton;
    private ProgressDialog loadingBar;
    private boolean isPressed = true;

    //region newInstance
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    //endregion

    //region Fields
    private FragmentUpdateCallback mFragmentUpdateCallback;
    //endregion




    //region Fragment lifecycle
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof FragmentUpdateCallback) {
            mFragmentUpdateCallback = (FragmentUpdateCallback)context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        view = inflater.inflate(R.layout.fragment_login, container, false);
        logEmail = view.findViewById(R.id.editTextEmailAddress);
        logPassword = view.findViewById(R.id.editTextPassword);
        loadingBar = new ProgressDialog(getActivity());
        mViewPager = view.findViewById(R.id.viewPager);

        //for "Войти" button
        logButton = view.findViewById(R.id.loginButton);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              loginUser();
            }
        });

        //for "Забыли пароль?" text
        forgotPass = view.findViewById(R.id.forgotPassword);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentUpdateCallback.addFragment(ForgotPassword.newInstance(), TAB_POSITION);
            }
        });

        //for "Очистить поля" button
        clearButton = view.findViewById(R.id.clear_text);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logEmail.setText("");
                logPassword.setText("");
            }
        });

            // click the text "Регистрация"
            registrationText = view.findViewById(R.id.sign_up_text);
            registrationText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        mFragmentUpdateCallback.addFragment(SignupFragment.newInstance(), TAB_POSITION);
                }
            });
        return view;
    }

    //pressing the button "Войти"
    private void loginUser() {
        String loginEmail = logEmail.getText().toString().trim();
        String loginPassword = logPassword.getText().toString().trim();
        if (TextUtils.isEmpty(loginEmail)) {
            Toast.makeText(getActivity(), "Введите E-mail", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(loginPassword)) {
            Toast.makeText(getActivity(), "Введите пароль", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Вход в приложение");
            loadingBar.setMessage("Пожалуйста, подождите...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            //if user exists, login
            logIn(loginEmail, loginPassword);
        }
    }

    // checking of the user`s existing and login with firebase auth
    private void logIn(String loginEmail, String loginPassword) {
        if (firebaseAuth != null) {
            firebaseAuth.signInWithEmailAndPassword(loginEmail, loginPassword)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            loadingBar.dismiss();
                           mFragmentUpdateCallback.openAccountFragment();
                            Toast.makeText(getActivity(), "Успешный вход!", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadingBar.dismiss();
                            Toast.makeText(getActivity(), "Проверьте корректность вводимых данных, а также отсутствие пробелов", Toast.LENGTH_LONG).show();
                        }
                    });
        }


    }



}
//old code (log in using phone) without email auth
//only database
/*

    private void logIn(String loginEmail, String loginPassword) {



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
                            fm.replace(R.id.frame_layout, CatalogFragment.class, null ).commit();
                        }
                    else {
                        loadingBar.dismiss();
                            Toast.makeText(getActivity(), "Неверный пароль", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    loadingBar.dismiss();
                    Toast.makeText(getActivity(), "Аккаунта " + loginPhone + " не существует", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.frame_layout, SignupFragment.class, null).commit();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

*/