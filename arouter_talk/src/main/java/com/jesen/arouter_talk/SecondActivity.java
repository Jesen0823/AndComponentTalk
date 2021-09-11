package com.jesen.arouter_talk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jesen.arouter_annotation_.ARouter;


@ARouter(path = "/app/SecondActivity")
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}