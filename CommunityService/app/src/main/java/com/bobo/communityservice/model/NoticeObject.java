package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;

import java.text.SimpleDateFormat;

/**
 * Created by zzb on 2017/4/5.
 * used to store publish notice
 */

public class NoticeObject extends DroiObject {
    @DroiExpose
    String noticeTitle;

    @DroiExpose
    String noticeContent;

//    @DroiExpose
//    Date noticeTime;

    @DroiExpose
    String noticeWriter;


    public NoticeObject(){

    }

    public NoticeObject(String title, String content,String writer){
        this.noticeTitle = title;
        this.noticeContent = content;
        this.noticeWriter = writer;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

//    public void setNoticeTime(Date noticeTime) {
//        this.noticeTime = noticeTime;
//    }

    public void setNoticeWriter(String noticeWriter) {
        this.noticeWriter = noticeWriter;
    }

    public String getDate(){
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        return sdformat.format(this.getCreationTime());
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
