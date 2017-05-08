package com.bobo.communityservice.viewmodel;

import android.content.Context;
import android.util.Log;

import com.bobo.communityservice.adapter.GoodsListAdapter;
import com.bobo.communityservice.adapter.SelledAdapter;
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
 * Created by zhouzhongbo on 2017/4/26.
 */

public class MySelledViewModel {

    Context context;
    List<PersionGoods> myOrder = new ArrayList<PersionGoods>();
    public MySelledViewModel(Context context){
        this.context = context;
    }



    public void refreshItem(SelledAdapter adapter){
        loadquery(adapter,false);
    }


    public void LoadMoreItem(SelledAdapter adapter){
        loadquery(adapter,true);
    }

    private void loadquery(final SelledAdapter adapter, final boolean isLoad){
        CommunityUser user= DroiUser.getCurrentUser(CommunityUser.class);
        DroiCondition cond;
        if(user != null && !user.isAnonymous()){
            cond = DroiCondition.cond("writer._Id", DroiCondition.Type.EQ, user.getObjectId())
                    .and(DroiCondition.cond("isSelled",DroiCondition.Type.EQ,true));
            if(isLoad){
                if(myOrder.size()>0){
                    PersionGoods pg = myOrder.get(myOrder.size()-1);
                    DroiCondition cond2 = DroiCondition.cond("_CreationTime", DroiCondition.Type.LT, pg.getCreationTime());
                    cond = cond.and(cond2);
                }
            }
            DroiQuery query = DroiQuery.Builder.newBuilder().orderBy("_CreationTime", true).limit(10).where(cond).query(PersionGoods.class).build();
            query.runQueryInBackground(new DroiQueryCallback<PersionGoods>() {
                @Override
                public void result(List<PersionGoods> list, DroiError droiError) {
                    if(droiError.isOk()){
                        Log.d("zzb","query selled success! listsize ="+list.size());

                        if (list.size()>0){
                            if(!isLoad){
                                myOrder.clear();
                                adapter.clearData();
                            }
                            myOrder.addAll(list);
                            adapter.addData(list);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });

        }
    }

}
