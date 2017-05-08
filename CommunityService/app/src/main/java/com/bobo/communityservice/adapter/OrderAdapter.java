package com.bobo.communityservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.communityservice.R;
import com.bobo.communityservice.model.PersionGoods;
import com.bobo.communityservice.viewmodel.MyOrderViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzhongbo on 2017/4/26.
 */

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_FOOTER = 1;

    private Context context;
    OnItemClick clicklister;
    List<PersionGoods> sellItem = new ArrayList<PersionGoods>();
    MyOrderViewModel model;


    public OrderAdapter(Context mcontext, MyOrderViewModel ordermodel) {
        super();
        this.context = mcontext;
        this.model = ordermodel;
        model.refreshItem(this);
    }

    public void addData(List<PersionGoods> goods){
        sellItem.addAll(goods);
    }

    public void clearData(){
        sellItem.clear();
    }


    public interface OnItemClick {
        public void onItemClick(View v, int position, PersionGoods goods);
    }

    public void setClickListeren(OrderAdapter.OnItemClick l){
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
            return new HelpItemCustomViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {//加载进度条的布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_footer_layout, parent, false);
            return new GoodsListAdapter.FooterViewHolder(view);
        }
        return null;    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_CONTENT) {
            PersionGoods sellitem = sellItem.get(position);
            ((HelpItemCustomViewHolder)holder).fillData(sellitem,context);
//            ((HelpItemCustomViewHolder)holder).itemView.setOnClickListener(getClickListener(holder.itemView,position));
        } else if (type == TYPE_FOOTER) {

        }
    }

    @Override
    public int getItemCount() {
        return sellItem.size() == 0?0:sellItem.size()+1;
    }

    public void loadMoreRefresh(){
        if(model != null){
            model.LoadMoreItem(this);
        }
    }

    public void pullRefresh(){
        if(model != null){
            model.refreshItem(this);
        }
    }

}
