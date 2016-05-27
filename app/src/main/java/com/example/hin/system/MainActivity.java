package com.example.hin.system;

import android.app.Activity;

import com.example.hin.myadapter.MyPagerAdapter;
import com.example.hin.myfragment.FragmentConsult;
import com.example.hin.myfragment.FragmentExperts;
import com.example.hin.myfragment.FragmentMy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.RadioGroup;

/**
 * Created by Hin on 2016/5/16.
 */
public class MainActivity extends FragmentActivity {



    private RadioGroup myTabRg;
    private FragmentConsult fragmentConsult;
    private FragmentExperts fragmentExperts;
    private FragmentMy fragmentMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentExperts = new FragmentExperts();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentExperts).commit();
        myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
        myTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.expertDatabase:
                        if (fragmentExperts==null) {
                            fragmentExperts =new FragmentExperts();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentExperts)
                                .commit();

                        break;
                    case R.id.consult:
                        if (fragmentConsult==null) {
                            fragmentConsult =new FragmentConsult();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentConsult).commit();

                        break;
                    case R.id.my:
                        if (fragmentMy==null) {
                            fragmentMy =new FragmentMy();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentMy).commit();


                        break;

                    default:
                        break;
                }
            }
        });


    }


}
