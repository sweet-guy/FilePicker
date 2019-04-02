package com.wdcloud.selectfiletest.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wdcloud.selectfiletest.BuildConfig;
import com.wdcloud.selectfiletest.R;
import com.wdcloud.selectfiletest.bean.FileBean;
import com.wdcloud.selectfiletest.glideutil.GlideUtil;
import com.wdcloud.selectfiletest.recycleAdapterUtil.BaseQuickAdapter;
import com.wdcloud.selectfiletest.recycleAdapterUtil.BaseViewHolder;

import java.io.File;
import java.util.List;

/**
 * Created by Umbrella on 2019/3/25.
 */

public class SelectPicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public Context context;
    private final RequestOptions options;
    public static String clickIndexPic = "res://" + BuildConfig.APPLICATION_ID + "/" + R.mipmap.camera_white;

    public SelectPicAdapter(int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;
        options = GlideUtil.getRequestOptions();
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        int layoutPosition = helper.getLayoutPosition();
        helper.addOnClickListener(R.id.addgridview_adapter_item_linearlayout);
        helper.addOnClickListener(R.id.addgridview_adapter_item_delete);
        helper.addOnClickListener(R.id.addgridview_adapter_item_draweeView);
        ImageView view = (ImageView) helper.getView(R.id.addgridview_adapter_item_draweeView);
        if(layoutPosition==9)
        {
            helper.setVisible(R.id.addview_parent,false);
        }
        else
        {
            helper.setVisible(R.id.addview_parent,true);
        }
        if (item.equals(clickIndexPic)) {
            helper.setVisible(R.id.addgridview_adapter_item_delete, false);
            helper.setText(R.id.addgridview_adapter_item_num, layoutPosition + "/9");
            helper.setVisible(R.id.addgridview_adapter_item_linearlayout, true);
            Glide.with(context).load(item).into(view);
        } else {
            helper.setVisible(R.id.addgridview_adapter_item_delete, true);
            helper.setVisible(R.id.addgridview_adapter_item_linearlayout, false);
            Glide.with(context).load(item).into(view);
        }
//        if (layoutPosition == 9) {
//            helper.setVisible(R.id.addview_parent, false);
//        }
//        helper.setVisible(R.id.addgridview_adapter_item_linearlayout,false);
    }
}
