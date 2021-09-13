package com.jesen.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jesen.component_annotation.ComARouter;

@ComARouter(path = "/personal/Person_3Activity")
public class Person_3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person3);
    }
}