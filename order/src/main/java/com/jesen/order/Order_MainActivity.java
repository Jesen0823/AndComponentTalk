package com.jesen.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.jesen.common_lib.jumppage.RecordPathManager;

public class Order_MainActivity extends AppCompatActivity {

    private Button goHomeBtn,goPersonalBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_main);

        goHomeBtn = findViewById(R.id.goHomeBtn);
        goPersonalBtn = findViewById(R.id.goPersonalBtn);

        goHomeBtn.setOnClickListener(view -> {
            Class<?> targetClass = RecordPathManager.getTargetClass("app", "MainActivity");
            Intent intent = new Intent(this,targetClass);
            intent.putExtra("name","app");
            startActivity(intent);
        });

        goPersonalBtn.setOnClickListener(view -> {
            Class<?> targetClass = RecordPathManager.getTargetClass("personal", "Person_MainActivity");
            Intent intent = new Intent(this,targetClass);
            intent.putExtra("name","personal");
            startActivity(intent);
        });
    }
}