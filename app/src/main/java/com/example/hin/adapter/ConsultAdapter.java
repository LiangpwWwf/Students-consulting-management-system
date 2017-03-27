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
 * Created by WWF on 2017/3/27.
 */

public class ConsultAdapter extends BaseAdapter {

    private Context context;
    private List<Post> data;

    public ConsultAdapter(Context context, List<Post> data) {
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
            Post post = data.get(position);
            if (post != null) {
                tvTitle.setText(post.getTitle());
                tvContent.setText(post.getContent());
            }
        }

    }
}
