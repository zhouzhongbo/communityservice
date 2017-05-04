package com.bobo.communityservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.communityservice.R;
import com.bobo.communityservice.model.PersionGoods;
import com.bobo.communityservice.model.PersionGoodsComment;
import com.bobo.communityservice.model.PersionGoodsLike;
import com.bobo.communityservice.viewmodel.SellInfoViewModel;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzb on 2017/5/1.
 */

public class GoodsInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    static final int GOODS_ITEM_IMG = 100;
    static final int GOODS_ITEM_DIVIDER = 101;
    static final int GOODS_ITEM_COMMENTS = 102;
    String TAG = "Like";
    SellInfoViewModel model;
    List<DroiFile> imgList = new ArrayList<DroiFile>();
    ArrayList<PersionGoodsLike> likeList = new ArrayList<PersionGoodsLike>();
    ArrayList<PersionGoodsComment> commentList = new ArrayList<PersionGoodsComment>();

    Context context;

    public GoodsInfoAdapter(Context context, SellInfoViewModel model) {
        super();
        this.model = model;
        this.context = context;
        imgList = model.getImageList();
        likeList.clear();
        commentList.clear();
        model.querycommentsList(this,false);
        model.queryLikeList(this);
    }

    public void addComments(List<PersionGoodsComment> list){
        commentList.addAll(list);
    }

    public void addComment(PersionGoodsComment obj){
        commentList.add(0,obj);
    }

    public void addlike(List<PersionGoodsLike> list){
        likeList.addAll(list);
    }


    public void doLike(PersionGoodsLike like){
        likeList.add(like);
        PersionGoods goods = like.getGoods();
        goods.likeCount = likeList.size();
        goods.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if(droiError.isOk()){
                    Log.d(TAG,"add like success");
                }else {
                    Log.d(TAG,"add like failed");
                }
            }
        });
        this.notifyDataSetChanged();
    }

    public void doUnLike(PersionGoodsLike like){
        likeList.remove(like);
        PersionGoods goods = like.getGoods();
        goods.likeCount = likeList.size();
        goods.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if(droiError.isOk()){
                    Log.d(TAG,"add like success");
                }else {
                    Log.d(TAG,"add like failed");
                }
            }
        });

        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public void loadCommentsMore(){
        model.querycommentsList(this,true);
    }

    @Override
    public int getItemViewType(int position) {
        int itemType = 0;
        if(imgList.size()>0){
            if(position<imgList.size()){
                itemType = GOODS_ITEM_IMG;
            }else if(position == imgList.size()){
                itemType = GOODS_ITEM_DIVIDER;
            }else if(position>imgList.size()){
                itemType = GOODS_ITEM_COMMENTS;
            }
        }else{
            if(position == 0){
                itemType =GOODS_ITEM_DIVIDER;
            }else{
                itemType = GOODS_ITEM_COMMENTS;
            }
        }
        Log.d("zzb","position"+position+";type ="+itemType);

        return itemType;
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
        if (viewType == GOODS_ITEM_IMG) {
            View view = LayoutInflater.from(context).inflate(R.layout.sellinfo_item_layout, parent, false);
            return new SellInfoImageHolder(view);
        } else if (viewType == GOODS_ITEM_DIVIDER) {//加载进度条的布局
            View view = LayoutInflater.from(context).inflate(R.layout.sellinfo_divder_layout, parent, false);
            return new SellInfoDivderHolder(view);
        } else if(viewType == GOODS_ITEM_COMMENTS){
            View view = LayoutInflater.from(context).inflate(R.layout.sellinfo_comments_layout, parent, false);
            return new SellInfoCommentsHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch(type){
            case GOODS_ITEM_COMMENTS:
                Log.d("zzb","position ="+position);
                int inner_position = position - imgList.size() - 1;
                if(inner_position>=0&&inner_position<commentList.size()){
                    PersionGoodsComment cm = commentList.get(inner_position);
                    ((SellInfoCommentsHolder)holder).fillData(context,cm);
                }else{
                    Log.d("zzb","inner position calc error!");
                }
                break;
            case GOODS_ITEM_DIVIDER:
                int likeCount = likeList.size();
                ((SellInfoDivderHolder)holder).fillData(context,likeCount);

                break;
            case GOODS_ITEM_IMG:
                DroiFile img = imgList.get(position);
                ((SellInfoImageHolder)holder).fillData(context,img);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        int count = imgList.size()+commentList.size()+1;
        return count;
    }
}
