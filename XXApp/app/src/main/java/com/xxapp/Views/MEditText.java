package com.xxapp.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xxapp.R;

/**
 * Created by Administrator on 2016/7/14.
 */
public class MEditText extends RelativeLayout implements View.OnFocusChangeListener,View.OnClickListener,View.OnTouchListener,TextWatcher {
    private RelativeLayout relativeLayout;
    private EditText etInput;
    private ImageView imgLable, imgDel, imgEye;
    private boolean delEnable, eyeEnable;
    private int paddingTop;
    private int paddintBottom;

    public MEditText(Context context) {
        super(context);
        init(null);
    }

    public MEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        paddingTop=getContext().getResources().getDimensionPixelSize(R.dimen.paddingTop_10);
        paddintBottom=getContext().getResources().getDimensionPixelSize(R.dimen.paddingBottom_3);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_medit, this);
        relativeLayout = (RelativeLayout) findViewById(R.id.medit_root);
        etInput = (EditText) findViewById(R.id.medit_input);
        imgLable = (ImageView) findViewById(R.id.medit_lable);
        imgDel = (ImageView) findViewById(R.id.medit_del);
        imgEye = (ImageView) findViewById(R.id.medit_eye);
        etInput.setOnFocusChangeListener(this);
        //文本改变监听
        etInput.addTextChangedListener(this);
        imgDel.setOnClickListener(this);
        imgEye.setOnTouchListener(this);
        if (attrs == null)
            return;
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MEditText);
        int N = array.getIndexCount();
        for (int i = 0; i < N; i++) {
            int index = array.getIndex(i);
            switch (index) {
                case R.styleable.MEditText_me_text:
                    etInput.setText(array.getString(index));
                    break;
                case R.styleable.MEditText_me_del_enable:
                    delEnable = array.getBoolean(index, false);
                    break;
                case R.styleable.MEditText_me_hint:
                    etInput.setHint(array.getString(index));
                    break;
                case R.styleable.MEditText_me_lable_src:
                    imgLable.setImageDrawable(array.getDrawable(index));
                    break;
                case R.styleable.MEditText_me_eye_enable:
                    eyeEnable = array.getBoolean(index, false);
                    break;
                case R.styleable.MEditText_me_inputType:
                    //默认为显示
                    setInputType(array.getInt(index,0));
                    break;
            }
        }
    }

    /*设置输入框明文密文begin*/
    public  static final int INPUTTYPE_PASSWORD=1;
    public  static final int INPUTTYPE_NORMAL=0;
    public void setInputType(int type){
        switch(type){
            case INPUTTYPE_NORMAL:
                etInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                break;
            case INPUTTYPE_PASSWORD:
                etInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
        }
    }
    /*设置输入框明文密文end*/

    public void setText(String text) {
        etInput.setText(text);
    }

    public String getText() {
      return  etInput.getText().toString().trim();
    }

    public void setHint(String text) {
        etInput.setHint(text);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        relativeLayout.setSelected(b);
        //删除可用且内容不为空，则显示删除按钮
        if (delEnable && b && etInput.length() > 0) {
            imgDel.setVisibility(View.VISIBLE);
        } else if (delEnable && !b) {
            imgDel.setVisibility(View.GONE);
        }
        if (eyeEnable && b) {
            imgEye.setVisibility(View.VISIBLE);
        } else if (eyeEnable) {
            imgEye.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        etInput.getText().clear(); //点击删除
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //眼睛图标按下事件
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            setInputType(INPUTTYPE_NORMAL);
            return true;
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            setInputType(INPUTTYPE_PASSWORD);
            return true;
        }
        return false;
    }

    /*文本改变监听begin*/
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (delEnable&&editable.length()>0){
            imgDel.setVisibility(View.VISIBLE);
        }else if(delEnable){
            imgDel.setVisibility(View.GONE);
        }
    }
    /*文本改变监听end*/

    /*设置图片大小begin*/
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int hh=h-paddingTop-paddintBottom;
        imgLable.getLayoutParams().width=hh;
        imgDel.getLayoutParams().width=hh;
        imgEye.getLayoutParams().width=hh;
    }
    /*设置图片大小end*/

    /*设置文本改变监听begin*/
    public void addTextChangeListener(TextWatcher watcher){
        etInput.addTextChangedListener(watcher);
    }
     /*设置文本改变监听end*/
}
