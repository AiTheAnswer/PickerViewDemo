package com.allen.pickerviewdemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.allen.pickerviewdemo.R;
import com.allen.pickerviewdemo.model.DateModel;
import com.allen.pickerviewdemo.model.DateType;
import com.allen.pickerviewdemo.utils.DensityUtil;
import com.allen.pickerviewdemo.view.loopview.LoopListener;
import com.allen.pickerviewdemo.view.loopview.LoopView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SelectDatePopupWindow extends PopupWindow {
    private Context mContext;
    private Resources mResources;
    private TextView mTxtStartDate;
    private TextView mTxtEndDate;
    private LoopView mYearLoopView;
    private LoopView mWeekOrMonthLoopView;
    private LoopView mDayLoopView;
    private TextView mTxtReset;
    private TextView mTxtConfirm;
    private View mAlphaView;
    private DateModel mDefaultDate;
    private DateModel mSelectDate;
    private int mLoopViewTextSize;
    private int maxYear;
    private int minYear;
    private List mYearList;
    private List mWeekOrMonthList;
    //当前年份
    private int mYear;
    //当前月或者周
    private int mWeekOrMonth;
    //当前天
    private int mDay;

    //当前选择那个日期
    enum SelectType {
        START_DATE,//选择开始日期
        END_DATE//选择结束日期
    }

    //当前选择那个日期
    private SelectType mSelectType;

    public SelectDatePopupWindow(Context context, DateModel dateModel) {
        super(LayoutInflater.from(context).inflate(R.layout.select_date_popup_window, null), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mContext = context;
        mResources = context.getResources();
        mDefaultDate = dateModel;
        mTxtStartDate = getContentView().findViewById(R.id.select_date_start_date);
        mTxtEndDate = getContentView().findViewById(R.id.select_date_end_date);
        mYearLoopView = getContentView().findViewById(R.id.select_date_year_loop_view);
        mWeekOrMonthLoopView = getContentView().findViewById(R.id.select_date_week_or_month_loop_view);
        mDayLoopView = getContentView().findViewById(R.id.select_date_day_loop_view);
        mTxtReset = getContentView().findViewById(R.id.select_date_reset);
        mTxtConfirm = getContentView().findViewById(R.id.select_date_confirm);
        mAlphaView = getContentView().findViewById(R.id.alpha_view);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setClippingEnabled(false);
        initData();
        initLoopView();
        initListener();
    }

    private void initData() {
        mLoopViewTextSize = 18;
        maxYear = 2050;
        minYear = 1900;
        setSelectType(SelectType.START_DATE);
        mYearList = new ArrayList();
        mWeekOrMonthList = new ArrayList();

    }

    /**
     * 设置当前选择的是那个日期
     *
     * @param selectType 选择的是开始日期或者结束日期
     */
    private void setSelectType(SelectType selectType) {
        if (selectType == mSelectType) {
            return;
        }
        mSelectType = selectType;
        if (mSelectType == SelectType.START_DATE) {
            mYear = Integer.parseInt(mDefaultDate.getStartDate().split("-")[0]);
            mWeekOrMonth = Integer.parseInt(mDefaultDate.getStartDate().split("-")[1]);
            if (mDefaultDate.getType() == DateType.DAY) {
                mDay = Integer.parseInt(mDefaultDate.getStartDate().split("-")[2]);
            }
        } else {
            mYear = Integer.parseInt(mDefaultDate.getEndDate().split("-")[0]);
            mWeekOrMonth = Integer.parseInt(mDefaultDate.getEndDate().split("-")[1]);
            if (mDefaultDate.getType() == DateType.DAY) {
                mDay = Integer.parseInt(mDefaultDate.getEndDate().split("-")[2]);
            }
        }

    }

    private void initLoopView() {
        if (mDefaultDate.getType().equals(DateType.DAY)) {
            mDayLoopView.setVisibility(View.VISIBLE);
        } else {
            mDayLoopView.setVisibility(View.GONE);
        }
        mTxtStartDate.setText(format2DesDate(mDefaultDate.getType(), mDefaultDate.getStartDate()));
        mTxtStartDate.setTextColor(mResources.getColor(R.color.ui_color_blue_00a0e9));
        mTxtEndDate.setText(format2DesDate(mDefaultDate.getType(), mDefaultDate.getEndDate()));
        //设置是否可以循环
        mYearLoopView.setNotLoop();
        mWeekOrMonthLoopView.setNotLoop();
        mDayLoopView.setNotLoop();
        //设置字体大小
        mYearLoopView.setTextSize(mLoopViewTextSize);
        mWeekOrMonthLoopView.setTextSize(mLoopViewTextSize);
        mDayLoopView.setTextSize(mLoopViewTextSize);
        initPickerViews();
    }

    private String format2DesDate(DateType type, String date) {
        String dateDes;
        String[] split = date.split("-");
        if (type == DateType.WEEK) {
            dateDes = split[0] + "第" + split[1] + "周";
        } else if (type == DateType.MONTH) {
            dateDes = split[0] + "第" + split[1] + "月";
        } else {
            dateDes = date;
        }
        return dateDes;
    }

    private void initPickerViews() {
        int yearCount = maxYear - minYear;
        for (int i = 0; i < yearCount; i++) {
            mYearList.add(String.valueOf(minYear + i) + "年");
        }
        if (mDefaultDate.getType().equals(DateType.WEEK)) {
            int maxWeekNumOfYear = getMaxWeekNumOfYear(mYear);
            for (int i = 1; i <= maxWeekNumOfYear; i++) {
                mWeekOrMonthList.add("第" + i + "周");
            }
        } else {//月或者日
            for (int j = 0; j < 12; j++) {
                mWeekOrMonthList.add(String.valueOf(j + 1) + "月");
            }
        }


        mYearLoopView.setArrayList((ArrayList) mYearList);
        mYearLoopView.setInitPosition(mYear - minYear);

        mWeekOrMonthLoopView.setArrayList((ArrayList) mWeekOrMonthList);
        mWeekOrMonthLoopView.setInitPosition(mWeekOrMonth - 1);
    }

    // 获取当前时间所在年的最大周数
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        return getWeekOfYear(c.getTime());
    }

    // 获取当前时间所在年的周数
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    private static String format2LenStr(int num) {

        return (num < 10) ? "0" + num : String.valueOf(num);
    }

    private void initListener() {
        mYearLoopView.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {

            }
        });
        mWeekOrMonthLoopView.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {

            }
        });
        mDayLoopView.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {

            }
        });
    }

    public void show(View anchor) {
        showAsDropDown(anchor);
    }
}
