package com.bobo.communityservice.viewmodel;

import android.content.Context;
import android.view.View;

import com.bobo.communityservice.model.CommunityUser;
import com.droi.sdk.core.DroiUser;

/**
 * Created by zhouzhongbo on 2017/4/20.
 */

public class MineViewModel {
    public CommunityUser user;
    public boolean isLogin;
    Context context;

    public MineViewModel(Context context){
//        user.Icon.getUri();
        this.context = context;
        init();
    }

    private void init(){
        CommunityUser user = DroiUser.getCurrentUser(CommunityUser.class);
        if (user != null && user.isAuthorized() && !user.isAnonymous()) {
            isLogin = true;
        } else {
            isLogin = false;
        }

    }


    public void handlerUserIconClick(View v){

    }

    public void handlerUserNameClick(View v){

    }

    public void handlerEditUsrInfo(View v){

    }

    public void handlerRegisterOrLogin(View v){

    }

    public void handlerCheckin(View v){
        if(isLogin){

        }else{
            handlerRegisterOrLogin(v);
        }
    }

    public void goStartActivity(View v){

    }

    public void goPublishActivity(View v){

    }

    public void goOrderActivity(View v){

    }

    public void goFeedBackActivity(View v){

    }
    public void goSetttingActivity(View v){

    }


}
