package com.laika.miaudota.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.laika.miaudota.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    //sobreposição para implementar o tempo que a splash aparece
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run(){
                mostrarInicio();
            }
        }, 1500);

    }
    private void mostrarInicio(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}