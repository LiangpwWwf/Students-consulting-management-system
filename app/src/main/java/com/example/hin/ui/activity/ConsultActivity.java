package com.example.hin.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hin.Consts.LocalStringConst;
import com.example.hin.entity.Post;
import com.example.hin.system.R;
import com.example.hin.ui.widget.SpinnerWindow.AbstractSpinerAdapter;
import com.example.hin.ui.widget.SpinnerWindow.SpinerPopWindow;
import com.example.hin.ui.widget.SpinnerWindow.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by WWF on 2016/6/4.
 */
public class ConsultActivity extends Activity implements View.OnClickListener, AbstractSpinerAdapter.IOnItemSelectListener {

    private TextView tvExpert;
    private AbstractSpinerAdapter adapter;
    private List<String> name = new ArrayList<String>();
    private SpinerPopWindow mSpinerPopWindow;
    private int star[] = {R.id.iv_star_one, R.id.iv_star_two, R.id.iv_star_three};
    int flag;
    private EditText et_title, et_content;
    private TextView tv_kind, tv_topic, tv_expert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        initView();
    }

    public void initView() {
        findViewById(R.id.btn_upload).setOnClickListener(this);
        tvExpert = (TextView) findViewById(R.id.tv_expert);
        tvExpert.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        for (int i : star) {
            findViewById(i).setOnClickListener(this);
        }
        name.add("小华");
        name.add("小周");
        name.add("小黄");
        name.add("小刘");
        mSpinerPopWindow = new SpinerPopWindow(this);

        adapter = new SpinnerAdapter(this);
        adapter.refreshData(name, 0);
        mSpinerPopWindow.setAdatper(adapter);
        mSpinerPopWindow.setItemListener(this);

        findViewById(R.id.iv_send).setOnClickListener(this);

        et_title = (EditText) findViewById(R.id.et_title);
        tv_kind = (TextView) findViewById(R.id.tv_kind);
        tv_topic = (TextView) findViewById(R.id.tv_topic);
        tv_expert = (TextView) findViewById(R.id.tv_expert);
        et_content = (EditText) findViewById(R.id.et_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_upload:
                new AlertDialog.Builder(this).setItems(new String[]{"上传图片", "上传文档"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                break;
                            case 1:
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.tv_expert:
                mSpinerPopWindow.setWidth(tvExpert.getWidth());
                mSpinerPopWindow.showAsDropDown(tvExpert);
                break;
            case R.id.iv_star_one:
                flag = 1;
                setEmergent();
                break;
            case R.id.iv_star_two:
                flag = 2;
                setEmergent();
                break;
            case R.id.iv_star_three:
                flag = 3;
                setEmergent();
                break;
            case R.id.iv_send:
                Post post = new Post();
                if (!(et_title.getText().toString().equals(""))) {
                    post.setTitle(et_title.getText().toString());
                    post.setKind(1);
                    post.setTopic(tv_topic.getText().toString());
                    post.setExpert(tv_expert.getText().toString());
                    if (!(et_content.getText().toString().equals(""))) {
                        post.setContent(et_content.getText().toString());
                        post.save(ConsultActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {

                                Toast.makeText(ConsultActivity.this, "success", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(ConsultActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(ConsultActivity.this, "内容不能为空哦!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(ConsultActivity.this, "标题不能为空哦！", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }

    private void setEmergent() {
        int i;
        for (i = 0; i < flag; i++) {
            ((ImageView) findViewById(star[i])).setImageResource(R.mipmap.star_on);
        }
        for (; i < star.length; i++) {
            ((ImageView) findViewById(star[i])).setImageResource(R.mipmap.star);
        }
    }

    @Override
    public void onItemClick(int pos) {
        startActivityForResult(new Intent(this, ExpertsActivity.class)
                , LocalStringConst.REQUEST_CODE_EXPERT_VIEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LocalStringConst.REQUEST_CODE_EXPERT_VIEW && resultCode == RESULT_OK) {
        }
    }
}
