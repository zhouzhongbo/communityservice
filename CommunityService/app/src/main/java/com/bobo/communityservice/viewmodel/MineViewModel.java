package com.bobo.communityservice.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.bobo.communityservice.R;
import com.bobo.communityservice.activity.EditUserInfoActivity;
import com.bobo.communityservice.activity.LoginActivity;
import com.bobo.communityservice.activity.MineRelativeActivity;
import com.bobo.communityservice.activity.SystemSettingActivity;
import com.bobo.communityservice.databinding.MineBinding;
import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.tools.ConstantObject;
import com.bumptech.glide.Glide;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.feedback.DroiFeedback;
import com.jph.takephoto.model.TImage;

import java.io.File;

/**
 * Created by zhouzhongbo on 2017/4/20.
 */

public class MineViewModel {
    public CommunityUser user;
    private boolean isLogin;
    Context context;
    MineBinding mineBinding;


    public MineViewModel(Context context,MineBinding minebinding){
        this.context = context;
        isLogin = isLogin();
        mineBinding = minebinding;
    }

    public boolean isLogin(){
        boolean islogin;
        user = DroiUser.getCurrentUser(CommunityUser.class);
        if (user != null && user.isAuthorized() && !user.isAnonymous()) {
            islogin = true;
        } else {
            islogin = false;
        }
        return islogin;
    }

    public void handlerUserIconClick(View v){
        if(isLogin){
            handlerEditUsrInfo(v);
        }else{
            handlerRegisterOrLogin(v);
        }
    }

    public void handlerUserNameClick(View v){
        if(isLogin){
            handlerEditUsrInfo(v);
        }else{
            handlerRegisterOrLogin(v);
        }

    }

    public void handlerEditUsrInfo(View v){
        if(isLogin){
            Intent mintent = new Intent(context, EditUserInfoActivity.class);
            mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mintent);
        }else{
            handlerRegisterOrLogin(v);
        }
    }

    public void handlerRegisterOrLogin(View v){
        Intent mintent = new Intent(context, LoginActivity.class);
        mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mintent);
    }

    public void handlerCheckin(View v){
        if(isLogin){

        }else{
            handlerRegisterOrLogin(v);
        }
    }

    public void handlerMyStartClick(View v){
        if(isLogin){
            Intent mintent = new Intent(v.getContext(), MineRelativeActivity.class);
            mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mintent.putExtra("TAG", ConstantObject.START);
            context.startActivity(mintent);
        }else{
            handlerRegisterOrLogin(v);
        }
    }

    public void handlerMyPublishClick(View v){
        if(isLogin){
            Intent mintent = new Intent(v.getContext(), MineRelativeActivity.class);
            mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mintent.putExtra("TAG", ConstantObject.PUBLISH);
            context.startActivity(mintent);
        }else{
            handlerRegisterOrLogin(v);
        }
    }

    public void handlerMyOrderClick(View v){
        if(isLogin){
            Intent mintent = new Intent(v.getContext(), MineRelativeActivity.class);
            mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mintent.putExtra("TAG", ConstantObject.ORDER);
            context.startActivity(mintent);
        }else{
            handlerRegisterOrLogin(v);
        }
    }

    public void handlerMySelledClick(View v){
        if(isLogin){
            Intent mintent = new Intent(v.getContext(), MineRelativeActivity.class);
            mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mintent.putExtra("TAG", ConstantObject.SELLED);
            context.startActivity(mintent);
        }else{
            handlerRegisterOrLogin(v);
        }
    }

    public void handlerFeedBackClick(View v){
        if(isLogin){
            //打开反馈页面
            DroiFeedback.callFeedback((Activity)context);
        }else{
            handlerRegisterOrLogin(v);
        }
    }
    public void handlerSystemSetttingClick(View v){
        if(isLogin){
            Intent setting = new Intent(context, SystemSettingActivity.class);
            setting.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(setting);
        }else{
            handlerRegisterOrLogin(v);
        }
    }


    public void refreshUi(){
        isLogin = isLogin();
        if(isLogin){
            mineBinding.loginStatusView.setVisibility(View.VISIBLE);
            mineBinding.registerOrLoging.setVisibility(View.GONE);
            String name = user.getNickname();
            if(name != null&&!name.equals("")){
                Log.d("zzb","11name ="+name);
                mineBinding.userName.setText(name);
            }else{
                Log.d("zzb","22name ="+user.getUserId());
                mineBinding.userName.setText(user.getUserId());
            }
            DroiFile icon = user.getIcon();
            if(icon != null && icon.hasUri()){
                Glide.with(context).load(icon.getUri()).into(mineBinding.usrIcon);
            }else{
                Glide.with(context).load(R.drawable.default_icon).into(mineBinding.usrIcon);
            }
        }else{
            mineBinding.loginStatusView.setVisibility(View.GONE);
            mineBinding.registerOrLoging.setVisibility(View.VISIBLE);
            Glide.with(context).load(R.drawable.default_icon).into(mineBinding.usrIcon);
        }
    }

    public void setUserIcon(final TImage imageFile){
        String imagePath = imageFile.getOriginalPath();
        final DroiFile droifile = new DroiFile(new File(imagePath));
        droifile.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if(droiError.isOk()){
                    user.setIcon(droifile);
                    user.saveInBackground(new DroiCallback<Boolean>() {
                        @Override
                        public void result(Boolean aBoolean, DroiError droiError) {
                            if(droiError.isOk()){
                                Log.d("zzb","update icon success");
                                mineBinding.executePendingBindings();
                            }
                        }
                    });
                }
            }
        });
    }
}
