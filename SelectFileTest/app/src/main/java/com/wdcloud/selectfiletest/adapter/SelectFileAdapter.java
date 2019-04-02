package com.wdcloud.selectfiletest.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wdcloud.selectfiletest.BuildConfig;
import com.wdcloud.selectfiletest.R;
import com.wdcloud.selectfiletest.bean.FileBean;
import com.wdcloud.selectfiletest.recycleAdapterUtil.BaseQuickAdapter;
import com.wdcloud.selectfiletest.recycleAdapterUtil.BaseViewHolder;
import com.wdcloud.selectfiletest.util.FilePathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Umbrella on 2019/3/25.
 */

public class SelectFileAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private int max_count = 3;
    public static String fileclickIndexPic = "res://" + BuildConfig.APPLICATION_ID + "/" + R.mipmap.scwj;
    private Context context;

    public SelectFileAdapter(int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        String filepath = item;
        int layoutPosition = helper.getLayoutPosition();
        helper.addOnClickListener(R.id.sendmsgaccessorydocadapter_item_linearlayout);
        helper.addOnClickListener(R.id.sendmsgaccessorydocadapter_item_delete);
        helper.setText(R.id.sendmsgaccessorydocadapter_item_num, layoutPosition + "/3");
        if(layoutPosition==3)
        {
            helper.setVisible(R.id.sendmsgaccessorydocadapter_root,false);
        }
        else
        {
            helper.setVisible(R.id.sendmsgaccessorydocadapter_root,true);
        }
        if (filepath.equals(fileclickIndexPic)) {
            helper.setVisible(R.id.sendmsgaccessorydocadapter_item_delete, false);
            helper.setVisible(R.id.sendmsgaccessorydocadapter_item_linearlayout, true);
            helper.setVisible(R.id.sendmsgaccessorydocadapter_item_filename, false);
            helper.setVisible(R.id.sendmsgaccessorydocadapter_item_draweeView,false);
        } else {
            helper.setVisible(R.id.sendmsgaccessorydocadapter_item_draweeView,true);
            FilePathUtil filePathUtil = new FilePathUtil(context);
            String fileName = filePathUtil.getFileName(item);
            helper.setText(R.id.sendmsgaccessorydocadapter_item_filename, fileName);
            ImageView imageView = helper.getView(R.id.sendmsgaccessorydocadapter_item_draweeView);
            String pexfix = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
            if (".docx".equals(pexfix) || ".doc".equals(pexfix)||".xls".equals(pexfix)) {
                imageView.setImageResource(R.mipmap.word2); //设置word图片
            } else if (".pdf".equals(pexfix) || ".ppt".equals(pexfix)) {
                imageView.setImageResource(R.mipmap.ppt2);//设置ppt的图片
            }
            helper.setVisible(R.id.sendmsgaccessorydocadapter_item_filename, true);
            helper.setVisible(R.id.sendmsgaccessorydocadapter_item_linearlayout, false);
            helper.setVisible(R.id.sendmsgaccessorydocadapter_item_delete, true);
            ImageView view = helper.getView(R.id.sendmsgaccessorydocadapter_item_draweeView);
//            Glide.with(context).load(item).into(view);
        }
    }
}
