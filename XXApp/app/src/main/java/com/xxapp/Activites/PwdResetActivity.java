package com.xxapp.Activites;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.xxapp.Entities.Result;
import com.xxapp.Https.BaseRequestCallBack;
import com.xxapp.Https.XUtils;
import com.xxapp.R;
import com.xxapp.Utils.Code;
import com.xxapp.Views.TitleView;

/**
 * Created by Administrator on 2016/10/20.
 */
public class PwdResetActivity extends BaseActivity{
    @ViewInject(R.id.resetPwd_title)
    private TitleView titleView;
    @ViewInject(R.id.resetPwd_email)
    private TextView email;
    @ViewInject(R.id.resetPwd_submit)
    private Button btnSubmit;
    private String phone;
    public static void startActivity(Context context,String phone){
        Intent intent=new Intent(context,PwdResetActivity.class);
        intent.putExtra("phone",phone);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpwd);
        ViewUtils.inject(this);
        phone=getIntent().getStringExtra("phone");
        if (!phone.matches(Code.PHONE_MATCH)) {
            XUtils.show(R.string.phone_format_error);
            finish();
        }
        //获取邮箱
        sendEmail();
    }

    private View.OnClickListener clickListener=new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imgLeft:
                    finish();
                    break;
                case R.id.resetPwd_submit:
                    sendEmail();
                    break;
            }
        }
    };

    private void sendEmail() {
        RequestParams params=new RequestParams();
        params.addBodyParameter("phone",phone);
        String url="";
        XUtils.send(url, params, new BaseRequestCallBack<Result<Boolean>>() {
            @Override
            public void success(Result<Boolean> data) {
                XUtils.show(data.msg);
                if (data.data){
                    //弹出对话框
                    new AlertDialog.Builder(PwdResetActivity.this).
                            setMessage("邮件已发送，有效期24小时").
                            setNegativeButton(R.string.ok,null).show();
                }
            }
        }, true);
    }

    public void getEmail(){
        String emailValue;
        String url="";
        RequestParams params=new RequestParams();
        params.addBodyParameter("phone",phone);

        XUtils.send(url, params, new BaseRequestCallBack<Result<String>>() {
            @Override
            public void success(Result<String> data) {

                if (data.state==Result.STATE_SUCCESS){
                   email.setText(data.data);
                }else{
                    XUtils.show(R.string.data_load_error);
                    finish();
                }
            }
        }, true);
    }
}
