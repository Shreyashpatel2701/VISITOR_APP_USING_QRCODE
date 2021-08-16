package com.example.clgproject1.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.clgproject1.Fragments.ActivityFragment;
import com.example.clgproject1.Fragments.HomeFragment;
import com.example.clgproject1.Fragments.SettingsFragment;
import com.example.clgproject1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

     FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
     FirebaseUser currentuser = firebaseAuth.getCurrentUser();
     DatabaseReference userdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;




    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
        init();
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);
        actionBar.setTitle("HOME");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000077")));


//        Intent serviceintent = new Intent(HomeActivity.this,ExampleService.class);
//        serviceintent.putExtra("inputExtra","Hello world");
//        startService(serviceintent);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
        String s1 = sh.getString("token","no");
       // Toast.makeText(this, s1, Toast.LENGTH_SHORT).show();

        userdatabase.child(currentuser.getUid()).child("token").setValue(s1);






        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment, "");
        fragmentTransaction.commit();


    }

    void init(){
       bottomNavigationView = findViewById(R.id.navigation);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Home Activity");

    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.nav_home:
                    actionBar.setTitle("Home");
                    HomeFragment fragment = new HomeFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment, "");
                    fragmentTransaction.commit();
                    return true;

                case R.id.nav_activity:
                    actionBar.setTitle("Activity");
                    ActivityFragment fragment1 = new ActivityFragment();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.content, fragment1);
                    fragmentTransaction1.commit();
                    return true;

                case R.id.nav_settings:

                    actionBar.setTitle("Users");
                    SettingsFragment fragment2 = new SettingsFragment();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.content, fragment2, "");
                    fragmentTransaction2.commit();
                    return true;


            }
            return false;



        }


    };

}
