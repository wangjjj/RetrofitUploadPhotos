package com.cocoon.jay.retrofituploadphotos.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cocoon.jay.retrofituploadphotos.R;
import com.cocoon.jay.retrofituploadphotos.retrofitutils.ApiService;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.utils.AndroidLifecycleUtils;

public class AddPhotoAdapter2 extends RecyclerView.Adapter<AddPhotoAdapter2.PhotoViewHolder> {

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

  public final static int MAX = 10;

  public void setPhotoPaths(ArrayList<String> photoLists) {
    photoPaths.clear();
    for(int i = 0 ; i < photoLists.size(); i++){
      photoPaths.add(ApiService.Base_URL + photoLists.get(i));
    }

  }

  public AddPhotoAdapter2(Activity mContext) {
    this.mContext = mContext;
    inflater = LayoutInflater.from(mContext);
  }


  @Override public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = inflater.inflate(R.layout.item_photo_add, parent, false);
    return new PhotoViewHolder(itemView);
  }


  @Override
  public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

    final int posi = position - 1;



    if(position == 0){
      holder.tvPhoto.setVisibility(View.VISIBLE);
      holder.ivDelete.setVisibility(View.INVISIBLE);
      holder.ivPhoto.setImageResource(R.mipmap.yapin_add);

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

    }else  {


      holder.tvPhoto.setVisibility(View.INVISIBLE);
      holder.ivDelete.setVisibility(View.VISIBLE);


      holder.ivDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

          if(mListener != null){
            mListener.onClickDelete(posi);
          }


        }
      });

      holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          PhotoPreview.builder()
                  .setPhotos(photoPaths)
                  .setCurrentItem(posi)
                  .setShowDeleteButton(false)
                  .start(mContext);

          if(mListener != null){
            mListener.onClickPhoto();
          }
        }
      });


      boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(holder.ivPhoto.getContext());
      if (canLoadImage) {

        String url = photoPaths.get(posi);
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .thumbnail(0.1f)
                .placeholder(R.mipmap.pic_loading)
                .error(R.mipmap.pic_loadfail)
                .into(holder.ivPhoto);
//        }

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




  public static class PhotoViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivPhoto;
    private ImageView ivDelete;
    private TextView tvPhoto;
    public PhotoViewHolder(View itemView) {
      super(itemView);
      ivPhoto   = (ImageView) itemView.findViewById(R.id.iv_photo);
      tvPhoto = (TextView)itemView.findViewById(R.id.tv_photo);
      ivDelete   = (ImageView) itemView.findViewById(R.id.iv_delete);
    }
  }

}
