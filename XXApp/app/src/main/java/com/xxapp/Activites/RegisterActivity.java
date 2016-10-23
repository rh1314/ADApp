package com.xxapp.Activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.xxapp.Entities.Result;
import com.xxapp.Https.BaseRequestCallBack;
import com.xxapp.Https.XUtils;
import com.xxapp.R;
import com.xxapp.Utils.Code;
import com.xxapp.Views.MEditText;
import com.xxapp.Views.TitleView;

/**
 * Created by Administrator on 2016/10/20.
 */
public class RegisterActivity extends BaseActivity{
    @ViewInject(R.id.register_title)
    private TitleView titleView;
    @ViewInject(R.id.register_eamil)
    private MEditText email;
    @ViewInject(R.id.register_pwd)
    private MEditText pwd;
    @ViewInject(R.id.register_rePwd)
    private MEditText rePwd;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_register);
        titleView.setImgLeftClickListenser(clickListener);
        titleView.setRightClickListenser(clickListener);
    }

    public static void startActivity(Context context, String phone){
        Intent intent=new Intent(context,RegisterActivity.class);
        intent.putExtra("phone",phone);
        context.startActivity(intent);
    }

    private View.OnClickListener clickListener=new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imgLeft:
                    finish();
                    break;
                case R.id.imgRight:
                    signUp();
                    break;
            }
        }
    };

    /*注册begin*/
    private void signUp(){
        //上页面传值
        String phone=getIntent().getStringExtra("phone");
        String emailValue=email.getText();
        String pwdValue=pwd.getText();
        String rePwdValue=rePwd.getText();
        if (phone==null){
            XUtils.show(R.string.register_phone_isnull);
        }
        if (!emailValue.matches(Code.EMAIL_MATCH)){
            XUtils.show(R.string.email_format_error);
            return;
        }
        //密码格式
        if (!pwdValue.matches(Code.PWD_MATCH)){
            XUtils.show(R.string.pwd_format_error);
            return;
        }
        if (!rePwdValue.equals(pwdValue)){
            XUtils.show(R.string.register_pwd_rePwd_notEquals);
            return;
        }
        //执行网络请求
        RequestParams params=new RequestParams();
        params.addBodyParameter("u.phone",phone);
        params.addBodyParameter("u.email",emailValue);
        params.addBodyParameter("u.pwd",pwdValue);
        XUtils.send(XUtils.REGISTER, params, new BaseRequestCallBack<Result<Boolean>>() {
            @Override
            public void success(Result<Boolean> data) {
                XUtils.show(data.descr);
                if (data.data){
                    //注册页面关闭
                    finish();
                }
            }
        },true);
    }
    /*注册end*/
}
