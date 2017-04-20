package com.bobo.communityservice.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo.communityservice.R;
import com.bobo.communityservice.model.PersionGoods;
import com.bobo.communityservice.view.CircleImageView;
import com.bumptech.glide.Glide;
import com.droi.sdk.core.DroiFile;

import java.util.List;

/**
 * Created by zhouzhongbo on 2017/4/18.
 */

public class CustomViewHolder extends RecyclerView.ViewHolder{

    CircleImageView userIcon;
    TextView  userName;
    TextView price;
    TextView  desCrpition;
    TextView  like;
    RecyclerView imgContainer;
    GalleryAdapter gadapter;

    public CustomViewHolder(View itemView) {
        super(itemView);
        userIcon = (CircleImageView)itemView.findViewById(R.id.user_icon);
        userName = (TextView)itemView.findViewById(R.id.user_name);
        price = (TextView)itemView.findViewById(R.id.price_text);
        desCrpition = (TextView)itemView.findViewById(R.id.description);
        imgContainer = (RecyclerView)itemView.findViewById(R.id.img_container);
        like = (TextView)itemView.findViewById(R.id.like_num);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void fillData(PersionGoods sellitem, Context mcontext){

//        userIcon.setImageDrawable(sellitem.icon);
            Glide.with(mcontext)
                    .load(sellitem.goodsIcon.getUri())
                    .placeholder(R.drawable.default_icon)
                    .error(R.drawable.default_icon)
                    .into(userIcon);

        userName.setText(sellitem.writer.nickname);
        price.setText(String.valueOf(sellitem.goodsPrice));
        desCrpition.setText(sellitem.Description);
        like.setText(String.valueOf(sellitem.likeCount));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imgContainer.setLayoutManager(linearLayoutManager);

        gadapter = new GalleryAdapter(mcontext,sellitem.goodsImg);
        imgContainer.setAdapter(gadapter);
    }

    /**
     * 宝贝图片描述的适配器，用于支持图片的横向滑动浏览图片
     */
    public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
        private List<DroiFile> desImg;
        private Context context;
        private LayoutInflater mInflater;

        public GalleryAdapter(Context context, List<DroiFile> datats) {
            this.context = context;
            desImg = datats;
            mInflater = LayoutInflater.from(context);
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView mImg;

            public ViewHolder(View container)
            {
                super(container);
                mImg = (ImageView) container.findViewById(R.id.img_des);
            }
        }

        @Override
        public int getItemCount() {
            return desImg.size();
        }

        /**
         * 创建ViewHolder
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = mInflater.inflate(R.layout.imgcontainer_layout,
                    viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        /**
         * 设置值
         */
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
//            viewHolder.mImg.setImageDrawable(desImg.get(i));
            Glide.with(context)
                    .load(desImg.get(i).getUri())
                    .placeholder(R.drawable.default_icon)
                    .error(R.drawable.default_icon)
                    .into(viewHolder.mImg);
        }

    }


}
