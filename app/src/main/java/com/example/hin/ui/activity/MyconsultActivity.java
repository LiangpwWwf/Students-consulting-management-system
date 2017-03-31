package com.example.hin.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hin.adapter.ConsultCommonQuestionAdapter;
import com.example.hin.fragment.ConsultFragment;
import com.example.hin.system.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;

/**
 * Created by Hin on 2016/5/24.
 */
public class MyconsultActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView iv_back;
    private RadioGroup rg_class;
    private RadioButton rbtn_consult, rbtn_reply;

    private String TAB_CONSULT = "tab_consult";
    private String TAB_REPLY = "tab_reply";
    private String currentIndex;
    private Fragment currentFragment;
    private FragmentManager manager;
    private ConsultFragment consultFragment;
    private ConsultFragment replyFragment;
    private boolean isInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myconsult);
        ButterKnife.bind(this);
        //用于退出程序
        CloseActivity.activityList.add(this);
        iniView();
        iniListener();
        init();


    }

    //初始化数据，主要是adapter数据的初始化
    public void init() {
        consultFragment = ConsultFragment.newInstance(false);
        replyFragment = ConsultFragment.newInstance(true);
        manager = getSupportFragmentManager();
        initFragment();
    }

    private void initFragment() {
        Fragment f = manager.findFragmentByTag(TAB_CONSULT);
        if (f != null) {
            consultFragment = (ConsultFragment) f;
        } else {
            consultFragment = ConsultFragment.newInstance(false);
        }
        f = manager.findFragmentByTag(TAB_REPLY);
        if (f != null) {
            replyFragment = (ConsultFragment) f;
        } else {
            replyFragment = ConsultFragment.newInstance(true);
        }
    }

    //获取控件ID
    public void iniView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rg_class = (RadioGroup) findViewById(R.id.rg_class);
        rbtn_consult = (RadioButton) findViewById(R.id.rbtn_consult);
        rbtn_reply = (RadioButton) findViewById(R.id.rbtn_reply);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isInit) {
            isInit = true;
            switchFragment(TAB_CONSULT);
        }
    }

    //监听事件
    public void iniListener() {
        iv_back.setOnClickListener(this);
        rg_class.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_consult:
                        switchFragment(TAB_CONSULT);
                        break;
                    case R.id.rbtn_reply:
                        switchFragment(TAB_REPLY);
                        break;
                    default:
                        break;
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

    private void switchFragment(String tab) {
        if (TextUtils.equals(tab, currentIndex) || null == manager) {
            return;
        }
        boolean isValid = false;
        FragmentTransaction mFragmentTransaction = manager.beginTransaction();
        if (mFragmentTransaction == null) {
            return;
        }
        hideFragment(mFragmentTransaction);
        if (TextUtils.equals(tab, TAB_CONSULT) && consultFragment != null) {
            if (consultFragment.isAdded()) {
                mFragmentTransaction.show(consultFragment);
            } else {
                mFragmentTransaction.add(R.id.view_container, consultFragment, TAB_CONSULT);
            }
            currentFragment = consultFragment;
            isValid = true;
        } else if (TextUtils.equals(tab, TAB_REPLY) && replyFragment != null) {
            if (replyFragment.isAdded()) {
                mFragmentTransaction.show(replyFragment);
            } else {
                mFragmentTransaction.add(R.id.view_container, replyFragment, TAB_REPLY);
            }
            currentFragment = replyFragment;
            isValid = true;
        }
        if (isValid) {
            mFragmentTransaction.commitAllowingStateLoss();
            currentIndex = tab;
        }
    }

    private void hideFragment(FragmentTransaction mFragmentTransaction) {
        if (currentFragment != null) {
            mFragmentTransaction.hide(currentFragment);
        }
    }

}
