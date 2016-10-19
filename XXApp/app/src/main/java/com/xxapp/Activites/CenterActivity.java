package com.xxapp.Activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/10/18.
 */
public class CenterActivity extends BaseActivity {

    public static void startActivity(Context context){
        Intent intent=new Intent(context,CenterActivity.class);
        context.startActivity(intent);
    }
}
