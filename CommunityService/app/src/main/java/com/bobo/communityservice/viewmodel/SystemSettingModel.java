package com.bobo.communityservice.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bobo.communityservice.R;
import com.bobo.communityservice.activity.EditUserInfoActivity;
import com.bobo.communityservice.activity.LoginActivity;
import com.bobo.communityservice.model.CommunityUser;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.selfupdate.DroiUpdate;

/**
 * Created by zzb on 2017/5/1.
 */

public class SystemSettingModel {

    Context context;
    CommunityUser user;

    public SystemSettingModel(Context context){
        this.context = context;
        user = DroiUser.getCurrentUser(CommunityUser.class);
    }

    public void handlerEditUserInfo(View view){
        Intent mintent = new Intent(context, EditUserInfoActivity.class);
        mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mintent);
    }

    public void handlerAccountBinding(View view){
        Log.d("zzb","binding account!");
    }

    public void handlerClearCache(View view){
        Toast.makeText(context,R.string.clear_cache_toast,Toast.LENGTH_SHORT).show();
    }

    public void handlerAboutUs(View view){
        Intent mintent = new Intent(context, LoginActivity.class);
        mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mintent);
    }

    public void handlerCheckVersion(View view){
        DroiUpdate.manualUpdate(context);
    }

    public void handlerLoginOut(View view){
        if (user != null && user.isAuthorized()) {
            user.logout();
        }
    }
}
