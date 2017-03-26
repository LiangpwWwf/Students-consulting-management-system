package com.example.hin.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hin.adapter.CollectionAdapter;
import com.example.hin.common.UserPref;
import com.example.hin.entity.Favourite;
import com.example.hin.entity.Post;
import com.example.hin.system.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by WWF on 2017/3/25.
 */

public class MyCollectionActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_content)
    ListView lvContent;

    private boolean isInit = false;
    List<Favourite> data = new ArrayList<>();
    private CollectionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isInit) {
            isInit = true;
            initView();
        }
    }

    private void initView() {
        mAdapter = new CollectionAdapter(MyCollectionActivity.this, data);
        lvContent.setOnItemClickListener(this);
        lvContent.setAdapter(mAdapter);
        BmobQuery<Favourite> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", UserPref.get().get(UserPref.KEY_UID));
        query.setLimit(50);
        query.findObjects(MyCollectionActivity.this, new FindListener<Favourite>() {
            @Override
            public void onSuccess(List<Favourite> list) {
                data.addAll(list);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @OnClick({R.id.iv_back})
    public void OnClick() {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Post post = data.get(i).getPost();
        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", post.getObjectId());
        query.findObjects(MyCollectionActivity.this, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                if (list != null && list.size() > 0) {
                    Post p = list.get(0);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("postObject", p);
                    startActivity(new Intent(MyCollectionActivity.this, QuestionDetailActivity.class).putExtras(bundle));
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
