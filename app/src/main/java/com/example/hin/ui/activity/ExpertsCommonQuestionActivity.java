package com.example.hin.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hin.finshActivity.CloseActivityClass;
import com.example.hin.myadapter.ExpertCommonQuestionAdapter;
import com.example.hin.system.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hin on 2016/5/16.
 */
public class ExpertsCommonQuestionActivity extends Activity {


    private ListView lv_commomquestion;
    ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    HashMap<String, Object> question = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_question);
        //用于退出程序
        CloseActivityClass.activityList.add(this);
        iniView();
        iniListener();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");

        selectPart(id);

    }

    public void iniView() {
        lv_commomquestion = (ListView) findViewById(R.id.lv_commomquestion);

    }
    public void iniListener(){

    }

    public void selectPart(int position) {
        switch (position) {
            case 0:
                question.put("tv_name", "小纯");
                question.put("tv_field", "数学");
                list.add(question);
                break;
            case 1:
                question.put("tv_name", "小韦");
                question.put("tv_field", "计算机");
                list.add(question);
                break;
            case 2:
                question.put("tv_name", "小锋");
                question.put("tv_field", "计算机");
                list.add(question);
                break;
            case 3:
                question.put("tv_name", "小昊");
                question.put("tv_field", "数学");
                list.add(question);
                break;
            case 4:
                question.put("tv_name", "小婷");
                question.put("tv_field", "数学");
                list.add(question);
                break;
            case 5:
                question.put("tv_name", "不知名");
                question.put("tv_field", "全能");
                list.add(question);
                break;
        }
        ExpertCommonQuestionAdapter expertCommonQuestionAdapter = new ExpertCommonQuestionAdapter(this, list);
        lv_commomquestion.setAdapter(expertCommonQuestionAdapter);

    }
}
