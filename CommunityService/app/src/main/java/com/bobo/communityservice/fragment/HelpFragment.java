package com.bobo.communityservice.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.communityservice.R;
import com.bobo.communityservice.activity.SellActivity;
import com.bobo.communityservice.adapter.BannerAdapter;
import com.bobo.communityservice.adapter.SellAdapter;
import com.bobo.communityservice.databinding.HelpBinding;
import com.bobo.communityservice.model.PersionGoods;
import com.bobo.communityservice.viewmodel.HelpViewModel;

import java.util.ArrayList;

/**
 * Created by zhouzhongbo on 2017/3/28.
 */

public class HelpFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,SellAdapter.OnItemClick{
    HelpBinding hb;
    HelpViewModel mhelpViewModel;
    ArrayList<Integer> banners;
    LinearLayoutManager linearLayoutManager;
    SellAdapter adapter;
    BannerAdapter mBannerAdapter;

    public static HelpFragment newInstance(String param1) {
        HelpFragment fragment = new HelpFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public HelpFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mhelpViewModel = new HelpViewModel(getActivity().getApplication());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hb = DataBindingUtil.inflate(inflater,R.layout.fragment_helpsell_layout,container,false);
        hb.setHelpViewModel(mhelpViewModel);
        hb.idSwipeSells.setOnRefreshListener(this);
        return hb.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bannerViewinit();
        recycleViewInit();
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

    public void bannerViewinit(){
        banners = mhelpViewModel.getBanner();
        hb.bannerLayout.headIndicatorLayout.setCount(banners.size());
        hb.bannerLayout.headIndicatorLayout.select(0);
        mBannerAdapter = new BannerAdapter(getActivity(), banners);
        hb.bannerLayout.headViewPager.setAdapter(mBannerAdapter);
        hb.bannerLayout.headViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                hb.bannerLayout.headIndicatorLayout.select(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        final GestureDetector tapGestureDetector = new GestureDetector(getActivity(), new TapGestureListener());
        hb.bannerLayout.headViewPager.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                tapGestureDetector.onTouchEvent(event);
                return false;
            }
        });
    }

    public void recycleViewInit(){
        adapter = new SellAdapter(getActivity(),mhelpViewModel);
        adapter.setClickListeren(this);
        hb.recyclerList.setAdapter(adapter);
        hb.recyclerList.addOnScrollListener(new OnRecyclerScrollListener());

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hb.recyclerList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(111, 1000);
    }


    /**
     * 设置Banner点击事件
     */
    class TapGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            ArrayList<String> tags = mhelpViewModel.getTags();

            String tag = tags.get(hb.bannerLayout.headViewPager.getCurrentItem());
            Intent intent;
            if (tag != null && tag !="") {
//                intent = new Intent(getActivity(), SellActivity.class);
//                intent.putExtra("TAG", tag);
//                startActivity(intent);
                Log.d("zzb","item clicked!"+tag);
            }
            return true;
        }
    }

    boolean isLoading;

    public class OnRecyclerScrollListener extends RecyclerView.OnScrollListener {
        int lastVisibleItem = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (adapter != null &&
                    newState == RecyclerView.SCROLL_STATE_IDLE &&
                    lastVisibleItem + 1 == adapter.getItemCount()) {

                //滚动到底部了，可以进行数据加载等操作
                boolean isRefreshing = hb.idSwipeSells.isRefreshing();
                if (isRefreshing) {
                    adapter.notifyItemRemoved(adapter.getItemCount());
                    return;
                }
                if (!isLoading) {
                    isLoading = true;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter.loadMoreRefresh();
                            adapter.notifyDataSetChanged();
                            hb.idSwipeSells.setRefreshing(false);
//                            adapter.notifyItemRemoved(adapter.getItemCount());
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

    @Override
    public void onItemClick(View v, int position, PersionGoods goods) {
        Log.d("zzb","item clicked "+position);
        Intent mintent = new Intent(getActivity(),SellActivity.class);
        mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Log.d("zzb","goods img size ="+goods.goodsImg.size());
        Log.d("zzb","goods img size ="+goods.toString());
        mintent.putExtra("Goods",goods);
        startActivity(mintent);
    }

    Handler handler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            int what = msg.what;
            if(what == 111){
                adapter.pullRefresh();
                hb.idSwipeSells.setRefreshing(false);
            }
        }
    };
}
