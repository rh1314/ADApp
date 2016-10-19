package com.xxapp.Entities;

/**
 * Created by Administrator on 2016/10/14.
 */
public class Result<T> {
    public static final int STATE_SUCCESS=1;
    public static final int STATE_ERROR=2;
    public int state;
    public String descr;
    public T data;
}
