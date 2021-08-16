package com.example.clgproject1.Admin.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.clgproject1.Activity.LoginActivity;
import com.example.clgproject1.R;

public class LoginOptionsActivity extends AppCompatActivity {
      Button Login_User,Login_admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);
        ActivityCompat.requestPermissions(LoginOptionsActivity.this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
        init();
        Login_User.setOnClickListener(new Loginonclickuser());
        Login_admin.setOnClickListener(new Loginonclickadmin());

    }


   void init(){
       ActionBar actionBar= getSupportActionBar();
       actionBar.hide();
        Login_admin = findViewById(R.id.btn_LoginAsAdmin);
        Login_User = findViewById(R.id.btn_LoginAsUser);
   }

     private class Loginonclickuser implements View.OnClickListener{
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(LoginOptionsActivity.this, LoginActivity.class);
             startActivity(intent);
         }
     }

     private class Loginonclickadmin implements View.OnClickListener{

         @Override
         public void onClick(View v) {
            Intent intent = new Intent(LoginOptionsActivity.this,AdminLoginActivity.class);
            startActivity(intent);


         }


    }



}