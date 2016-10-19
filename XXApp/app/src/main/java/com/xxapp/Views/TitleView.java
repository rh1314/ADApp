package com.xxapp.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xxapp.Https.XUtils;
import com.xxapp.R;

public class TitleView extends RelativeLayout {
    private TextView txtTitle,txtRight=null;
    private ImageView imgLeft,imgRight=null;
    private String currentUrl;
    public TitleView(Context context) {
        super(context);
        init(null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        LayoutInflater.from(getContext()).inflate(R.layout.layout_title,this);
        txtTitle=(TextView)findViewById(R.id.txtTitle);
        txtRight=(TextView)findViewById(R.id.txtRight);
        imgLeft=(ImageView)findViewById(R.id.imgLeft);
        imgRight=(ImageView)findViewById(R.id.imgRight);
        if (attrs==null)
            return;
        TypedArray array= getContext().obtainStyledAttributes(attrs,R.styleable.TitleView);
        int N=array.getIndexCount();
        for (int i=0;i<N;i++){
            int index=array.getIndex(i);
            switch (index){
                case R.styleable.TitleView_tv_img_left_visibility:
                    setVisibility(imgLeft,array.getInt(index,0)); //getInt默认为0
                    break;
                case R.styleable.TitleView_tv_title:
                    txtTitle.setText(array.getString(index));
                    break;
                case R.styleable.TitleView_tv_right:
                    txtRight.setText(array.getString(index));
                    break;
                case R.styleable.TitleView_tv_img_right_visibility:
                    setVisibility(imgRight,array.getInt(index,2)); //getInt默认为2
                    break;
                case R.styleable.TitleView_tv_tv_right_visibility:
                    setVisibility(txtRight,array.getInt(index,2));
                    break;
            }
        }
    }

    //设置显示隐藏
    private void setVisibility(View v, int visibility){
        switch (visibility){
            case 0:
                v.setVisibility(View.VISIBLE);
                break;
            case 1:
                v.setVisibility(View.INVISIBLE);
                break;
            case 2:
                v.setVisibility(View.GONE);
                break;
        }
    }

    public void setTxtTitle(String text){
        txtTitle.setText(text);
    }

    public void setImgLeftClickListenser(View.OnClickListener clickListenser){
        imgLeft.setOnClickListener(clickListenser);
    }
    //设置点击事件
    public void setRightClickListenser(View.OnClickListener clickListenser){
        imgRight.setOnClickListener(clickListenser);
        txtRight.setOnClickListener(clickListenser);
    }

    public void setImgUrl(String url){
        if (url.equals(currentUrl))
            return;
        currentUrl=url;
        XUtils.disPlay(imgRight,currentUrl);
    }

    public void setTxtRightVisibility(int visibility){
        txtRight.setVisibility(visibility);
    }
    public void setTextRight(String text){
        txtRight.setText(text);
    }
}
