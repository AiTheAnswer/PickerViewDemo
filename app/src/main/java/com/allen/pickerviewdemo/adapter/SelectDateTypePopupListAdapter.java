package com.allen.pickerviewdemo.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.pickerviewdemo.R;
import com.allen.pickerviewdemo.model.DateType;

import java.util.List;

/**
 * 选择日期类型PopupWindow中类型列表的适配器
 *
 * @author Renjy
 */
public class SelectDateTypePopupListAdapter extends BaseAdapter {
    private List<DateType> mDateTypes;
    private LayoutInflater mInflater;
    private Resources mResources;
    private DateType mSelectDateType;

    public SelectDateTypePopupListAdapter(Context context, List<DateType> mDateTypes, DateType selectDateType) {
        this.mInflater = LayoutInflater.from(context);
        this.mResources = context.getResources();
        this.mDateTypes = mDateTypes;
        this.mSelectDateType = selectDateType;
    }

    @Override
    public int getCount() {
        return (null != mDateTypes) ? mDateTypes.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mDateTypes.get(position);
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
            convertView = mInflater.inflate(R.layout.item_select_date_type_popup_lst, null);
            viewHolder.mTxtTypeName = convertView.findViewById(R.id.item_txt_date_type_name);
            viewHolder.mImgTick = convertView.findViewById(R.id.item_img_date_selected_tick);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DateType dateType = mDateTypes.get(position);
        viewHolder.mTxtTypeName.setText(dateType.getName());
        if (dateType.equals(mSelectDateType)) {
            viewHolder.mTxtTypeName.setTextColor(mResources.getColor(R.color.ui_color_blue_00a0e9));
            viewHolder.mImgTick.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mTxtTypeName.setTextColor(mResources.getColor(R.color.ui_color_black_333333));
            viewHolder.mImgTick.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHolder {
        private TextView mTxtTypeName;
        private ImageView mImgTick;
    }
}
