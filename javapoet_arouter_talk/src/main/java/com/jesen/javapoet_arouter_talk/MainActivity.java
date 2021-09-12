package com.jesen.javapoet_arouter_talk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.jesen.javapoet_annotation_lib.PARouter;
import com.jesen.javapoet_arouter_talk.ui.login.LoginActivity$$PARouter;

@PARouter(path = "/test/MainActivity")
public class MainActivity extends AppCompatActivity {

    private Button testARouterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testARouterBtn = findViewById(R.id.testRouterBtn);
        testARouterBtn.setOnClickListener(view -> {
            Class targetClass = LoginActivity$$PARouter.findTargetClass("/test/LoginActivity");
            startActivity(new Intent(this, targetClass));
        });
    }
}