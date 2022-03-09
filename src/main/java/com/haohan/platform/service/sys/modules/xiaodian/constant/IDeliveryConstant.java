package com.haohan.platform.service.sys.modules.xiaodian.constant;

import com.haohan.platform.service.sys.common.utils.StringUtils;

/**
 * 配送相关
 * Created by dy on 2018/8/31.
 */
public interface IDeliveryConstant {
    // 配送周期
    enum DeliverySchedule {
        everyday("0","每日送"),
        interval_one("1","隔一天"),
        interval_two("2", "隔两天"),
        interval_three("3", "隔三天"),
        workday("4","工作日送"),
        everyweek("5","每周送"),
        everymonth("6","每月送"),
        ;

        private String code;
        private String desc;

        DeliverySchedule(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

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

        public static DeliverySchedule valueOfScheduleCode (String code){
            for(DeliverySchedule scheduleEnum : DeliverySchedule.values()){
                if(StringUtils.equals(code, scheduleEnum.getCode())){
                    return scheduleEnum;
                }
            }
            return null;
        }

    }

    enum OrderDeliveryStatus{
        wait("0","未配送"),
        doing("1","配送中"),
        finished("2", "配送完成"),
        cancel("3","取消配送");

        private String code;
        private String desc;

        OrderDeliveryStatus(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

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

    // 配送方式
    enum DeliveryType{
        express("0","快递"),
        self_delivery("1","自提"),
        home_delivery("9", "送货上门");

        private String code;
        private String desc;

        DeliveryType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

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

        public static String getValueByCode(String code){
            for(DeliveryType entity:DeliveryType.values()){
                if(code.equals(entity.getCode())){
                    return entity.getDesc();
                }
            }
            return  null;
        }

    }

    // 配送计划类型
    enum DeliveryPlanType{
        once("0","一次"),
        cyclical("1","周期性"),
        specific("2", "指定时间");

        private String code;
        private String desc;

        DeliveryPlanType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

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


    // 配送状态
    enum DeliveryStatus{
        wait("0","未配送"),
        success("1","已配送"),
        failed("2", "配送失败"),
        change("3", "配送变更"),
        cancel("4", "已取消");

        private String code;
        private String desc;

        DeliveryStatus(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

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

        public static DeliveryStatus getEnumByCode(String code){
            for(DeliveryStatus deliveryStatus : DeliveryStatus.values()){
                if(StringUtils.equals(code, deliveryStatus.getCode())){
                    return deliveryStatus;
                }
            }
            return null;
        }
    }

    enum PlanGenStatus{
        not_gen("0","未生成"),
        doing("1","正在生成"),
        finished("2", "已生成"),
        gen_fail("3","生成失败")
        ;

        private String code;
        private String desc;

        PlanGenStatus(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

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

        public static String getValueByCode(String code){
            for(PlanGenStatus entity:PlanGenStatus.values()){
                if(code.equals(entity.getCode())){
                    return entity.getDesc();
                }
            }
            return  null;
        }
    }


}
