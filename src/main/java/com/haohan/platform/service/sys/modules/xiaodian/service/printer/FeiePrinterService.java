package com.haohan.platform.service.sys.modules.xiaodian.service.printer;

import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.Encodes;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.feieyun.entity.FeieyunRequestParam;
import com.haohan.platform.service.sys.modules.thirdplatform.feieyun.inf.IFeieyunOpenService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant.YesNoType;
import com.haohan.platform.service.sys.modules.xiaodian.dao.printer.FeiePrinterDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.*;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.builder.PrintContentBuilder;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.util.PrinterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 飞鹅打印机管理Service
 *
 * @author haohan
 * @version 2018-08-02
 */
@Service
@Transactional(readOnly = true)
public class FeiePrinterService extends CrudService<FeiePrinterDao, FeiePrinter> {

    @Qualifier("feieyunOpenServiceImpl")
    @Autowired
    private IFeieyunOpenService feieyunOpenService;

    @Lazy(true)
    @Autowired
    private GoodsOrderService goodsOrderService;

    @Lazy(true)
    @Autowired
    private GoodsOrderDetailService goodsOrderDetailService;

    public FeiePrinter get(String id) {
        return super.get(id);
    }

    public List<FeiePrinter> findList(FeiePrinter feiePrinter) {
        return super.findList(feiePrinter);
    }

    public List<FeiePrinter> findByMerchantId(String merchantId){
        FeiePrinter feiePrinter = new FeiePrinter();
        feiePrinter.setMerchantId(merchantId);
        feiePrinter.setUseable(YesNoType.yes.getCode());
        return super.findList(feiePrinter);
    }

    public List<FeiePrinter> findByMerchantIdAndTplType(String merchantId,PrintTemplateType templateType){
        FeiePrinter feiePrinter = new FeiePrinter();
        feiePrinter.setMerchantId(merchantId);
        feiePrinter.setTemplateType(templateType.getValue());
        feiePrinter.setUseable(YesNoType.yes.getCode());
        return super.findList(feiePrinter);
    }

    public Page<FeiePrinter> findPage(Page<FeiePrinter> page, FeiePrinter feiePrinter) {
        return super.findPage(page, feiePrinter);
    }

    // 根据打印机编号查询
    public FeiePrinter getPrinterBySn(String printerSn) {
        FeiePrinter feiePrinter = new FeiePrinter();
        feiePrinter.setPrinterSn(printerSn);
        List<FeiePrinter> list = super.findList(feiePrinter);
        return Collections3.isEmpty(list) ? null : list.get(0);
    }

    @Transactional(readOnly = false)
    public void save(FeiePrinter feiePrinter) {
        super.save(feiePrinter);
    }

    @Transactional(readOnly = false)
    public void delete(FeiePrinter feiePrinter) {
        super.delete(feiePrinter);
    }


    /**
     * 打印订单   同一店铺下的所有打印机都打印
     *
     * @param shopId
     * @param order
     * @return
     */
    public BaseResp printByShopId(String shopId, PrintOrder order) {
        BaseResp resp = BaseResp.newError();
        // 订单或订单商品不能为空
        if (null == order || Collections3.isEmpty(order.getProductList())) {
            resp.setMsg("订单不存在或没有找到订单商品");
            return resp;
        }
        FeiePrinter FeiePrinter = new FeiePrinter();
        FeiePrinter.setShopId(shopId);
        List<FeiePrinter> printers = super.findList(FeiePrinter);
        int flag = 0;
        BaseResp tempResp;
        if (printers.size() == 0) {
            resp.setMsg("找不到店铺的打印机");
            return resp;
        } else {
            // 店铺下的所有可打印的打印机都打印，有一个完成都算成功
            for (FeiePrinter printer : printers) {
                // 备注处理
                String printerRemarks = StringUtils.defaultString(printer.getRemarks());
                String orderRemarks = StringUtils.defaultString(order.getRemarks());
                if (StringUtils.isEmpty(printerRemarks) || StringUtils.isEmpty(orderRemarks)) {
                    order.setRemarks(orderRemarks.concat(printerRemarks));
                } else {
                    order.setRemarks(orderRemarks.concat("<BR>").concat(printerRemarks));
                }
                // 打印机已启用 并且 已添加至飞鹅云
                if (StringUtils.equals(YesNoType.yes.getCode(), printer.getStatus()) && StringUtils.equals(YesNoType.yes.getCode(), printer.getUseable())) {
                    tempResp = printOrder(printer, order);
                    if (tempResp.isSuccess()) {
                        flag += 1;
                    }
                }
            }
        }
        if (flag > 0) {
            resp.success();
        }else {
            resp.setMsg("无可用打印机(未启用或未添加至后台)");
        }
        return resp;
    }

