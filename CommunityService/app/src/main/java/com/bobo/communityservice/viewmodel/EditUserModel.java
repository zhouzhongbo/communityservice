package com.bobo.communityservice.viewmodel;

import android.content.Context;

import com.bobo.communityservice.databinding.MineBinding;
import com.bobo.communityservice.model.CommunityUser;
import com.jph.takephoto.app.TakePhoto;

/**
 * Created by zhouzhongbo on 2017/4/28.
 */

public class EditUserModel {
    public CommunityUser user;
    private boolean isLogin;
    Context context;
    MineBinding mineBinding;
    private TakePhoto takePhoto;


    public EditUserModel(Context context){

    }

    public void setUser(CommunityUser user) {
        this.user = user;

    }
}
