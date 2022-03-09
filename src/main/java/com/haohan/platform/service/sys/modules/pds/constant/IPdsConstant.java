package com.haohan.platform.service.sys.modules.pds.constant;

import com.haohan.framework.utils.EnumUtils;
import com.haohan.platform.service.sys.common.config.Global;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shenyu
 * @create 2018/10/20
 */
public interface IPdsConstant {

    /**
     * 租户id
     */
    String TENANT_KEY = "tenantId";
    String PM_KEY = "pmId";
    String TENANT_MAP_KEY = "tenant-pm";
    /**
     * header 中租户ID
     */
    String TENANT_ID = "tenant-id";
    /**
     * 逻辑主键 sn  缓存键值前缀
     */
    String SN_PREFIX = "scm-sn:";
    /**
     * 主键 id  缓存键值前缀
     */
//    @Value("${redis.id.prefix}")
    String ID_PREFIX = "scm-id:";


    // 交易流程编号 前缀
    String PROCESS_PREFIX = "PDS";
    // 交易流程编号 连接符  拼接使用(sequence)
    String PROCESS_INFIX = "S";
    // 公众号APPID
    String WX_MP_APPID = Global.getWechatMpId();
    // 小程序APPID
    String WX_MINIAPP_APPID = Global.getWechatMaId();

    int IdStartIndex = 100000;

    int defaultScale = 2;

    int EXPIRE_TIME = 3600 * 4;//4小时不过期
    String TOKEN_KEY = "haohan.session.id";

    /**
     * 采购单
     */
    String BUY_ORDER_SN_PRE = "OB";

    /**
     * 采购部采购单明细
     */
    String BUY_ORDER_DETAIL_SN_PRE = "ODB";

    /**
     * 汇总单
     */
    String SUMMARY_ORDER_SN_PRE = "SOC";

    /**
     * 交易订单
     */
    String TRADE_ORDER_SN_PRE = "TC";
    /**
     * 报价单号
     */
    String OFFER_ORDER_SN_PRE = "OY";

    // pss 在scm系统编号前加S
    /**
     * 仓库
     */
    String WAREHOUSE_SN_PRE = "SWSW";
    /**
     * 库存调拨记录
     */
    String WAREHOUSE_ALLOT_SN_PRE = "SAW";
    /**
     * 采购部采购单
     */
    String PURCHASE_SN_PRE = "SPO";
    /**
     * 退货单编号
     */
    String RETURN_ORDER_SN_PRE = "SRA";


    // 售后单 编号  customer service
    String SERVICEORDER_PREFIX = "AA";
    // 结算单 编号  settlement payment
    String SETTLEMENT_PREFIX = "SC";
    // 采购商货款编号  buyer payment number
    String BUYER_PAYMENT_PREFIX = "PB";
    // 供应商货款编号  supplier payment number
    String SUPPLIER_PAYMENT_PREFIX = "PY";

    /**
     * 物流配送
     */
    String DELIVERY_FLOW_SN_PR = "FD";
    /**
     * 送货单
     */
    String SHIP_ORDER_SN_PRE = "SP";

    // 营业税率(暂定)
    double sale_rate = 0.03;

    // 业务流程阶段：pds_process_stage
    enum ProcessStageType {
        collect("1", "收货"),
        sorting("2", "分拣"),
        loading("3", "装车"),
        delivery("4", "配送"),
        arrive("5", "到达"),
        check("6", "验货"),
        finish("7", "完成");

        private String code;
        private String desc;

        private static final Map<String, ProcessStageType> MAP = new HashMap<>();

