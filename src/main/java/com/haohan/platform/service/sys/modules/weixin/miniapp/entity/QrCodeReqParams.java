package com.haohan.platform.service.sys.modules.weixin.miniapp.entity;

import java.io.Serializable;

/**
 * Created by zgw on 2018/4/24.
 */
public class QrCodeReqParams implements Serializable{

    private String scene;

    private String page;

    private String path; //不能为空，最大长度 128 字节

    private int width; //默认430 二维码的宽度

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
