package com.cocoon.jay.retrofituploadphotos.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cocoon.jay.retrofituploadphotos.R;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.utils.AndroidLifecycleUtils;

public class AddPhotoAdapter1 extends RecyclerView.Adapter<AddPhotoAdapter1.PhotoViewHolder> {

    private ArrayList<String> photoPaths = new ArrayList<String>();
    private LayoutInflater inflater;
    private Activity mContext;

    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onClickAdd();
        void onClickPhoto();
        void onClickDelete(int position);
    }

    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public final static int TYPE_ADD = 1;
    public final static int TYPE_PHOTO = 2;

    public final static int MAX = 10;


    public void setPhotoPaths(ArrayList<String> photoPaths) {
        this.photoPaths = photoPaths;
    }

    public AddPhotoAdapter1(Activity mContext, ArrayList<String> photoPaths) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);

    }


    @Override public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_photo_add1, parent, false);
        return new PhotoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

        if (getItemViewType(position) == TYPE_ADD) {
            holder.ivDelete.setVisibility(View.GONE);

            holder.ivPhoto.setImageResource(R.mipmap.busi_upphoto);

            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotoPicker.builder()
                            .setPhotoCount(MAX)
                            .setShowCamera(true)
                            .setPreviewEnabled(false)
//                            .setSelected(photoPaths)
                            .start(mContext);

                    if(mListener != null){
                        mListener.onClickAdd();
                    }
                }
            });


        }else if (getItemViewType(position) == TYPE_PHOTO) {
            holder.ivDelete.setVisibility(View.VISIBLE);
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(mListener != null){
                        mListener.onClickDelete(position);
                    }

                }
            });

            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotoPreview.builder()
                            .setPhotos(photoPaths)
                            .setCurrentItem(position)
                            .setShowDeleteButton(false)
                            .start(mContext);

                    if(mListener != null){
                        mListener.onClickPhoto();
                    }
                }
            });


            boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(holder.ivPhoto.getContext());
            if (canLoadImage) {

                String url =  photoPaths.get(position);
                Glide.with(mContext)
                        .load(url)
                        .centerCrop()
                        .thumbnail(0.1f)
                        .placeholder(R.mipmap.pic_loading)
                        .error(R.mipmap.pic_loadfail)
                        .into(holder.ivPhoto);
//                }

            }
        }

    }







    @Override public int getItemCount() {
        int count = photoPaths.size() + 1;
        if (count > MAX) {
            count = MAX;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == photoPaths.size() && position != MAX) ? TYPE_ADD : TYPE_PHOTO;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private ImageView ivDelete;
        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto   = (ImageView) itemView.findViewById(R.id.iv_photo);
            ivDelete   = (ImageView) itemView.findViewById(R.id.iv_delete);
        }
    }

}
