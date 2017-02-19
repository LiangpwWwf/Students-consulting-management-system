/**
 *
 */
package com.example.hin.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hin.common.LHImageSelectActivityHelper;
import com.example.hin.system.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author yanry
 *         <p>
 *         2016年2月1日
 */
public class SelectImageActivity extends BaseActivity {

    private LHImageSelectActivityHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageselect_activity_default);
        ButterKnife.bind(this);
        helper = new LHImageSelectActivityHelper(this) {

            @Override
            protected void onCommit() {
                finish();
            }

        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        helper.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.btn_back)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
