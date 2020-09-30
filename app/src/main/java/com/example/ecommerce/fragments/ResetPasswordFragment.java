package com.example.ecommerce.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class ResetPasswordFragment extends Fragment {

    MaterialEditText email_forgot;
    Button reset_btn;
    TextView go_back;
    FirebaseAuth auth;

    FrameLayout parentFrameLayout;

    private ViewGroup emailIconContainer;
    private ImageView emailIcon;
    private TextView emailIconText;
    private ProgressBar progressBar;

    private String emailPattern = "[a-zA=z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);

        email_forgot = view.findViewById(R.id.forgot_email);
        reset_btn = view.findViewById(R.id.reset_password_btn);
        go_back = view.findViewById(R.id.go_back);
        parentFrameLayout = getActivity().findViewById(R.id.auth_framelayout);
        auth = FirebaseAuth.getInstance();
        emailIconContainer = view.findViewById(R.id.linearLayout);
        emailIcon = view.findViewById(R.id.emailIcon);
        emailIconText = view.findViewById(R.id.emailIconText);
        progressBar = view.findViewById(R.id.progressBar);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new LoginFragment());
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIcon.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                reset_btn.setEnabled(false);

                auth.sendPasswordResetEmail(email_forgot.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Email был успешно отправлен", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    TransitionManager.beginDelayedTransition(emailIconContainer);
                                    emailIconText.setText(error);
                                    emailIconText.setTextColor(getResources().getColor(R.color.colorPrimary, getActivity().getTheme()));
                                }
                                reset_btn.setEnabled(true);
                            }
                        });
            }
        });

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_fom_right, R.anim.slideout_from_left);
        transaction.replace(parentFrameLayout.getId(), fragment);
        transaction.commit();
    }

}