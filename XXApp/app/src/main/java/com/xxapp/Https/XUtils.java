package com.xxapp.Https;
import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xxapp.R;
import com.xxapp.Utils.Loading;

/**
 * Created by Administrator on 2016/7/14.
 */
public class XUtils {
    private static BitmapUtils bitmapUtils;
    private static Context mContext;
    private static HttpUtils httpUtils;
    private static DbUtils dbUtils;
    private static final String U="http://(ip):8080/college/";
    public static final String LOGIN="login";
    public static final String REGISTER="register";
    private static HttpHandler handler;

    public static void init(Context context){
        mContext=context;
        if (bitmapUtils==null){
            bitmapUtils=new BitmapUtils(context);
            //设置默认加载图片
            bitmapUtils.configDefaultLoadingImage(R.mipmap.ic_launcher);
            //设置加载失败图片
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.nodata);
            //设置图片缓存
            bitmapUtils.configDiskCacheEnabled(true);
        }
        if (httpUtils==null){
            httpUtils=new HttpUtils();
        }
        if (dbUtils==null){
            dbUtils=DbUtils.create(context,"test.db");//数据库名称
        }
    }

    /*网络请求begin*/
    public static <T> void send(String url, RequestParams params, RequestCallBack<T> callBack,boolean showLoading){
        //dialog显示
        if (showLoading){
            Loading.show();
        }
        if (params==null){
            //请求都会返回handler
            handler=httpUtils.send(HttpRequest.HttpMethod.GET,U+url,callBack);
        }else{
            handler=httpUtils.send(HttpRequest.HttpMethod.POST,U+url,params,callBack);
        }
    }

    public static void cancel(){
        if (handler==null){
            handler.cancel();
            handler=null;
        }
    }

    //Get请求
    public static <T> void send(String url,RequestCallBack<T> callBack,boolean showLoading){
        send(url,null,callBack,showLoading);
    }
    /*网络请求end*/

    /*图片显示begin*/
    public static void disPlay(ImageView view, String url) {
        bitmapUtils.display(view, url);
    }
    /*图片显示end*/

    /*吐司提示begin*/
    public static void show(String str){
        Toast.makeText(mContext,str,Toast.LENGTH_LONG).show();
    }
    public static void show(int id){
        Toast.makeText(mContext,id,Toast.LENGTH_LONG).show();
    }
    /*吐司提示end*/
}
