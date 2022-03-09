package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant;

/**
 * Created by zgw on 2018/3/26.
 */
public interface XmBankEnums {

    enum BaseEnum {
        BASE_ENUM("00","SUCCESS");

        BaseEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private String code;
        private String desc;
    }

   enum  TransType {

       consume("1","消费"),cancel("2","撤销"),
       refund("3","退款"),closed("4","关闭");

       TransType(String code, String desc) {
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
