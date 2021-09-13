package com.jesen.andcomponenttalk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.jesen.andcomponenttalk.apt.ComARouter$$Group$$order;
import com.jesen.andcomponenttalk.apt.ComARouter$$Path$$order;
import com.jesen.common_lib.order.OrderAddress;
import com.jesen.common_lib.order.OrderBean;
import com.jesen.common_lib.order.drawable.OrderDrawable;
import com.jesen.component_annotation.ComARouter;
import com.jesen.component_annotation.ComParameter;
import com.jesen.component_annotation.model.RouterBean;
import com.jesen.component_arouter_api.ParameterManager;
import com.jesen.component_arouter_api.RouterManager;
import com.jesen.component_arouter_api.core.ComARouterLoadGroup;
import com.jesen.component_arouter_api.core.ComARouterLoadPath;

import java.io.IOException;
import java.util.Map;

@ComARouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    private Button goOrder,goPeron;

    @ComParameter
    String name;

    @ComParameter
    int age;

    @ComParameter
    boolean isSuccess;

    @ComParameter(name = "mainmain")
    String object;

    @ComParameter(name = "/order/getOrderBean")
    OrderAddress orderAddress;

    @ComParameter(name = "/order/getDrawable")
    OrderDrawable orderDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goOrder = findViewById(R.id.goOrderBtn);
        goPeron = findViewById(R.id.goPerBtn);


        // 懒加载方式，跳到哪加载哪个类
        ParameterManager.getInstance().loadParameter(this);

        // 测试接收传递参数
        Log.e("MainActivity", toString());

        int drawableId = orderDrawable.getDrawable();
        ImageView img = findViewById(R.id.img);
        img.setImageResource(drawableId);

        // 测试获取接口通信
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OrderBean orderBean = orderAddress.getOrderBean("aa205eeb45aa76c6afe3c52151b52160", "144.34.161.97");
                    Log.e("netease >>> ", orderBean.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        goOrder.setOnClickListener(view -> {
            // 使用路由跳转
            /*ComARouterLoadGroup group = new ComARouter$$Group$$order();
            Map<String, Class<? extends ComARouterLoadPath>> classMap = group.loadGroup();
            Class<? extends ComARouterLoadPath> clazz = classMap.get("order");
            ComARouter$$Path$$order path = null;
            try {
                path = (ComARouter$$Path$$order) clazz.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            Map<String, RouterBean> pathMap = path.loadPath();
            RouterBean bean = pathMap.get("/order/Order_MainActivity");
            if (bean != null){
                Intent intent = new Intent(this, bean.getClazz());
                intent.putExtra("nimama","jiankang");
                startActivity(intent);
            }*/

            RouterManager.getInstance()
                    .build("/order/Order_MainActivity")
                    .withString("username", "simon")
                    .navigation(this, 101);
        });

        // 路由跳转
        goPeron.setOnClickListener(view -> {
            RouterManager.getInstance()
                    .build("/personal/Person_MainActivity")
                    .withString("nimama","eat")
                    .withInt("age",50)
                    .navigation(this);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Log.e("MainActivity", data.getStringExtra("call"));
        }
    }

    @Override
    public String toString() {
        return "MainActivity{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isSuccess=" + isSuccess +
                ", object='" + object + '\'' +
                '}';
    }
}