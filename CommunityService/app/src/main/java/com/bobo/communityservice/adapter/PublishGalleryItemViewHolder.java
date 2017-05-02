package com.bobo.communityservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bobo.communityservice.R;

/**
 * Created by zhouzhongbo on 2017/4/25.
 */

public class PublishGalleryItemViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public ImageView remove;
    public ProgressBar pb;

    public PublishGalleryItemViewHolder(View view) {
        super(view);
        image = (ImageView) itemView.findViewById(R.id.img_des);
        remove = (ImageView) itemView.findViewById(R.id.remove_button);
        pb = (ProgressBar)itemView.findViewById(R.id.update_progress);
    }
}