        static {
            for (ProcessStageType type : ProcessStageType.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static ProcessStageType getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        ProcessStageType(String code, String desc) {
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

    // 平台交易流程：pds_trade_process_status
    enum TradeProcessStatus {
        submit("1", "采购商下单"),
        summary("2", "采购单汇总"),
        offer("3", "供应商报价"),
        valuation("4", "商品定价"),
        confirm("5", "采购商确认"),
        match("6", "交易匹配"),
        order("7", "订单生成"),
        finish("8", "交易完成");

        private String code;
        private String desc;

        private static final Map<String, TradeProcessStatus> MAP = new HashMap<>();

        static {
            for (TradeProcessStatus type : TradeProcessStatus.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static TradeProcessStatus getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        TradeProcessStatus(String code, String desc) {
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

    // 采购单状态：pds_buy_status
    enum BuyOrderStatus {
        submit("1", "已下单"),
        wait("2", "待确认"),
        success("3", "成交"),
        cancel("4", "取消"),
        delivery("5", "待发货"),
        arrive("6", "待收货");

        private String code;
        private String desc;

        private static final Map<String, BuyOrderStatus> MAP = new HashMap<>();

        static {
            for (BuyOrderStatus type : BuyOrderStatus.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static BuyOrderStatus getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        BuyOrderStatus(String code, String desc) {
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

    // 报价单状态：pds_offer_status
    enum OfferOrderStatus {
        wait("1", "待报价"),
        submit("2", "已报价"),
        success("3", "中标"),
        cancel("4", "未中标");

        private String code;
        private String desc;

        private static final Map<String, OfferOrderStatus> MAP = new HashMap<>();

        static {
            for (OfferOrderStatus type : OfferOrderStatus.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static OfferOrderStatus getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        OfferOrderStatus(String code, String desc) {
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

    // 报价类型：pds_offer_type
    enum OfferType {
        platform("1", "平台报价"),
        market("2", "市场报价"),
        point("3", "指定报价");

        private String code;
        private String desc;

        private static final Map<String, OfferType> MAP = new HashMap<>();

        static {
            for (OfferType type : OfferType.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static OfferType getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        OfferType(String code, String desc) {
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

    // 售后单状态：pds_service_status
    enum ServiceStatus {
        wait("1", "待处理"),
        handling("2", "已处理"),
        finish("3", "已解决");

        private String code;
        private String desc;

        private static final Map<String, ServiceStatus> MAP = new HashMap<>();

        static {
            for (ServiceStatus type : ServiceStatus.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static ServiceStatus getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        ServiceStatus(String code, String desc) {
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

    // 售后分类：pds_service_category
    enum ServiceCategory {
        back("2", "退货"),
        change("3", "调换");

        private String code;
        private String desc;

        private static final Map<String, ServiceCategory> MAP = new HashMap<>();

        static {
            for (ServiceCategory type : ServiceCategory.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static ServiceCategory getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        ServiceCategory(String code, String desc) {
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


    /**
     * 报价单 发货状态:-1不需发货 0待备货 1备货中 2已发货 4已接收 5售后中
     */
    enum OfferShipStatus {
        disabled("-1", "不需发货"),
        prepare("0", "待备货"),
        take("1", "备货中"),
        shipped("2", "已发货"),
        receiveCargo("4", "已接收"),
        afterSale("5", "售后中");

        private String code;
        private String desc;

        OfferShipStatus(String code, String desc) {
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

    //交易单-供应商视图状态
    enum SupplierDealStatus {
        prepare("0", "待备货"),
        take("1", "待揽货"),
        shipped("2", "已发货"),;

        private String code;
        private String desc;

        SupplierDealStatus(String code, String desc) {
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

    //交易单-采购商视图状态
    enum BuyerDealStatus {
        wait_ship("0", "待发货"),
        wait_check("1", "待验货"),
        received("2", "已收货"),;

        private String code;
        private String desc;

        BuyerDealStatus(String code, String desc) {
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

    //运营/采购视图状态
    enum OperatorViewStatus {
        prepare("0", "备货中"),
        feight("1", "待揽货"),
        waitSortOut("2", "待分拣"),
        sorted("3", "已分拣"),
        truckLoad("4", "已装车"),;

        private String code;
        private String desc;

        OperatorViewStatus(String code, String desc) {
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

    //交易单状态
    enum TradeOrderStatus {
        done("0", "成交"),
        after_sale("1", "售后"),;

        private String code;
        private String desc;

        TradeOrderStatus(String code, String desc) {
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

    //配送状态
    enum DeliveryStatus {
        wait_delivery("0", "待配送"),
        delivering("1", "配送中"),
        arrived("2", "已送达");

        private String code;
        private String desc;

        DeliveryStatus(String code, String desc) {
            this.code = code;
            this.desc = desc;
            EnumUtils.put(getClass().getName().toString() + code, this);
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

    // 交易匹配状态
    enum TradeMatchStatus {
        not_gen("0", "匹配失败"),
        is_gen("1", "已生成"),;

        private String code;
        private String desc;

        TradeMatchStatus(String code, String desc) {
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

    // 采购批次
    enum BuySeq {
        first("0", "第一批"),
        second("1", "第二批"),
//        evening("2","晚上"),
        ;

        private String code;
        private String desc;

        BuySeq(String code, String desc) {
            this.code = code;
            this.desc = desc;
            EnumUtils.put(getClass().getName().toString() + code, this);
        }

        public static IPdsConstant.BuySeq valueOfBuySeq(String code) {
            Object obj = EnumUtils.get(IPdsConstant.BuySeq.class.getName() + code);
            if (null != obj) {
                return (IPdsConstant.BuySeq) obj;
            }
            return null;
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

    //员工角色
    enum EmployeeRole {
        delivery("0", "配送员"),
        buyer("1", "采购员"),
        sortor("2", "分拣员"),
        operator("3", "运营"),
        driver("4", "司机");

        private String code;
        private String desc;

        private static final Map<String, EmployeeRole> MAP = new HashMap<>();

        static {
            for (EmployeeRole type : EmployeeRole.values()) {
                MAP.put(type.getDesc(), type);
            }
        }

        public static EmployeeRole getTypeByDesc(String desc) {
            if (MAP.containsKey(desc)) {
                return MAP.get(desc);
            }
            return null;
        }

        EmployeeRole(String code, String desc) {
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

    // 结算类型  pds_settlement_type
    enum SettlementType {
        revenues("1", "收入"),
        expenses("2", "支出"),;

        private String code;
        private String desc;

        SettlementType(String code, String desc) {
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

    // 结算公司类型  pds_company_type
    enum CompanyType {
        buyer("1", "采购商"),
        supplier("2", "供应商"),;

        private String code;
        private String desc;

        private static final Map<String, CompanyType> MAP = new HashMap<>();

        static {
            for (CompanyType type : CompanyType.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static CompanyType getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        CompanyType(String code, String desc) {
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

    // 结算账期  pds_pay_period
    enum PayPeriod {
        one("1", "T+1"),
        three("2", "T+3"),
        five("3", "T+5"),
        seven("4", "T+7"),;

        private String code;
        private String desc;

        PayPeriod(String code, String desc) {
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

    //车辆状态
    enum TruckStatus {
        rest("0", "休息中"),
        empty("1", "空闲中"),
        work("2", "送货中");

        private String code;
        private String desc;

        TruckStatus(String code, String desc) {
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

    enum SummaryOrderStatus {
        wait("0", "待报价"),
        offered("1", "已报价"),
        confirm("2", "平台确认"),
        deal("3", "成交"),
        cancel("4", "取消");

        private String code;
        private String desc;

        SummaryOrderStatus(String code, String desc) {
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

    enum DeliveryType {
        self("1", "自提"),
        home("9", "送货上门"),;

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

    }

    // 同scm系统使用
    enum ReceiveType {
        self("1", "自提"),
        delivery("2","送货上门");

        private String code;
        private String desc;

        ReceiveType(String code, String desc) {
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

    enum SupplierType {
        normal("0", "普通"),
        stock("1", "库存供应");

        private String code;
        private String desc;

        private static final Map<String, SupplierType> MAP = new HashMap<>(8);

        static {
            for (SupplierType type : SupplierType.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static SupplierType getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        SupplierType(String code, String desc) {
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

    enum BuyerType {
        employee("1", "员工"),
        boss("2", "老板"),
        operator("3", "运营"),
        self("4","自营")
        ;

        private String code;
        private String desc;

        private static final Map<String, BuyerType> MAP = new HashMap<>(8);

        static {
            for (BuyerType type : BuyerType.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static BuyerType getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        BuyerType(String code, String desc) {
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

    enum BillType {
        /**
         * 账单类型: 1订单应收 2退款应收 3采购应付 4退款应付
         */
        order("1", "订单应收"),
        offerBack("2", "退款应收"),
        purchase("3", "采购应付"),
        orderBack("4", "退款应付");

        private String code;
        private String desc;

        private static final Map<String, BillType> MAP = new HashMap<>(8);

        static {
            for (BillType type : BillType.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static BillType getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        BillType(String code, String desc) {
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


    enum DetailSummaryFlag {
        /**
         * 客户订单明细汇总状态:0未处理1已汇总2已备货
         */
        wait("0", "未处理"),
        summary("1", "已汇总"),
        prepare("2", "已备货");

        private String code;
        private String desc;

        private static final Map<String, DetailSummaryFlag> MAP = new HashMap<>(8);

        static {
            for (DetailSummaryFlag type : DetailSummaryFlag.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static DetailSummaryFlag getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        DetailSummaryFlag(String code, String desc) {
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


//    enum StatisPeriod{
//        week("0","一周"),
//        month("1","一个月");
//
//        private String code;
//        private String desc;
//
//        StatisPeriod(String code, String desc) {
//            this.code = code;
//            this.desc = desc;
//        }
//
//        public String getCode() {
//            return code;
//        }
//
//        public void setCode(String code) {
//            this.code = code;
//        }
//
//        public String getDesc() {
//            return desc;
//        }
//
//        public void setDesc(String desc) {
//            this.desc = desc;
//        }
//    }


}
