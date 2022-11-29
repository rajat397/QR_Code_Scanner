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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import com.example.android.qrcodescanner.Models.User;

public class SignUpActivity extends AppCompatActivity {
    private TextView tv;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private EditText etEmailSignUp, etPasswordSignUp, etNameSignUp;
    private Button btnSignUp;
    private ProgressDialog progressDialog;
    private Typeface customfont;
    private ImageView bksn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        bksn=(ImageView) findViewById(R.id.backbuttsign);
        bksn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });

// ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        etEmailSignUp = findViewById(R.id.etEmailSignUp);
        etPasswordSignUp = findViewById(R.id.etPasswordSignUp);
        etNameSignUp = findViewById(R.id.etNameSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account ");
        progressDialog.setMessage("We are creating your account");









        btnSignUp.setOnClickListener(new View.OnClickListener() {
            private String email,password,name,id;
            @Override
            public void onClick(View view) {
                progressDialog.show();
                email = etEmailSignUp.getText().toString();
                password = etPasswordSignUp.getText().toString();
                name = etNameSignUp.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    etEmailSignUp.setError("Email is Required");
                    progressDialog.hide();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPasswordSignUp.setError("Password is Required");
                    progressDialog.hide();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    etNameSignUp.setError("UserName is Required");
                    progressDialog.hide();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    private User user;
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){

                            user = new User(etNameSignUp.getText().toString(),etEmailSignUp.getText().toString(),etPasswordSignUp.getText().toString());

                            id = task.getResult().getUser().getUid();



                            database.getReference().child("Users").child(id).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Account Created Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                        }
                    }
                });
            }

        });

    }
}











