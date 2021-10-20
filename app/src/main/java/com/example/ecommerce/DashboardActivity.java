package com.example.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ecommerce.fragments.AdFragment;
import com.example.ecommerce.fragments.FavouriteFragment;
import com.example.ecommerce.fragments.MainFragment;
import com.example.ecommerce.fragments.NotificationsFragment;
import com.example.ecommerce.fragments.SearchFragment;
import com.example.ecommerce.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class DashboardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    FrameLayout mainFrameLayout;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/montserrat_regular.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        //init
        mainFrameLayout = findViewById(R.id.main_frame_layout);

        setFragment(new MainFragment());

        //Toolbar
        Toolbar toolbar = findViewById(R.id.detailsActivityToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        //Floating button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    startActivity(new Intent(DashboardActivity.this, ItemAddActivity.class));
                }
                else{
                    startActivity(new Intent(DashboardActivity.this, SignActivity.class));
                }
            }
        });

        //Navigation Drawer
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        //Navigation Menu
        final NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final Menu menu = navigationView.getMenu();

        final TextView navUserName = (TextView) headerView.findViewById(R.id.userInfo);
        final TextView navUserEmail = (TextView) headerView.findViewById(R.id.userEmail);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){

            reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        navUserName.setText(user.getUsername());
                        navUserName.setVisibility(View.VISIBLE);
                        navUserEmail.setText(user.getEmail());
                        navUserEmail.setVisibility(View.VISIBLE);

                        MenuItem loginItem = (MenuItem) menu.findItem(R.id.login);
                        loginItem.setVisible(false);
                        MenuItem myBlocks = (MenuItem) menu.findItem(R.id.nav_mycatalog);
                        myBlocks.setVisible(true);
                        MenuItem signOutItem = (MenuItem) menu.findItem(R.id.signout);
                        signOutItem.setVisible(true);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            Log.i("MyTag", "No data");
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        //todo nav_home
                        setFragment(new MainFragment());
                        break;
                    case R.id.nav_mycatalog:
                        //todo nav_home
                        setFragment(new AdFragment());
                        break;
                    case R.id.nav_search:
                        //todo nav_search
                        Intent intent = new Intent(DashboardActivity.this, SearchActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_favourites:
                        //todo nav_favourites
                        setFragment(new FavouriteFragment());
                        break;
                    case R.id.nav_notifications:
                        //todo nav_notifications
                        setFragment(new NotificationsFragment());
                        break;
                    case R.id.login:
                        startActivity(new Intent(DashboardActivity.this, SignActivity.class));
                        break;
                    case R.id.signout:
                        FirebaseAuth.getInstance().signOut();
                        navUserName.setText("");
                        navUserEmail.setText("");
                        MenuItem loginItem = (MenuItem) menu.findItem(R.id.login);
                        loginItem.setVisible(true);
                        MenuItem signOutItem = (MenuItem) menu.findItem(R.id.signout);
                        signOutItem.setVisible(false);
                        MenuItem myBlocks = (MenuItem) menu.findItem(R.id.nav_mycatalog);
                        myBlocks.setVisible(false);
                        finish();
                        startActivity(getIntent());
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_fom_right, R.anim.slideout_from_left);
        transaction.replace(mainFrameLayout.getId(), fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setFragment(new MainFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_search:
                //todo: Search Part
                Intent intent = new Intent(DashboardActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.main_notifications:
                //todo: Notification Part
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}