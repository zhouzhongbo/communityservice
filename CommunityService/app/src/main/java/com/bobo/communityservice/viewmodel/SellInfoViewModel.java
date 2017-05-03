package com.bobo.communityservice.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bobo.communityservice.R;
import com.bobo.communityservice.adapter.GoodsInfoAdapter;
import com.bobo.communityservice.databinding.SellInfoBinding;
import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.model.PersionGoods;
import com.bobo.communityservice.model.PersionGoodsComment;
import com.bobo.communityservice.model.PersionGoodsLike;
import com.bumptech.glide.Glide;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.sdk.core.DroiUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzb on 2017/5/1.
 */

public class SellInfoViewModel {

    Context context;
    PersionGoods goods;
    SellInfoBinding binding;
    private PopupWindow mPopupWindow;
    CommunityUser user;
    ArrayList<PersionGoodsLike> likesList = new ArrayList<PersionGoodsLike>();
    ArrayList<PersionGoodsComment> commentList = new ArrayList<PersionGoodsComment>();

    GoodsInfoAdapter adapter;
    LinearLayoutManager  linearLayoutManager;


    public SellInfoViewModel(Context context, SellInfoBinding binding){
        this.context = context;
        this.binding = binding;
        user = DroiUser.getCurrentUser(CommunityUser.class);
    }

    public void setGoods(PersionGoods goods){
        this.goods = goods;
    }


    public void fillData(){
        if(goods!= null&&goods.writer!= null){
            DroiFile icon = goods.writer.getIcon();
            if(icon != null &&icon.hasUri()){
                Glide.with(context).load(icon.getUri()).into(binding.userIcon);
            }
            String nickName = goods.writer.getNickname();
            if(nickName!= null&&!nickName.equals("")){
                binding.userName.setText(goods.writer.getNickname());
            }else{
                binding.userName.setText(goods.writer.getUserId());
            }

            String price_format = context.getResources().getString(R.string.item_sell_price);
            String price_value = String.format(price_format, goods.goodsPrice.doubleValue());
            binding.priceText.setText(price_value);
            binding.description.setText(goods.Description);

//            String like_format = context.getResources().getString(R.string.item_like_count);
//            String like_value = String.format(like_format, goods.likeCount.intValue());
//            binding.likeNum.setText(String.valueOf(like_value));
        }
    }


    public void doLike(){

    }

    public void doUnLike(){

    }


    public void handlerMakeComments(View view){
        showPopupWin();
    }

    boolean hasLike = false;
    PersionGoodsLike localLike;
    public void handlerMakeLike(View view){
        int i = 0;
        while( (likesList.size()>0) && (i<likesList.size()) ){
            if(likesList.get(i).getLikeCommunityUser().getObjectId() == user.getObjectId()){
                localLike = likesList.get(i);
                hasLike = true;
                break;
            }
            i++;
        }
        if (hasLike){
            adapter.doUnLike(localLike);
            localLike.delete();
        }else{
            localLike = new PersionGoodsLike();
            localLike.setGoods(goods);
            localLike.setLikeCommunityUser(user);
            localLike.saveInBackground(new DroiCallback<Boolean>() {
                @Override
                public void result(Boolean aBoolean, DroiError droiError) {
                    if(droiError.isOk()){
                        Log.d("zzb","like ok");
                        likesList.add(localLike);
                        adapter.doLike(localLike);
                    }else{
                        Log.d("zzb","like failed");
                    }
                }
            });

        }

    }

    public void handlerBuyIt(View view){
        Log.d("zzb","handlerBuyIt");
    }



    public List<DroiFile> getImageList(){
        return goods.goodsImg;
    }

