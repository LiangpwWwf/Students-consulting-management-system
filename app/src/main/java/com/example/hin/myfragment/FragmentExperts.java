package com.example.hin.myfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.example.hin.mytoptabview.MyTopTabView;
import com.example.hin.system.R;

/**
 * Created by Hin on 2016/5/25.
 */
public class FragmentExperts extends Fragment {

    private View view;
    private MyTopTabView myTopTabView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.myfragment_topcontent, null);
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
