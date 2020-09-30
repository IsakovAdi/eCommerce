package com.example.ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.DashboardActivity;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

import static com.example.ecommerce.AuthActivity.onResetPasswordFragment;

public class LoginFragment extends Fragment {

    TextView forgot_pass;
    FrameLayout parentFrameLayout;
    MaterialEditText email;
    MaterialEditText password;
    Button signin_btn;

    ProgressBar login_progress;

    FirebaseAuth firebaseAuth;

    private String emailPattern = "[a-zA=z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        parentFrameLayout = getActivity().findViewById(R.id.auth_framelayout);

        //View elements
        email = view.findViewById(R.id.sign_in_email);
        password = view.findViewById(R.id.sign_in_password);
        signin_btn = view.findViewById(R.id.sign_in_button);
        login_progress = view.findViewById(R.id.login_progress);
        forgot_pass = view.findViewById(R.id.sign_in_forgot);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPasswordFragment = true;
                setFragment(new ResetPasswordFragment());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPass();
            }
        });
    }

    private void checkEmailAndPass() {
        if (email.getText().toString().matches(emailPattern)){
            if (password.length() >= 8){
                login_progress.setVisibility(View.VISIBLE);
                signin_btn.setEnabled(false);

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    login_progress.setVisibility(View.INVISIBLE);
                                    Intent intent = new Intent(getActivity(), DashboardActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }else{
                                    signin_btn.setEnabled(true);
                                    login_progress.setVisibility(View.INVISIBLE);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else{
                Toast.makeText(getActivity(), "Неверный Email или пароль", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getActivity(), "Неверный Email или пароль", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(password.getText())){
                signin_btn.setEnabled(true);
                signin_btn.setTextColor(getResources().getColor(R.color.colorAccent, getActivity().getTheme()));
            }
        }
        else{
            signin_btn.setEnabled(false);
        }
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_fom_right, R.anim.slideout_from_left);
        transaction.replace(parentFrameLayout.getId(), fragment);
        transaction.commit();
    }
}