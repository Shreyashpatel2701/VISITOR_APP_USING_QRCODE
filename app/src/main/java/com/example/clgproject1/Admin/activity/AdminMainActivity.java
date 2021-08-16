package com.example.clgproject1.Admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.clgproject1.Activity.RegistrationActivity;
import com.example.clgproject1.Admin.fragments.AdminActivityFragment;
import com.example.clgproject1.Admin.fragments.AdminHomeFragment;
import com.example.clgproject1.Admin.fragments.AdminSettingsFragment;
import com.example.clgproject1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminMainActivity extends AppCompatActivity {
    FloatingActionButton adduser;
    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        init();
        adduser.setOnClickListener(new adduserclickonlistener());
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);
        actionBar.setTitle("HOME");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000077")));


        AdminHomeFragment fragment = new AdminHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment, "");
        fragmentTransaction.commit();

    }
    void init(){
        adduser = findViewById(R.id.add_user);
        bottomNavigationView = findViewById(R.id.navigation);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Home Activity");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.nav_home:
                    adduser.setVisibility(View.VISIBLE);
                    actionBar.setTitle("Home");
                    AdminHomeFragment fragment = new AdminHomeFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment, "");
                    fragmentTransaction.commit();
                    return true;

                case R.id.nav_activity:
                    adduser.setVisibility(View.INVISIBLE);
                    actionBar.setTitle("Activity");
                    AdminActivityFragment fragment1 = new AdminActivityFragment();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.content, fragment1);
                    fragmentTransaction1.commit();
                    return true;

                case R.id.nav_settings:
                    adduser.setVisibility(View.INVISIBLE);
                    actionBar.setTitle("Users");
                    AdminSettingsFragment fragment2 = new AdminSettingsFragment();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.content, fragment2, "");
                    fragmentTransaction2.commit();
                    return true;


            }
            return false;



        }


    };


    private class adduserclickonlistener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(AdminMainActivity.this, RegistrationActivity.class);
            startActivity(intent);
        }
    }

}