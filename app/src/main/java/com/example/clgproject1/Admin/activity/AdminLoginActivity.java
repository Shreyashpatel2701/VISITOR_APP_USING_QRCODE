package com.example.clgproject1.Admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clgproject1.Activity.HomeActivity;
import com.example.clgproject1.Activity.LoginActivity;
import com.example.clgproject1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminLoginActivity extends AppCompatActivity {

    ProgressDialog progressBar;
    TextView forgotPassword;
    TextInputEditText AdminEmail,AdminPassword;
    Button LoginasAdmin;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        init();

        LoginasAdmin.setOnClickListener(new Adminlogin());

    }



   void init(){
       ActionBar actionBar= getSupportActionBar();
       actionBar.hide();
        firebaseAuth = FirebaseAuth.getInstance();
       progressBar = new ProgressDialog(AdminLoginActivity.this);
        //AdminEmail = findViewById(R.id.admin_login);
        //AdminPassword = findViewById(R.id.admin_pass);
       AdminEmail = findViewById(R.id.admin_login);
       AdminPassword = findViewById(R.id.admin_pass);
        LoginasAdmin = findViewById(R.id.AdminLogin_Login);
        forgotPassword = findViewById(R.id.forgotadminpassword);
       if (firebaseAuth != null){
           firebaseUser =firebaseAuth.getCurrentUser();
       }
    }


     class Adminlogin implements View.OnClickListener{
         @Override
         public void onClick(View v) {
            String adminemail = AdminEmail.getText().toString().trim() ;
             String adminpassword = AdminPassword.getText().toString().trim();




           if (!Patterns.EMAIL_ADDRESS.matcher(adminemail).matches()) {
                 AdminEmail.setError("Invalid Email");
                 AdminPassword.setFocusable(true);

             } /*else if (!adminemail.matches("atul.kulkarni@viit.ac.in")  ){
                 AdminEmail.setError("Invalid Email");
                 AdminPassword.setFocusable(true);
             } */else if (!adminemail.matches("munira.bharmal@vit.edu")){
               AdminEmail.setError("Invalid Email");
               AdminPassword.setFocusable(true);
           }
           else {
                 //calling Login method TO sign in
                 loginUser(adminemail, adminpassword);
             }



         }

    }




     void loginUser(String Email,String Password){
        progressBar.setMessage("Logging In....");
        progressBar.show();

        // sign in with email and password after authenticating
        firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    progressBar.dismiss();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    // checking if user has registered or not
                    if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                        String email = user.getEmail();
                        String uid = user.getUid();
                        HashMap<Object, String> hashMap = new HashMap<>();
                        hashMap.put("email", email);
                        hashMap.put("uid", uid);
                        hashMap.put("name", "");
                        hashMap.put("onlineStatus", "online");
                        hashMap.put("typingTo", "noOne");
                        hashMap.put("phone", "");
                        hashMap.put("image", "");
                        hashMap.put("cover", "");
                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                        // store the value in Database in "Users" Node
                        DatabaseReference reference = database.getReference("Users");

                        // storing the value in Firebase
                        reference.child(uid).setValue(hashMap);
                    }
                    Toast.makeText(AdminLoginActivity.this, "Registered User " + user.getEmail(), Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(AdminLoginActivity.this, AdminMainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                } else {
                    progressBar.dismiss();
                    Toast.makeText(AdminLoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.dismiss();
                Toast.makeText(AdminLoginActivity.this, "Error Occured", Toast.LENGTH_LONG).show();
            }
        });

     }




}