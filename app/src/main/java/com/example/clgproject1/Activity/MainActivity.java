package com.example.clgproject1.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.clgproject1.Admin.activity.AdminMainActivity;
import com.example.clgproject1.Admin.activity.LoginOptionsActivity;
import com.example.clgproject1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
       FirebaseAuth firebaseAuth;
       FirebaseUser currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          firebaseAuth = FirebaseAuth.getInstance();
          if (firebaseAuth != null){
              currentuser = firebaseAuth.getCurrentUser();
          }
        ActionBar actionBar= getSupportActionBar();
          actionBar.hide();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                    if (user == null) {
                        Intent intent = new Intent(MainActivity.this, LoginOptionsActivity.class);
                        startActivity(intent);
                        finish();
                    }


                    else if ( user != null ) {
                        String uid = user.getUid();
                        if (uid.matches("2ce1HTmrUzemVG7uEDV1gxQDMgG2")) {
                            Intent Homeintent = new Intent(MainActivity.this, AdminMainActivity.class);
                            Homeintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(Homeintent);

                            finish();

                        } else {

                            Intent Homeintent = new Intent(MainActivity.this, HomeActivity.class);
                            Homeintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(Homeintent);

                            finish();
                        }


                    }

            }
        },1000);

    }
}

