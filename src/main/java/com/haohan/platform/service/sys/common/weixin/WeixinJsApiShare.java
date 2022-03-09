package com.haohan.platform.service.sys.common.weixin;

import java.io.Serializable;

/**
 * @author zhaokuner
 * @ClassName: WeixinJsApiShare
 * @Description: 微信分享
 * @date 2016年8月30日 下午4:58:43
 */
public class WeixinJsApiShare implements Serializable {
    private static final long serialVersionUID = 1L;

    // 分享标题
    private String title;
    // 分享链接
    private String link;
    // 分享图标
    private String imgUrl;
    // 分享描述
    private String desc;
    // 分享类型,music、video或link，不填默认为link
    private String type = "link";
    // 如果type是music或video，则要提供数据链接，默认为空
    private String dataUrl = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

}
