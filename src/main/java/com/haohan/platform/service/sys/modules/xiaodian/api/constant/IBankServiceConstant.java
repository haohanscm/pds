package com.haohan.platform.service.sys.modules.xiaodian.api.constant;

import com.haohan.framework.utils.EnumUtils;

/**
 * Created by zgw on 2017/12/9.
 */
public interface IBankServiceConstant {


    enum BankRegStatus {

       TODO(-1,"未开户"),
       CHECK(0,"待审核"),
       FAIL(1,"开户失败"),
       SUCCESS(2,"已开户"),
       PAUSE(4,"暂停使用"),
       ;

       BankRegStatus(Integer code, String desc) {
           this.code = code;
           this.desc = desc;
       }

       private Integer code;
       private String desc;

       public Integer getCode() {
           return code;
       }

       public void setCode(Integer code) {
           this.code = code;
       }

       public String getDesc() {
           return desc;
       }

       public void setDesc(String desc) {
           this.desc = desc;
       }
   }

   enum TransType {

       consume(1,"消费"),refund(2,"撤销");


       TransType(Integer code, String desc) {
           this.code = code;
           this.desc = desc;
       }

       private Integer code;
       private String desc;

       public Integer getCode() {
           return code;
       }

       public void setCode(Integer code) {
           this.code = code;
       }

       public String getDesc() {
           return desc;
       }

       public void setDesc(String desc) {
           this.desc = desc;
       }
   }

   enum TransStatus{
       FAIL("0","失败"),
       PART_SUCCESS("1","部分成功"),
       SUCCESS("2","成功"),
       UNKNOW("3","交易中"),
       CANCELED("4","已撤销"),
       CLOSED("5","已关闭"),
       REFUNDED("6","有退款"),
       CANCELING("7","撤销中"),
       REFUNDING("8","退款中");

       TransStatus(String code,String desc){
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

   enum PayStatus {

       NOPAY("0","未付款"),
       DOING("1","付款中"),
       SUCCESS("2","已付款"),
       FAIL("-2","付款失败"),
       CLOSED("-1","已关闭"),
       UNKNOW("3","交易中未知"),
       CHECK("4","退款审核中"),
       REFUNDED("5","全部退款"),
       REFUND_PART("6","部分退款")
       ;

       //BankPayStatus

//       WP("WP","等待付款"),
//       PD("PD","支付完成"),
//       EX("EX","订单过期"),
//       RP("RP","部分退款"),
//       RF("RF","全部退款"),
//       RQ("RQ",	"退款申请中"),
//       W("W","退款申请中"),   //暂未使用
//       S("S","退款成功"),
//       F("F","退款失败"),
//       U("U","订单不存在");

       PayStatus(String code, String desc) {
           this.code = code;
           this.desc = desc;
           EnumUtils.put(getClass().getName().toString()+code,this);
       }

       public static PayStatus valueOfPayStatus(String code) {
           Object obj = EnumUtils.get(PayStatus.class.getName() + code);
           if (null != obj) {
               return (PayStatus) obj;
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


   enum BankServiceStatus {

       SUCCESS("00","成功","所有接口"),

       ;


       private String code;
       private String desc;
       private String type;

       BankServiceStatus(String code, String desc, String type) {
           this.code = code;
           this.desc = desc;
           this.type = type;
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

       public String getType() {
           return type;
       }

       public void setType(String type) {
           this.type = type;
       }
   }

    enum RefundStatus {

        REFUNDED("2","退款成功"),
        REFUNDAPPLY("1","退款申请中"),
        REFUNDFAIL("0","退款失败");

        RefundStatus(String code, String desc) {
            this.code = code;
            this.desc = desc;
            EnumUtils.put(getClass().getName().toString()+code,this);
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

    enum CancelStatus {

        CANCELEDSUCCESS("2","撤销成功"),
        CANCELING("1","撤销中"),
        CANCELFAIL("0","撤销失败"),
        UNKNOWN("3","未知");

        CancelStatus(String code, String desc) {
            this.code = code;
            this.desc = desc;
            EnumUtils.put(getClass().getName().toString()+code,this);
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

    enum OrderQueryStatus {

        SUCCESS("2","交易成功"),
        FAIL("0","交易失败"),
        UNKNOWN("3","未知");

        OrderQueryStatus(String code, String desc) {
            this.code = code;
            this.desc = desc;
            EnumUtils.put(getClass().getName().toString()+code,this);
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
