package com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin;

/**
 * @author shenyu
 * @create 2018/11/1
 */
public class PdsApiTradeSortProcessResp {
    private String buyId;
    private String buyerName;
    private Integer totalNum;
    private Integer sortedNum;
    private Integer notNum;

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getSortedNum() {
        return sortedNum;
    }

    public void setSortedNum(Integer sortedNum) {
        this.sortedNum = sortedNum;
    }

    public Integer getNotNum() {
        return notNum;
    }

    public void setNotNum(Integer notNum) {
        this.notNum = notNum;
    }
}
