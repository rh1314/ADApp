package com.xxapp.Utils;

import android.os.AsyncTask;
import android.widget.TextView;

import com.xxapp.R;

/**
 * Created by Administrator on 2016/10/20.
 */
public class CodeTimerTask extends AsyncTask<Void,Void,Void> {
    //计时时间
    private int time=90;
    private TextView textView;
    private static CodeTimerTask task;
    private CodeTimerTask(){}
    //是否第一次进入
    private static boolean isNew;
    private boolean isRun;

    public static CodeTimerTask getInstance(){
        if (task==null){
            task=new CodeTimerTask();
            isNew=true;
        }
        return task;
    }
    public void startTimer(TextView textView){
        this.textView=textView;
        if (isNew){
            execute();
        }
    }

    @Override
    protected void onPreExecute() {
        time=90;
        isNew=false;
        isRun=true;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (;time>=0;time--){
            publishProgress();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        //格式化秒
        if (textView!=null){
            textView.setText(String.format("%ds",time));
            if (textView.isEnabled()){
                textView.setEnabled(false);
            }
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        end();
    }

    private void end(){
        if (textView!=null){
            textView.setEnabled(true);
            textView.setText(R.string.get_code);
        }
        cancelTimer();
    }

    //取消
    public void cancelTimer(){
        if (task!=null){
            isRun=false;
            //父类取消
            super.cancel(true);
            task=null;
            isNew=true;
        }
    }

    public boolean isRun(){
        return isRun;
    }
}
