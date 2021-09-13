package com.jesen.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.jesen.common_lib.bean.user.BaseUser;
import com.jesen.common_lib.bean.user.IUser;
import com.jesen.component_annotation.ComARouter;
import com.jesen.component_annotation.ComParameter;
import com.jesen.component_arouter_api.ParameterManager;
import com.jesen.component_arouter_api.RouterManager;

@ComARouter(path = "/personal/Person_MainActivity")
public class Person_MainActivity extends AppCompatActivity {

    private Button goHomeBtn, goOrderBtn;

    @ComParameter(name = "/app/getUserInfo")
    IUser iUser;

    @ComParameter
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_main);

        goHomeBtn = findViewById(R.id.goHomeBtn);
        goOrderBtn = findViewById(R.id.goOrderBtn);

        Log.d("Personal_MainActivity", "personal/Personal_MainActivity");

        // 懒加载方式，跳到哪加载哪个类
        ParameterManager.getInstance().loadParameter(this);

        Log.d("Personal_MainActivity", "接收参数值：" + username);

        BaseUser userInfo = iUser.getUserInfo();
        if (userInfo != null) {
            Log.d("Personal_MainActivity", userInfo.getName() + " / "
                    + userInfo.getAccount() + " / "
                    + userInfo.getPassword());
        }

        TextView textView = findViewById(R.id.info);
        textView.setText(userInfo.toString());

        goHomeBtn.setOnClickListener(view -> {
            RouterManager.getInstance()
                    .build("/app/MainActivity")
                    .withResultString("nimama", "I'am comeback!")
                    .navigation(this);
        });

        goOrderBtn.setOnClickListener(view -> {
            RouterManager.getInstance()
                    .build("/order/Order_MainActivity")
                    .withString("name", "person")
                    .navigation(this);
        });
    }
}