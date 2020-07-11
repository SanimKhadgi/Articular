package com.sanim.articular.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sanim.articular.Model.User;
import com.sanim.articular.R;

public class UpdatedProfileActivity extends AppCompatActivity {
    EditText name, phonenumber, email,password;
    Button btnUpdate;
    FirebaseUser firebaseUser;
    DatabaseReference refrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updated_profile);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        refrences= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        name = findViewById(R.id.et_update_name);
        phonenumber = findViewById(R.id.et_update_Phone);
        email = findViewById(R.id.et_update_Email);
        password = findViewById(R.id.et_update_Password);
        btnUpdate = findViewById(R.id.button_update);
        refrences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                email.setText(user.getEmail());
                password.setText(user.getPassword());
                name.setText(user.getFullname());
                phonenumber.setText(user.getPhoneNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = name.getText().toString();
                String fphone = phonenumber.getText().toString();
                String femail = email.getText().toString();
                String fpassword = password.getText().toString();
                User user = new User(FirebaseAuth.getInstance().getCurrentUser().getUid(),  fname, femail,fpassword, "Default",fphone);
                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(user);
                Toast.makeText(UpdatedProfileActivity.this, "User Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}