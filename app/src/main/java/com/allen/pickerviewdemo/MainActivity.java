package com.allen.pickerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.pickerviewdemo.adapter.DateSelectGridViewAdapter;
import com.allen.pickerviewdemo.model.DateModel;
import com.allen.pickerviewdemo.model.DateType;
import com.allen.pickerviewdemo.utils.DateUtils;
import com.allen.pickerviewdemo.view.SelectDatePopupWindow;
import com.allen.pickerviewdemo.view.SelectDateTypePopupWindow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //当前选择的日期的类型
    private TextView mTxtDateType;
    //自定义选择日期
    private TextView mTxtDateCustom;
    //日期选择和内容的布局的分割线
    private View mDividerView;
    //当前日期类型下默认的日期列表
    private GridView mDefaultDateGridView;
    //所有类型的默认的日期集合
    private List<DateModel> dateModelList;
    //当前选择的日期
    private DateModel selectDate;
    //选择默认日期的位置
    private int selectDatePosition;
    //当前类型下日期的集合
    private List<DateModel> currentTypeDateList;
    //默认日期列表的适配器
    private DateSelectGridViewAdapter mAdapter;
    //日期类型集合
    private List<DateType> mDateTypes;
    //当前选择的日期类型
    private DateType mSelectDateType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        mTxtDateType.setOnClickListener(this);
        mTxtDateCustom.setOnClickListener(this);
        mDefaultDateGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == selectDatePosition) {
                    return;
                } else {
                    selectDatePosition = position;
                }
                for (DateModel dateModel : currentTypeDateList) {
                    dateModel.setSelected(false);
                }
                currentTypeDateList.get(position).setSelected(true);
                mAdapter.notifyDataSetChanged();
                selectDate = currentTypeDateList.get(position);
                Toast.makeText(MainActivity.this, selectDate.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        initDefaultDateType();
        initDefaultDate();
        currentTypeDateList = new ArrayList<>();
        mAdapter = new DateSelectGridViewAdapter(this, currentTypeDateList);
        mDefaultDateGridView.setAdapter(mAdapter);
        mSelectDateType = DateType.DAY;
        setDateType(DateType.WEEK);
    }

    private void initDefaultDateType() {
        mDateTypes = new ArrayList<>();
        mDateTypes.add(DateType.DAY);
        mDateTypes.add(DateType.WEEK);
        mDateTypes.add(DateType.MONTH);
    }

    private void setDateType(DateType type) {
        if (mSelectDateType.equals(type)) {
            return;
        } else {
            mSelectDateType = type;
            mTxtDateType.setText(type.getName());
        }
        currentTypeDateList.clear();
        for (DateModel dateModel : dateModelList) {
            if (dateModel.getType().equals(type)) {
                dateModel.setSelected(false);
                currentTypeDateList.add(dateModel);
            }
        }
        selectDatePosition = 0;
        currentTypeDateList.get(0).setSelected(true);
        selectDate = currentTypeDateList.get(0);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 显示选择日期类型选择的PopupWindow
     */
    private void showDateTypeSelectPopupWindow() {
        final SelectDateTypePopupWindow popupWindow = new SelectDateTypePopupWindow(this, mDateTypes, mSelectDateType);
        popupWindow.show(mDividerView);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setDateType(popupWindow.getSelectDateType());
            }
        });
    }

    /**
     * 显示自定义选择日期的PopupWindow
     */
    private void showDateSelectPopupWindow() {
        SelectDatePopupWindow popupWindow = new SelectDatePopupWindow(this, selectDate);
        popupWindow.showAsDropDown(mDividerView);
    }

    /**
     * 初始化默认的日期
     */
    private void initDefaultDate() {
        dateModelList = new ArrayList<>();
        //添加日类型的日期
        DateModel dayDateModel0 = new DateModel(DateType.DAY, "今天", DateUtils.getDate(new Date(), "yyyy-MM-dd"), DateUtils.getDate(new Date(), "yyyy-MM-dd"), false);
        DateModel dayDateModel1 = new DateModel(DateType.DAY, "昨天", DateUtils.getXDateBeforeToday(1, "yyyy-MM-dd"), DateUtils.getXDateBeforeToday(1, "yyyy-MM-dd"), false);
        DateModel dayDateModel2 = new DateModel(DateType.DAY, "近7天", DateUtils.getXDateBeforeToday(6, "yyyy-MM-dd"), DateUtils.getDate(new Date(), "yyyy-MM-dd"), false);
        DateModel dayDateModel3 = new DateModel(DateType.DAY, "近30天", DateUtils.getXDateBeforeToday(29, "yyyy-MM-dd"), DateUtils.getDate(new Date(), "yyyy-MM-dd"), false);
        dateModelList.add(dayDateModel0);
        dateModelList.add(dayDateModel1);
        dateModelList.add(dayDateModel2);
        dateModelList.add(dayDateModel3);
        //添加周类型
        DateModel weekDateModel0 = new DateModel(DateType.WEEK, "本周", DateUtils.getTodayDateWeekOfYear(), DateUtils.getTodayDateWeekOfYear(), false);
        DateModel weekDateModel1 = new DateModel(DateType.WEEK, "上周", DateUtils.getXDateBeforeWeekOfYear(1), DateUtils.getXDateBeforeWeekOfYear(1), false);
        DateModel weekDateModel2 = new DateModel(DateType.WEEK, "近4周", DateUtils.getXDateBeforeWeekOfYear(3), DateUtils.getTodayDateWeekOfYear(), false);
        DateModel weekDateModel3 = new DateModel(DateType.WEEK, "近13周", DateUtils.getXDateBeforeWeekOfYear(12), DateUtils.getTodayDateWeekOfYear(), false);
        dateModelList.add(weekDateModel0);
        dateModelList.add(weekDateModel1);
        dateModelList.add(weekDateModel2);
        dateModelList.add(weekDateModel3);
        //添加月类型
        DateModel yearDateModel0 = new DateModel(DateType.MONTH, "本月", DateUtils.getFirstDateOfCurrentMonth("yyyy-MM"), DateUtils.getLastDateOfCurrentMonth("yyyy-MM"), false);
        DateModel yearDateModel1 = new DateModel(DateType.MONTH, "上月", DateUtils.getFirstDateOfLastMonth("yyyy-MM"), DateUtils.getLastDateOfLastMonth("yyyy-MM"), false);
        DateModel yearDateModel2 = new DateModel(DateType.MONTH, "近3月", DateUtils.getXDateBeforeCurrentMonth(2, "yyyy-MM"), DateUtils.getDate(new Date(), "yyyy-MM"), false);
        DateModel yearDateModel3 = new DateModel(DateType.MONTH, "近6月", DateUtils.getXDateBeforeCurrentMonth(5, "yyyy-MM"), DateUtils.getDate(new Date(), "yyyy-MM"), false);
        dateModelList.add(yearDateModel0);
        dateModelList.add(yearDateModel1);
        dateModelList.add(yearDateModel2);
        dateModelList.add(yearDateModel3);
    }

    private void initView() {
        mTxtDateType = findViewById(R.id.txt_date_type);
        mTxtDateCustom = findViewById(R.id.txt_custom_date);
        mDefaultDateGridView = findViewById(R.id.default_grid_view);
        mDividerView = findViewById(R.id.divider);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_date_type:
                showDateTypeSelectPopupWindow();
                break;
            case R.id.txt_custom_date:
                showDateSelectPopupWindow();
        }
    }


}
