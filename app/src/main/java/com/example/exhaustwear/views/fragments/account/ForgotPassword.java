package com.example.exhaustwear.views.fragments.account;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.exhaustwear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPassword extends Fragment {

    private EditText emailEdit;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        emailEdit = view.findViewById(R.id.resetTextEmailAddress);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = view.findViewById(R.id.progressBar);
        Button resetBut = view.findViewById(R.id.resetButton);
        resetBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
        return view;
    }

    private void resetPassword() {
        String email = emailEdit.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(getActivity(), "Поле не должно быть пустым", Toast.LENGTH_SHORT).show();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEdit.setError("Введите корректный E-mail");
            emailEdit.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "На Вашу почту отправлено письмо, следуйте инструкциям", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getActivity(), "Попробуйте ещё раз", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}