    /**
     * 打印订单  根据打印机id
     *
     * @param printerId 打印机id
     * @param order     订单
     * @return
     */
    public BaseResp printOrder(String printerId, PrintOrder order) {
        FeiePrinter printer = get(printerId);
        return printOrder(printer, order);
    }

    /**
     * 打印订单
     *
     * @param feiePrinter 打印机
     * @param order       订单
     * @return
     */
    public BaseResp printOrder(FeiePrinter feiePrinter, PrintOrder order) {
        BaseResp baseResp = BaseResp.newError();
        // 订单或订单商品不能为空
        if (null == order || Collections3.isEmpty(order.getProductList())) {
            baseResp.setMsg("订单不存在或没有找到订单商品");
            return baseResp;
        }
        String content = PrinterUtils.printMsg(order, PrinterUtils.fetchTemplate(Encodes.unescapeHtml(feiePrinter.getTemplate())));
        FeieyunRequestParam param = new FeieyunRequestParam();
        param.setSn(feiePrinter.getPrinterSn());
        param.setContent(content);
        param.setTimes(feiePrinter.getTimes());
        baseResp = feieyunOpenService.printMsg(param);
        return baseResp;
    }

    public BaseResp printBuyOrder(FeiePrinter feiePrinter, PrintParams... printParams){
        BaseResp baseResp = BaseResp.newError();
        String content = "";
        List<String> templates = PrinterUtils.fetchTemplate(Encodes.unescapeHtml(feiePrinter.getTemplate()));
        try {
            List<PrintParams> paramsList = Arrays.asList(printParams);
            for (PrintParams param : paramsList){
                int index = paramsList.indexOf(param);
                content = content + new PrintContentBuilder().buildHeader(templates.get(0+index*3),param.getHeader()).buildBody(templates.get(1+index*3),param.getProducts()).buildFooter(templates.get(2+index*3),param.getFooter()).create();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            baseResp.setMsg("编码转换错误");
            return baseResp;
        }
        FeieyunRequestParam param = new FeieyunRequestParam();
        param.setSn(feiePrinter.getPrinterSn());
        param.setContent(content);
        param.setTimes(feiePrinter.getTimes());
        baseResp = feieyunOpenService.printMsg(param);
        return baseResp;
    }

    // 根据订单号 返回打印的订单对象
    public PrintOrder fetchPrintOrderById(String orderId) {
        PrintOrder printOrder = new PrintOrder();
        try{
            GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
            if (null == goodsOrder) {
                return null;
            }
            printOrder.setName(goodsOrder.getShopName());
            printOrder.setOrderId(orderId);
            printOrder.setOrderTime(goodsOrder.getOrderTime());
            printOrder.setPayAmount(goodsOrder.getOrderAmount());
            // 商品列表的金额汇总为合计金额，应付金额为订单支付金额，优惠金额=合计金额-应付金额
            List<PrintProduct> productList = new ArrayList<>();
            GoodsOrderDetail goodsOrderDetail = new GoodsOrderDetail();
            goodsOrderDetail.setOrderId(orderId);
            List<GoodsOrderDetail> detailList = goodsOrderDetailService.findList(goodsOrderDetail);
            if (Collections3.isEmpty(detailList)) {
                return null;
            }
            BigDecimal totalAmount = BigDecimal.valueOf(0);
            PrintProduct product;
            BigDecimal goodsAmount;
            BigDecimal num;
            for (GoodsOrderDetail detail : detailList) {
                num = detail.getGoodsNum().setScale(0, BigDecimal.ROUND_HALF_UP);
                goodsAmount = detail.getGoodsPrice().multiply(num);
                // 金额累计
                totalAmount = totalAmount.add(goodsAmount);
                product = new PrintProduct();
                product.setName(detail.getGoodsName());
                product.setPrice(detail.getGoodsPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
                product.setNumber(num);
                product.setAmount(goodsAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
                productList.add(product);
            }
            printOrder.setProductList(productList);
            printOrder.setTotalAmount(totalAmount);
            printOrder.setDiscountAmount(totalAmount.subtract(printOrder.getPayAmount()));
            // 备注信息为订单备注
            printOrder.setRemarks(goodsOrder.getOrderMarks());
        }catch (Exception e){
            // 订单转换错误时
            printOrder = null;
        }
        return printOrder;
    }

    /**
     * 添加打印机至飞鹅云
     * @param feiePrinter
     * @param isSave  是否保存  保存时需完整信息
     * @return
     */
    public BaseResp addYunPrinter(FeiePrinter feiePrinter, boolean isSave) {
        FeieyunRequestParam param = new FeieyunRequestParam();
        param.fetchPrinterContent(feiePrinter);
        BaseResp baseResp = feieyunOpenService.addPrinter(param);
        if (baseResp.isSuccess()) {
            // 操作成功时判断  飞鹅云是否添加成功
            Map<String, ArrayList> result = JacksonUtils.readMapValue(baseResp.getExt().toString(), ArrayList.class);
            if (null != result && result.get("ok").size() > 0) {
                // 添加成功
                feiePrinter.setStatus(YesNoType.yes.getCode());
                feiePrinter.setUseable(YesNoType.yes.getCode());
                if(isSave){
                    super.save(feiePrinter);
                }
            } else {
                baseResp.error();
            }
        }
        return baseResp;
    }

    /**
     * 从飞鹅云删除打印机  根据打印机id
     *
     * @param feiePrinter 打印机 需完整信息
     * @return
     */
    @Transactional(readOnly = false)
    public BaseResp delYunPrinter(FeiePrinter feiePrinter) {
        FeieyunRequestParam param = new FeieyunRequestParam();
        param.setSnList(feiePrinter.getPrinterSn());
        BaseResp baseResp = feieyunOpenService.delPrinter(param);
        if (baseResp.isSuccess()) {
            // 操作成功时判断是否从飞鹅云删除
            Map<String, ArrayList> result = JacksonUtils.readMapValue(baseResp.getExt().toString(), ArrayList.class);
            // 删除成功
            if (null != result && result.get("ok").size() > 0) {
                feiePrinter.setStatus(YesNoType.no.getCode());
                feiePrinter.setUseable(YesNoType.no.getCode());
                super.save(feiePrinter);
            } else {
                baseResp.error();
            }
        }
        return baseResp;
    }


//    public void printTest(FeiePrinter feiePrinter) {
//        PrintProduct product;
//        List<PrintProduct> list = new ArrayList<>();
//        for (int i = 1; i < 4; i++) {
//            product = new PrintProduct();
//            product.setName("炒菜" + i);
//            product.setPrice(new BigDecimal(3.5 * i));
//            product.setNumber(new BigDecimal(i));
//            product.setAmount(new BigDecimal(3.5 * i * i));
//            list.add(product);
//        }
//
//        PrintOrder order = new PrintOrder();
//        order.setName("5号桌");
//        order.setOrderId("123456");
//        order.setProductList(list);
//        order.setOrderAmount(new BigDecimal("100.00"));
//        order.setDiscountAmount(new BigDecimal("25.00"));
//        order.setPayAmount(new BigDecimal("75.00"));
//        order.setRemarks("谢谢惠顾!");
//
//        BaseResp baseResp = printOrder(feiePrinter, order);
//        logger.debug("操作结果:");
//        logger.debug(baseResp.toJson());
//    }
}