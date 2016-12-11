package com.example.hin.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.hin.system.R;

import butterknife.OnClick;

/**
 * Created by WWF on 2016/12/11.
 */

public class RegisterActivity extends Activity {

    private boolean isInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isInit) {
            isInit = true;
            initView();
        }
    }

    private void initView() {

    }

    @OnClick({})
    public void OnClick(View view) {
        switch (view.getId()) {
        }
    }
}
