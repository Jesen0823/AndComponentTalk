package com.jesen.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.jesen.component_annotation.ComARouter;

@ComARouter(path = "/personal/Person_MainActivity")
public class Person_MainActivity extends AppCompatActivity {

    private Button goHomeBtn, goOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_main);

        goHomeBtn = findViewById(R.id.goHomeBtn);
        goOrderBtn = findViewById(R.id.goOrderBtn);

        goHomeBtn.setOnClickListener(view -> {

        });

        goOrderBtn.setOnClickListener(view -> {

        });
    }
}