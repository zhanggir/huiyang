package com.example.okhttptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class MyAdapter extends BaseAdapter {
    Context mContext;
    List<Entity> mDatas;

    public MyAdapter(Context mContext, List<Entity> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size()!=0?mDatas.size():0;
    }

    @Override
    public Entity getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_listview,null);
            viewHolder=new MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        viewHolder.mImgLeft.setImageResource(mDatas.get(position).arr);
       viewHolder.mTxt.setText(mDatas.get(position).str);
        viewHolder.mImgRight.setImageResource(R.drawable.ic_chevron_right_black_24dp);
        return convertView;
    }
    class MyViewHolder {
        View itemView;
        ImageView mImgLeft;
        ImageView mImgRight;
        TextView mTxt;
        public MyViewHolder(View itemView) {
            mImgLeft= (ImageView) itemView.findViewById(R.id.img_left);
            mImgRight= (ImageView) itemView.findViewById(R.id.img_right);
            mTxt= (TextView) itemView.findViewById(R.id.txt_center);

        }
    }
}
