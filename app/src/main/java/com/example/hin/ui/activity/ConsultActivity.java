package com.example.hin.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hin.Consts.LocalStringConst;
import com.example.hin.system.R;
import com.example.hin.ui.widget.SpinnerWindow.AbstractSpinerAdapter;
import com.example.hin.ui.widget.SpinnerWindow.SpinerPopWindow;
import com.example.hin.ui.widget.SpinnerWindow.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

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
        startActivityForResult(new Intent(this,ExpertsActivity.class)
                ,LocalStringConst.REQUEST_CODE_EXPERT_VIEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==LocalStringConst.REQUEST_CODE_EXPERT_VIEW&&resultCode==RESULT_OK){
        }
    }
}
