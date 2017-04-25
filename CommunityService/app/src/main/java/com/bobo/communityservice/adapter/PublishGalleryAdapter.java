package com.bobo.communityservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.communityservice.R;
import com.bumptech.glide.Glide;
import com.droi.sdk.core.DroiFile;

import java.util.List;

/**
 * Created by zhouzhongbo on 2017/4/24.
 */

public class PublishGalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DroiFile> desImg;
    private Context context;
    private LayoutInflater mInflater;
    private static final int TYPE_IMG_CONTENT = 0;
    private static final int TYPE_IMG_ADD = 1;
    private OnItemClickListener itemClickListener;

    public PublishGalleryAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return desImg.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_IMG_ADD;
        } else {
            return TYPE_IMG_CONTENT;
        }
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_IMG_ADD) {
            View view = mInflater.inflate(R.layout.publish_gallery_add_layout, viewGroup, false);
            return new PublishGalleryAddViewHolder(view);
        } else if (viewType == TYPE_IMG_CONTENT) {
            View view = mInflater.inflate(R.layout.publish_gallery_item_layout, viewGroup, false);
            return  new PublishGalleryItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int type = getItemViewType(position);
        if (type == TYPE_IMG_ADD) {
            PublishGalleryAddViewHolder addholder = (PublishGalleryAddViewHolder)holder;
            addholder.image.setImageResource(R.drawable.img_add);
            //为整体布局设置点击事件，触发接口的回调
            addholder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener!=null)
                        itemClickListener.onAddImageClick(desImg);
                }
            });
        } else if (type == TYPE_IMG_CONTENT) {
            PublishGalleryItemViewHolder itemholder = (PublishGalleryItemViewHolder)holder;
            Glide.with(context)
                    .load(desImg.get(position).getUri())
                    .placeholder(R.drawable.img_waiting)
                    .error(R.drawable.img_waiting)
                    .into(itemholder.image);
            itemholder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener!=null)
                        itemClickListener.onPreViewImageClick(desImg,position);
                }
            });
            itemholder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener!=null)
                        itemClickListener.onRemoveImageClick(desImg,position);
                }
            });
        }
    }



    //点击事件接口
    public interface OnItemClickListener{
        void onAddImageClick(List<DroiFile> desImg);
        void onRemoveImageClick(List<DroiFile> desImg,int position);
        void onPreViewImageClick(List<DroiFile> desImg,int position);
    }
    //设置点击事件的方法
    public void setItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}