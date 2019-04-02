package com.wdcloud.selectfiletest;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdcloud.selectfiletest.util.DeleteListener;
import com.wdcloud.selectfiletest.util.PreviewPagerAdapter;
import com.wdcloud.selectfiletest.util.PreviewViewPager;
import com.wdcloud.selectfiletest.util.rxBus.RxBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PreviewActivity extends AppCompatActivity {
    public static final String POSTION = "pageIndex";
    public static final String DATA_LIST = "PreviewActivity";
    public static final String IS_SHOW_DEL_VIEW = "isShowDeleteView";
    public static final String IS_NET_PIC = "isNetPic";
    @BindView(R.id.previewactivity_num_tv)
    TextView numTv;
    @BindView(R.id.previewactivity_viewpager)
    PreviewViewPager viewpager;
    @BindView(R.id.previewactivity_delete)
    ImageView delView;
    private ArrayList<String> list;
    private PreviewPagerAdapter adapter;
    private int pageIndex;
    private boolean isNetPic;
    private Unbinder unbind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        unbind = ButterKnife.bind(this);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pageIndex = intent.getIntExtra(POSTION,0);
        list = intent.getStringArrayListExtra(DATA_LIST);
        boolean isShowDeleteView = intent.getBooleanExtra(IS_SHOW_DEL_VIEW, true);
        if (!isShowDeleteView){
            delView.setVisibility(View.GONE);
        }
        isNetPic = intent.getBooleanExtra(IS_NET_PIC, false);//是否是网络图片
        if (list != null && list.size() > 0){
            numTv.setText((pageIndex+1) +"/"+list.size());
            setAdapterViewPager();
        }
    }
    private void setAdapterViewPager() {
        if (adapter == null){
            adapter = new PreviewPagerAdapter(list,getLayoutInflater(),isNetPic);
            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    pageIndex = position;
                    numTv.setText((pageIndex+1)+"/"+list.size());
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            viewpager.setAdapter(adapter);
            viewpager.setCurrentItem(pageIndex);
        }else {
            adapter.notifyDataSetChanged();
        }

    }
    @OnClick({R.id.previewactivity_back, R.id.previewactivity_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.previewactivity_back:
                finish();
                break;
            case R.id.previewactivity_delete:
                if (list.size() == 1){
                    RxBus.getDefault().post(new DeleteListener(0));
                    finish();
                }else {
                    RxBus.getDefault().post(new DeleteListener(pageIndex));
                    list.remove(pageIndex);
                    setAdapterViewPager();
                    numTv.setText((pageIndex+1)+"/"+list.size());
                }
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind.unbind();
    }
}
