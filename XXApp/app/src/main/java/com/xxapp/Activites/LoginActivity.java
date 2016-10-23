package com.xxapp.Activites;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xxapp.Entities.Result;
import com.xxapp.Entities.UserInfo;
import com.xxapp.Https.BaseRequestCallBack;
import com.xxapp.Https.XUtils;
import com.xxapp.R;
import com.xxapp.Utils.Code;
import com.xxapp.Views.MEditText;
import com.xxapp.Views.TitleView;

public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.login_title)
    private TitleView titleView;
    @ViewInject(R.id.login_user)
    private MEditText loginUser;
    @ViewInject(R.id.login_pwd)
    private MEditText loginPwd;
    @ViewInject(R.id.btnLogin)
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);

        //设置事件,登录返回
        titleView.setImgLeftClickListenser(clickListener);
        titleView.setRightClickListenser(clickListener);
        btnLogin.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener=new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnLogin:
                    login();
                    break;
                case R.id.imgLeft:
                    finish();
                    break;
                case R.id.imgRight:
                    break;
            }
        }
    };

    //XUtils注解
    @OnClick({R.id.login_sign_up,R.id.login_reset_pwd})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_sign_up:
                ValidateActivity.startActivity(LoginActivity.this,true);
                break;
            case R.id.login_reset_pwd:
//                ValidateActivity.startActivity(LoginActivity.this,false);
                RegisterActivity.startActivity(LoginActivity.this,"18666228953");
                break;
        }
    }
    private void login(){
        String account=loginUser.getText();
        String pwd=loginPwd.getText();
        RequestParams params =new RequestParams();
        if (account.matches(Code.PHONE_MATCH)){
            params.addBodyParameter("Name",account);
        }else if(account.matches(Code.EMAIL_MATCH)){
            params.addBodyParameter("Name",account);
        }else{
            XUtils.show(R.string.login_account_error);
            return;
        }
        if (!pwd.matches(Code.PWD_MATCH)){
            XUtils.show(R.string.login_pwd_error);
            return;
        }
        params.addBodyParameter("Pwd",pwd);
        String url=getString(R.string.url_login);
        //网络请求
        XUtils.send(url,null,new BaseRequestCallBack<Result<UserInfo>>() {
            @Override
            public void success(Result<UserInfo> data) {
                XUtils.show(data.descr);
                if (data.state==Result.STATE_SUCCESS){
                    ((MyApp)getApplication()).setUserInfo(data.data);
                    //跳转
                    if(getIntent().getBooleanExtra("isToCenter",false)){
                        CenterActivity.startActivity(LoginActivity.this);
                    }else{
                        finish();
                    }
                }
            }
        }, true);
    }
}
