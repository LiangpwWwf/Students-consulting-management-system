package com.example.hin.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hin.system.R;

/**
 * Created by WWF on 2016/6/5.
 */
public class QuestionDetailActivity extends Activity implements View.OnClickListener{
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        iniView();
        iniListener();
    }
    public void iniView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
    }

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
}
