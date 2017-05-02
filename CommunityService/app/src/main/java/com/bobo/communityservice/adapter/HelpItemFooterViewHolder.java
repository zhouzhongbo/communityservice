package com.bobo.communityservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bobo.communityservice.R;

/**
 * Created by zhouzhongbo on 2017/4/20.
 */
public class HelpItemFooterViewHolder extends RecyclerView.ViewHolder {
    private TextView tvLoadMore;
    private ProgressBar pbLoading;

    public HelpItemFooterViewHolder(View itemView) {
        super(itemView);
        tvLoadMore = (TextView) itemView.findViewById(R.id.tv_item_footer_load_more);
        pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_item_footer_loading);
    }
}
