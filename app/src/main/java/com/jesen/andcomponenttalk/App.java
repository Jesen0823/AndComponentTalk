package com.jesen.andcomponenttalk;

import com.jesen.common_lib.BaseApplication;
import com.jesen.common_lib.jumppage.RecordPathManager;
import com.jesen.order.Order_MainActivity;
import com.jesen.personal.Person_MainActivity;

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        RecordPathManager.joinGroup("app", "MainActivity",MainActivity.class);
        RecordPathManager.joinGroup("order", "Order_MainActivity", Order_MainActivity.class);
        RecordPathManager.joinGroup("personal", "Person_MainActivity", Person_MainActivity.class);
    }
}
