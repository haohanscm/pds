/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: OrderServiceConstant
 * Author:   Lenovo
 * Date:     2018/6/13 14:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.haohan.platform.service.sys.modules.xiaodian.api.constant;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;

/**
 * @author shenyu
 * @create 2018/6/13
 */
public interface IOrderServiceConstant {
    String disAgreeOperation = "0";
    String agreeOperation = "1";
    String selfPdsPmId = DictUtils.getDictValue("浩瀚小店","self_pds_pmid","");

    enum OrderStatus {

        NOT_CONFIRM("0","待确认"),
        WAITE_PAY("1","待付款"),
        WAITE_SHIP("2","待发货"),
        SHIPPED("3","已发货"),
        FINISHED("4","已完成"),
        REFUND_CHECK("5","退款审核中"),
        REFUNDING("6","退款中"),
        CLOSED("7","已关闭"),
        WAITE_ACCEPT("8","待接单"),
        ;

        OrderStatus (String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    enum OrderFrom {

        wx_app("0","微信小程序"),
        merchant_admin("1","商家后台"),
        terminal("2","终端")
        ;

        OrderFrom (String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    enum OrderType {
        electricity("0","电商"),
        reserve("1","预定"),
        arrived("2","到店"),
        take_out("3","外卖"),
        face_pay("4","当面付"),
        purchase("5","自营平台采购单"),
        prodService("6","产品服务"),
        ;

        OrderType (String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static IOrderServiceConstant.OrderType valueOfCode (String code){
            for(IOrderServiceConstant.OrderType orderType : IOrderServiceConstant.OrderType.values()){
                if(StringUtils.equals(code, orderType.getCode())){
                    return orderType;
                }
            }
            return null;
        }

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }


}
