package com.jesen.andcomponenttalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button goOrder,goPeron;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goOrder = findViewById(R.id.goOrderBtn);
        goPeron = findViewById(R.id.goPerBtn);

        goOrder.setOnClickListener(view -> {
            Class<?> aClass = null;
            try {
                aClass = Class.forName("com.jesen.personal.Order_MainActivity");
                Intent intent = new Intent(this,aClass);
                startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        goPeron.setOnClickListener(view -> {
            Class<?> aClass = null;
            try {
                aClass = Class.forName("com.jesen.personal.Person_MainActivity");
                Intent intent = new Intent(this,aClass);
                startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}