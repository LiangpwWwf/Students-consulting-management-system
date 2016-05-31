package com.example.hin.myfragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.example.hin.mytoptabview.MyTopTabView;
import com.example.hin.system.R;
import com.example.hin.system.SearchActivity;

/**
 * Created by Hin on 2016/5/25.
 */
public class FragmentExperts extends Fragment {

    private View view;
    private ImageView iv_searvh;
    private MyTopTabView myTopTabView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.myfragment_topcontent, null);
        iv_searvh = (ImageView) view.findViewById(R.id.iv_search);
        iv_searvh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivityForResult(intent,0);
            }
        });
        myTopTabView=new MyTopTabView(getContext());
        myTopTabView.init(savedInstanceState, view, 0);
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
