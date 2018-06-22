package com.allen.pickerviewdemo.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.allen.pickerviewdemo.R;
import com.allen.pickerviewdemo.adapter.SelectDateTypePopupListAdapter;
import com.allen.pickerviewdemo.model.DateType;

import java.util.List;

/**
 * 显示选择日期类型的PopupWindow
 */
public class SelectDateTypePopupWindow extends PopupWindow {
    private Context mContext;
    private ListView mListView;
    private View alphaView;
    private List<DateType> mDateTypes;
    private SelectDateTypePopupListAdapter mAdapter;
    private DateType mSelectDateType;

    public SelectDateTypePopupWindow(Context context, List<DateType> typeList, DateType selectDateType) {
        super(LayoutInflater.from(context).inflate(R.layout.select_date_type_popup_window, null), FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mContext = context;
        mSelectDateType = selectDateType;
        mListView = getContentView().findViewById(R.id.select_date_type_popup_lst);
        alphaView = getContentView().findViewById(R.id.alpha_view);
        this.mDateTypes = typeList;
        this.mSelectDateType = selectDateType;
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setClippingEnabled(false);
        initData();
        initListener();
    }

    private void initData() {
        mAdapter = new SelectDateTypePopupListAdapter(mContext, mDateTypes, mSelectDateType);
        mListView.setAdapter(mAdapter);
    }

    private void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectDateType = mDateTypes.get(position);
                dismissPopupWidow();
            }
        });
        alphaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPopupWidow();
            }
        });
    }

    /**
     * 获取选择的日期类型
     *
     * @return 选择的日期类型
     */
    public DateType getSelectDateType() {
        return mSelectDateType;
    }

    /**
     * 显示PopupWindow在某个View的下面
     *
     * @param anchor 显示在那个View的下面
     */
    public void show(View anchor) {
        showAsDropDown(anchor);
    }

    private void dismissPopupWidow() {
        dismiss();
    }
}
