package com.bobo.communityservice.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.bobo.communityservice.activity.PublishNewActivity;
import com.bobo.communityservice.adapter.SellAdapter;
import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.model.NoticeObject;
import com.bobo.communityservice.model.PersionGoods;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.sdk.core.DroiUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzhongbo on 2017/4/24.
 */

public class MyPublishViewModel {

    ArrayList<PersionGoods> mypublish = new ArrayList<PersionGoods>();

    Context context;

    public MyPublishViewModel(Context context){
        this.context = context;
//        queryList();
    }

    public void handlerNewPublish(View view){
        Intent newPublish = new Intent(view.getContext(),PublishNewActivity.class);
        newPublish.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newPublish);
    }

    public ArrayList<PersionGoods> queryList(final SellAdapter adapter, final ArrayList<PersionGoods> sellItem){
        CommunityUser user = DroiUser.getCurrentUser(CommunityUser.class);
        Log.d("zzb","userid ="+user.getUserId()+";objectid ="+user.getObjectId());

        if (user != null && user.isAuthorized() && !user.isAnonymous()) {
            DroiCondition cond = DroiCondition.cond("writer._Id", DroiCondition.Type.EQ, user.getObjectId());
            DroiQuery query = DroiQuery.Builder.newBuilder().limit(10).where(cond).query(PersionGoods.class).build();
            query.runQueryInBackground(new DroiQueryCallback<PersionGoods>() {
                @Override
                public void result(List<PersionGoods> list, DroiError droiError) {
                    if(droiError.isOk()){
                        Log.d("zzb","query success! listsize ="+list.size());
                        if (list.size()>0){
                            sellItem.addAll(list);
                            mypublish.addAll(list);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        return mypublish;
    }

    public ArrayList<PersionGoods> refreshItem(SellAdapter adapter,ArrayList<PersionGoods> list){
        refreshquery(adapter,list);
        return mypublish;
    }


    public ArrayList<PersionGoods> LoadMoreItem(SellAdapter adapter,ArrayList<PersionGoods> list){
        loadquery(adapter,list);
        return mypublish;
    }

    private void refreshquery(final SellAdapter adapter,final ArrayList<PersionGoods> itemdata){
        DroiQuery query = DroiQuery.Builder.newBuilder().limit(10).query(PersionGoods.class).build();
        query.runQueryInBackground(new DroiQueryCallback<PersionGoods>() {
            @Override
            public void result(List<PersionGoods> list, DroiError droiError) {
                if(droiError.isOk()){
                    Log.d("zzb","query success! listsize ="+list.size());

                    if (list.size()>0){
                        itemdata.clear();
                        itemdata.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void loadquery(final SellAdapter adapter,final ArrayList<PersionGoods> itemdata){
        PersionGoods pg = itemdata.get(itemdata.size()-1);
        DroiCondition cond = DroiCondition.cond("modifiedTime", DroiCondition.Type.LT, pg.getModifiedTime());
        DroiQuery query = DroiQuery.Builder.newBuilder().limit(10).where(cond).query(PersionGoods.class).build();
        query.runQueryInBackground(new DroiQueryCallback<PersionGoods>() {
            @Override
            public void result(List<PersionGoods> list, DroiError droiError) {
                if(droiError.isOk()){
                    Log.d("zzb","query success! listsize ="+list.size());

                    if (list.size()>0){
                        itemdata.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }




}
