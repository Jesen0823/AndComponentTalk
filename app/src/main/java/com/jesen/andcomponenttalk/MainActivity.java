package com.jesen.andcomponenttalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.jesen.andcomponenttalk.apt.ComARouter$$Group$$order;
import com.jesen.andcomponenttalk.apt.ComARouter$$Path$$order;
import com.jesen.component_annotation.ComARouter;
import com.jesen.component_annotation.model.RouterBean;
import com.jesen.component_arouter_api.core.ComARouterLoadGroup;
import com.jesen.component_arouter_api.core.ComARouterLoadPath;

import java.util.Map;

@ComARouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    private Button goOrder,goPeron;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goOrder = findViewById(R.id.goOrderBtn);
        goPeron = findViewById(R.id.goPerBtn);

        goOrder.setOnClickListener(view -> {
            // 使用路由跳转
            ComARouterLoadGroup group = new ComARouter$$Group$$order();
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
            }
        });

        goPeron.setOnClickListener(view -> {

        });
    }
}