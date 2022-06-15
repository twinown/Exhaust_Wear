package com.example.exhaustwear.views.fragments.account;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.exhaustwear.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private View view;
    private EditText logEmail, logPassword;
    private ProgressDialog loadingBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This callback will only be called when LoginFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.navigation_home);
                //NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.navigation_home1);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        firebaseAuth = FirebaseAuth.getInstance();
        view = inflater.inflate(R.layout.fragment_login, container, false);
        logEmail = view.findViewById(R.id.editTextEmailAddress);
        logPassword = view.findViewById(R.id.editTextPassword);
        loadingBar = new ProgressDialog(getActivity());

        //for "Войти" button
        Button logButton = view.findViewById(R.id.loginButton);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        //for "Забыли пароль?" text
        TextView forgotPass = view.findViewById(R.id.forgotPassword);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forgotPassword3);
            }
        });

        // click the text "Регистрация"
        TextView registrationText = view.findViewById(R.id.sign_up_text);
        registrationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment2);
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
                            // NavHostFragment.findNavController(LoginFragment.this).popBackStack();
                            Navigation.findNavController(view).popBackStack();
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