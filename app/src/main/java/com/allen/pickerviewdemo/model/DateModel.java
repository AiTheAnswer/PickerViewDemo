package com.allen.pickerviewdemo.model;

public class DateModel {
    private DateType type ; // 0:日  1:周  2:月
    private String name;//今日，昨日，本周，上月。。。
    private String startDate;//开始日期 日： yyyy-MM-dd  月： yyyy-MM  周： yyyy-W
    private String endDate;
    private boolean isSelected;

    public DateModel(DateType type, String name, String startDate, String endDate,boolean isSelected) {
        this.type = type;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isSelected = isSelected;
    }

    public DateType getType() {
        return type;
    }

    public void setType(DateType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "DateModel{" +
                "dateType=" + type +
                ", name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }

}
