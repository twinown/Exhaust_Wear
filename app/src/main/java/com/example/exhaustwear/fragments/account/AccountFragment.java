package com.example.exhaustwear.fragments.account;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.exhaustwear.R;
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


public class AccountFragment extends Fragment {

    private  View view;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private EditText nameView, emailView, phoneView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        nameView = view.findViewById(R.id.correct_name);
        emailView = view.findViewById(R.id.correct_email);
        phoneView = view.findViewById(R.id.correct_phone);
            gettingInfo();

        //for "изменить" update data
        Button buttonUpdate = view.findViewById(R.id.update_info);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameView.getText().toString();
                String email = emailView.getText().toString();
                String phone = phoneView.getText().toString();
                if (name.isEmpty() && email.isEmpty() && phone.isEmpty()) {
                    Toast.makeText(getActivity(), "Поля не должны быть пустыми!", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailView.setError("Используйте корректный E-mail без пробелов");
                    emailView.requestFocus();
                }
                else updateInfo(name, email, phone);
            }
        });
        
    //for exit button
        Button buttonExit = view.findViewById(R.id.exit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    firebaseAuth.signOut();
                    Navigation.findNavController(view).navigate(R.id.loginFragment);
                    Toast.makeText(getActivity(), "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();

            }
        });
     return view;
    }


    //if user doesnt login, open login fragment
    //else open account
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (firebaseAuth.getCurrentUser() == null){
            Navigation.findNavController(view).navigate(R.id.loginFragment);
        }
    }

   //getting info about user from database
    private void gettingInfo() {
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseUser = firebaseAuth.getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference("Users");
            String userId = firebaseUser.getUid();
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
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), "Данные изменены", Toast.LENGTH_SHORT).show();
                            gettingInfo();
                        }else{
                            Toast.makeText(getActivity(), "Ошибка", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}