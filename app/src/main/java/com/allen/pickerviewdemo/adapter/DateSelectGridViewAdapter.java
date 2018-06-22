package com.allen.pickerviewdemo.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.allen.pickerviewdemo.R;
import com.allen.pickerviewdemo.model.DateModel;

import java.util.List;

public class DateSelectGridViewAdapter extends BaseAdapter {
    private Resources mResources;
    private LayoutInflater mInflater;
    private List<DateModel> mData;

    public DateSelectGridViewAdapter(Context context, List<DateModel> mData) {
        this.mResources = context.getResources();
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return (null != mData) ? mData.size() : 0;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_select_date_grid_view, null);
            viewHolder.mTxtDate = convertView.findViewById(R.id.item_txt_date_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DateModel dateModel = mData.get(position);
        viewHolder.mTxtDate.setText(dateModel.getName());
        if (dateModel.isSelected()) {
            viewHolder.mTxtDate.setTextColor(mResources.getColor(R.color.ui_color_blue_00a0e9));
        } else {
            viewHolder.mTxtDate.setTextColor(mResources.getColor(R.color.ui_color_black_333333));
        }
        return convertView;
    }

    class ViewHolder {
        TextView mTxtDate;
    }
}
