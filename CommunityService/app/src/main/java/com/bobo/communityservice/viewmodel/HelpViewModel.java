package com.bobo.communityservice.viewmodel;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

import com.bobo.communityservice.R;
import com.bobo.communityservice.adapter.GoodsListAdapter;
import com.bobo.communityservice.model.PersionGoods;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzhongbo on 2017/4/17.
 */

public class HelpViewModel {
    Context context;
    ArrayList<Integer> mBanners = new ArrayList<Integer>();
    ArrayList<PersionGoods> itemData = new ArrayList<PersionGoods>();
    ArrayList<String> tags = new ArrayList<String>();

    public HelpViewModel(Context mcontext){
        context = mcontext;
        bannerInit();
    }

    private void bannerInit() {
        int i = 0;
        TypedArray pictures = context.getResources().obtainTypedArray(R.array.banner);
        mBanners.clear();
        tags.clear();
       while (i < pictures.length()) {
            mBanners.add(pictures.getResourceId(i,0));
           i++;
        }
        pictures.recycle();
        TypedArray  texts = context.getResources().obtainTypedArray(R.array.tags);
        i = 0;
        while (i < pictures.length()) {
            tags.add(texts.getString(i));
            i++;
        }
        texts.recycle();
    }

    public int getBannerCount(){
        return mBanners.size();
    }

    public ArrayList<Integer> getBanner(){
        return mBanners;
    }

    public ArrayList<String> getTags(){
        return tags;
    }



    public void refreshItem(GoodsListAdapter adapter){
        loadquery(adapter,false);
    }


    public void LoadMoreItem(GoodsListAdapter adapter){
        loadquery(adapter,true);
    }

    //注意这里是公共的查询，和UserID无关；
    private void loadquery(final GoodsListAdapter adapter, final boolean isLoad){
        DroiCondition cond = null;
        Log.d("zzb","helpViewModel star query:isload ="+isLoad);
        if(isLoad && itemData.size()>0){
            PersionGoods pg = itemData.get(itemData.size()-1);
            cond = DroiCondition.cond("_CreationTime", DroiCondition.Type.LT, pg.getCreationTime());
        }
        DroiQuery.Builder builder = DroiQuery.Builder.newBuilder().limit(10).orderBy("_CreationTime", false);
        if(cond!= null){
            builder = builder.where(cond);
        }
        DroiQuery query = builder.query(PersionGoods.class).build();
        query.runQueryInBackground(new DroiQueryCallback<PersionGoods>() {
            @Override
            public void result(List<PersionGoods> list, DroiError droiError) {
                if(droiError.isOk()){
                    Log.d("zzb","helpViewModel query success! listsize ="+list.size());
                    if (list.size()>0){
                        if(!isLoad){
                            itemData.clear();
                            adapter.clearData();
                        }
                        itemData.addAll(list);
                        adapter.addData(list);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
