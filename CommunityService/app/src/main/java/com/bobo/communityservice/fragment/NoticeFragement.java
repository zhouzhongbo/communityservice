package com.bobo.communityservice.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.app.Fragment;
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

    NoticeBinding  nb;
    NoticeViewModel mNoticeViewModel;
    List<NoticeObject> noticeList;


    public static NoticeFragement newInstance(String param1) {
        NoticeFragement fragment = new NoticeFragement();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public NoticeFragement() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNoticeViewModel = new NoticeViewModel(getActivity().getApplication());
        mNoticeViewModel.dataInit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nb = DataBindingUtil.inflate(inflater,R.layout.fragment_notice_layout,container,false);
        nb.setNoticeViewModel(mNoticeViewModel);
        nb.idSwipeNotice.setOnRefreshListener(this);
        return nb.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mSwipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.id_swipe_notice);
//        mSwipeLayout.setOnRefreshListener(this);
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
                    nb.idSwipeNotice.setRefreshing(false);
                    break;

                case DATA_INIT:
                    mNoticeViewModel.refresh();
                    break;

            }
        };
    };

    public void onClick(View view){

    }
}
