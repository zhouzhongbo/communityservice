package com.bobo.communityservice.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.communityservice.R;
import com.bobo.communityservice.databinding.NoticeBinding;
import com.bobo.communityservice.model.NoticeObject;
import com.bobo.communityservice.viewmodel.NoticeViewModel;

import java.util.List;

/**
 * Created by zhouzhongbo on 2017/3/28.
 */

public class NoticeFragement extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int REFRESH_COMPLETE = 0X1111;
    private static final int DATA_INIT= 0X1112;

    private SwipeRefreshLayout mSwipeLayout;
    NoticeViewModel mNoticeViewModel;
    List<NoticeObject> noticeList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNoticeViewModel = new NoticeViewModel(getActivity().getApplication());
        mNoticeViewModel.dataInit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NoticeBinding  nb = DataBindingUtil.inflate(inflater,R.layout.notice_layout,container,false);
        nb.setNoticeViewModel(mNoticeViewModel);

        return nb.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.id_swipe_notice);
        mSwipeLayout.setOnRefreshListener(this);
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

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case REFRESH_COMPLETE:
                    mNoticeViewModel.refresh();
                    mSwipeLayout.setRefreshing(false);
                    break;

                case DATA_INIT:
                    mNoticeViewModel.refresh();
                    break;

            }
        };
    };


    private void bindView(){

    }
}
