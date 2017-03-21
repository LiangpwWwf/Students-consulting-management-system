package com.example.hin.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.hin.common.UserPref;
import com.example.hin.entity.User;

import butterknife.ButterKnife;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by WWF on 2017/3/19.
 */

public class SplashActivity extends BaseActivity {

    private boolean isInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
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
        if (UserPref.get().get(UserPref.KEY_USERNAME) != null && !UserPref.get().get(UserPref.KEY_USERNAME).isEmpty() &&
                UserPref.get().get(UserPref.KEY_PWD) != null && !UserPref.get().get(UserPref.KEY_PWD).isEmpty()) {
            User user = new User();
            user.setUsername(UserPref.get().get(UserPref.KEY_USERNAME));
            user.setPassword(UserPref.get().get(UserPref.KEY_PWD));
            user.login(SplashActivity.this, new SaveListener() {
                @Override
                public void onSuccess() {
                    UserPref.get().syncProfile(User.getCurrentUser(SplashActivity.this, User.class));
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            });
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    }
}
