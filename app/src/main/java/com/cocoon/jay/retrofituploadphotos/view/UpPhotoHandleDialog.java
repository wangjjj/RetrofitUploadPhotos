package com.cocoon.jay.retrofituploadphotos.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cocoon.jay.retrofituploadphotos.R;


public class UpPhotoHandleDialog extends Dialog
{


	/** 布局文件 **/
	int layoutRes;
	/** 上下文对象 **/
	Context context;

	public final static int TYPE_DELETE = 1;//删除图片
	public final static int TYPE_CONTINUE = 2;//继续上传图片
	public final static int TYPE_FAIL = 3;//上传图片失败
	public final static int TYPE_DELETE_DATA = 4;//删除数据
	private int type ;



	public interface OnCertainButtonClickListener {
		void onCertainButtonClick();
	}
	private OnCertainButtonClickListener mOnCertainButtonClickListener;


	public UpPhotoHandleDialog(Context context, int theme, int resLayout, int type) {
		super(context, theme);
		this.context = context;
		this.layoutRes = resLayout;
		this.type = type;
	}


	public void setOnCertainButtonClickListener(OnCertainButtonClickListener listener) {
		mOnCertainButtonClickListener = listener;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 指定布局
		this.setContentView(layoutRes);

//		View layout_dialog =  findViewById(R.id.layout_dialog);
//		layout_dialog.setMinimumWidth((int)(ScreenUtil.getScreenWidth(context) * 0.9));//设置dialog的宽度 
//		layout_dialog.setMinimumHeight((int)(ScreenUtil.getScreenHeight(context) * 0.7));

		//		// 根据id在布局中找到控件对象
		TextView confirm =  (TextView) findViewById(R.id.confirm);
		TextView cancel =  (TextView) findViewById(R.id.cancel);
		ImageView img =  (ImageView) findViewById(R.id.img);
		TextView title = (TextView) findViewById(R.id.title);


		// 为按钮绑定点击事件监听器
		if(confirm != null) {
			confirm.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if(mOnCertainButtonClickListener != null){
						mOnCertainButtonClickListener.onCertainButtonClick();
					}
				}

			});
		}
		if(cancel != null) {
			cancel.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					UpPhotoHandleDialog.this.dismiss();
				}

			});
		}

		switch (type){
			case TYPE_DELETE:
				img.setVisibility(View.GONE);
				title.setText("是否删除？");
				confirm.setText("是");
				cancel.setText("否");
				break;
			case TYPE_CONTINUE:
				img.setVisibility(View.VISIBLE);
				img.setImageResource(R.mipmap.up_photo_succ);
				title.setText("上传成功");
				confirm.setText("继续上传");
				cancel.setText("取消");
				break;
			case TYPE_FAIL:
				img.setVisibility(View.VISIBLE);
				img.setImageResource(R.mipmap.up_photo_fail);
				title.setText("上传失败");
				confirm.setText("重新上传");
				cancel.setText("取消");
				break;
			case TYPE_DELETE_DATA:
				img.setVisibility(View.GONE);
				title.setText("是否删除该条数据？");
				confirm.setText("是");
				cancel.setText("否");
				break;


		}

	}




}


