package com.sanim.articular.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sanim.articular.R;

public class MainActivity extends AppCompatActivity {

    private TextView AgainText, ViewConnectionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewConnectionText = findViewById(R.id.noInternet);
        AgainText = findViewById(R.id.tryAgain);

        final boolean wifi = checkConnection();
        refresh(wifi);

        AgainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Checking Connection Please Wait.....", Toast.LENGTH_SHORT).show();
                boolean checkConnection=checkConnection();
                refresh(checkConnection);
            }
        });
    }

    private void refresh(boolean wifi)
    {
        if (wifi) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        } else {
            ViewConnectionText.setVisibility(View.VISIBLE);
            AgainText.setVisibility(View.VISIBLE);

            ViewConnectionText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                }
            });
            Toast.makeText(this, "No Connection Found", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkConnection() {
        ConnectivityManager connectivityManager=(ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi=connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo network=connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

        if(wifi.isConnected()){
            return true;
        }
        else if(network.isConnected()){
            return true;
        }else{
            return false;
        }
    }
}
