package com.bobo.communityservice.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bobo.communityservice.R;
import com.bobo.communityservice.adapter.NoticeListAdapter;

/**
 * Created by zzb on 2017/4/14.
 */

public class MoreNoticeActivity extends AppCompatActivity {

    NoticeListAdapter adapter;
    RecyclerView  rcView;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_notice_layout);
        rcView = (RecyclerView)findViewById(R.id.notice_list);
        recycleViewInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void recycleViewInit(){
        adapter = new NoticeListAdapter(this);
        rcView.setAdapter(adapter);
        rcView.addOnScrollListener(new OnRecyclerScrollListener());

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcView.setLayoutManager(linearLayoutManager);
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

                if (!isLoading) {
                    isLoading = true;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter.loadquery(true);
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
}
