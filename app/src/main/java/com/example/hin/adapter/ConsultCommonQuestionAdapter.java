package com.example.hin.adapter;

/**
 * Created by Hin on 2016/5/25.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hin.entity.Post;
import com.example.hin.entity.User;
import com.example.hin.system.R;
import com.example.hin.ui.activity.QuestionDetailActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * ViewPager的适配器
 */
public class ConsultCommonQuestionAdapter extends BaseAdapter {

    private Context context;
    private List<Post> mData;
    private LayoutInflater mInflater;
    private List<String> avatarList;


    public ConsultCommonQuestionAdapter(Context context, List<Post> data,List<String> avatarList) {
        this.context = context;
        this.mData = data;
        this.avatarList=avatarList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        final int pos=position;
        //判断是否缓存
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.consult_common_question_item, null);
            convertView.findViewById(R.id.item_question);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("postObject", mData.get((Integer) holder.tv_title.getTag()));
                    Intent intent = new Intent(context, QuestionDetailActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            holder.iv_head = (SimpleDraweeView) convertView.findViewById(R.id.iv_head);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            if (mData.get(position).getExpertReply() != null) {
                convertView.findViewById(R.id.item_question).setBackgroundResource(R.drawable.editext_bg_gray);
            } else {
                convertView.findViewById(R.id.item_question).setBackgroundResource(R.drawable.editext_bg);

            }
            convertView.setTag(holder);
        } else {
            //通过Tag找到缓存布局
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv_head.setImageURI(avatarList.get(position));
        holder.tv_title.setText(mData.get(position).getTitle());
        holder.tv_date.setText(mData.get(position).getCreatedAt());
        holder.tv_title.setTag(position);

        return convertView;
    }

    public final class ViewHolder {
        public SimpleDraweeView iv_head;
        public TextView tv_title, tv_date;
    }
}
