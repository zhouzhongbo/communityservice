package com.bobo.communityservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bobo.communityservice.R;
import com.bobo.communityservice.model.NoticeObject;

/**
 * Created by zhouzhongbo on 2017/4/18.
 */

public class NoticeViewHolder extends RecyclerView.ViewHolder{
    TextView  title;
    TextView content;
    TextView  writer;
    TextView  time;

    public NoticeViewHolder(View itemView) {
        super(itemView);
        title = (TextView)itemView.findViewById(R.id.notice_title);
        content = (TextView)itemView.findViewById(R.id.notice_content);
        writer = (TextView)itemView.findViewById(R.id.notice_write);
        time = (TextView)itemView.findViewById(R.id.notice_time);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void fillData(NoticeObject item){
        title.setText(item.getNoticeTitle());
        content.setText(item.getNoticeContent());
        writer.setText(item.getNoticeWriter());
        time.setText(item.getDate());
    }
}
