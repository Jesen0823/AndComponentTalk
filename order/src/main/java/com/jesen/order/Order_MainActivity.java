package com.jesen.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class Order_MainActivity extends AppCompatActivity {

    private Button goHomeBtn,goPersonalBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_main);

        goHomeBtn = findViewById(R.id.goHomeBtn);
        goPersonalBtn = findViewById(R.id.goPersonalBtn);

        goHomeBtn.setOnClickListener(view -> {

        });

        goPersonalBtn.setOnClickListener(view -> {

        });
    }
}