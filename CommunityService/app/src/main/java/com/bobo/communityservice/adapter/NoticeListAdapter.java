package com.bobo.communityservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.communityservice.R;
import com.bobo.communityservice.model.NoticeObject;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzhongbo on 2017/4/26.
 */

public class NoticeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_FOOTER = 1;

    private Context context;
    OnItemClick clicklister;
    List<NoticeObject> noticeItem = new ArrayList<NoticeObject>();


    public NoticeListAdapter(Context mcontext) {
        super();
        this.context = mcontext;
        loadquery(false);
    }

    public interface OnItemClick {
        public void onItemClick(View v, int position, NoticeObject notice);
    }

    public void setClickListeren(NoticeListAdapter.OnItemClick l){
        this.clicklister = l;
    }

    public View.OnClickListener getClickListener(final View v, final int positon){
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(clicklister != null){
                    clicklister.onItemClick(v,positon,noticeItem.get(positon));
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
            View view = LayoutInflater.from(context).inflate(R.layout.notice_item_layout, parent, false);
            return new NoticeViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {//加载进度条的布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_footer_layout, parent, false);
            return new GoodsListAdapter.FooterViewHolder(view);
        }
        return null;    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_CONTENT) {
            NoticeObject notice = noticeItem.get(position);
            ((NoticeViewHolder)holder).fillData(notice);
        } else if (type == TYPE_FOOTER) {

        }
    }

    @Override
    public int getItemCount() {
        return noticeItem.size() == 0?0:noticeItem.size()+1;
    }


    public void loadquery(final boolean isLoad){
        DroiQuery.Builder builder = DroiQuery.Builder.newBuilder().orderBy("_CreationTime", false).limit(10);
        if(isLoad){
            if(noticeItem.size()>0){
                NoticeObject pg = noticeItem.get(noticeItem.size()-1);
                DroiCondition cond = DroiCondition.cond("_CreationTime", DroiCondition.Type.LT, pg.getCreationTime());
                builder = builder.where(cond);
            }
        }

        DroiQuery query = builder.query(NoticeObject.class).build();
        query.runQueryInBackground(new DroiQueryCallback<NoticeObject>() {
            @Override
            public void result(List<NoticeObject> list, DroiError droiError) {
                if(droiError.isOk()){
                    Log.d("zzb","query order success! listsize ="+list.size());
                    if (list.size()>0){
                        if(!isLoad){
                            noticeItem.clear();
                        }
                        noticeItem.addAll(list);
                        notifyDataSetChanged();
                    }
                }
            }
        });
    }

}
