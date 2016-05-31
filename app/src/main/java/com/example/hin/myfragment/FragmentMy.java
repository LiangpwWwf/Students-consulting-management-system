package com.example.hin.myfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hin.mytoptabview.MyTopTabView;
import com.example.hin.system.R;
import com.example.hin.system.SearchActivity;

/**
 * Created by Hin on 2016/5/25.
 */
public class FragmentMy extends Fragment {

    private View view;
    private ImageView iv_search;
    private MyTopTabView myTopTabView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.myfragment_my, null);
        iv_search = (ImageView) view.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivityForResult(intent,0);
            }
        });
        return view;
    }
}
