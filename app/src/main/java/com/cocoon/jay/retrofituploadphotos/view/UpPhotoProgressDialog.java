package com.cocoon.jay.retrofituploadphotos.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cocoon.jay.retrofituploadphotos.R;


public class UpPhotoProgressDialog extends Dialog
{

	/** 布局文件 **/
	int layoutRes;
	/** 上下文对象 **/
	Context context;

	ProgressBar mUploadProgress;
	TextView mUploadProgressText;



	public UpPhotoProgressDialog(Context context, int theme, int resLayout) {
		super(context, theme);
		this.context = context;
		this.layoutRes = resLayout;
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 指定布局
		this.setContentView(layoutRes);

//		View layout_dialog = (View) findViewById(R.id.layout_dialog);
//		layout_dialog.setMinimumWidth((int)(ScreenUtil.getScreenWidth(context) * 0.9));//设置dialog的宽度 
//		layout_dialog.setMinimumHeight((int)(ScreenUtil.getScreenHeight(context) * 0.7));


		mUploadProgress = (ProgressBar)findViewById(R.id.upload_progress);
		mUploadProgressText = (TextView) findViewById(R.id.upload_progress_text);


	}



	public void setProgress(int progress) {
		mUploadProgress.setProgress(progress);
		mUploadProgressText.setText(progress + "%");
	}




}


