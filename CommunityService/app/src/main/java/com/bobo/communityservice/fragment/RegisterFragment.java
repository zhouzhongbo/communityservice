package com.bobo.communityservice.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.communityservice.R;
import com.bobo.communityservice.databinding.RegisterBinding;

/**
 * Created by zhouzhongbo on 2017/4/20.
 */

public class RegisterFragment extends Fragment {
    RegisterBinding registerBind;
    private ProgressDialog mProgressView;
    private Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        registerBind = DataBindingUtil.inflate(inflater,R.layout.fragment_register_layout,container,false);
        mProgressView = new ProgressDialog(getActivity());
        return registerBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public void onRegisterClick(View view){

    }

    public void RegisterBind(View view){

    }

}
