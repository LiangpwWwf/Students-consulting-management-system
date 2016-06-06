package com.example.hin.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hin.finshActivity.CloseActivityClass;
import com.example.hin.system.R;

public class ExpertsActivity extends Activity implements View.OnClickListener {

    private ImageView iv_back;
    private RelativeLayout rl_achievement;
    private TextView tv_achievement;
    private Button Regist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experts_introduce);
        //用于退出程序
        CloseActivityClass.activityList.add(this);

        iniView();
        iniListener();
    }
    //获取控件ID
    public void iniView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rl_achievement=(RelativeLayout)findViewById(R.id.rl_achievement);
        tv_achievement=(TextView)findViewById(R.id.tv_achievement);

    }
    //监听事件
    public void iniListener() {

        iv_back.setOnClickListener(this);
        rl_achievement.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_achievement:
                new AlertDialog.Builder(this).setTitle("研究成果").setItems(
                        new String[] { "Item1", "Item2","Item1", "Item2" }, null).setNegativeButton(
                        "确定", null).show();
                break;
            default:
                break;
        }

    }
}
