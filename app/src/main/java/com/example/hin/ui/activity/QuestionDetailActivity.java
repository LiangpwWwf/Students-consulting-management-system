package com.example.hin.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hin.adapter.CommentListAdapter;
import com.example.hin.entity.Post;
import com.example.hin.system.R;
import com.example.hin.ui.widget.ListViewForScrollView;

import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by WWF on 2016/6/5.
 */
public class QuestionDetailActivity extends Activity implements View.OnClickListener {

    private ScrollView scrollView;
    private ListViewForScrollView lvComment;
    private TextView tv_title, tv_kind, tv_topic, tv_content, tv_reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        initView();
        getQuestionDetail();

    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_kind = (TextView) findViewById(R.id.tv_kind);
        tv_topic = (TextView) findViewById(R.id.tv_topic);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_reply = (TextView) findViewById(R.id.tv_reply);
        scrollView = (ScrollView) findViewById(R.id.scroll_detail);
        scrollView.smoothScrollTo(0, 0);
        findViewById(R.id.iv_back).setOnClickListener(this);
        lvComment = (ListViewForScrollView) findViewById(R.id.lv_comment);
        ArrayList<String> list = new ArrayList<String>();
        list.add("某用户");
        list.add("小刘");
        list.add("小张");
        list.add("小关");
        list.add("小马");
        list.add("小黄");
        list.add("小徐");
        CommentListAdapter adapter = new CommentListAdapter(this, list);
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

    public void getQuestionDetail() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Post post = (Post) bundle.getSerializable("postObject");
        post.getObjectId();
        tv_title.setText(post.getTitle());
        switch (post.getKind()){
            case 0:
                tv_kind.setText("课程答疑");
                break;
            case 1:
                tv_kind.setText("人际交往");
                break;
            case 2:
                tv_kind.setText("身心健康");
                break;
            case 3:
                tv_kind.setText("就业招聘");
                break;
            case 4:
                tv_kind.setText("发展规划");
                break;
            case 5:
                tv_kind.setText("其他");
                break;
        }
        tv_topic.setText(post.getTopic());
        tv_content.setText(post.getContent());
        tv_reply.setText(post.getReply());
        Toast.makeText(QuestionDetailActivity.this, post.getObjectId(), Toast.LENGTH_SHORT).show();
        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
        post.getCreatedAt();

    }
}