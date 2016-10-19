package com.xxapp.Activites;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/10/20.
 */
public class PwdResetActivity extends BaseActivity{
    public static void startActivity(Context context,String phone){
        Intent intent=new Intent(context,PwdResetActivity.class);
        intent.putExtra("phone",phone);
        context.startActivity(intent);
    }
}
