package com.sanim.articular.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.sanim.articular.Notification.createChannel;
import com.sanim.articular.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText EmailAddress;
    private Button ResetButton;
    FirebaseAuth firebaseAuth;
    private NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        firebaseAuth=FirebaseAuth.getInstance();
        notificationManagerCompat=NotificationManagerCompat.from(this);
        createChannel createChannel=new createChannel(this);
        createChannel.createChannel();

        EmailAddress=findViewById(R.id.etEmail_reset);
        ResetButton=findViewById(R.id.btn_reset);

        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = EmailAddress.getText().toString().trim();

                if (email.equals("")) {
                    Toast.makeText(ResetPasswordActivity.this, "All Field Are Required", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this, "Please check you Email", Toast.LENGTH_SHORT).show();
                                DisplayNotification1();
                                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(ResetPasswordActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
    int id=1;
    int Code;
    int Number =12222;
    int newcode;
    private void DisplayNotification1() {

        Notification notification=new NotificationCompat.Builder(this,createChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_message_black_24dp)
                .setContentTitle("Email Code :"+newcode )
                .setContentText("Your Email is " +EmailAddress.getText().toString())
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(id,notification);
        id++;

    }

}