package com.example.hin.system;

import com.example.hin.finshActivity.CloseActivityClass;
import com.example.hin.myfragment.FragmentConsult;
import com.example.hin.myfragment.FragmentExperts;
import com.example.hin.myfragment.FragmentMy;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Hin on 2016/5/16.
 */
public class MainActivity extends FragmentActivity {


    private RadioGroup myTabRg;
    private FragmentConsult fragmentConsult;
    private FragmentExperts fragmentExperts;
    private FragmentMy fragmentMy;
    private RadioButton expertDatabase, consult, my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //用于退出程序
        CloseActivityClass.activityList.add(this);
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

    //该方法设置底部Tab图片的大小
    public void setDrawableSize(RadioButton v) {
        Drawable[] drawable = v.getCompoundDrawables();
        int height = (int) (drawable[1].getIntrinsicHeight() / 1.5);
        int width = (int) (drawable[1].getIntrinsicWidth() / 1.5);
        drawable[1].setBounds(0, 0, height, width);
        v.setCompoundDrawables(null, drawable[1], null, null);
    }

}
