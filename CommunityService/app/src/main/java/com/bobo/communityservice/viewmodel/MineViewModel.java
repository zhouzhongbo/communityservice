package com.bobo.communityservice.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bobo.communityservice.activity.MineRelativeActivity;
import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.tools.ConstantObject;
import com.droi.sdk.core.DroiUser;

/**
 * Created by zhouzhongbo on 2017/4/20.
 */

public class MineViewModel {
    public CommunityUser user;
    private boolean isLogin;
    Context context;

    public MineViewModel(Context context){
//        user.Icon.getUri();
        this.context = context;
        isLogin = isLogin();
    }

    private boolean isLogin(){
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

        }else{
            handlerRegisterOrLogin(v);
        }
    }

    public void handlerUserNameClick(View v){
        if(isLogin){

        }else{
            handlerRegisterOrLogin(v);
        }

    }

    public void handlerEditUsrInfo(View v){
        if(isLogin){

        }else{
            handlerRegisterOrLogin(v);
        }

    }

    public void handlerRegisterOrLogin(View v){
        if(isLogin){

        }else{
            handlerRegisterOrLogin(v);
        }

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


//    public void refreshUi(MineBinding minebinding){
//        isLogin = isLogin();
//        if(isLogin){
//
//        }else{
//
//        }
//
//        if (user != null && user.isAuthorized() && !user.isAnonymous()) {
////            nameTextView.setText(user.getUserId());
////            if (user.avatar != null) {
////                user.avatar.getInBackground(new DroiCallback<byte[]>() {
////                    @Override
////                    public void result(byte[] bytes, DroiError error) {
////                        if (error.isOk()) {
////                            if (bytes == null) {
////                                Log.i(TAG, "bytes == null");
////                            } else {
////                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
////                                titleImg.setImageBitmap(bitmap);
////                            }
////                        }
////                    }
////                }, null);
////            }
////        } else {
////            titleImg.setImageResource(R.drawable.profile_default_icon);
////            nameTextView.setText(R.string.fragment_mine_login);
////        }
//        }
//    }

}