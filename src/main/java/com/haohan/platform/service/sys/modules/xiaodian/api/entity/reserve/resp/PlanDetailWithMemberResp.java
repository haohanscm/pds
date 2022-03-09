package com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.resp;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *  订单配送详情  按会员查询 返回
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanDetailWithMemberResp extends PlanDetailResp {

    private String memberId;// 会员id
    private String memberName;// 会员名称
    private String telephone;// 电话
    private String address; // 配送地址

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
