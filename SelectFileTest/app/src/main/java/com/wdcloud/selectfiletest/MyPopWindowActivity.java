package com.wdcloud.selectfiletest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wdcloud.selectfiletest.wight.NiceTextView;

public class MyPopWindowActivity extends AppCompatActivity {
    private Boolean isshow=true;
    private NiceTextView niceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pop_window);
//        niceTextView = findViewById(R.id.nicetext);
//        Button btn = findViewById(R.id.btn);
//        int length = niceTextView.getText().length();
//        niceTextView.setTextWidth(length);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isshow=!isshow;
//                niceTextView.setShowLine(isshow);
//            }
//        });
    }
}
