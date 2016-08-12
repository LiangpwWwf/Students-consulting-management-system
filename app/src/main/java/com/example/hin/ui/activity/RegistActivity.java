package com.example.hin.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hin.system.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class RegistActivity extends BaseActivity implements View.OnClickListener {

    private Button bt_regist;
    private EditText et_account, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        //用于退出程序
        CloseActivity.activityList.add(this);

        findView();
        initOnclick();

    }

    //获得空间ID
    public void findView() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        bt_regist = (Button) findViewById(R.id.bt_regist);
    }
    public void initOnclick(){
        bt_regist.setOnClickListener(this);
    }

    /*
    * 假登陆，测试时直接使用后台提供的登陆账号*/
    public void login() {
        BmobUser bu2 = new BmobUser();
        bu2.setUsername(et_account.getText().toString());
        bu2.setPassword(et_password.getText().toString());
        bu2.login(RegistActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegistActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(RegistActivity.this, "登陆失败"+s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_regist:
                login();
                break;
            default:
                break;
        }
    }


}
