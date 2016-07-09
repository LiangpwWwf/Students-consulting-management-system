package com.example.hin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hin.system.R;

import java.util.ArrayList;

/**
 * Created by WWF on 2016/6/6.
 */
public class CommentListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> commentList;

    public CommentListAdapter(Context context, ArrayList<String> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_comment,null);
        }
        holder.tvName= (TextView) convertView.findViewById(R.id.tv_name);
        holder.tvName.setText(commentList.get(position));
        holder.tvZan= (TextView) convertView.findViewById(R.id.tv_zan_count);
        holder.tvComment= (TextView) convertView.findViewById(R.id.tv_comment_text);
        return convertView;
    }

    private class ViewHolder{
        public TextView tvName,tvZan,tvComment;
    }
}
