package com.jesen.order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jesen.component_annotation.ComARouter;

@ComARouter(path = "/order/Order_2Activity")
public class Order_2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order2);
    }
}