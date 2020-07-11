package com.sanim.articular.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sanim.articular.Notification.createChannel;
import com.sanim.articular.R;

public class LoginActivity extends AppCompatActivity {

    ProgressBar progressBar1;
    EditText EmailText,PasswordText;
    Button LoginButton;
    FirebaseUser firebaseUser;
    TextView ForgotPasswordText;
    NotificationManagerCompat notificationManagerCompat;

    FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar1=findViewById(R.id.progressBar1);

        EmailText=findViewById(R.id.etEmail_Login);
        PasswordText=findViewById(R.id.etPassword_Login);
        ForgotPasswordText=findViewById(R.id.forgotPassword);
        LoginButton=findViewById(R.id.btnLoginL);
        notificationManagerCompat=notificationManagerCompat.from(this);

        auth=FirebaseAuth.getInstance();

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(EmailText.getText().toString())|| TextUtils.isEmpty(PasswordText.getText().toString()))
                {
                    progressBar1.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Enter Values", Toast.LENGTH_SHORT).show();
                    Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(50);
                }
                progressBar1.setVisibility(View.VISIBLE);
                login();
            }
        });

        ForgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        final Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    if (event.values[0] == 0) {
                        params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                        params.screenBrightness = 0;
                        getWindow().setAttributes(params);
                    } else {
                        params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                        params.screenBrightness = -1f;
                        getWindow().setAttributes(params);
                    }
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        if(proximitySensor !=null){
            sensorManager.registerListener(sensorEventListener,proximitySensor,sensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(this, "No Sensor Available", Toast.LENGTH_SHORT).show();
        }
    }

    private void login() {

        final String email = EmailText.getText().toString();
        String password= PasswordText.getText().toString();
        if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Enter Values", Toast.LENGTH_SHORT).show();
            Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(50);
        }
        else
        {
            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar1.setVisibility(View.GONE);
                            if(task.isSuccessful())
                            {
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                DisplayNotification(email);
                                finish();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                                vibrator.vibrate(500);
                                vibrator.vibrate(500);
                            }
                        }
                    });
        }
    }

    private void DisplayNotification(String email) {
        Notification notification=new NotificationCompat.Builder(getApplicationContext(),createChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_person_black_24dp)
                .setContentTitle("Login Successfull").setContentText(email)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(1,notification);
    }
}

