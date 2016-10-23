package com.xxapp.Https;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.xxapp.R;
import com.xxapp.Utils.Loading;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/10/18.
 */
public abstract class BaseRequestCallBack<T> extends RequestCallBack<String> {
    private Type type;
    public BaseRequestCallBack(){
        //获取超类
        Type superClass=this.getClass().getGenericSuperclass();
        //第一个参数
        this.type=((ParameterizedType)superClass).getActualTypeArguments()[0];
    }
    @Override
    public void onSuccess(ResponseInfo<String> responseInfo) {
        Loading.dismiss();
        if (responseInfo!=null){
            String json=responseInfo.result;
//            json="[{\"Name\":\"01177950\",\"Pwd\":\"123\",\"Remark\":\"aaddsfa\"}]";
            if (json.matches("^\\{(.+:.+,*){1,}\\}$")){
                T t=JSON.parseObject(json,type);
                if(t!=null){
                    success(t);
                }else{
                    XUtils.show(R.string.request_nodata);
                }
            }else{
                XUtils.show(R.string.request_data_format_error);
            }
        }else{
            XUtils.show(R.string.data_load_error);
        }
    }

    @Override
    public void onFailure(HttpException e, String s) {
        Loading.dismiss();
        XUtils.show(R.string.request_error);
        Log.e("error:",s);
        e.printStackTrace();
        failure();
    }

   public abstract void success(T data);

    public void failure(){}
}
