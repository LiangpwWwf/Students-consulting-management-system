package com.example.hin.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.ImageView;

import com.example.hin.system.R;
import com.example.hin.ui.widget.ListViewForScrollView;

/**
 * Created by WWF on 2016/6/5.
 */
public class QuestionDetailActivity extends Activity implements View.OnClickListener{

    private ScrollView scrollView;
    private ListViewForScrollView lvComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        initView();
    }

    private void initView() {
        scrollView= (ScrollView) findViewById(R.id.scroll_detail);
        scrollView.smoothScrollTo(0,0);
        findViewById(R.id.iv_back).setOnClickListener(this);
        lvComment= (ListViewForScrollView) findViewById(R.id.lv_comment);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new String[]{"one","two","three",
                "four","five","six","seven","eight","nine","ten"});
        lvComment.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }
}
