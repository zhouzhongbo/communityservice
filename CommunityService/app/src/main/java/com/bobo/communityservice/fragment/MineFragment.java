package com.bobo.communityservice.fragment;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.communityservice.R;
import com.bobo.communityservice.databinding.MineBinding;
import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.viewmodel.MineViewModel;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.feedback.DroiFeedback;

/**
 * Created by zhouzhongbo on 2017/3/28.
 */

public class MineFragment extends Fragment {
    MineViewModel minemodel;
    MineBinding mineBinding;
    CommunityUser communitUser;
    boolean isLogin;

    public static MineFragment newInstance(String param1) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public MineFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        communitUser = DroiUser.getCurrentUser(CommunityUser.class);
        minemodel = new MineViewModel(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mineBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_mine_layout,container,false);
        return mineBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void onClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.user_icon:
                break;
            case R.id.user_name:
            case R.id.edit_user_info:
                break;

            case R.id.user_register:
            case R.id.login_on:
                break;

            case R.id.checkin_button:
                break;

            case R.id.star_icon:
            case R.id.my_star_text:
                break;

            case R.id.publish_icon:
            case R.id.my_public_text:
                break;

            case R.id.order_icon:
            case R.id.my_order_text:
                break;

            case R.id.feed_back_text:
            case R.id.feedback_icon:
                //打开反馈页面
                DroiFeedback.callFeedback(getActivity());
                break;

            case R.id.settings_icon:
            case R.id.settings_text:
                break;
        }
    }

    private void initView(){
        if(isLogin){
            mineBinding.registerOrLoging.setVisibility(View.GONE);
            mineBinding.loginStatusView.setVisibility(View.VISIBLE);
        }else{
            mineBinding.registerOrLoging.setVisibility(View.VISIBLE);
            mineBinding.loginStatusView.setVisibility(View.GONE);
        }

    }




}
