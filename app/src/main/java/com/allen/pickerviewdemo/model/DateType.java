package com.allen.pickerviewdemo.model;

public enum DateType {
    DAY("按日"), WEEK("按周"), MONTH("按月");
    private String name;

    DateType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
