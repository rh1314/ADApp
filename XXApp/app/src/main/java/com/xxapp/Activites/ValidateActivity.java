package com.xxapp.Activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.xxapp.Https.XUtils;
import com.xxapp.R;
import com.xxapp.Utils.Code;
import com.xxapp.Utils.CodeTimerTask;
import com.xxapp.Utils.Loading;
import com.xxapp.Utils.MyTextWatcher;
import com.xxapp.Views.MEditText;
import com.xxapp.Views.TitleView;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/10/18.
 */
public class ValidateActivity extends BaseActivity{
    @ViewInject(R.id.register_validate_title)
    private TitleView titleView;
    @ViewInject(R.id.validatePhone)
    private MEditText phone;
    @ViewInject(R.id.validateCode)
    private MEditText validateCode;
    @ViewInject(R.id.validate_getCode)
    private TextView getCode;
    private static String ph;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    XUtils.show((String)msg.obj);
                    break;
                case 1:
                    XUtils.show(msg.arg1);
                    //失败
                    if (msg.arg2==1){
                        CodeTimerTask.getInstance().cancelTimer();
                    }
                    break;
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*SMSSDK短信验证*/
        SMSSDK.initSDK(this,"181ce1a9b39c5","13e5942b3174b86047b1c21f804dfa97");
        setContentView(R.layout.activity_register_validate);
        ViewUtils.inject(this);
        titleView.setImgLeftClickListenser(clickListener);
        titleView.setRightClickListenser(clickListener);
        phone.addTextChangeListener(phoneWatcher);
        validateCode.addTextChangeListener(codeWatcher);
        getCode.setOnClickListener(clickListener);
        SMSSDK.registerEventHandler(eventHandler);
        //自动填充手机号
        if (ph!=null){
            phone.setText(ph);
        }
        /*返回后再次进入，仍显示读秒begin*/
        if (CodeTimerTask.getInstance().isRun()){
            CodeTimerTask.getInstance().startTimer(getCode);
        }
        /*返回后再次进入，仍显示读秒end*/
    }

    private EventHandler eventHandler=new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result==SMSSDK.RESULT_COMPLETE){
                switch (event){

                    case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                        //提示验证码已发送
                        handler.sendMessage(handler.obtainMessage(1,R.string.validateCode_send_success,-1));
                        break;
                    //得到验证结果
                    case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                        //注册
                        Loading.dismiss();
                        if (getIntent().getBooleanExtra("isRegister",true)){
                            RegisterActivity.startActivity(ValidateActivity.this,ph);
                        }else{
                            //跳转
                            PwdResetActivity.startActivity(ValidateActivity.this,ph);
                        }
                        finish(); //跳转后关闭
                        break;
                }
            }else{
                Log.e("demo","===error===:"+ JSON.toJSONString(data));
                switch (event){
                    case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                        handler.sendMessage(handler.obtainMessage(1,R.string.validateCode_send_error,1));
                        break;
                    case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                        handler.sendMessage(handler.obtainMessage(1,R.string.validateCode_not_right,1));
                        Loading.dismiss();
                        break;
                }
            }
        }
    };

    private MyTextWatcher codeWatcher=new MyTextWatcher() {
        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length()==4){
                titleView.setTxtRightVisibility(View.VISIBLE);
            }else{
                titleView.setTxtRightVisibility(View.GONE);
            }
        }
    };

    private MyTextWatcher phoneWatcher=new MyTextWatcher() {
        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().matches(Code.PHONE_MATCH)){
                getCode.setEnabled(true);
            }else{
                getCode.setEnabled(false);
            }
        }
    };

    private View.OnClickListener clickListener =new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imgLeft:
                    finish();
                    break;
                case R.id.imgRight:
                    //提交验证码
                    ph=phone.getText();
                    String code=validateCode.getText();
                    if (!ph.matches(Code.PHONE_MATCH)) {
                        XUtils.show(R.string.phone_format_error);
                        return;
                    }
                    Loading.show();
                    SMSSDK.submitVerificationCode("86",ph,code);
                    break;
                case R.id.validate_getCode:
                    getCode();
                    break;
            }
        }
    };

    public static void startActivity(Context context, boolean isRegister){
        Intent intent=new Intent(context,ValidateActivity.class);
        intent.putExtra("isRegister",isRegister);
        context.startActivity(intent);
    }
    /*获取验证码begin*/
    private void getCode() {
        ph =phone.getText();
        if (ph.matches(Code.PHONE_MATCH)) {
            SMSSDK.getVerificationCode("86",ph);
            //按钮倒计时
            CodeTimerTask.getInstance().startTimer(getCode);
        }
    }
    /*获取验证码end*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
