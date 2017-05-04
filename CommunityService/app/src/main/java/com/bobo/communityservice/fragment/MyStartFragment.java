package com.bobo.communityservice.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.communityservice.R;
import com.bobo.communityservice.activity.SellActivity;
import com.bobo.communityservice.adapter.GoodsListAdapter;
import com.bobo.communityservice.databinding.MyStartBinding;
import com.bobo.communityservice.model.PersionGoods;
import com.bobo.communityservice.viewmodel.MyStarViewModel;

/**
 * Created by zhouzhongbo on 2017/4/24.
 */

public class MyStartFragment extends Fragment implements GoodsListAdapter.OnItemClick{

    MyStarViewModel myStarViewModel;
    MyStartBinding startbinding;
    LinearLayoutManager linearLayoutManager;
    GoodsListAdapter adapter;
    boolean isLoading;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleViewInit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void recycleViewInit(){
        adapter = new GoodsListAdapter(getActivity(),myStarViewModel);
        startbinding.myStartList.setAdapter(adapter);
        adapter.setClickListeren(this);
        startbinding.myStartList.addOnScrollListener(new MyStartFragment.OnRecyclerScrollListener());

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        startbinding.myStartList.setLayoutManager(linearLayoutManager);
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
                            isLoading = false;
                        }
                    }, 3000);
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

    @Override
    public void onItemClick(View v, int position, PersionGoods goods) {
        Log.d("zzb","item clicked "+position);
        Intent mintent = new Intent(getActivity(),SellActivity.class);
        mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.d("zzb","goods img size ="+goods.toString());
        mintent.putExtra("Goods",goods);
        startActivity(mintent);
    }
}
