package com.cocoon.jay.retrofituploadphotos.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cocoon.jay.retrofituploadphotos.R;
import com.cocoon.jay.retrofituploadphotos.adapter.AddPhotoAdapter2;
import com.cocoon.jay.retrofituploadphotos.adapter.AddPhotoAdapter1;
import com.cocoon.jay.retrofituploadphotos.bean.BaseBean;
import com.cocoon.jay.retrofituploadphotos.bean.BaseListBean;
import com.cocoon.jay.retrofituploadphotos.retrofitutils.ApiService;
import com.cocoon.jay.retrofituploadphotos.utils.HttpParameterBuilder;
import com.cocoon.jay.retrofituploadphotos.utils.PermissionsUtil;
import com.cocoon.jay.retrofituploadphotos.utils.ToastUtil;
import com.cocoon.jay.retrofituploadphotos.view.UpPhotoHandleDialog;
import com.cocoon.jay.retrofituploadphotos.view.UpPhotoProgressDialog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * AddPhotoAdapter2添加按钮在最前
 * retrofit批量上传图片，这里图片上传的接口和连同其他数据上传接口是分开的；亦可调为一块上传
 * 删除图片只本地删除，最终连同其他信息一块上传
 */

public class UploadAct2 extends BaseActivity {


    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.topbar_back)
    TextView topbarBack;
    @BindView(R.id.topbar_title)
    TextView topbarTitle;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    private AddPhotoAdapter2 addPhotoAdapter2;

    private List<String> getPhotos = new ArrayList<>();//请求得到的
    private List<String> upPhotos = new ArrayList<>();//每次上传的
    private ArrayList<String> showPhotos = new ArrayList<>();//展示用的

    private UpPhotoProgressDialog mProgressDialog = null;
    private Handler handler = new Handler();
    private int progress = 0;
    private boolean isAddUp = false;//  为true时点的是添加按钮  而不是查看图片


    @Override
    protected int getLayout() {
        return R.layout.activity_upload;
    }


    @Override
    protected void initEventAndData() {

        PermissionsUtil.requestMultiPermissions(this, mPermissionGrant);

        getImgs();

        addPhotoAdapter2 = new AddPhotoAdapter2(this);
//        rvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvList.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        rvList.setAdapter(addPhotoAdapter2);


        addPhotoAdapter2.setmListener(new AddPhotoAdapter2.OnItemClickListener() {
            @Override
            public void onClickAdd() {
                isAddUp = true;
            }

            @Override
            public void onClickPhoto() {
                isAddUp = false;
            }

            @Override
            public void onClickDelete(final int position) {
                final UpPhotoHandleDialog mDialog = new UpPhotoHandleDialog(mContext,
                        R.style.selectdialogstyle, R.layout.dialog_handle_photo, UpPhotoHandleDialog.TYPE_DELETE);
                mDialog.setOnCertainButtonClickListener(new UpPhotoHandleDialog.OnCertainButtonClickListener() {

                    @Override
                    public void onCertainButtonClick() {
                        mDialog.dismiss();

                        try {
                            showPhotos.remove(position);
                            addPhotoAdapter2.setPhotoPaths(showPhotos);
                            addPhotoAdapter2.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                mDialog.show();
            }
        });


    }


    @OnClick({R.id.topbar_back, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.topbar_back:
                finish();
                break;
            case R.id.btn_commit:
                getCommit();
                break;
        }
    }


    /**
     * 获取图片列表
     */
    private void getImgs() {

        JSONObject object = new JSONObject();
        try {
            object.put("id", "xxx");
            object.put("xxx", "xxx");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), object.toString());

        ApiService apiService = ApiService.Scalarsretrofit.create(ApiService.class);
        Call<String> call = apiService.getImgs(body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.e("TAG", response.body().toString());
                    BaseListBean data = new Gson().fromJson(response.body().toString(), BaseListBean.class);
                    switch (data.getStatus()) {
                        case 1:
                            //成功
                            getPhotos.clear();
                            showPhotos.clear();

                            getPhotos = data.getImgs();

                            if (getPhotos != null && getPhotos.size() > 0) {
                                showPhotos = (ArrayList<String>) getPhotos;
                                addPhotoAdapter2.setPhotoPaths(showPhotos);
                                addPhotoAdapter2.notifyDataSetChanged();
                            }

                            break;

                        default:
                            ToastUtil.Toast(data.getMessage());
                            break;

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ToastUtil.ToastFail();
            }
        });
    }


    /**
     * 点击提交
     */
    private void getCommit() {

        JSONObject object = new JSONObject();
        try {
            object.put("id", "xxx");
            object.put("xxx", "xxx");
            if (showPhotos != null && showPhotos.size() > 0) {
                object.put("xxxx", new Gson().toJson(showPhotos));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), object.toString());

        ApiService apiService = ApiService.Scalarsretrofit.create(ApiService.class);
        Call<String> call = apiService.getSaveagreement(body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.e("TAG", response.body().toString());
                    BaseBean data = new Gson().fromJson(response.body().toString(), BaseBean.class);
                    switch (data.getStatus()) {
                        case 1:
                            //成功

                            break;

                        default:
                            ToastUtil.Toast(data.getMessage());
                            break;

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ToastUtil.ToastFail();
            }
        });
    }


    /**
     * 上传图片
     */
    private void getUpPhoto() {
        HttpParameterBuilder builder = HttpParameterBuilder.newBuilder();
        builder.addParameter("xx", "xx");
        builder.addParameter("xxx", "xxx");

        if (upPhotos != null && upPhotos.size() > 0) {
//            //多张图片
            for (int i = 0; i < upPhotos.size(); i++) {
                builder.addParameter("papers" + i, new File(upPhotos.get(i)));
                //key可以随便填，不重复就行；后台接收时是根据value取的，若不是就和你的后台人员沟通下
            }
        }

        Map<String, RequestBody> params = builder.bulider();

        ApiService apiService = ApiService.Scalarsretrofit.create(ApiService.class);
        Call<String> call = apiService.upImg(params);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.e("TAG", response.body().toString());
                    BaseListBean data = new Gson().fromJson(response.body().toString(), BaseListBean.class);
                    switch (data.getStatus()) {
                        case 1:
                            //成功
                            showPhotos.addAll(data.getPapers());
                            addPhotoAdapter2.setPhotoPaths(showPhotos);
                            addPhotoAdapter2.notifyDataSetChanged();

                            if (mProgressDialog != null) {
                                mProgressDialog.dismiss();
                            }

                            final UpPhotoHandleDialog mContinueDialog = new UpPhotoHandleDialog(mContext,
                                    R.style.selectdialogstyle, R.layout.dialog_handle_photo, UpPhotoHandleDialog.TYPE_CONTINUE);
                            mContinueDialog.setOnCertainButtonClickListener(new UpPhotoHandleDialog.OnCertainButtonClickListener() {

                                @Override
                                public void onCertainButtonClick() {

                                    PhotoPicker.builder()
                                            .setPhotoCount(AddPhotoAdapter2.MAX)
                                            .setShowCamera(true)
                                            .setPreviewEnabled(false)
//                                            .setSelected(showPhotos)
                                            .start(mContext);

                                    isAddUp = true;

                                    mContinueDialog.dismiss();
                                }
                            });
                            mContinueDialog.show();

                            break;

                        default:
                            //失败
                            upDataFail();
                            break;

                    }


                } catch (Exception e) {
                    upDataFail();

                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                upDataFail();

            }
        });
    }


    /**
     * 上传失败处理
     */
    private void upDataFail() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

        final UpPhotoHandleDialog mFailDialog = new UpPhotoHandleDialog(mContext,
                R.style.selectdialogstyle, R.layout.dialog_handle_photo, UpPhotoHandleDialog.TYPE_FAIL);
        mFailDialog.setOnCertainButtonClickListener(new UpPhotoHandleDialog.OnCertainButtonClickListener() {

            @Override
            public void onCertainButtonClick() {

                PhotoPicker.builder()
                        .setPhotoCount(AddPhotoAdapter1.MAX)
                        .setShowCamera(true)
                        .setPreviewEnabled(false)
//                        .setSelected(showPhotos)
                        .start(mContext);

                isAddUp = true;

                mFailDialog.dismiss();
            }
        });
        mFailDialog.show();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }

            if (photos != null) {

                if (isAddUp) {

                    upPhotos.clear();
                    upPhotos = photos;

                    if (mProgressDialog == null) {
                        mProgressDialog = new UpPhotoProgressDialog(mContext,
                                R.style.selectdialogstyle, R.layout.dialog_up_photo_progress);
                        mProgressDialog.setCanceledOnTouchOutside(false);
                        mProgressDialog.setCancelable(false);
                    }
                    mProgressDialog.show();

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (progress < 95) {
                                progress += 5;
                                mProgressDialog.setProgress(progress);
                                handler.postDelayed(this, 1000);
                            }
                        }
                    };
                    handler.post(runnable);
                    progress = 0;

                    getUpPhoto();
                }
            }
        }
    }



    private PermissionsUtil.PermissionGrant mPermissionGrant = new PermissionsUtil.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionsUtil.CODE_RECORD_AUDIO:
                    Toast.makeText(mContext, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionsUtil.CODE_GET_ACCOUNTS:
                    Toast.makeText(mContext, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionsUtil.CODE_READ_PHONE_STATE:
                    Toast.makeText(mContext, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionsUtil.CODE_CALL_PHONE:
                    Toast.makeText(mContext, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionsUtil.CODE_CAMERA:
                    Toast.makeText(mContext, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionsUtil.CODE_ACCESS_FINE_LOCATION:
                    Toast.makeText(mContext, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionsUtil.CODE_ACCESS_COARSE_LOCATION:
                    Toast.makeText(mContext, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionsUtil.CODE_READ_EXTERNAL_STORAGE:
                    Toast.makeText(mContext, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionsUtil.CODE_WRITE_EXTERNAL_STORAGE:
                    Toast.makeText(mContext, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
//        PermissionsUtil.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);

    }


}
