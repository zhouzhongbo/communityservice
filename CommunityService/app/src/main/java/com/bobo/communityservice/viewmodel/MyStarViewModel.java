package com.bobo.communityservice.viewmodel;

import android.content.Context;
import android.util.Log;

import com.bobo.communityservice.adapter.GoodsListAdapter;
import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.model.PersionGoods;
import com.bobo.communityservice.model.PersionGoodsLike;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.sdk.core.DroiUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzhongbo on 2017/4/26.
 */

public class MyStarViewModel {
    List<PersionGoods> mystarList = new ArrayList<PersionGoods>();
    Context context;

    public MyStarViewModel(Context context){
        this.context = context;
        mystarList.clear();

    }

    public void refreshItem(GoodsListAdapter adapter){
        loadquery(adapter,false);
    }


    public void LoadMoreItem(GoodsListAdapter adapter){
        loadquery(adapter,true);
    }

    private void loadquery(final GoodsListAdapter adapter, final boolean isLoadMore){
        Log.d("zzb","helpViewModel star query:isload ="+isLoadMore);
        CommunityUser user = DroiUser.getCurrentUser(CommunityUser.class);
        if (user != null && user.isAuthorized() && !user.isAnonymous()) {
            DroiCondition cond = DroiCondition.cond("likeCommunityUser._Id", DroiCondition.Type.EQ, user.getObjectId());
            if(isLoadMore){
                DroiCondition cond2 = DroiCondition.cond("likeCommunityUser._Id", DroiCondition.Type.EQ, user.getObjectId());
                cond = cond.and(cond2);
            }
            DroiQuery query = DroiQuery.Builder.newBuilder().orderBy("_CreationTime",true).limit(10).where(cond).query(PersionGoodsLike.class).build();
            query.runQueryInBackground(new DroiQueryCallback<PersionGoodsLike>() {
                @Override
                public void result(List<PersionGoodsLike> list, DroiError droiError) {
                    if(droiError.isOk()){
                        Log.d("zzb","starViewModel query success! listisize ="+list.size());
                        if (list.size()>0){
                            int i = 0;
                            if(!isLoadMore){
                                mystarList.clear();
                                adapter.clearData();
                            }
                            while(i<list.size()){
                                mystarList.add(list.get(i).getGoods());
                                i++;
                            }
                            adapter.addData(mystarList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}
