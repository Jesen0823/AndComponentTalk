package com.jesen.andcomponenttalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.jesen.component_annotation.ComARouter;

@ComARouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    private Button goOrder,goPeron;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goOrder = findViewById(R.id.goOrderBtn);
        goPeron = findViewById(R.id.goPerBtn);

        goOrder.setOnClickListener(view -> {

        });

        goPeron.setOnClickListener(view -> {

        });
    }
}