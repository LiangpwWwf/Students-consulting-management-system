package com.example.hin.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.ImageView;

import com.example.hin.adapter.CommentListAdapter;
import com.example.hin.system.R;
import com.example.hin.ui.widget.ListViewForScrollView;

import java.util.ArrayList;

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
        scrollView.smoothScrollTo(0, 0);
        findViewById(R.id.iv_back).setOnClickListener(this);
        lvComment= (ListViewForScrollView) findViewById(R.id.lv_comment);
        ArrayList<String> list = new ArrayList<String>();
        list.add("某用户");
        list.add("小刘");
        list.add("小张");
        list.add("小关");
        list.add("小马");
        list.add("小黄");
        list.add("小徐");
        CommentListAdapter adapter=new CommentListAdapter(this,list);
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
