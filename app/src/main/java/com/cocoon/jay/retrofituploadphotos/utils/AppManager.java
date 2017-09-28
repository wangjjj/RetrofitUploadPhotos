package com.cocoon.jay.retrofituploadphotos.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Activity管理类
 */
public class AppManager {

public Stack<Activity> mStack;
public ArrayList<Activity> mActivities;
public static AppManager mInstance;

public static AppManager getInstance(){
    if( mInstance==null ) {
        mInstance = new AppManager();
    }
    return mInstance;
}

/**
 * 添加Activity到栈中
 */
public void addActivity(Activity activity){
    if( mStack==null ){
        mStack = new Stack<>();
    }
    mStack.add(activity);
}

/**
 * 获取当前Activity
 */
public Activity getCurrentActivity(){
    return mStack.lastElement();
}

/**
 * 结束指定的Activity
 */
public void killSingleActivity(Activity activity){
    if( activity==null ){
        return;
    }
    mStack.remove(activity);
    activity.finish();
}

/**
 * 结束指定的Activity
 */
public void killSingleActivity(Class<?> cls){
    for( Activity activity : mStack ){
        if( activity.getClass().equals(cls) ){
            killSingleActivity(activity);
        }
    }
}

/**
 * 结束多个Activity
 */
public void killMoreActivity(ArrayList<Class<?>> activities){
    if( mActivities==null ){
        mActivities = new ArrayList<>();
    }
    for( Class<?> cls : activities ){
        Activity activity = isHere(cls);
        if( activity!=null ){
            mActivities.add(activity);
        }
    }
    mStack.removeAll(mActivities);
    for( Activity activity : mActivities ){
        activity.finish();
    }
    mActivities.clear();
}

/**
 * 是否在栈中
 */
public Activity isHere(Class<?> cls){
    for( Activity activity : mStack ){
        if( activity.getClass().equals(cls) ){
            return activity;
        }
    }
    return null;
}

/**
 * 结束所有
 */
public void killAllActivity(){
    for( Activity activity : mStack ){
        activity.finish();
    }
    mStack.clear();
}

/**
 * 退出程序
 */
public void exitApp(){
    killAllActivity();
    System.exit(0);
}

}