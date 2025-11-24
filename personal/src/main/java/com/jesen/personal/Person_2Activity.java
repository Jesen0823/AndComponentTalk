package com.jesen.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jesen.component_annotation.ComARouter;
import com.jesen.personal.databinding.ActivityPerson2Binding;
import com.jesen.personal.databinding.ActivityPerson3Binding;

@ComARouter(path = "/personal/Person_2Activity")
public class Person_2Activity extends AppCompatActivity {

    private ActivityPerson2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerson2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}