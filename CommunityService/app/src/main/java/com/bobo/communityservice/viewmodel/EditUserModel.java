package com.bobo.communityservice.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bobo.communityservice.R;
import com.bobo.communityservice.databinding.UserEditBinding;
import com.bobo.communityservice.model.CommunityUser;
import com.bumptech.glide.Glide;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;

/**
 * Created by zhouzhongbo on 2017/4/28.
 */

public class EditUserModel {

    UserEditBinding binding;
    Context context;
    PopupWindow mPopupWindow;
    EditText editbox;
    String editbox_context;

    public static CommunityUser user;

    public static final int USER_NAME_EDIT = 1;
    public static final int USER_ADDRESS_EDIT = 2;

    public EditUserModel(Context context, UserEditBinding binding){
        this.context = context;
        this.binding = binding;
        initView();
    }

    public void initView(){
        user = DroiUser.getCurrentUser(CommunityUser.class);
        if (user != null && user.isAuthorized() && !user.isAnonymous()) {
            if(user.getIcon()!= null && user.getIcon().hasUri()){
                Glide.with(context).load(user.getIcon().getUri()).into(binding.userIcon);
            }
        }
        if(user.getNickname()!= null){
            binding.userName.setText(user.getNickname());
        }else{
            binding.userName.setText(user.getUserId());
        }

        if (user.getSex()!= null && user.getSex()){
            binding.userSex.setText(R.string.male);
        }else{
            binding.userSex.setText(R.string.female);
        }

        if(user.getAddress()!= null){
            binding.userAddress.setText(user.getAddress());
        }
    }

    public void showPopwin(final int editContextType){
        View popupView = ((Activity)context).getLayoutInflater().inflate(R.layout.popupwin_edit_userinfo, null);
        popupView.findViewById(R.id.cancle_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        popupView.findViewById(R.id.sure_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editbox_context!= null&&!editbox_context.equals("")){
                    if(editContextType == USER_ADDRESS_EDIT){
                        user.setAddress(editbox_context);
                        binding.userAddress.setText(editbox_context);
                    }else if(editContextType == USER_NAME_EDIT){
                        user.setNickname(editbox_context);
                        binding.userName.setText(editbox_context);
                    }
                    user.saveInBackground(new DroiCallback<Boolean>() {
                        @Override
                        public void result(Boolean aBoolean, DroiError droiError) {
                            if(droiError.isOk()){
                                Log.d("zzb","user info is save ok !");
                            }else{
                                Log.d("zzb","user info save failed !");
                            }
                        }
                    });
                    mPopupWindow.dismiss();
                }else{
                    Toast.makeText(context,"输入内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        editbox = (EditText)popupView.findViewById(R.id.userinfo_input);
        editbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editbox_context = editbox.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        switch (editContextType){
            case USER_ADDRESS_EDIT:
                editbox.setHint(R.string.hint_address);
                if(user.getAddress()!= null){
                    editbox.setText(user.getAddress());
                }
                break;
            case USER_NAME_EDIT:
                editbox.setHint(R.string.hint_nickname);
                if(user.getNickname()!= null){
                    editbox.setText(user.getNickname());
                }else{
                    editbox.setText(user.getUserId());
                }
                break;
            default:
                break;
        }

        mPopupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setContentView(popupView);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        View rootview = LayoutInflater.from(context).inflate(R.layout.activity_edituser_layout, null);

        mPopupWindow.showAtLocation(rootview, Gravity.CENTER,0,0);
    }

    public void handlerUserNameEdit(){
        showPopwin(USER_NAME_EDIT);
    }

    public void handlerUserAddressEdit(){
        showPopwin(USER_ADDRESS_EDIT);
    }
}
