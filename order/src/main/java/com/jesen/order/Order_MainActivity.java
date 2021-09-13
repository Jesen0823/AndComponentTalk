package com.jesen.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.jesen.component_annotation.ComARouter;
import com.jesen.component_annotation.Parameter;

@ComARouter(path = "/order/Order_MainActivity")
public class Order_MainActivity extends AppCompatActivity {

    private Button goHomeBtn,goPersonalBtn;

    @Parameter
    String nimama;

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

        if (getIntent() != null) {

            new Order_MainActivity$$Parameter().loadParameter(this);
            Log.d("Main", "接收参数值：" + nimama);
        }
    }
}