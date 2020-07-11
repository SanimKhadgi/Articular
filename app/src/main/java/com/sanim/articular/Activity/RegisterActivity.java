package com.sanim.articular.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.sanim.articular.Model.User;
import com.sanim.articular.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText FullNameText, EmailText, PasswordText, PhoneText;
    private Button LoginButton;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FullNameText = findViewById(R.id.etFullName);
        EmailText = findViewById(R.id.etEmail);
        PasswordText = findViewById(R.id.etPassword);
        PhoneText = findViewById(R.id.etPhone);
        LoginButton = findViewById(R.id.button_register);
        mAuth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });



    }

    private void registerUser() {
        final String name = FullNameText.getText().toString().trim();
        final String email = EmailText.getText().toString().trim();
        final String password = PasswordText.getText().toString().trim();
        final String phone = PhoneText.getText().toString().trim();
        final String profileimage = "default";
        final String status = "online";

        if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)|| TextUtils.isEmpty(name)|| TextUtils.isEmpty(phone))
        {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Enter Values", Toast.LENGTH_SHORT).show();
            Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(50);
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(FirebaseAuth.getInstance().getCurrentUser().getUid(),  name, email,password, profileimage,phone);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user);
                            Toast.makeText(RegisterActivity.this, "User created", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
