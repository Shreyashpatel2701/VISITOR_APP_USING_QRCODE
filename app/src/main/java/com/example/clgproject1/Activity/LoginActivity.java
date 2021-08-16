package com.example.clgproject1.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.PrecomputedText;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clgproject1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URL;
import java.util.HashMap;

import javax.xml.transform.Result;

public class LoginActivity extends AppCompatActivity {
    TextView Sign_up,Forgetpassword;
    EditText PASSWORD,EMAIL;
    Button Login_In;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ProgressDialog progressBar;



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_acitivity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        progressBar = new ProgressDialog(LoginActivity.this);
        init();
        progressBar = new ProgressDialog(LoginActivity.this);

        Login_In.setOnClickListener(new LoginButtononclicklistener());
//        Sign_up.setOnClickListener(new Signupclicklistener());
        Forgetpassword.setOnClickListener(new RecoverPassword());
//        Sign_up.setVisibility(View.INVISIBLE);



    }


    //initializing the view items
    void  init(){
        ActionBar actionBar= getSupportActionBar();
        actionBar.hide();

        firebaseAuth =FirebaseAuth.getInstance();
        Login_In = findViewById(R.id.login_button);
//        Sign_up = findViewById(R.id.sign_up);
        Forgetpassword = findViewById(R.id.forgetpassword);
        EMAIL = findViewById(R.id.login_email);
        PASSWORD = findViewById(R.id.login_password);
        if (firebaseAuth != null){
            firebaseUser =firebaseAuth.getCurrentUser();
        }
    }



    //Login button on click listener
     private class LoginButtononclicklistener implements View.OnClickListener {
         @Override
         public void onClick(View v) {
             String email = EMAIL.getText().toString().trim();
             String password = PASSWORD.getText().toString().trim();

             // if format of email doesn't matches return null
             if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                 EMAIL.setError("Invalid Email");
                 PASSWORD.setFocusable(true);

             } else {
                 //calling Login method TO sign in
                 //loginUser(email, password);
                 new BackgroundTask(1,email,password).execute();
             }
         }
     }

     //Login method (add to async task class)
    void loginUser(String Email,String Password){

        runOnUiThread(new Runnable() {
            public void run() {
                progressBar.setMessage("Logging In....");
                progressBar.show();
            }
        });

        // sign in with email and password after authenticating
        firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {



                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressBar.dismiss();
                        }
                    });
                      // checking if user has registered or not
                    if (task.getResult().getAdditionalUserInfo().isNewUser()) {

                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(LoginActivity.this,"invalid email",Toast.LENGTH_LONG  ).show();
                                finish();
                            }
                        });



                    }

                    FirebaseUser user = firebaseAuth.getCurrentUser();


                    //Toast.makeText(LoginActivity.this, "Registered User " + user.getEmail(), Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                } else {

                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Error Occured", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }



         // Sign up listener take users to Registration activity
         private class Signupclicklistener implements View.OnClickListener {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                 startActivity(intent);
             }
         }

         // Text view to recover password
         private class RecoverPassword implements View.OnClickListener {
             @Override
             public void onClick(View v) {
                // showRecoverPasswordDialog();
                 new BackgroundTask(2,null,null).execute();
             }
         }



         // to pop up with alert dialog (add to async task class)
         private void showRecoverPasswordDialog() {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {


        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout = new LinearLayout(LoginActivity.this);
        final EditText emailet = new EditText(LoginActivity.this);//write your registered email
        emailet.setText("Email");
        emailet.setMinEms(16);
        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailet);
        linearLayout.setPadding(10, 10, 10, 10);
        builder.setView(linearLayout);
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String emaill = emailet.getText().toString().trim();

                new BackgroundTask(3,emaill,null).execute();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

           }
       });
    }



    // begin recovery email (async task class)
    private void beginRecovery(String emaill) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {



        progressBar.setMessage("Sending Email....");
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();
            }
        });

        // send reset password email
        firebaseAuth.sendPasswordResetEmail(emaill).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                progressBar.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Done sent", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Error Occured", Toast.LENGTH_LONG).show();
                }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                progressBar.dismiss();
                Toast.makeText(LoginActivity.this, "Error Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


    private class BackgroundTask extends AsyncTask<Void, Void, Boolean> {

        private final int parameter;
        private final String email;
        private final String password;

        public BackgroundTask(int parameter, String email1, String password1) {
            this.parameter = parameter;
            this.email = email1;
            this.password = password1;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            switch(parameter) {
                case 1:
                    // code block
                    loginUser(email,password);
                    break;
                case 2:
                    // code block
                    showRecoverPasswordDialog();
                    break;
                case 3:
                    // code block
                    beginRecovery(email);
                    break;
                default:
                    break;
            }


            return true;
        }

    }

}












