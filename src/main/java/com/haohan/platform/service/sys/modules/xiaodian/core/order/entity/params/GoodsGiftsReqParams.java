package com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.params;

/**
 * @author shenyu
 * @create 2018/10/1
 */
public class GoodsGiftsReqParams {
    private String giftName;		// 赠品名称
    private String giftId;		// 赠品id
    private String giftSchedule;		// 赠送周期
    private Integer giftNum;		// 赠送数量

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGiftSchedule() {
        return giftSchedule;
    }

    public void setGiftSchedule(String giftSchedule) {
        this.giftSchedule = giftSchedule;
    }

    public Integer getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(Integer giftNum) {
        this.giftNum = giftNum;
    }
}
