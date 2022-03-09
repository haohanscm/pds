package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.constant;

import com.haohan.framework.entity.BaseStatus;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.util.XmBankConfigUtil;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/7/27
 */
public class MshPayConstant implements Serializable {

    public static final String ORG_NO = XmBankConfigUtil.getMshOrgNo();

    public static final String partnerNum = "019";

    public static final BaseStatus SUCCESS = new BaseStatus(0,"SUCCESS");

    public static final BaseStatus ERROR = new BaseStatus(1,"系统出错");

    public static final BaseStatus AMOUNT_INCORRECT = new BaseStatus(10001,"输入金额不正确");

    public static final BaseStatus PARAMS_IS_EMPTY = new BaseStatus(10002,"必填参数为空");

    public static final BaseStatus SIGN_VALID_FAIL = new BaseStatus(10003,"验签失败");

    public static final BaseStatus MER_ERROR = new BaseStatus(10004,"商家不存在");

    public static final BaseStatus ORDER_NOT_EXIST = new BaseStatus(10005,"订单不存在");

    public static final BaseStatus ORG_NOT_EXIST = new BaseStatus(10006,"组织不存在");

    public static final BaseStatus UNKNOW = new BaseStatus(10007,"未知错误");

    public static final BaseStatus PAY_TYPE_ERROR = new BaseStatus(10010,"支付类型不支持");

    public static final String csbActionName = "wallet/trans/csbSale";

    public static final String bscActionName = "wallet/trans/bscSale";

    public static final String jsActionName = "wallet/trans/jsSale";

    public static final String refundActionName = "wallet/trans/refund";

    public static final String queryActionName = "query/trans/detail";

    public static final String wechat_channel = "WECHAT";

    public static final String alipay_channel = "ALIPAY";

    enum MshOrderStatus{
        PAY_SUCCESS("SUCCESS","支付成功"),
        DEALING("USERPAYING","付款中"),
        PAY_FAIL("PAYERROR","支付失败"),
        REFUND_SUCCESS("REFUND_SUCCESS","退款成功")
        ;
        MshOrderStatus(String code,String desc){
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
