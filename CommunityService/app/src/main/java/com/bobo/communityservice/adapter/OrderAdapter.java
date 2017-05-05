package com.bobo.communityservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

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

    OnItemClick clicklister;
    List<PersionGoods> sellItem = new ArrayList<PersionGoods>();

    public OrderAdapter(MyOrderViewModel ordermodel) {
        super();
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
        return super.getItemViewType(position);
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
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return sellItem.size() == 0?0:sellItem.size()+1;
    }
}
