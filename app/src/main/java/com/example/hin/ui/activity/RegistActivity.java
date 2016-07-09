package com.example.hin.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hin.system.R;

public class RegistActivity extends Activity {

    private Button Regist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        //用于退出程序
        CloseActivity.activityList.add(this);
        Regist=(Button)findViewById(R.id.Regist);
        Regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegistActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
