package com.wdcloud.selectfiletest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wdcloud.selectfiletest.adapter.ListAdapter;
import com.wdcloud.selectfiletest.recycleAdapterUtil.BaseQuickAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SwipeRecycleActivity extends AppCompatActivity {
    private List<String> list = new ArrayList<>();
    private ListAdapter listAdapter;
    private SwipeMenuCreator swipeMenuCreator;
    private SwipeMenuItemClickListener swipeMenuItemClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_recycle);
        initdata();
        setSwipeMenu();
        SwipeMenuRecyclerView swipeMenuRecyclerView = findViewById(R.id.rv_swipe);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        swipeMenuRecyclerView.setLayoutManager(linearLayoutManager);
        listAdapter = new ListAdapter(R.layout.layout_list_item, list);
        swipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(swipeMenuItemClickListener);
        swipeMenuRecyclerView.setAdapter(listAdapter);
        listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    private void setSwipeMenu() {
        //                        .setImage(R.mipmap.icon_img)
        swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                int width = 200;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                SwipeMenuItem deleteItem = new SwipeMenuItem(SwipeRecycleActivity.this)
                        .setBackground(R.drawable.ic_launcher_background)
//                        .setImage(R.mipmap.icon_img)
                        .setText("删除")
                        .setWidth(width)
                        .setHeight(height);
                SwipeMenuItem addItem=new SwipeMenuItem(SwipeRecycleActivity.this)
                        .setBackground(R.color.c10)
                        .setText("添加")
                        .setWidth(width)
                        .setHeight(height);
                SwipeMenuItem editItem=new SwipeMenuItem(SwipeRecycleActivity.this)
                        .setBackground(R.color.colorAccent)
                        .setText("编辑")
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem);
                swipeRightMenu.addMenuItem(editItem);
                swipeRightMenu.addMenuItem(deleteItem);
            }
        };
        swipeMenuItemClickListener = new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection();//左边还是右边菜单
                int adapterPosition = menuBridge.getAdapterPosition(); // ecyclerView的Item的position。
                int position = menuBridge.getPosition();   // 菜单在左边或右边添加几个菜单的下标
                if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                    if (position == 0) {
                        Toast.makeText(SwipeRecycleActivity.this, "添加新条目", Toast.LENGTH_LONG).show();
                        list.add("添加-=-=-=-=-=-=-=-=数据");
                        listAdapter.notifyDataSetChanged();
                    } else if(position==1){
                        Toast.makeText(SwipeRecycleActivity.this, "编辑第" + adapterPosition + "个条目", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(SwipeRecycleActivity.this, "删除第" + adapterPosition + "个条目", Toast.LENGTH_LONG).show();
                        list.remove(adapterPosition);
                        listAdapter.notifyDataSetChanged();
                    }
                }
            }
        };
    }
    private void initdata() {
        for (int i = 0; i < 30; i++) {
            list.add("添加-=-=-=-=-=-=-=-=数据" + i);
        }
    }
}
