package com.jesen.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jesen.component_annotation.ComARouter;
import com.jesen.personal.databinding.ActivityPerson3Binding;

@ComARouter(path = "/personal/Person_3Activity")
public class Person_3Activity extends AppCompatActivity {
    private ActivityPerson3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerson3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}