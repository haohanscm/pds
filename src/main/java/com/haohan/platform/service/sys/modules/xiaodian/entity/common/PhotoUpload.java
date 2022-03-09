package com.haohan.platform.service.sys.modules.xiaodian.entity.common;

import java.io.Serializable;

/**
 * Created by zgw on 2018/4/16.
 */
public class PhotoUpload implements Serializable{


    private String groupNum;

    private String groupName;

    private int maxSize=10;

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
