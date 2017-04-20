package com.bobo.communityservice.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.bobo.communityservice.activity.MoreNoticeActivity;
import com.bobo.communityservice.model.NoticeObject;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;

import java.util.List;


/**
 * Created by zzb on 2017/4/12.
 */

public class NoticeViewModel{
    public final ObservableField<String> noticeTitle = new ObservableField<>();
    public final ObservableField<String> noticeContent = new ObservableField<>();
    public final ObservableField<String> noticeTime = new ObservableField<>();
    public final ObservableField<String> noticeWriter = new ObservableField<>();

    public boolean isDataLoaded = false;
    private Context context;
    private NoticeObject noticeObject;

    public NoticeViewModel(Context mcontext){
        this.context = mcontext;
    }

    public void dataInit(){
        queryFromCloud();
        this.noticeTitle.set("test");
        this.noticeContent.set("test_content");
        this.noticeWriter.set("test_write");
        this.noticeTime.set("2017-2-28");
    }

    public void refresh(){
        queryFromCloud();
    }

    private void fillData(NoticeObject no){
        if(no != null){
            this.noticeTitle.set(no.getNoticeTitle());
            this.noticeContent.set(no.getNoticeContent());
            this.noticeWriter.set(no.getNoticeWriter());
            this.noticeTime.set(no.getDate());
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
                    Log.d("zzb","query success! listsize ="+list.size());

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


