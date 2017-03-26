package com.example.hin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hin.StudentSystemApp;
import com.example.hin.entity.Favourite;
import com.example.hin.entity.Post;
import com.example.hin.system.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by WWF on 2017/3/25.
 */

public class CollectionAdapter extends BaseAdapter {

    private Context context;
    private List<Favourite> data;

    public CollectionAdapter(Context context, List<Favourite> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if (convertView == null || convertView.getTag() == null) {
            vh = new ViewHolder();
            vh.initView();
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.initData(i);
        return vh.viewParent;
    }

    public class ViewHolder {

        View viewParent;
        TextView tvTitle;
        TextView tvContent;


        public ViewHolder() {
        }

        void initView() {
            viewParent = StudentSystemApp.getView(R.layout.item_favourite);
            tvTitle = (TextView) viewParent.findViewById(R.id.tv_title);
            tvContent = (TextView) viewParent.findViewById(R.id.tv_content);
            viewParent.setTag(this);
        }

        void initData(int position) {
            Favourite favourite = data.get(position);
            if (favourite != null) {
                Post post = favourite.getPost();
                if (post != null) {
                    BmobQuery<Post> query = new BmobQuery<>();
                    query.addWhereEqualTo("objectId", post.getObjectId());
                    query.findObjects(context, new FindListener<Post>() {
                        @Override
                        public void onSuccess(List<Post> list) {
                            if (list != null && list.size() > 0) {
                                Post p = list.get(0);
                                tvTitle.setText(p.getTitle());
                                tvContent.setText(p.getContent());
                            }
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                }
            }
        }

    }
}