    public void queryLikeList(final GoodsInfoAdapter adapter){
        DroiCondition cond = DroiCondition.cond("goods._Id", DroiCondition.Type.EQ, goods.getObjectId());
        DroiQuery query = DroiQuery.Builder.newBuilder().where(cond).query(PersionGoodsLike.class).build();
        query.runQueryInBackground(new DroiQueryCallback<PersionGoodsLike>() {
            @Override
            public void result(List<PersionGoodsLike> list, DroiError droiError) {
                if(droiError.isOk()){
                    Log.d("zzb","queryLikeList success! listsize ="+list.size());

                    if (list.size()>0){
                        likesList.addAll(list);
                        adapter.addlike(list);
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    Log.d("zzb","queryLikeList failed!"+droiError.getCode()+":"+droiError.getAppendedMessage());
                }
            }
        });
    }

    public void querycommentsList(final GoodsInfoAdapter adapter, boolean isLoadMore){
        DroiCondition cond = DroiCondition.cond("goods._Id", DroiCondition.Type.EQ, goods.getObjectId());
        Log.d("zzb","goodsid ="+goods.getObjectId());
        if(isLoadMore){
            if(commentList!= null&&commentList.size()>0){
                PersionGoodsComment comments = commentList.get(commentList.size()-1);
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
//                String time=format.format(comments.getCreationTime());
//                Log.d("zzb","query by loadmore:"+time);
                DroiCondition cond2 =  DroiCondition.cond("_CreationTime", DroiCondition.Type.GT, comments.getCreationTime());
                cond = cond.and(cond2);
            }
        }
        DroiQuery query = DroiQuery.Builder.newBuilder().limit(10).orderBy("_CreationTime", true).where(cond).query(PersionGoodsComment.class).build();
        query.runQueryInBackground(new DroiQueryCallback<PersionGoodsComment>() {
            @Override
            public void result(List<PersionGoodsComment> list, DroiError droiError) {
                if(droiError.isOk()){
                    Log.d("zzb","querycommentsList success! listsize ="+list.size());
                    if (list.size()>0){
                        commentList.addAll(list);
                        adapter.addComments(list);
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    Log.d("zzb","querycommentsList failed!");
                }
            }
        });
    }

    EditText comments_box;
    String comments_text;

    public void showPopupWin(){
        View popupView = ((Activity)context).getLayoutInflater().inflate(R.layout.popupwin_add_comments, null);
        popupView.findViewById(R.id.cancle_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        popupView.findViewById(R.id.sure_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comments_text!= null&&!comments_text.equals("")){
                    final PersionGoodsComment comments = new PersionGoodsComment();
                    comments.setCommnetsBody(comments_text);
                    comments.setCommentsFrom(user);
                    comments.setGoods(goods);
                    comments.saveInBackground(new DroiCallback<Boolean>() {
                        @Override
                        public void result(Boolean aBoolean, DroiError droiError) {
                            if(droiError.isOk()){
                                Log.d("zzb","comments is save ok !");
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                                String time=format.format(comments.getCreationTime());
                                Log.d("zzb","create comments time ="+time);
                                adapter.addComment(comments);
                                adapter.notifyDataSetChanged();
                            }else{
                                Log.d("zzb","comments save failed !");
                            }
                        }
                    });
                    mPopupWindow.dismiss();
                }else{
                    Toast.makeText(context,"评论内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        comments_box = (EditText)popupView.findViewById(R.id.comments_input);
        comments_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                comments_text = comments_box.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPopupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setContentView(popupView);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        View rootview = LayoutInflater.from(context).inflate(R.layout.activity_edituser_layout, null);

        mPopupWindow.showAtLocation(rootview, Gravity.CENTER,0,0);
    }

    public void viewInit(){
        fillData();
        adapter = new GoodsInfoAdapter(context,this);
        binding.infoList.setAdapter(adapter);
        binding.infoList.addOnScrollListener(new OnRecyclerScrollListener());
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.infoList.setLayoutManager(linearLayoutManager);
    }

    private boolean isLoading = false;
    public class OnRecyclerScrollListener extends RecyclerView.OnScrollListener {
        int lastVisibleItem = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (adapter != null &&
                    newState == RecyclerView.SCROLL_STATE_IDLE &&
                    lastVisibleItem + 1 == adapter.getItemCount()) {
                if (!isLoading) {
                    isLoading = true;
                    adapter.loadCommentsMore();
                    isLoading = false;
                }

            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

        }
    }

}
