package com.jesen.order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jesen.component_annotation.ComARouter;
import com.jesen.component_annotation.ComParameter;
import com.jesen.component_arouter_api.ParameterManager;

@ComARouter(path = "/order/Order_DetailActivity")
public class Order_DetailActivity extends AppCompatActivity {

    @ComParameter
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Log.d("Order_DetailActivity", "order/Order_DetailActivity");

        // 懒加载方式，跳到哪加载哪个类
        ParameterManager.getInstance().loadParameter(this);

        Log.d("Order_DetailActivity", "接收参数值：" + username);
        TextView textView = findViewById(R.id.info);
        textView.setText(username);
    }
}