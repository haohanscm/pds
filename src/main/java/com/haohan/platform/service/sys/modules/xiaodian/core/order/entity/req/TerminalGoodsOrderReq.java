package com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req;

/**
 * @author shenyu
 * @create 2019/1/3
 */
public class TerminalGoodsOrderReq extends BaseOrderReq<BaseOrderDetailReq> {
    private String uid;
    private String userName;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
