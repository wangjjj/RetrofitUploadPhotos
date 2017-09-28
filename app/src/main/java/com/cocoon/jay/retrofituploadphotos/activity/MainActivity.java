package com.cocoon.jay.retrofituploadphotos.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.cocoon.jay.retrofituploadphotos.R;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 利用retrofit批量上传图片文件
 * 两个类有些区别的
 * 如有问题发邮件18513571552@163.com
 * 水平邮箱，希望批评指正
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_uploadact1)
    TextView tvUploadact1;
    @BindView(R.id.tv_uploadact2)
    TextView tvUploadact2;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.tv_uploadact1, R.id.tv_uploadact2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_uploadact1:
                startActivity(new Intent(this, UploadAct1.class));
                break;
            case R.id.tv_uploadact2:
                startActivity(new Intent(this, UploadAct2.class));
                break;
        }
    }
}
