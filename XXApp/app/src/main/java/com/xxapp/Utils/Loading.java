package com.xxapp.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import com.xxapp.R;

/**
 * Created by Administrator on 2016/10/14.
 */
public class Loading {
    private static Dialog dialog;
    private static Context mContext;

    public static void init(Context context){
        mContext=context;
    }
    public static void show(){
        if (dialog==null){
            dialog=new AlertDialog.Builder(mContext).create();
            //触摸边缘不消失
            dialog.setCanceledOnTouchOutside(false);
            Window window=dialog.getWindow();
            //设置全局类型
            window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            //显示才能设置内容
            dialog.show();
            window.setContentView(R.layout.layout_loading);
        }else {
            dialog.show();
        }
    }

    public  static void dismiss(){
        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }

    public static boolean isShowing(){
        if (dialog!=null){
            return dialog.isShowing();
        }
        return false;
    }

    public static void destory(){
        dismiss();
        if (dialog!=null){
            dialog=null;
            mContext=null;
        }
    }
}
