package com.example.hin.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hin.common.DraftPref;
import com.example.hin.system.R;
import com.example.hin.utils.TimeUtils;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Hin on 2016/5/24.
 */
public class DraftsCollectActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;

    private ImageView iv_back;
    private TextView tv_drafts_collect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drafts_collect);
        ButterKnife.bind(this);
        //用于退出程序
        CloseActivity.activityList.add(this);

        iniView();
        iniListener();
        init();

        Intent it = super.getIntent();
        String info = it.getStringExtra("label");
        if (info.equals("Drafts")) {
            tv_drafts_collect.setText("草稿箱");
        } else {
            tv_drafts_collect.setText("我的收藏");
        }


    }

    //初始化数据，主要是adapter数据的初始化
    public void init() {
        if (DraftPref.get().getBoolean(DraftPref.KEY_HAS_DRAFT)) {
            tvTitle.setText(TimeUtils.milliseconds2String(DraftPref.get().getLong(DraftPref.KEY_TIME), new SimpleDateFormat("yyyy/MM/dd HH:mm")));
            tvContent.setText(DraftPref.get().get(DraftPref.KEY_TITLE));
        } else {
            findViewById(R.id.view_draft).setVisibility(View.GONE);
        }
    }

    //获取控件ID
    public void iniView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_drafts_collect = (TextView) findViewById(R.id.tv_drafts_collect);

    }

    //监听事件
    public void iniListener() {
        iv_back.setOnClickListener(this);

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

    @OnClick({R.id.view_draft})
    public void OnClick() {
        startActivity(new Intent(DraftsCollectActivity.this, ConsultActivity.class));
    }

}
