package com.bobo.communityservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bobo.communityservice.R;

/**
 * Created by zzb on 2017/5/1.
 */

public class SellInfoDivderHolder extends RecyclerView.ViewHolder{

    TextView text;
    public SellInfoDivderHolder(View itemView){
        super(itemView);
        text = (TextView)itemView.findViewById(R.id.like_num);
    }

    public void fillData(Context context, int count){
        String like_format = context.getResources().getString(R.string.item_like_count);
        String like_value = String.format(like_format, count);
        text.setText(like_value);
    }
}
