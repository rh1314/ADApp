package com.xxapp.Activites;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.xxapp.Https.XUtils;
import com.xxapp.R;
import com.xxapp.Utils.ActivityController;

public class BaseActivity extends FragmentActivity {
    private boolean isExit;
    private Handler handler=new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ActivityController.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.ACTION_DOWN){
            if (getClass().getName().equals(MainActivity.class.getName())){
                //退出
                if(!isExit){
                    isExit=true;
                    XUtils.show(getString(R.string.base_exit));
                    //定时操作r,2秒之内再次点击即退出
                    handler.postDelayed(r,2000);
                }else{
                    ActivityController.closeAllActivity();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private Runnable r=new Runnable() {
        @Override
        public void run() {
            isExit=false;
        }
    };
}
