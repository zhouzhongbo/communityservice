package com.bobo.communityservice.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.communityservice.R;
import com.bobo.communityservice.adapter.SellAdapter;
import com.bobo.communityservice.databinding.PublishListBinding;
import com.bobo.communityservice.viewmodel.MyPublishViewModel;

/**
 * Created by zhouzhongbo on 2017/4/24.
 */

public class MyPublishFragment extends Fragment {


    PublishListBinding  plbinding;
    MyPublishViewModel myPublishViewModel;
    LinearLayoutManager linearLayoutManager;
    SellAdapter adapter;//sell item  与个人发布相同，只不过内容不同（内容经过过滤后只剩下个人发布的了）
    boolean isLoading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPublishViewModel = new MyPublishViewModel(this.getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_mypublish_layout,container,false);
        plbinding = DataBindingUtil.inflate(inflater,R.layout.fragment_mypublish_layout,container,false);
        return plbinding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleViewInit();
        plbinding.toPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPublishViewModel.handlerNewPublish(v);
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
    }


    public void recycleViewInit(){
        adapter = new SellAdapter(getActivity(),myPublishViewModel);
        plbinding.myPublishList.setAdapter(adapter);
        plbinding.myPublishList.addOnScrollListener(new MyPublishFragment.OnRecyclerScrollListener());

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        plbinding.myPublishList.setLayoutManager(linearLayoutManager);
    }

    public class OnRecyclerScrollListener extends RecyclerView.OnScrollListener {
        int lastVisibleItem = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (adapter != null &&
                    newState == RecyclerView.SCROLL_STATE_IDLE &&
                    lastVisibleItem + 1 == adapter.getItemCount()) {

                if (!isLoading) {
                    isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                        public void run() {
                            adapter.loadMoreRefresh();
//                            adapter.notifyDataSetChanged();
                            Log.d("test", "load more completed");
                            isLoading = false;
                        }
                    }, 1000);
                }

            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

        }
    }


    Handler handler = new Handler(){
    };
}
