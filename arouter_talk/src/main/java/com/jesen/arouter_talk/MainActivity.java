package com.jesen.arouter_talk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.jesen.arouter_annotation_.ARouter;

@ARouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(view -> {
            Class<?> targetClass = SecondActivity$ARouter.findTargetClass("/app/SecondActivity");
            startActivity(new Intent(this, targetClass));
        });
    }
}