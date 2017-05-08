package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiReference;
import com.droi.sdk.core.DroiUser;

/**
 * Created by zzb on 2017/4/5.
 */

public class CommunityUser extends DroiUser {

    @DroiExpose
    public String nickname;

    @DroiExpose
    public Boolean Sex;

    @DroiExpose
    public DroiFile Icon;

    @DroiExpose
    public int checkin;

    @DroiExpose
    public String address;


    public void setIcon(DroiFile icon){
        this.Icon = icon;
    }

    public void checkin(){
        checkin++;

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getSex() {
        return Sex;
    }

    public void setSex(Boolean sex) {
        Sex = sex;
    }

    public DroiFile getIcon() {
        return Icon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
