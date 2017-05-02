package com.bobo.communityservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bobo.communityservice.R;
import com.bobo.communityservice.model.PersionGoods;
import com.bobo.communityservice.viewmodel.HelpViewModel;
import com.bobo.communityservice.viewmodel.MyPublishViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzhongbo on 2017/4/18.
 */

public class SellAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_FOOTER = 1;

    HelpViewModel helpModel =null;
    MyPublishViewModel publishModel =null;

    ArrayList<PersionGoods> sellItem = new ArrayList<PersionGoods>();
    OnItemClick clicklister;

    public SellAdapter(Context mcontext,HelpViewModel helpViewModel) {
        super();
        this.context = mcontext;
        helpModel = helpViewModel;
        helpModel.refreshItem(this,sellItem);
        Log.d("zzb","adadpter1 count = "+sellItem.size());
    }

    public SellAdapter(Context mcontext,MyPublishViewModel publishViewModel){
        super();
        this.context = mcontext;
        publishModel = publishViewModel;
        publishModel.queryList(this,sellItem);
        Log.d("zzb","adadpter2 count = "+sellItem.size());
    }

    public interface OnItemClick {
        public void onItemClick(View v, int position,PersionGoods goods);
    }


    public void setClickListeren(OnItemClick l){
        this.clicklister = l;
    }

    public View.OnClickListener getClickListener(final View v, final int positon){
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(clicklister != null){
                    clicklister.onItemClick(v,positon,sellItem.get(positon));
                }
            }
        };
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_CONTENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.sell_item_layout, parent, false);
//            view.setOnClickListener();
            return new HelpItemCustomViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {//加载进度条的布局
            View view = LayoutInflater.from(context).inflate(R.layout.sell_item_footer_layout, parent, false);
            return new FooterViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_CONTENT) {
            PersionGoods sellitem = sellItem.get(position);
        ((HelpItemCustomViewHolder)holder).fillData(sellitem,context);
            ((HelpItemCustomViewHolder)holder).itemView.setOnClickListener(getClickListener(holder.itemView,position));
        } else if (type == TYPE_FOOTER) {

        }
    }

    @Override
    public int getItemCount() {
        return  (sellItem == null||sellItem.size() == 0) ?0:sellItem.size()+1;
    }


    /**
     * footer的ViewHolder
     */
    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLoadMore;
        private ProgressBar pbLoading;

        public FooterViewHolder(View itemView) {
            super(itemView);
            tvLoadMore = (TextView) itemView.findViewById(R.id.tv_item_footer_load_more);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_item_footer_loading);
        }
    }

    public void loadMoreRefresh(){
        if(helpModel != null){
            helpModel.LoadMoreItem(this,sellItem);
        }
        if(publishModel != null){
            publishModel.LoadMoreItem(this,sellItem);
        }
//        sellItem.addAll(vm.refreshData());
    }

    public void pullRefresh(){
        if(helpModel != null){
            helpModel.refreshItem(this,sellItem);
        }
        if(publishModel != null){
            publishModel.refreshItem(this,sellItem);
        }
//        si.remove(si.size());
//        sellItem.clear();
//        sellItem.addAll(vm.refreshData());
    }
}
