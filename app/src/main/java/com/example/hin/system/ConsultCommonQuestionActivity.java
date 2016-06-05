package com.example.hin.system;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.hin.finshActivity.CloseActivityClass;
import com.example.hin.myadapter.ConsultCommonQuestionAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hin on 2016/5/16.
 */
public class ConsultCommonQuestionActivity extends Activity {


    private ListView lv_commomquestion;

    ArrayList<HashMap<String, Object>> list;
    HashMap<String, Object> question;
    private ConsultCommonQuestionAdapter consultCommonQuestionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_question);
        //用于退出程序
        CloseActivityClass.activityList.add(this);

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

    }

    public void selectPart(int position) {
        switch (position) {
            case 0:
                question.put("tv_title", "数学分析Cauchy准则怎么证明？");
                question.put("tv_date", "2015年12月10日");
                list.add(question);
                break;
            case 1:
                question.put("tv_title", "想脱单怎么办？");
                question.put("tv_date", "2015年12月10日");
                list.add(question);
                break;
            case 2:
                question.put("tv_title", "头痛怎么破？");
                question.put("tv_date", "2015年12月10日");
                list.add(question);
                break;
            case 3:
                question.put("tv_title", "想找一份实习？");
                question.put("tv_date", "2015年12月10日");
                list.add(question);
                break;
            case 4:
                question.put("tv_title", "以后想当老师？");
                question.put("tv_date", "2015年12月10日");
                list.add(question);
                break;
            case 5:
                question.put("tv_title", "二手交易？");
                question.put("tv_date", "2015年12月10日");
                list.add(question);
                break;
        }
        consultCommonQuestionAdapter = new ConsultCommonQuestionAdapter(this, list);
        lv_commomquestion.setAdapter(consultCommonQuestionAdapter);

    }
}
