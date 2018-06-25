package com.allen.pickerviewdemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.pickerviewdemo.R;
import com.allen.pickerviewdemo.model.DateModel;
import com.allen.pickerviewdemo.model.DateType;
import com.allen.pickerviewdemo.view.loopview.LoopListener;
import com.allen.pickerviewdemo.view.loopview.LoopView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SelectDatePopupWindow extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private Resources mResources;
    private TextView mTxtStartDate;
    private TextView mTxtEndDate;
    private LoopView mYearLoopView;
    private LoopView mWeekOrMonthLoopView;
    private LoopView mDayLoopView;
    private TextView mTxtReset;
    private TextView mTxtConfirm;
    private OnConfirmListener mConfirmListener;
    private View mAlphaView;
    private DateModel mDefaultDate;
    private DateModel mSelectDate;
    private int mLoopViewTextSize;
    private int maxYear;
    private int minYear;
    private ArrayList mYearList;
    private ArrayList mWeekOrMonthList;
    private ArrayList<String> mDayList;
    //当前年份
    private int mYear;
    //当前月或者周
    private int mWeekOrMonth;
    //当前天
    private int mDay = 1;
    private int mYearPos = 0;
    private int mWeekOrMonthPos = 0;
    private int mDayPos = 0;

    //当前选择那个日期
    enum SelectType {
        START_DATE,//选择开始日期
        END_DATE//选择结束日期
    }

    //当前选择那个日期
    private SelectType mSelectType;

    public SelectDatePopupWindow(Context context, DateModel dateModel, OnConfirmListener onConfirmListener) {
        super(LayoutInflater.from(context).inflate(R.layout.select_date_popup_window, null), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mContext = context;
        mResources = context.getResources();
        mDefaultDate = dateModel;
        mConfirmListener = onConfirmListener;
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
        //设置默认先选择开始日期
        setSelectType(SelectType.START_DATE);
        initListener();
    }

    private void initData() {
        mLoopViewTextSize = 18;
        maxYear = 2050;
        minYear = 1900;
        mTxtStartDate.setText(format2DesDate(mDefaultDate.getType(), mDefaultDate.getStartDate()));
        mTxtEndDate.setText(format2DesDate(mDefaultDate.getType(), mDefaultDate.getEndDate()));
        mSelectDate = new DateModel(mDefaultDate.getType(), mDefaultDate.getName(), mDefaultDate.getStartDate(), mDefaultDate.getEndDate(), false);
    }

    /**
     * 设置当前选择的是那个日期
     *
     * @param selectType 选择的是开始日期或者结束日期
     */
    private void setSelectType(SelectType selectType) {
        mSelectType = selectType;
        if (mSelectType == SelectType.START_DATE) {
            mYear = Integer.parseInt(mSelectDate.getStartDate().split("-")[0]);
            mWeekOrMonth = Integer.parseInt(mSelectDate.getStartDate().split("-")[1]);
            if (mSelectDate.getType() == DateType.DAY) {
                mDay = Integer.parseInt(mSelectDate.getStartDate().split("-")[2]);
                mDayPos = mDay - 1;
            }
            mTxtStartDate.setTextColor(mResources.getColor(R.color.ui_color_blue_00a0e9));
            mTxtEndDate.setTextColor(mResources.getColor(R.color.ui_color_black_333333));
        } else {
            mYear = Integer.parseInt(mSelectDate.getEndDate().split("-")[0]);
            mWeekOrMonth = Integer.parseInt(mSelectDate.getEndDate().split("-")[1]);
            if (mSelectDate.getType() == DateType.DAY) {
                mDay = Integer.parseInt(mSelectDate.getEndDate().split("-")[2]);
                mDayPos = mDay - 1;
            }
            mTxtEndDate.setTextColor(mResources.getColor(R.color.ui_color_blue_00a0e9));
            mTxtStartDate.setTextColor(mResources.getColor(R.color.ui_color_black_333333));
        }
        initYearPickerView();
        initWeekOrMonthPickerView();
        initDayPickerView();
    }

    private void initLoopView() {
        if (mDefaultDate.getType().equals(DateType.DAY)) {
            mDayLoopView.setVisibility(View.VISIBLE);
        } else {
            mDayLoopView.setVisibility(View.GONE);
        }
        //设置是否可以循环
        mYearLoopView.setNotLoop();
        mWeekOrMonthLoopView.setNotLoop();
        mDayLoopView.setNotLoop();
        //设置字体大小
        mYearLoopView.setTextSize(mLoopViewTextSize);
        mWeekOrMonthLoopView.setTextSize(mLoopViewTextSize);
        mDayLoopView.setTextSize(mLoopViewTextSize);
        //设置间隔大小
        mYearLoopView.setLineSpacingMultiplier(3.0f);
        mWeekOrMonthLoopView.setLineSpacingMultiplier(3.0f);
        mDayLoopView.setLineSpacingMultiplier(3.0f);

    }

    private String format2DesDate(DateType type, String date) {
        String dateDes;
        String[] split = date.split("-");
        if (type == DateType.WEEK) {
            dateDes = split[0] + "年第" + format2LenStr(Integer.parseInt(split[1])) + "周";
        } else if (type == DateType.MONTH) {
            dateDes = split[0] + "年第" + format2LenStr(Integer.parseInt(split[1])) + "月";
        } else {
            dateDes = date;
        }
        return dateDes;
    }

    private void initYearPickerView() {
        mYearList = new ArrayList();
        int yearCount = maxYear - minYear;
        for (int i = 0; i < yearCount; i++) {
            mYearList.add(String.valueOf(minYear + i) + "年");
        }
        mYearPos = mYear - minYear;
        mYearLoopView.setArrayList(mYearList);
        mYearLoopView.setInitPosition(mYearPos);
    }

    private void initWeekOrMonthPickerView() {
        mWeekOrMonthList = new ArrayList();
        if (mDefaultDate.getType().equals(DateType.WEEK)) {
            int maxWeekNumOfYear = getMaxWeekNumOfYear(mYear);
            for (int i = 1; i <= maxWeekNumOfYear; i++) {
                mWeekOrMonthList.add("第" + format2LenStr(i) + "周");
            }
        } else {//月或者日
            for (int j = 0; j < 12; j++) {
                mWeekOrMonthList.add(format2LenStr(j + 1) + "月");
            }
        }
        mWeekOrMonthPos = mWeekOrMonth - 1;
        mWeekOrMonthLoopView.setArrayList(mWeekOrMonthList);
        mWeekOrMonthLoopView.setInitPosition(mWeekOrMonthPos);
    }

    /**
     * Init day item
     */
    private void initDayPickerView() {
        if (mDefaultDate.getType() != DateType.DAY) {
            return;
        }
        int dayMaxInMonth;
        Calendar calendar = Calendar.getInstance();
        mDayList = new ArrayList<>();
        calendar.set(Calendar.YEAR, minYear + mYearPos);
        calendar.set(Calendar.MONTH, mWeekOrMonthPos);
        // get max day in month
        dayMaxInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < dayMaxInMonth; i++) {
            mDayList.add(format2LenStr(i + 1) + "日");
        }
        mDayLoopView.setInitPosition(mDayPos);
        mDayLoopView.setArrayList(mDayList);
    }

    /**
     * 计算某年某周的结束日期
     *
     * @param yearNum 格式 yyyy  ，必须大于1900年度 小于9999年
     * @param weekNum 1到52或者53
     * @return 日期，格式为yyyy-MM-dd
     */
    public static String getYearWeekEndDay(int yearNum, int weekNum) {
        if (yearNum < 1900 || yearNum > 9999) {
            throw new NullPointerException("年度必须大于等于1900年小于等于9999年");
        }
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY); //设置每周的第一天为星期一
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//每周从周一开始
//       上面两句代码配合，才能实现，每年度的第一个周，是包含第一个星期一的那个周。
        cal.setMinimalDaysInFirstWeek(7);  //设置每周最少为7天
        cal.set(Calendar.YEAR, yearNum);
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);
        Date time = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

    // 获取当前时间所在年的最大周数
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        c.setMinimalDaysInFirstWeek(7);
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
                mYearPos = item;
                mYear = minYear + item;
                if (mDefaultDate.getType() == DateType.WEEK) {
                    initWeekOrMonthPickerView();
                }
                initDayPickerView();
                updateSelectDate();
            }
        });
        mWeekOrMonthLoopView.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {
                mWeekOrMonth = item + 1;
                mWeekOrMonthPos = item;
                initDayPickerView();
                updateSelectDate();
            }
        });
        mDayLoopView.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {
                mDayPos = item;
                updateSelectDate();
            }
        });
        mTxtStartDate.setOnClickListener(this);
        mTxtEndDate.setOnClickListener(this);
        mTxtReset.setOnClickListener(this);
        mTxtConfirm.setOnClickListener(this);
        mAlphaView.setOnClickListener(this);

    }

    /**
     * 当用户选择滑动选择日期齿轮的时，更改展示的选择
     */
    private void updateSelectDate() {
        int year = mYearPos + minYear;
        int weekOrMonth = mWeekOrMonthPos + 1;
        int day = mDayLoopView.getSelectedItem() + 1;
        String dateDesc;
        String date;
        if (mDefaultDate.getType() == DateType.DAY) {
            dateDesc = year + "-" + format2LenStr(weekOrMonth) + "-" + format2LenStr(day);
            date = year + "-" + format2LenStr(weekOrMonth) + "-" + format2LenStr(day);
        } else if (mDefaultDate.getType() == DateType.MONTH) {
            dateDesc = year + "年第" + format2LenStr(weekOrMonth) + "月";
            date = year + "-" + format2LenStr(weekOrMonth);
        } else {
            dateDesc = year + "年第" + format2LenStr(weekOrMonth) + "周";
            date = year + "-" + format2LenStr(weekOrMonth);
        }
        if (mSelectType == SelectType.START_DATE) {
            mTxtStartDate.setText(dateDesc);
            mSelectDate.setStartDate(date);
        } else {
            mTxtEndDate.setText(dateDesc);
            mSelectDate.setEndDate(date);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_date_start_date://开始日期
                setSelectType(SelectType.START_DATE);
                break;
            case R.id.select_date_end_date://结束日期
                setSelectType(SelectType.END_DATE);
                break;
            case R.id.select_date_reset://重置
                mSelectDate.setStartDate(mDefaultDate.getStartDate());
                mSelectDate.setEndDate(mDefaultDate.getEndDate());
                mTxtStartDate.setText(format2DesDate(mDefaultDate.getType(), mDefaultDate.getStartDate()));
                mTxtEndDate.setText(format2DesDate(mDefaultDate.getType(), mDefaultDate.getEndDate()));
                setSelectType(mSelectType);
                break;
            case R.id.select_date_confirm://确定
                if (startDateLessOrEqualEndDate(mSelectDate)) {
                    mConfirmListener.onConfirm(mSelectDate);
                    dismiss();
                } else {
                    Toast.makeText(mContext, "开始日期大于结束日期，请重新选择日期", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.alpha_view://透明区域
                dismiss();
                break;
        }

    }

    /**
     * 判断开始日期是否小于等于结束日期
     *
     * @param selectDate 选择的日期
     * @return true 开始日期小于等于结束日期 false  反之
     */
    private boolean startDateLessOrEqualEndDate(DateModel selectDate) {
        String[] startDate = selectDate.getStartDate().split("-");
        String[] endDate = selectDate.getEndDate().split("-");
        int startYear = Integer.parseInt(startDate[0]);
        int endYear = Integer.parseInt(endDate[0]);
        int startWeekOrMonth = Integer.parseInt(startDate[1]);
        int endWeekOrMonth = Integer.parseInt(endDate[1]);
        if (startYear > endYear) {
            return false;
        }
        if (startYear == endYear && startWeekOrMonth > endWeekOrMonth) {
            return false;
        }
        if (selectDate.getType() == DateType.DAY) {
            int startDay = Integer.parseInt(startDate[2]);
            int endDay = Integer.parseInt(endDate[2]);
            if (startWeekOrMonth == endWeekOrMonth && startDay > endDay) {
                return false;
            }
        }
        return true;
    }

    public void show(View anchor) {
        showAsDropDown(anchor);
    }

    public interface OnConfirmListener {
        void onConfirm(DateModel dateModel);

    }
}
