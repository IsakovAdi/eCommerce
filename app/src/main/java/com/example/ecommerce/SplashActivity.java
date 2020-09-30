package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/montserrat_regular.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        auth = FirebaseAuth.getInstance();

        SystemClock.sleep(3000);

        Intent login = new Intent(SplashActivity.this, DashboardActivity.class);
        startActivity(login);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Auto login
//        FirebaseUser currentUser = auth.getCurrentUser();
//        if (currentUser == null){
//            Intent login = new Intent(SplashActivity.this, AuthActivity.class);
//            startActivity(login);
//            finish();
//        }
//        else{
//            Intent dashboard = new Intent(SplashActivity.this, DashboardActivity.class);
//            startActivity(dashboard);
//            finish();
//        }
    }
}