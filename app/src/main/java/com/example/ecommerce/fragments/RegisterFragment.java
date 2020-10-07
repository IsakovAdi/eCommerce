package com.example.ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

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
import com.example.ecommerce.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;


public class RegisterFragment extends Fragment {

    FrameLayout parentFrameLayout;
    MaterialEditText email;
    MaterialEditText username;
    MaterialEditText password;
    MaterialEditText confirmPassword;
    Button signup_btn;
    ProgressBar signupProgress;

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    String emailPattern = "[a-zA=z0-9._-]+@[a-z]+.[a-z]+";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        //FrameLayout
        parentFrameLayout = getActivity().findViewById(R.id.auth_framelayout);

        //View elements
        email = view.findViewById(R.id.sign_up_email);
        username = view.findViewById(R.id.sign_up_username);
        password = view.findViewById(R.id.sign_up_password);
        confirmPassword = view.findViewById(R.id.sign_up_confirm);
        signup_btn = view.findViewById(R.id.sign_up_button);
        signupProgress = view.findViewById(R.id.register_progress);

        //Firebase config
        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        username.addTextChangedListener(new TextWatcher() {
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
        confirmPassword.addTextChangedListener(new TextWatcher() {
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
        

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPass();
            }
        });
    }

    private void checkEmailAndPass() {
        if (email.getText().toString().matches(emailPattern)){
            if (password.getText().toString().equals(confirmPassword.getText().toString())){

                signupProgress.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    User user = new User(email.getText().toString(),
                                            username.getText().toString(),
                                            password.getText().toString());

                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                    reference = FirebaseDatabase.getInstance().getReference("Users")
                                            .child(firebaseUser.getUid());

                                    reference.setValue(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        signupProgress.setVisibility(View.INVISIBLE);
                                                        ViewPager layout = (ViewPager) getActivity().findViewById(R.id.view_pager);
                                                        layout.setCurrentItem(0);
                                                    }
                                                    else{
                                                        signupProgress.setVisibility(View.INVISIBLE);
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                }else{
                                    signupProgress.setVisibility(View.INVISIBLE);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
            else {
                confirmPassword.setError("Password doesn't matched!");
            }
        }else{
            email.setError("Invalid Email!");
        }
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(username.getText())){
                if (!TextUtils.isEmpty(password.getText()) && password.length() >=8){
                    if (!TextUtils.isEmpty(confirmPassword.getText())){
                        signup_btn.setEnabled(true);
                        signup_btn.setTextColor(getResources().getColor(R.color.colorAccent, getContext().getTheme()));
                    }

                }
            }
        }
        else{
            signup_btn.setEnabled(false);
        }
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_fom_left, R.anim.slideout_from_right);
        transaction.replace(parentFrameLayout.getId(), fragment);
        transaction.commit();
    }
}