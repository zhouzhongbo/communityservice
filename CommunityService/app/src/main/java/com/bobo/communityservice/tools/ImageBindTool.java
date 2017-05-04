package com.bobo.communityservice.tools;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by zhouzhongbo on 2017/4/20.
 */

public class ImageBindTool {
    @BindingAdapter({"bind:imageUrl", "bind:placeHolder", "bind:error"})
    public static void loadImage(ImageView imageView, Uri url, Drawable placeholder, Drawable errorDrawable) {
        Glide.with(imageView.getContext())
                .load(url)
                .error(errorDrawable)
                .placeholder(placeholder)
                .into(imageView);
    }
}
