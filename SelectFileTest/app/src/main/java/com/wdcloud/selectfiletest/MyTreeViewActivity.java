package com.wdcloud.selectfiletest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wdcloud.selectfiletest.adapter.ListAdapter;
import com.wdcloud.selectfiletest.recycleAdapterUtil.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyTreeViewActivity extends AppCompatActivity {

    private List<String> first;
    private List<String> second;
    private RecyclerView recyclerView;
    private RecyclerView srecyclerView;
    private List<String> list;
//    private Button btn;
    private Boolean pddata=true;
    private ListAdapter listAdapter;
    private ListAdapter slistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tree_view);
        first = new ArrayList<>();
        second = new ArrayList<>();
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleview);
        srecyclerView=findViewById(R.id.srecycleview);
        LinearLayout firstlinearLayout = findViewById(R.id.first_linear);
        LinearLayout secondlinearLayout = findViewById(R.id.sencond);
        firstlinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyTreeViewActivity.this,"点击的是第一个LinearLayout",Toast.LENGTH_LONG).show();
                Log.d("第一个布局","点击打印");
            }
        });
        secondlinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyTreeViewActivity.this,"点击的是第二个LinearLayout",Toast.LENGTH_LONG).show();
                Log.d("第二个布局","点击打印");
            }
        });
//        btn = findViewById(R.id.btn);
        initdata();
        setAdapter();
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pddata=!pddata;
//                if(pddata==true)
//                {
//                    list.clear();
//                    list.addAll(first);
//                }
//                else
//                {
//                    list.clear();
//                    list.addAll(second);
//                }
//                listAdapter.notifyDataSetChanged();
//            }
//        });
    }

    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager slinearLayoutManager = new LinearLayoutManager(this);
        slinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        srecyclerView.setLayoutManager(slinearLayoutManager);
        listAdapter = new ListAdapter(R.layout.layout_list_item,first);
        slistAdapter = new ListAdapter(R.layout.layout_list_item,second);
        srecyclerView.setAdapter(slistAdapter);
        recyclerView.setAdapter(listAdapter);
        slistAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MyTreeViewActivity.this,"点击第一种数据"+position,Toast.LENGTH_LONG).show();
            }
        });
        listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MyTreeViewActivity.this,"点击第二种数据"+position,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initdata() {
        for (int i = 0; i <30; i++) {
            first.add("第一种数据"+i);
            second.add("第二种数据"+i);
        }
    }
}
