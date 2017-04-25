package com.bobo.communityservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bobo.communityservice.R;

/**
 * Created by zhouzhongbo on 2017/4/25.
 */

public class PublishGalleryAddViewHolder extends RecyclerView.ViewHolder{
    public ImageView  image;

    public PublishGalleryAddViewHolder(View view){
        super(view);
        image = (ImageView) itemView.findViewById(R.id.img_des);
    }


    public void fillData(){

    }
}
