package com.example.hin.ui.activity;

import com.example.hin.myfragment.FragmentConsult;
import com.example.hin.myfragment.FragmentExperts;
import com.example.hin.myfragment.FragmentMy;
import com.example.hin.system.R;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Hin on 2016/5/16.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {


    private RadioGroup myTabRg;
    private FragmentConsult fragmentConsult;
    private FragmentExperts fragmentExperts;
    private FragmentMy fragmentMy;
    private RadioButton expertDatabase, consult, my;
    private ImageView search, jiahao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //用于退出程序
        CloseActivity.activityList.add(this);

        iniView();
        iniListener();


        fragmentConsult = new FragmentConsult();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentConsult).commit();
        myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
        myTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.expertDatabase:
                        fragmentExperts = new FragmentExperts();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentExperts)
                                .commit();
                        break;
                    case R.id.consult:
                        fragmentConsult = new FragmentConsult();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentConsult).commit();
                        break;
                    case R.id.my:
                        fragmentMy = new FragmentMy();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentMy).commit();
                        break;
                    default:
                        break;
                }
            }
        });

        expertDatabase = (RadioButton) findViewById(R.id.expertDatabase);
        consult = (RadioButton) findViewById(R.id.consult);
        my = (RadioButton) findViewById(R.id.my);
        setDrawableSize(expertDatabase);
        setDrawableSize(consult);
        setDrawableSize(my);


    }

    public void iniView() {
        search = (ImageView) findViewById(R.id.iv_search);
        jiahao = (ImageView) findViewById(R.id.jiahao);

    }

    public void iniListener() {
        search.setOnClickListener(this);
        jiahao.setOnClickListener(this);
    }

    //该方法设置底部Tab图片的大小
    public void setDrawableSize(RadioButton v) {
        Drawable[] drawable = v.getCompoundDrawables();
        int height = (int) (drawable[1].getIntrinsicHeight() / 1.5);
        int width = (int) (drawable[1].getIntrinsicWidth() / 1.5);
        drawable[1].setBounds(0, 0, height, width);
        v.setCompoundDrawables(null, drawable[1], null, null);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.jiahao:
                startActivityForResult(new Intent(MainActivity.this, ConsultActivity.class), 0);
                break;
            default:
                break;
        }
    }
}
