package com.example.exhaustwear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exhaustwear.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AccountActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private EditText nameView, emailView, phoneView;
    private Button buttonExit, buttonUpdate;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        firebaseAuth = FirebaseAuth.getInstance();


        nameView = findViewById(R.id.correct_name);
        emailView = findViewById(R.id.correct_email);
        phoneView = findViewById(R.id.correct_phone);
        gettingInfo();


        //for "изменить" update data
        buttonUpdate = findViewById(R.id.update_info);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameView.getText().toString();
                String email = emailView.getText().toString();
                String phone = phoneView.getText().toString();
                if (name.isEmpty() && email.isEmpty() && phone.isEmpty()) {
                    Toast.makeText(AccountActivity.this, "Поля не должны быть пустыми!", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailView.setError("Используйте корректный E-mail без пробелов");
                    emailView.requestFocus();
                }
                else updateInfo(name, email, phone);
            }
        });

        //for exit button
        buttonExit = findViewById(R.id.exit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(AccountActivity.this, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();

            }
        });
    }
    //getting info about user from database
    private void gettingInfo() {
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseUser = firebaseAuth.getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference("Users");
            userId = firebaseUser.getUid();
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        String name = user.getName();
                        String email = user.getEmail();
                        String phone = user.getPhone();
                        nameView.setText(name);
                        emailView.setText(email);
                        phoneView.setText(phone);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AccountActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    //for updating info about user
    // changing is happening in database too
    private void updateInfo(String mName, String mEmail, String mPhone) {
        HashMap user = new HashMap();
        user.put("name", mName);
        user.put("email", mEmail);
        user.put("phone", mPhone);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseUser.getUid()).updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    nameView.setText("");
                    emailView.setText("");
                    phoneView.setText("");
                    Toast.makeText(AccountActivity.this, "Данные изменены", Toast.LENGTH_SHORT).show();
                    gettingInfo();
                }else{
                    Toast.makeText(AccountActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




}