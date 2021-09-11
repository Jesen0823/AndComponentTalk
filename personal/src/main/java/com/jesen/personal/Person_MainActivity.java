package com.jesen.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.jesen.common_lib.jumppage.RecordPathManager;

public class Person_MainActivity extends AppCompatActivity {

    private Button goHomeBtn, goOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_main);

        goHomeBtn = findViewById(R.id.goHomeBtn);
        goOrderBtn = findViewById(R.id.goOrderBtn);

        goHomeBtn.setOnClickListener(view -> {
            Class<?> targetClass = RecordPathManager.getTargetClass("app", "MainActivity");
            Intent intent = new Intent(this,targetClass);
            intent.putExtra("name","app");
            startActivity(intent);
        });

        goOrderBtn.setOnClickListener(view -> {
            Class<?> targetClass = RecordPathManager.getTargetClass("order", "Order_MainActivity");
            Intent intent = new Intent(this,targetClass);
            intent.putExtra("name","order");
            startActivity(intent);
        });
    }
}