package com.example.hin.system;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hin.finshActivity.CloseActivityClass;
import com.example.hin.myadapter.ConsultCommonQuestionAdapter;
import com.example.hin.myadapter.ExpertCommonQuestionAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hin on 2016/5/24.
 */
public class MyconsultActivity extends Activity implements View.OnClickListener {

    private ImageView iv_back;
    private RadioGroup rg_class;
    private RadioButton rbtn_consult, rbtn_reply;
    private ListView lv_content;

    private ArrayList<HashMap<String, Object>> topicList, expertList;
    private HashMap<String, Object> topicQuestion, expertQuestion;
    private ConsultCommonQuestionAdapter consultCommonQuestionAdapter;
    private ExpertCommonQuestionAdapter expertCommonQuestionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myconsult);
        //用于退出程序
        CloseActivityClass.activityList.add(this);
        iniView();
        iniListener();
        init();


    }

    //初始化数据，主要是adapter数据的初始化
    public void init() {

        topicList = new ArrayList<HashMap<String, Object>>();
        topicQuestion = new HashMap<String, Object>();
        expertList = new ArrayList<HashMap<String, Object>>();
        expertQuestion = new HashMap<String, Object>();

        topicQuestion.put("tv_title", "数学分析Cauchy准则怎么证明？");
        topicQuestion.put("tv_date", "2015年12月10日");
        topicList.add(topicQuestion);

        expertQuestion.put("tv_name", "小纯");
        expertQuestion.put("tv_field", "数学");
        expertList.add(expertQuestion);

        consultCommonQuestionAdapter = new ConsultCommonQuestionAdapter(MyconsultActivity.this, topicList);
        expertCommonQuestionAdapter = new ExpertCommonQuestionAdapter(MyconsultActivity.this, expertList);

        lv_content.setAdapter(consultCommonQuestionAdapter);
    }

    //获取控件ID
    public void iniView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rg_class = (RadioGroup) findViewById(R.id.rg_class);
        rbtn_consult = (RadioButton) findViewById(R.id.rbtn_consult);
        rbtn_reply = (RadioButton) findViewById(R.id.rbtn_reply);
        lv_content = (ListView) findViewById(R.id.lv_content);

    }

    //监听事件
    public void iniListener() {
        iv_back.setOnClickListener(this);
        rg_class.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_consult:
                        lv_content.setAdapter(consultCommonQuestionAdapter);
                        break;
                    case R.id.rbtn_reply:
                        lv_content.setAdapter(expertCommonQuestionAdapter);
                        break;
                    default:
                        break;
                }
            }
        });
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int rbtn_check_id = rg_class.getCheckedRadioButtonId();
                if (rbtn_check_id == R.id.rtbn_expert) {

                }
                else {

                }
            }
        });
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
