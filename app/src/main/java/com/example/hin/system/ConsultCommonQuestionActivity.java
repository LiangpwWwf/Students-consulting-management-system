package com.example.hin.system;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Hin on 2016/5/16.
 */
public class ConsultCommonQuestionActivity extends Activity {

    private TextView textView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.consult_common_question);

        textView = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");
        textView.setText(id + "");
    }
}
