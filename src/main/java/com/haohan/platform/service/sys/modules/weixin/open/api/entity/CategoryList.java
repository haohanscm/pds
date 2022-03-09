package com.haohan.platform.service.sys.modules.weixin.open.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by zgw on 2017/12/25.
 */

public class CategoryList implements Serializable{

    @JsonProperty("first_class")
    private String firstClass;

    @JsonProperty("second_class")
    private String secondClass;

    @JsonProperty("first_id")
    private int firstId;

    @JsonProperty("second_id")
    private int secondId;

    @JsonProperty("third_class")
    private String thirdClass;

    @JsonProperty("third_id")
    private int thirdId;

    public String getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(String firstClass) {
        this.firstClass = firstClass;
    }

    public String getSecondClass() {
        return secondClass;
    }

    public void setSecondClass(String secondClass) {
        this.secondClass = secondClass;
    }

    public int getFirstId() {
        return firstId;
    }

    public void setFirstId(int firstId) {
        this.firstId = firstId;
    }

    public int getSecondId() {
        return secondId;
    }

    public void setSecondId(int secondId) {
        this.secondId = secondId;
    }

    public int getThirdId() {
        return thirdId;
    }

    public void setThirdId(int thirdId) {
        this.thirdId = thirdId;
    }

    public String getThirdClass() {
        return thirdClass;
    }

    public void setThirdClass(String thirdClass) {
        this.thirdClass = thirdClass;
    }
}
