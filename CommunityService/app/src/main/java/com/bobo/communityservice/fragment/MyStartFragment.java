package com.bobo.communityservice.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.communityservice.R;
import com.bobo.communityservice.databinding.MyStartBinding;
import com.bobo.communityservice.viewmodel.MyStarViewModel;

/**
 * Created by zhouzhongbo on 2017/4/24.
 */

public class MyStartFragment extends Fragment {

    MyStarViewModel myStarViewModel;
    MyStartBinding startbinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myStarViewModel = new MyStarViewModel(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        startbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mystart_layout,container,false);
        return startbinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
