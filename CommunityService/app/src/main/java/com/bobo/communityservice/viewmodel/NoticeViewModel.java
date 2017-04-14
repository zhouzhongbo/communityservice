package com.bobo.communityservice.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bobo.communityservice.activity.MoreNoticeActivity;
import com.bobo.communityservice.model.NoticeObject;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;

import java.util.List;


/**
 * Created by zzb on 2017/4/12.
 */

public class NoticeViewModel {
    public String noticeTitle;
    public String noticeContent;
    public String noticeTime;
    public String noticeWriter;
    public boolean isDataLoaded = false;
    private Context context;
    private NoticeObject noticeObject;

    public NoticeViewModel(Context mcontext){
        this.context = mcontext;
    }

    public void dataInit(){
        queryFromCloud();
    }

    public void refresh(){
        queryFromCloud();
    }

    private void fillData(NoticeObject no){
        if(no != null){
            noticeTitle = no.getNoticeTitle();
            noticeContent = no.getNoticeContent();
            noticeTime = no.getDate();
            noticeWriter = no.getNoticeWriter();
            isDataLoaded = true;
        }else{
            isDataLoaded = false;
        }
    }

    private void queryFromCloud(){
        DroiQuery query = DroiQuery.Builder.newBuilder().limit(1).query(NoticeObject.class).build();
        query.runQueryInBackground(new DroiQueryCallback<NoticeObject>() {
            @Override
            public void result(List<NoticeObject> list, DroiError droiError) {
                if(droiError.isOk()){
                    if (list.size()>0){
                        noticeObject = list.get(0);
                        fillData(noticeObject);
                    }
                }

            }
        });

    }

    public void moreClick(View v){
        Intent moreNotice = new Intent(v.getContext(), MoreNoticeActivity.class);
        moreNotice.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        v.getContext().startActivity(moreNotice);
    }
}


