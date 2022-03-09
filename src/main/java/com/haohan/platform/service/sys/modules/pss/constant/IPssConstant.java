package com.haohan.platform.service.sys.modules.pss.constant;

/**
 * @author shenyu
 * @create 2018/10/16
 */
public interface IPssConstant {
    int IdStartIndex = 100000;

    //入库状态
    enum StockStatus {
        not_in("0","未入库"),
        entered("1","已入库"),
        ;

        StockStatus (String code, String desc) {
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

    //退货状态
    enum ReturnStatus {
        audit("0","审核中"),
        returned("1","已退货"),
        ;

        ReturnStatus (String code, String desc) {
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

    //调拨类型
    enum AllotType {
        shop("0","店铺"),
        warehouse("1","仓库"),
        ;

        AllotType (String code, String desc) {
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

    //调拨订单状态
    enum AllotOrderStatus {
        wait_audit("0","待审核"),
//        wait_delivery("1","待发货"),
//        wait_receive("2","待收货"),
        received("3","完成"),
        ;

        AllotOrderStatus (String code, String desc) {
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

    //调拨审核状态
    enum AllotAuditStatus {
        auditing("0","审核中"),
        wait_delivery("1","已审核"),
        ;

        AllotAuditStatus (String code, String desc) {
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

}
