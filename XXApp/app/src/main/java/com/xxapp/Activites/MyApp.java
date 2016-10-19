package com.xxapp.Activites;

import android.app.Application;

import com.xxapp.Entities.UserInfo;
import com.xxapp.Https.XUtils;
import com.xxapp.Utils.Loading;

public class MyApp extends Application {
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //类初始化
        XUtils.init(this);
        Loading.init(this);
    }
}
