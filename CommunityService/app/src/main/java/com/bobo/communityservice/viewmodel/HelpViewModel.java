package com.bobo.communityservice.viewmodel;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

import com.bobo.communityservice.R;
import com.bobo.communityservice.adapter.GoodsListAdapter;
import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.model.PersionGoods;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.sdk.core.DroiUser;

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
           Log.d("zzb","resouceid ="+pictures.getResourceId(i,0));
            mBanners.add(pictures.getResourceId(i,0));
           i++;
        }
        pictures.recycle();
        TypedArray  texts = context.getResources().obtainTypedArray(R.array.tags);
        i = 0;
        while (i < pictures.length()) {
            Log.d("zzb","resouce: string ="+texts.getString(i));
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

//    private void refreshquery(final GoodsListAdapter adapter){
//        DroiQuery query = DroiQuery.Builder.newBuilder().orderBy("_CreationTime", true).limit(10).query(PersionGoods.class).build();
//        query.runQueryInBackground(new DroiQueryCallback<PersionGoods>() {
//            @Override
//            public void result(List<PersionGoods> list, DroiError droiError) {
//                if(droiError.isOk()){
//                    Log.d("zzb","query success! listsize ="+list.size());
//                    if (list.size()>0){
//                        itemData.clear();
//                        itemData.addAll(list);
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        });
//    }


    private void loadquery(final GoodsListAdapter adapter, final boolean isLoad){
        CommunityUser user = DroiUser.getCurrentUser(CommunityUser.class);
        DroiCondition cond;
        if(user != null && !user.isAnonymous()){
            cond = DroiCondition.cond("writer._Id", DroiCondition.Type.EQ, user.getObjectId());
            if(isLoad){
                PersionGoods pg = itemData.get(itemData.size()-1);
                DroiCondition cond2 = DroiCondition.cond("_CreationTime", DroiCondition.Type.LT, pg.getCreationTime());
                cond = cond.and(cond2);
            }
            DroiQuery query = DroiQuery.Builder.newBuilder().orderBy("_CreationTime", true).limit(10).where(cond).query(PersionGoods.class).build();
            query.runQueryInBackground(new DroiQueryCallback<PersionGoods>() {
                @Override
                public void result(List<PersionGoods> list, DroiError droiError) {
                    if(droiError.isOk()){
                        Log.d("zzb","query success! listsize ="+list.size());
                        if (list.size()>0){
                            if(!isLoad){
                                itemData.clear();
                                adapter.clearData();
                            }
                            itemData.addAll(list);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}
