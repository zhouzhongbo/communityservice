package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zzb on 2017/4/5.
 * used to store publish notice
 */

public class NoticeObject extends DroiObject {
    @DroiExpose
    String noticeTitle;

    @DroiExpose
    String noticeContent;

    @DroiExpose
    Date noticeTime;

    @DroiExpose
    String noticeWriter;


    public String getDate(){
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        return sdformat.format(noticeTime);
    }

    public String getNoticeTitle(){
        return noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public String getNoticeWriter() {
        return noticeWriter;
    }

}
