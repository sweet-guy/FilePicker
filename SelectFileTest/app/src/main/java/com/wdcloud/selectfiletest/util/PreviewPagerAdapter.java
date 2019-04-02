package com.wdcloud.selectfiletest.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.request.RequestOptions;
import com.wdcloud.selectfiletest.glideutil.GlideUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Umbrella on 2018/7/12.
 */

public class PreviewPagerAdapter extends PagerAdapter {
    private final RequestOptions options;
    private boolean isNetPic;
    private Context context;
    private ArrayList<String> list;
    private LayoutInflater inflater;

    public PreviewPagerAdapter(ArrayList<String> list, LayoutInflater inflater, boolean isNetPic) {
        this.list = list;
        this.inflater = inflater;
        this.isNetPic = isNetPic;
        context = inflater.getContext();
        options = GlideUtil.getRequestOptions();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView view = new PhotoView(context);
        view.enable();
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        if (isNetPic) {
            GlideUtil.loadImageView(context, view, list.get(position), options);
        } else {
            GlideUtil.loadImageView(context, view, new File(list.get(position)), options);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
