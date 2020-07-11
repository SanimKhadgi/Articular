package com.sanim.articular.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sanim.articular.Interface.IArticulLoadDone;
import com.sanim.articular.Model.Article;
import com.sanim.articular.R;
import com.squareup.picasso.Picasso;

public class ReadArticulActivity extends AppCompatActivity {

    private TextView Title, Content1, Content2;
    private ImageView Image1, Image2;

    DatabaseReference article;
    String id;
    IArticulLoadDone articulLoadDone;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_articul);

        Title=findViewById(R.id.txt_artical_title);
        Content1=findViewById(R.id.article_content1);
        Content2=findViewById(R.id.article_content2);
        Image1=findViewById(R.id.image1);
        Image2=findViewById(R.id.image2);
        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        final Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                if(event.sensor.getType()==Sensor.TYPE_PROXIMITY){

                    if(event.values[0]==0){
                        params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                        params.screenBrightness = 1f;
                        getWindow().setAttributes(params);
                    }
                    else{
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
        if (proximitySensor != null) {
            sensorManager.registerListener(sensorEventListener, proximitySensor, sensorManager.SENSOR_DELAY_NORMAL);

        }else {
            Toast.makeText(this, "No sensor found", Toast.LENGTH_SHORT).show();
        }
        id=getIntent().getStringExtra("id");
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Article").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Article article=dataSnapshot.getValue(Article.class);
                Title.setText(article.Name.toString());
                Content1.setText(article.Content1.toString());
                Content2.setText(article.Content2.toString());
                Picasso.get().load(article.Image2).into(Image1);
                Picasso.get().load(article.Image3).into(Image2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}