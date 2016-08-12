package com.example.hin.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hin.adapter.ConsultCommonQuestionAdapter;
import com.example.hin.adapter.ExpertCommonQuestionAdapter;
import com.example.hin.system.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hin on 2016/5/24.
 */
public class SearchActivity extends Activity implements View.OnClickListener {

    private ImageView iv_back, iv_search;
    private RadioGroup rg_class;
    private RadioButton rbtn_topic, rbtn_expert;
    private ListView lv_content;
    private EditText et_search;

    private ArrayList<HashMap<String, Object>> topicList, expertList;
    private HashMap<String, Object> topicQuestion, expertQuestion;
    private ConsultCommonQuestionAdapter consultCommonQuestionAdapter;
    private ExpertCommonQuestionAdapter expertCommonQuestionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //用于退出程序
        CloseActivity.activityList.add(this);
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


        for (int i = 0; i < 10; i++) {
            topicQuestion.put("tv_title", "数学分析Cauchy准则怎么证明？");
            topicQuestion.put("tv_date", "2015年12月10日");
            topicList.add(topicQuestion);
        }
        for (int i = 0; i < 10; i++) {
            expertQuestion.put("tv_name", "小纯");
            expertQuestion.put("tv_field", "数学");
            expertList.add(expertQuestion);
        }

/*
        consultCommonQuestionAdapter = new ConsultCommonQuestionAdapter(SearchActivity.this, topicList);
        expertCommonQuestionAdapter = new ExpertCommonQuestionAdapter(SearchActivity.this, expertList);*/

        lv_content.setAdapter(consultCommonQuestionAdapter);
    }

    //获取控件ID
    public void iniView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rg_class = (RadioGroup) findViewById(R.id.rg_class);
        rbtn_topic = (RadioButton) findViewById(R.id.rtbn_topic);
        rbtn_expert = (RadioButton) findViewById(R.id.rtbn_expert);
        lv_content = (ListView) findViewById(R.id.lv_content);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        et_search = (EditText) findViewById(R.id.et_search);

    }

    //监听事件
    public void iniListener() {
        iv_back.setOnClickListener(this);
        rg_class.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rtbn_topic:
                        lv_content.setAdapter(consultCommonQuestionAdapter);
                        break;
                    case R.id.rtbn_expert:
                        lv_content.setAdapter(expertCommonQuestionAdapter);
                        break;
                    default:
                        break;
                }
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            /**
             * @param s
             * @param start
             * @param before
             * @param count
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int checkId = rg_class.getCheckedRadioButtonId();
                switch (checkId) {
                    case R.id.rtbn_topic:
                        String content = s.toString();
                        Pattern p = Pattern.compile(content);
                        ArrayList<HashMap<String, Object>> List = new ArrayList<HashMap<String, Object>>();
                        for (int i = 0; i < topicList.size(); i++) {
                            HashMap<String, Object> item = topicList.get(i);
                            Matcher matcher = p.matcher(item.get("tv_title").toString());
                            if (matcher.find()) {
                                List.add(item);
                            }
                        }
                     /*   ConsultCommonQuestionAdapter adapter = new ConsultCommonQuestionAdapter(SearchActivity.this, List);*/
                   /*     lv_content.setAdapter(adapter);*/
                        break;
                    case R.id.rtbn_expert:
                        String expert_content = s.toString();
                        Pattern p_expert = Pattern.compile(expert_content);
                        ArrayList<HashMap<String, Object>> List_expert = new ArrayList<HashMap<String, Object>>();
                        for (int i = 0; i < topicList.size(); i++) {
                            HashMap<String, Object> item = expertList.get(i);
                            Matcher matcher = p_expert.matcher(item.get("tv_name").toString()+item.get("tv_field").toString());
                            if (matcher.find()) {
                                List_expert.add(item);
                            }
                        }
                  /*      ExpertCommonQuestionAdapter adapter_expert = new ExpertCommonQuestionAdapter(SearchActivity.this, List_expert);
                        lv_content.setAdapter(adapter_expert);*/
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
