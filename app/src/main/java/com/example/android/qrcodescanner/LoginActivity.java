package com.example.android.qrcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private TextView tv,SignUpPromt;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ProgressDialog progressDialog;
    public Typeface customfont;
    private EditText EmailLogin,PasswordLogin;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
//        progressDialog = new ProgressDialog(LoginActivity.this);
//        progressDialog.setTitle("Login");
//        progressDialog.setMessage("Login to your account");
        EmailLogin = findViewById(R.id.etEmailLogin);
        PasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin= findViewById(R.id.btnLogin);
        SignUpPromt = findViewById(R.id.SignUpPromt);
        if(mAuth.getCurrentUser()!=null)
        {

//                        progressDialog.hide();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();








        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(EmailLogin.getText().toString()) || EmailLogin==null)
                {
                    EmailLogin.setError("Email is invalid");
                    Toast.makeText(LoginActivity.this,"Email is invalid",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(PasswordLogin.getText().toString()) || PasswordLogin==null)
                {
                    PasswordLogin.setError("Password is invalid");
                    Toast.makeText(LoginActivity.this,"Password is invalid",Toast.LENGTH_SHORT).show();
                    return;
                }
//                progressDialog.show();
                mAuth.signInWithEmailAndPassword(EmailLogin.getText().toString(),PasswordLogin.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {

//                              progressDialog.hide();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();







                            Toast.makeText(LoginActivity.this, "Login Successfull",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//                            progressDialog.hide();
                        }
                    }
                });
            }
        });

        SignUpPromt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                finish();
            }
        });


    }
}






