package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.params;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author shenyu
 * @create 2018/9/5
 */
public class GoodsGiftsResp implements Serializable {
    private String giftRule;       //赠送规则
    private Date giftBeginDate;   //活动开始日期
    private Date giftEndDate;       //活动结束日期
    private String giftName;        //赠品名称
    private Integer giftNum;         //赠品数量
    private Map<String,Object> giftSchedule;      //赠送周期
    private String giftUrl;         //赠品图片地址

    public String getGiftRule() {
        return giftRule;
    }

    public void setGiftRule(String giftRule) {
        this.giftRule = giftRule;
    }

    public Date getGiftBeginDate() {
        return giftBeginDate;
    }

    public void setGiftBeginDate(Date giftBeginDate) {
        this.giftBeginDate = giftBeginDate;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Integer getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(Integer giftNum) {
        this.giftNum = giftNum;
    }

    public Map<String, Object> getGiftSchedule() {
        return giftSchedule;
    }

    public void setGiftSchedule(Map<String, Object> giftSchedule) {
        this.giftSchedule = giftSchedule;
    }

    public String getGiftUrl() {
        return giftUrl;
    }

    public void setGiftUrl(String giftUrl) {
        this.giftUrl = giftUrl;
    }

    public Date getGiftEndDate() {
        return giftEndDate;
    }

    public void setGiftEndDate(Date giftEndDate) {
        this.giftEndDate = giftEndDate;
    }
}
