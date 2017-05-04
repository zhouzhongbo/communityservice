package com.bobo.communityservice.tools;

import android.content.Context;
import android.widget.Toast;

import com.bobo.communityservice.model.NoticeObject;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiObject;

/**
 * Created by zhouzhongbo on 2017/4/14.
 */

public class GeneralData {

    Context context;
    public GeneralData(Context mcontext){
        DroiObject.registerCustomClass(NoticeObject.class);
        this.context = mcontext;
    }


    public void generateNotice(){
        NoticeObject nb = new NoticeObject();
        nb.setNoticeTitle("小区通知");
        nb.setNoticeContent("最近天气不好，请小区住户离家时注意关好窗户，防止雨打进家里。出门在外注意携带雨伞！");
        nb.setNoticeWriter("小区物业");
        nb.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if(aBoolean){
                    Toast.makeText(context,"数据上传成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"数据上传失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
