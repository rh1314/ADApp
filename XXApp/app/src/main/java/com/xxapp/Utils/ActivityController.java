package com.xxapp.Utils;

import android.app.Activity;
import android.os.Process;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class ActivityController {
    private static List<Activity> activities=new LinkedList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void closeAllActivity(){
        for (Activity activity: activities){
            activity.finish();
        }
        Process.killProcess(Process.myPid());
    }
}
