package com.wdcloud.selectfiletest.adapter;

import android.support.annotation.Nullable;

import com.wdcloud.selectfiletest.R;
import com.wdcloud.selectfiletest.recycleAdapterUtil.BaseQuickAdapter;
import com.wdcloud.selectfiletest.recycleAdapterUtil.BaseViewHolder;

import java.util.List;

/**
 * Created by Umbrella on 2019/4/2.
 */

public class ListAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public ListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.list_item,item);
        helper.addOnClickListener(R.id.list_item);
    }
}
