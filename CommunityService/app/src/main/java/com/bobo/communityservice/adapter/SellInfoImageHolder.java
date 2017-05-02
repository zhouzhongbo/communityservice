package com.bobo.communityservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bobo.communityservice.R;
import com.bumptech.glide.Glide;
import com.droi.sdk.core.DroiFile;

/**
 * Created by zzb on 2017/5/1.
 */

public class SellInfoImageHolder extends RecyclerView.ViewHolder{

    ImageView img;
    public SellInfoImageHolder(View itemView){
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.item_img);
    }

    public void fillData(Context context, DroiFile icon){
        Glide.with(context).load(icon.getUri()).into(img);
    }
}
