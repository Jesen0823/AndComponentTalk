package com.jesen.order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.jesen.common_lib.bean.user.BaseUser;
import com.jesen.common_lib.bean.user.IUser;
import com.jesen.component_annotation.ComARouter;
import com.jesen.component_annotation.ComParameter;
import com.jesen.component_arouter_api.ParameterManager;
import com.jesen.component_arouter_api.RouterManager;

@ComARouter(path = "/order/Order_MainActivity")
public class Order_MainActivity extends AppCompatActivity {

    private Button goHomeBtn,goPersonalBtn;

    @ComParameter
    String username;

    @ComParameter(name = "/app/getUserInfo")
    IUser iUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_main);

        goHomeBtn = findViewById(R.id.goHomeBtn);
        goPersonalBtn = findViewById(R.id.goPersonalBtn);

        goHomeBtn.setOnClickListener(view -> {
            RouterManager.getInstance()
                    .build("/app/MainActivity")
                    .withResultString("nimama", "I'am comeback!")
                    .navigation(this);
        });

        goPersonalBtn.setOnClickListener(view -> {
            RouterManager.getInstance()
                    .build("/personal/Personal_MainActivity")
                    .withString("name", "simon")
                    .navigation(this);
        });

        /*if (getIntent() != null) {
            new Order_MainActivity$$ComParameter().loadParameter(this);
        }*/

        Log.d("Order_MainActivity", "order/Order_MainActivity");

        // 懒加载方式，跳到哪加载哪个类
        ParameterManager.getInstance().loadParameter(this);

        Log.d("Order_MainActivity", "接收参数值：" + username);

        BaseUser userInfo = iUser.getUserInfo();
        if (userInfo != null) {
            Log.d("Order_MainActivity", userInfo.getName() + " / "
                    + userInfo.getAccount() + " / "
                    + userInfo.getPassword());
        }
    }
}