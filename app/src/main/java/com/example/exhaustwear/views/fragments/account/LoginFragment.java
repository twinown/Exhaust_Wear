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

        //for "??????????" button
        Button logButton = view.findViewById(R.id.loginButton);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        //for "???????????? ?????????????" text
        TextView forgotPass = view.findViewById(R.id.forgotPassword);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forgotPassword3);
            }
        });

        // click the text "??????????????????????"
        TextView registrationText = view.findViewById(R.id.sign_up_text);
        registrationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment2);
            }
        });
        return view;
    }

    //pressing the button "??????????"
    private void loginUser() {
        String loginEmail = logEmail.getText().toString().trim();
        String loginPassword = logPassword.getText().toString().trim();
        if (TextUtils.isEmpty(loginEmail)) {
            Toast.makeText(getActivity(), "?????????????? E-mail", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(loginPassword)) {
            Toast.makeText(getActivity(), "?????????????? ????????????", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("???????? ?? ????????????????????");
            loadingBar.setMessage("????????????????????, ??????????????????...");
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
                            Toast.makeText(getActivity(), "???????????????? ????????!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadingBar.dismiss();
                            Toast.makeText(getActivity(), "?????????????????? ???????????????????????? ???????????????? ????????????, ?? ?????????? ???????????????????? ????????????????", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}