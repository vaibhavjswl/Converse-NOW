package com.zain.deeplearning.conversenow.conv_now;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class login  extends AppCompatActivity {
    Button buttonLogin, buttonRegister;
    EditText Emailtxt, PassText;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = findViewById(R.id.btnLogin) ;
        buttonRegister = findViewById(R.id.btnRegisterHere) ;
        Emailtxt = findViewById(R.id.txtEmail) ;
        PassText = findViewById(R.id.txtPassword );


        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunLoginUser();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, RegisterActivity.class));
            }
        });

    }

   private void FunLoginUser() {
        String email = Emailtxt.getText().toString().trim().toLowerCase();
        String password = PassText.getText().toString().trim().toLowerCase();

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Emailtxt.setError("Enter a valid Email Address");
            return;
        }

        if (TextUtils.isEmpty(password) || password.length()<4 || password.length() >10) {
            PassText.setError("Enter a valid Password");
            return;
        }

        progressDialog.setMessage("Please Wait ....");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email , password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(login.this,"Login Successfully" , Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(login.this, MainActivity.class));
                        }

                        else{
                            Toast.makeText(login.this,"Login Unsuccessfull" , Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }
}
