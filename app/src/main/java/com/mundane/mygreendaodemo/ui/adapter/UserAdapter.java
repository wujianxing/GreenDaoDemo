package com.mundane.mygreendaodemo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.mundane.mygreendaodemo.R;
import com.mundane.mygreendaodemo.entity.User;
import java.util.List;

/**
 * Created by mundane on 2016/10/16.
 */

public class UserAdapter extends BaseAdapter {
    private List<User> mUserList;
    private LayoutInflater mLayoutInflater;


    public UserAdapter(List<User> userList, Context context) {
        mUserList = userList;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mUserList.size();
    }


    @Override
    public User getItem(int position) {
        return mUserList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lv_user, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        User user = getItem(position);
        holder.tvId.setText(user.getId()+"");
        holder.tvName.setText(user.getName());
        holder.tvAge.setText(user.getAge());
        holder.tvGender.setText(user.getGender());
        holder.tvSalary.setText(user.getSalary());
        return convertView;
    }


    static class ViewHolder {
        TextView tvName;
        TextView tvAge;
        TextView tvGender;
        TextView tvSalary;
        TextView tvId;
        public ViewHolder(View view) {
            tvId = (TextView) view.findViewById(R.id.tv_id);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvAge = (TextView) view.findViewById(R.id.tv_age);
            tvGender = (TextView) view.findViewById(R.id.tv_gender);
            tvSalary = (TextView) view.findViewById(R.id.tv_salary);
        }
    }



}
