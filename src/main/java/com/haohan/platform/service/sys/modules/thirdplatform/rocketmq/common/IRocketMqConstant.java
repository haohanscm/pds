package com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.common;

import com.haohan.platform.service.sys.common.utils.StringUtils;

/**
 * @author shenyu
 * @create 2019/1/10
 */
public interface IRocketMqConstant {
    enum TopicType{
        ORDER("pds_order"),
        PAY("pds_pay"),
        CLOUD_PRINT("pds_cloud_print");

        private String name;

        TopicType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    enum OrderMsgTag {
        TEST("pds_TEST"),
        ORDER_CLOSE("pds_orderClose"),
        PLAN_GENERATE("pds_planGenerate"),
        PAY_SUCCESS("pds_orderPaySuccess"),
        ;

        private String tagName;

        private OrderMsgTag(String tagName){
            this.tagName = tagName;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public static OrderMsgTag valueOfTagName(String tagName){
            for (OrderMsgTag orderMsgTag : OrderMsgTag.values()){
                if(StringUtils.equals(tagName, orderMsgTag.getTagName())){
                    return orderMsgTag;
                }
            }
            return null;
        }
    }

    enum PayMsgTag {
        PAY_SUCCESS("pds_pay_success"),
        UNKNOW_PAY_STATUS("pds_unknownPayStatus"),
        NOTIFY_PARTNER("pds_notifyPartner"),
        ;

        private String tagName;

        private PayMsgTag(String tagName){
            this.tagName = tagName;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public static PayMsgTag valueOfTagName(String tagName){
            for (PayMsgTag payMsgTag : PayMsgTag.values()){
                if(StringUtils.equals(tagName, payMsgTag.getTagName())){
                    return payMsgTag;
                }
            }
            return null;
        }
    }

    enum PrintMsgTag{
        CREATE_ORDER("pds_set_up"),
        CANCEL_ORDER("pds_cancel"),
        UPDATE_ORDER("pds_update")
        ;

        private String tagName;

        PrintMsgTag(String tagName) {
            this.tagName = tagName;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }
        public static PrintMsgTag valueOfTagName(String tagName){
            for (PrintMsgTag printMsgTag : PrintMsgTag.values()){
                if(StringUtils.equals(tagName, printMsgTag.getTagName())){
                    return printMsgTag;
                }
            }
            return null;
        }

    }



}
