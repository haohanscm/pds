package com.haohan.platform.service.sys.modules.xiaodian.constant;

import com.haohan.framework.utils.EnumUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by zgw on 2017/12/25.
 */
public interface IMerchantConstant {

    String sessionName = "haohanshop-token";

    String commonStatus = "common_status";

    static String terminalPartnerNum = "003";

    String available = DictUtils.getDictValue("启用",commonStatus,"");

    String disabled = DictUtils.getDictValue("停用",commonStatus,"");

    String toAduit = DictUtils.getDictValue("待审核",commonStatus,"");

    long LIMIT_FILE_SIZE = 2*1024*1024;

    /**
     * 图片上传默认商家id
     */
    String DEFAULT_MERCHANT_ID = "9";

    enum BankChannel{
        XMBANK("0","厦门银行"),
        MSHPAY("1","码尚惠");

        private String code;
        private String channelName;

        BankChannel(String code,String channelName){
            this.code = code;
            this.channelName = channelName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }
    }

    // 商家的店铺类型
    enum MerchantShopType{
        restaurant("0","餐饮"),retail("1","零售");

        private String type;
        private String desc;

        MerchantShopType(String type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    enum MerchantRegStep{
        zero("0","未提交"),
        success("-1","资料提交完成"),
        one("1","营业执照") ,
        two("2","餐饮许可证") ,
        three("3","商家门店照片") ,
        four("4","身份证正反面"),
        five("5","结算卡或对公账户"),
//        six(6,"对公账户照片"),
//        seven(7,"开户许可证"),
        ;

        private String stepCode;
        private String stepDesc;

        MerchantRegStep(String stepCode, String stepDesc) {
            this.stepCode = stepCode;
            this.stepDesc = stepDesc;
        }

        public String getStepCode() {
            return stepCode;
        }

        public void setStepCode(String stepCode) {
            this.stepCode = stepCode;
        }

        public String getStepDesc() {
            return stepDesc;
        }

        public void setStepDesc(String stepDesc) {
            this.stepDesc = stepDesc;
        }
    }

    enum AccountAuthCode{
        parentAccount("0","总部权限"),childrenAccount("1","子店权限");

        private String code;
        private String type;

        AccountAuthCode(String code,String type){
            this.code = code;
            this.type = type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    enum MerchantFilesType{
        ShopPhotos("00","门店照片") ,
        MerchantPhotos("01","商户照片") ,
        ProtocolFiles("02","协议文件") ,
        productPhotos("03","商品照片"),
        cateringLicense("04","餐饮许可证"),
        license("05","营业执照"),
        bankCardPhotos("06","结算卡或对公账户"),
        idCardPhotos("07","身份证正反面"),
        billPhotos("08","账单图片"),
        back("09", "退货图片"),
        memento("10", "留影图片");

        private String groupNum;
        private String groupName;

        MerchantFilesType(String groupNum, String groupName) {
            this.groupNum = groupNum;
            this.groupName = groupName;
            EnumUtils.put(getClass().getName()+groupNum,this);
        }

        public MerchantFilesType fetchMerchantFiles(String groupNum){

            return (MerchantFilesType) EnumUtils.getEnumByCode(getClass(),groupNum);
        }

        public String getGroupNum() {
            return groupNum;
        }

        public void setGroupNum(String groupNum) {
            this.groupNum = groupNum;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }
    }


    enum MerchantAppStatus{
        submit_fail("-1","提交失败"),
        wx_check_fail("-2","微信审核失败"),
        wait_config("0","待配置"),
        publish_test_environment("1","发布测试环境"),
        submit_wx_check("2","提交微信审核"),
        wx_checking("3","微信审核中"),
        wx_check_success("4","微信审核成功"),
        publish_online("5","发布上线"),
        online_success("6","上线成功")
        ;

        private String code;
        private String desc;

        MerchantAppStatus(String code, String desc) {
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

    // 商家的店铺类型
    enum PdsType{
        general("0","普通商家"),platform("1","平台商家");

        private String type;
        private String desc;

        PdsType(String type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    String merchantFilesGroupNum = "merchant_files"; //商户资料组编号

    String shopPhotosNum =DictUtils.getDictValue("门店照片",merchantFilesGroupNum,"");

    String merchantPhotosNum =DictUtils.getDictValue("商户照片",merchantFilesGroupNum,"");

    String protocolFilesNum =DictUtils.getDictValue("协议文件",merchantFilesGroupNum,"");

    String productPhotosNum =DictUtils.getDictValue("商品照片",merchantFilesGroupNum,"");
}

