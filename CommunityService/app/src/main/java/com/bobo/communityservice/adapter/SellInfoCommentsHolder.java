package com.bobo.communityservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bobo.communityservice.R;
import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.model.PersionGoodsComment;
import com.bobo.communityservice.view.CircleImageView;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;

/**
 * Created by zzb on 2017/5/1.
 */

public class SellInfoCommentsHolder extends RecyclerView.ViewHolder{
    CircleImageView userIcon;
    TextView userName;
    TextView commentTime;
    TextView comments;

    public SellInfoCommentsHolder(View itemView){
        super(itemView);
        userIcon = (CircleImageView)itemView.findViewById(R.id.user_icon);
        userName = (TextView)itemView.findViewById(R.id.user_name);
        commentTime = (TextView)itemView.findViewById(R.id.comment_time);
        comments = (TextView)itemView.findViewById(R.id.comments_text);
    }

    public void fillData(Context context, PersionGoodsComment mComments){
        if(mComments == null){
            Log.d("zzb","comments object is null!");
            return;
        }
        CommunityUser from = mComments.getCommentsFrom();
        if(from!= null&&from.getIcon()!= null&&from.getIcon().hasUri()){
            Glide.with(context).load(from.getIcon().getUri()).into(userIcon);
        }else{
            Log.d("zzb","no icon");
        }
        if(from !=null&& from.getNickname()!= null){
            userName.setText(from.getNickname());
        }else{
            userName.setText(from.getUserId());
        }
        if(mComments.getCreationTime()!= null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String time=format.format(mComments.getCreationTime());
            commentTime.setText(time);
        }else{
            Log.d("zzb","no create time");
        }

        String comment = mComments.getCommnetsBody();
        if(comment != null){
            comments.setText(comment);
        }else{
            Log.d("zzb","no comments");
        }
    }
}
