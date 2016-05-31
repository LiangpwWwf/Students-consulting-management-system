package com.example.hin.myadapter;

/**
 * Created by Hin on 2016/5/25.
 */

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hin.system.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ViewPager的适配器
 */
public class ConsultCommonQuestionAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, Object>> mData;
    private LayoutInflater mInflater;


    public ConsultCommonQuestionAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        //判断是否缓存
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.consult_common_question_item, null);
            holder.iv_head = (ImageView) convertView.findViewById(R.id.iv_head);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(holder);
        } else {
            //通过Tag找到缓存布局
            holder = (ViewHolder) convertView.getTag();
        }
        //设置视图
        holder.iv_head.setBackgroundResource(R.drawable.teacher);
        holder.tv_title.setText(mData.get(position).get("tv_title").toString());
        holder.tv_date.setText(mData.get(position).get("tv_date").toString());

        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_head;
        public TextView tv_title, tv_date;
    }
}
