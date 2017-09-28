package com.cocoon.jay.retrofituploadphotos.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.cocoon.jay.retrofituploadphotos.utils.AppManager;
import com.cocoon.jay.retrofituploadphotos.utils.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends FragmentActivity {

    protected Activity mContext;
    private Unbinder mUnBinder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        AppManager.getInstance().addActivity(this);
        mContext = this;
        setStatusBar();
        initEventAndData();
    }

    protected void setStatusBar() {
        final int statusBarAlpha = 0; //状态栏透明度
        StatusBarUtil.setTranslucentForImageViewInFragment(this, statusBarAlpha,null);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        AppManager.getInstance().killSingleActivity(this);
    }

    protected abstract int getLayout();
    protected abstract void initEventAndData();




    /*
   * 关闭软键盘
   * */
    public void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
