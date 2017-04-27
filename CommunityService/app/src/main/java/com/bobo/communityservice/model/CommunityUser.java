package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiReference;
import com.droi.sdk.core.DroiUser;

import java.util.ArrayList;

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

    @DroiReference
    public ArrayList<String> address;


    public void setIcon(DroiFile icon){
        this.Icon = icon;
    }

    public void checkin(){
        checkin++;

    }
}
