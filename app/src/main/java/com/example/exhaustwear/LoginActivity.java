package com.example.exhaustwear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exhaustwear.fragments.account.AccountFragment;
import com.example.exhaustwear.fragments.account.ForgotPassword;
import com.example.exhaustwear.fragments.account.SignupFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText logEmail, logPassword;
    private TextView forgotPass, registrationText;
    View view;
    private BottomNavigationView bottomNavigationView;
    NavController navController;
    private Button logButton, clearButton;
    private ProgressDialog loadingBar;

   FragmentManager fm = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        logEmail = findViewById(R.id.editTextEmailAddress);
        logPassword = findViewById(R.id.editTextPassword);
        loadingBar = new ProgressDialog(LoginActivity.this);
        //  mViewPager = view.findViewById(R.id.viewPager);

        //for "Забыли пароль?" text
        forgotPass = findViewById(R.id.forgotPassword);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new ForgotPassword());
            }
        });

        //for "Войти" button
        logButton = findViewById(R.id.loginButton);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        //for "На главную" button
        clearButton = findViewById(R.id.clear_text);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if (firebaseAuth.getCurrentUser() != null) {
            replaceFragment(new AccountFragment());
        }


        // click the text "Регистрация"
        registrationText = findViewById(R.id.sign_up_text);
        registrationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SignupFragment());

            }
        });



    }

    //pressing the button "Войти"
    private void loginUser() {
        String loginEmail = logEmail.getText().toString().trim();
        String loginPassword = logPassword.getText().toString().trim();
        if (TextUtils.isEmpty(loginEmail)) {
            Toast.makeText(this, "Введите E-mail", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(loginPassword)) {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Вход в приложение");
            loadingBar.setMessage("Пожалуйста, подождите...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            //if user exists, login
            logIn(loginEmail, loginPassword, view);
        }
    }

    // checking of the user`s existing and login with firebase auth
    private void logIn(String loginEmail, String loginPassword, View view) {
        if (firebaseAuth != null) {
            firebaseAuth.signInWithEmailAndPassword(loginEmail, loginPassword)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            loadingBar.dismiss();
                        //   replaceFragment(new AccountFragment());
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                         //  fragmentTransaction.replace(R.id.container, AccountFragment.newInstance()).commit();
logButton.setVisibility(View.INVISIBLE);
                            clearButton.setVisibility(View.INVISIBLE);

                            Toast.makeText(LoginActivity.this, "Успешный вход!", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Проверьте корректность вводимых данных, а также отсутствие пробелов", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
  private void replaceFragment (@NonNull Fragment fragment){
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).commit();
  logButton.setVisibility(View.INVISIBLE);
      clearButton.setVisibility(View.INVISIBLE);

    }

 @Override
    public void onBackPressed() {
            super.onBackPressed();

    }

}
