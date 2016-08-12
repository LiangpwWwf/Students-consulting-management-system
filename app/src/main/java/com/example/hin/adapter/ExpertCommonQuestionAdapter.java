package com.example.hin.adapter;

/**
 * Created by Hin on 2016/5/25.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hin.entity.Experts;
import com.example.hin.system.R;
import com.example.hin.ui.activity.ExpertsActivity;
import com.example.hin.ui.activity.QuestionDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ViewPager的适配器
 */
public class ExpertCommonQuestionAdapter extends BaseAdapter {

    private List<Experts> mData;
    private LayoutInflater mInflater;
    private Context context;


    public ExpertCommonQuestionAdapter(Context c, List<Experts> data) {
        context = c;
        this.mData = data;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        //判断是否缓存
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.experts_common_question_item, null);
            convertView.findViewById(R.id.item_expert).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("expertsObject", mData.get(position));
                    Intent intent = new Intent(context, ExpertsActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            holder.iv_head = (ImageView) convertView.findViewById(R.id.iv_head);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_field = (TextView) convertView.findViewById(R.id.tv_field);
            convertView.setTag(holder);
        } else {
            //通过Tag找到缓存布局
            holder = (ViewHolder) convertView.getTag();
        }
        //设置视图
        holder.iv_head.setImageResource(R.drawable.head);
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_field.setText(mData.get(position).getStudy());

        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_head;
        public TextView tv_name, tv_field;
    }
}
