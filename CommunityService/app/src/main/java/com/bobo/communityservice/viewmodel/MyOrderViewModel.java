package com.bobo.communityservice.viewmodel;

import android.content.Context;
import android.util.Log;

import com.bobo.communityservice.adapter.GoodsListAdapter;
import com.bobo.communityservice.adapter.OrderAdapter;
import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.model.PersionGoods;
import com.bobo.communityservice.model.PersionOrder;
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

public class MyOrderViewModel {

    Context context;
    MyOrderViewModel model;
    List<PersionGoods> goodsList = new ArrayList<PersionGoods>();
    List<PersionOrder> orderList = new ArrayList<PersionOrder>();

    public MyOrderViewModel(Context context){
        this.context = context;
    }



    public void refreshItem(OrderAdapter adapter){
        loadquery(adapter,false);
    }


    public void LoadMoreItem(OrderAdapter adapter){
        loadquery(adapter,true);
    }

    private void loadquery(final OrderAdapter adapter, final boolean isLoad){
        CommunityUser user= DroiUser.getCurrentUser(CommunityUser.class);
        DroiCondition cond;
        if(user != null && !user.isAnonymous()){
            cond = DroiCondition.cond("buyer._Id", DroiCondition.Type.EQ, user.getObjectId());
            if(isLoad){
                if(orderList.size()>0){
                    PersionOrder pg = orderList.get(orderList.size()-1);
                    DroiCondition cond2 = DroiCondition.cond("_CreationTime", DroiCondition.Type.LT, pg.getCreationTime());
                    cond = cond.and(cond2);
                }
            }
            DroiQuery query = DroiQuery.Builder.newBuilder().orderBy("_CreationTime", true).limit(10).where(cond).query(PersionOrder.class).build();
            query.runQueryInBackground(new DroiQueryCallback<PersionOrder>() {
                @Override
                public void result(List<PersionOrder> list, DroiError droiError) {
                    if(droiError.isOk()){
                        Log.d("zzb","query order success! listsize ="+list.size());

                        if (list.size()>0){
                            if(!isLoad){
                                goodsList.clear();
                                orderList.clear();
                                adapter.clearData();
                            }
                            orderList.addAll(list);
                            int i = 0;
                            while(i<list.size()){
                                goodsList.add(list.get(i).getGoods());
                                i++;
                            }
                            Log.d("zzb","goodslist size ="+goodsList.size());
                            adapter.addData(goodsList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

}
