package com.example.hin.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hin.entity.Post;
import com.example.hin.myadapter.ConsultCommonQuestionAdapter;
import com.example.hin.system.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Hin on 2016/5/16.
 */
public class ConsultCommonQuestionActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    private ListView lv_commomquestion;

    List<Post> post;
    ArrayList<HashMap<String, Object>> list;
    HashMap<String, Object> question;
    private ConsultCommonQuestionAdapter consultCommonQuestionAdapter;
    private SwipeRefreshLayout swipeLayout;
    private ListView listView;
    private boolean isRefresh = false;//是否刷新中

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_question);

        iniView();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");
        list = new ArrayList<HashMap<String, Object>>();
        question = new HashMap<String, Object>();
        selectPart(id);

    }

    public void iniView() {
        lv_commomquestion = (ListView) findViewById(R.id.lv_commomquestion);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        //加载颜色是循环播放的，只要没有完成刷新就会一直循环，color1>color2>color3>color4
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

    }

    public void selectPart(int position) {
        switch (position) {
            case 0:
                getNetmessage(0);
                break;
            case 1:
                getNetmessage(1);
                break;
            case 2:
                getNetmessage(2);
                break;
            case 3:
                getNetmessage(3);
                break;
            case 4:
                getNetmessage(4);
                break;
            case 5:
                getNetmessage(5);
                break;
        }
    }

    public void getNetmessage(int label) {

        BmobQuery<Post> query = new BmobQuery<Post>();

        query.addWhereEqualTo("kind", label);
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
//执行查询方法
        query.findObjects(this, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                if (list.size() > 0) {
                    Toast.makeText(ConsultCommonQuestionActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
                    consultCommonQuestionAdapter = new ConsultCommonQuestionAdapter(ConsultCommonQuestionActivity.this, list);
                    lv_commomquestion.setAdapter(consultCommonQuestionAdapter);
                } else {
                    Toast.makeText(ConsultCommonQuestionActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(ConsultCommonQuestionActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        if (!isRefresh) {
            isRefresh = true;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    swipeLayout.setRefreshing(false);
                    Intent intent = getIntent();
                    Bundle bundle = intent.getExtras();
                    int id = bundle.getInt("id");
                    selectPart(id);
                }
            }, 3000);
        }
    }

}
