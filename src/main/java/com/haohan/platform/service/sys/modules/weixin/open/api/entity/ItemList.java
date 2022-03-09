package com.haohan.platform.service.sys.modules.weixin.open.api.entity;

import com.haohan.platform.service.sys.common.mapper.JsonMapper;

/**
 * Created by zgw on 2017/12/26.
 */
public class ItemList {


    private String address;

    private String tag;

    private String first_class;

    private String second_class;

    private String first_id;

    private String second_id;

    private String title;

    private String third_class;

    private String third_id;

    public ItemList() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getThird_class() {
        return third_class;
    }

    public void setThird_class(String third_class) {
        this.third_class = third_class;
    }

    public String getThird_id() {
        return third_id;
    }

    public void setThird_id(String third_id) {
        this.third_id = third_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFirst_class() {
        return first_class;
    }

    public void setFirst_class(String first_class) {
        this.first_class = first_class;
    }

    public String getSecond_class() {
        return second_class;
    }

    public void setSecond_class(String second_class) {
        this.second_class = second_class;
    }

    public String getFirst_id() {
        return first_id;
    }

    public void setFirst_id(String first_id) {
        this.first_id = first_id;
    }

    public String getSecond_id() {
        return second_id;
    }

    public void setSecond_id(String second_id) {
        this.second_id = second_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toJson(){
        return JsonMapper.toJsonString(this);
    }
}
