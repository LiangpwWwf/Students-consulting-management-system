package com.example.hin.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hin.system.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WWF on 2016/12/11.
 */

public class RegisterActivity extends Activity {

    @BindView(R.id.et_phone_num)
    EditText etPhone;
    @BindView(R.id.et_captcha)
    EditText etCaptcha;
    @BindView(R.id.tv_get_captcha)
    TextView tvGetCaptcha;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_register)
    Button btnRegister;

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
        etPhone.addTextChangedListener(new RegisterTextWatcher());
        etCaptcha.addTextChangedListener(new RegisterTextWatcher());
        etPwd.addTextChangedListener(new RegisterTextWatcher());
    }

    @OnClick({R.id.btn_register, R.id.tv_get_captcha})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                break;
            case R.id.tv_get_captcha:
                break;
        }
    }

    private class RegisterTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(etPhone.getText().toString().trim())
                    && !TextUtils.isEmpty(etCaptcha.getText().toString().trim())
                    && !TextUtils.isEmpty(etPwd.getText().toString().trim())) {
                btnRegister.setBackgroundResource(R.drawable.bg_round_orange);
                btnRegister.setEnabled(true);
            } else {
                btnRegister.setBackgroundResource(R.drawable.bg_round_gray);
                btnRegister.setEnabled(false);
            }
        }
    }
}
