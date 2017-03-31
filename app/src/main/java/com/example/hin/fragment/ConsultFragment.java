package com.example.hin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hin.adapter.ConsultAdapter;
import com.example.hin.common.UserPref;
import com.example.hin.entity.Post;
import com.example.hin.system.R;
import com.example.hin.ui.activity.QuestionDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by WWF on 2017/3/27.
 */

public class ConsultFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_content)
    ListView lvContent;

    private boolean reply;
    private List<Post> data = new ArrayList<>();
    private ConsultAdapter mAdapter;

    public static ConsultFragment newInstance(boolean reply) {
        ConsultFragment fragment = new ConsultFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        bundle.putBoolean("reply", reply);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reply = getArguments().getBoolean("reply");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_consult, container, false);
        ButterKnife.bind(this, mRootView);
        ViewGroup p = (ViewGroup) mRootView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        initView();
        return mRootView;
    }

    private void initView() {
        mAdapter = new ConsultAdapter(getActivity(), data);
        lvContent.setAdapter(mAdapter);
        lvContent.setOnItemClickListener(this);
        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", UserPref.get().get(UserPref.KEY_UID));
        query.setLimit(50);
        query.findObjects(getActivity(), new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                if (list != null) {
                    for (Post p : list) {
                        if (reply && p.getExpertReply() != null) {
                            data.add(p);
                        } else if (!reply && p.getExpertReply() == null) {
                            data.add(p);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Post post = data.get(i);
        if (post != null) {
            startActivity(new Intent(getActivity(), QuestionDetailActivity.class).putExtra("postObject", post));
        }
    }
}
