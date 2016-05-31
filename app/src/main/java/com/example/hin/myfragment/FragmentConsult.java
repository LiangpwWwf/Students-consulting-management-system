package com.example.hin.myfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
public class FragmentConsult extends Fragment {


    private View view;
    private ImageView search;
    private MyTopTabView myTopTabView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.myfragment_topcontent, null);
        search = (ImageView) view.findViewById(R.id.iv_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivityForResult(intent,0);
            }
        });
        myTopTabView = new MyTopTabView(getContext());
        myTopTabView.init(savedInstanceState, view, 1);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
      //  myTopTabView.initImage(LayoutInflater.from(getActivity()).inflate(R.layout.myfragment_topcontent, null));
    }

    @Override
    public void onPause() {
        super.onPause();

    }

}
