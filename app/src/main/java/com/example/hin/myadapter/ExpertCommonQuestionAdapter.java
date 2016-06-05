package com.example.hin.myadapter;

/**
 * Created by Hin on 2016/5/25.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hin.system.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ViewPager的适配器
 */
 public class ExpertCommonQuestionAdapter extends BaseAdapter {

    private ArrayList<HashMap<String,Object>>mData;
    private LayoutInflater mInflater;


    public ExpertCommonQuestionAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
        this.mData=data;
        mInflater=LayoutInflater.from(context);
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

        ViewHolder holder=null;
        //判断是否缓存
        if(convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.experts_common_question_item,null);
            holder.iv_head=(ImageView)convertView.findViewById(R.id.iv_head);
            holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_field=(TextView)convertView.findViewById(R.id.tv_field);
            convertView.setTag(holder);
        }
        else {
            //通过Tag找到缓存布局
            holder=(ViewHolder)convertView.getTag();
        }
        //设置视图
        holder.iv_head.setImageResource(R.drawable.head);
        holder.tv_name.setText(mData.get(position).get("tv_name").toString());
        holder.tv_field.setText(mData.get(position).get("tv_field").toString());

        return convertView;
    }

    public final class ViewHolder{
        public ImageView iv_head;
        public TextView tv_name,tv_field;
    }
}
