package com.haohan.platform.service.sys.modules.xiaodian.util;


import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.PrintOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.PrintProduct;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by dy on 2018/8/6.
 */
public class PrinterUtils {

    /**
     * 生成小票内容
     * @param order    订单 不能为空
     * @param template 样式模板
     */
    public static String printMsg(PrintOrder order, List<String> template) {
        StringBuilder result = new StringBuilder();
        result.append(String.format(template.get(0), order.getName()));
        String body;
        String templateStyle;
        String name;
        String tempName;
        int priceLen;
        int numberLen;
        int amountLen;
        for (PrintProduct product : order.getProductList()) {
            name = product.getName() == null ? "" : product.getName();
            priceLen = StringUtils.length(product.getPrice().toString());
            numberLen = StringUtils.length(product.getNumber().toString());
            amountLen = StringUtils.length(product.getAmount().toString());
            // 计算字符长度，中文长度为2
            try {
                byte[] buff = name.getBytes("GBK");
                tempName = new String(buff, "ISO-8859-1");
                // 判断1或2行输出商品
                if (buff.length <= 16 && (priceLen + numberLen + amountLen) <= 14) {
                    templateStyle = template.get(1);
                } else {
                    templateStyle = template.get(2);
                }
                body = String.format(templateStyle, tempName, product.getPrice(), product.getNumber(), product.getAmount());
                body = new String(body.getBytes("ISO-8859-1"), "GBK");
            } catch (UnsupportedEncodingException e) {
                templateStyle = template.get(2);
                body = String.format(templateStyle, name, product.getPrice(), product.getNumber(), product.getAmount());
            }
            result.append(body);
        }
        result.append(String.format(template.get(3), order.getOrderId(), DateUtils.formatDateTime(order.getOrderTime()),order.getTotalAmount(), order.getDiscountAmount(), order.getPayAmount(), order.getRemarks()));
        return result.toString();
    }

    // 小票样式模板  头部样式/产品样式1/产品样式2/底部样式
    public static List<String> defaultTemplate() {
        List<String> list = new ArrayList<>();
        // 小票头部  58型 宽度32字母
        String head = "<CB>%s</CB><BR>";
        head += "名称           单价  数量  金额<BR>";
        head += "--------------------------------<BR>";
        list.add(head);
        // 产品信息
        // 样式1,一行输出   名称只占16位    ,单价最少占6位,数量最少占2位,金额最少占6位
        String bodyStyle1 = "%-16.16s%-6s %2s %6s<BR>";
        list.add(bodyStyle1);

        // 样式2,两行输出   名称占一行    单价最少占6位,数量最少占4位,金额最少占6位
        String bodyStyle2 = "%s<BR>";
        bodyStyle2 += "<RIGHT>%6s %4s %6s</RIGHT><BR>";
        list.add(bodyStyle2);

        // 小票底部
        String bottom = "--------------------------------<BR>";
        bottom += "订单号：%s<BR>";
        bottom += "下单时间：%s<BR>";
        bottom += "<RIGHT>合计: %s元 优惠: %s元</RIGHT>";
        bottom += "<RIGHT>应付: %s元</RIGHT>";
        bottom += "%s<BR>";
        bottom += "--------------------------------<BR>";
        list.add(bottom);
        return list;
    }

    // 获取打印小票样式(分隔符为,) 未设置的按默认样式
    public static List<String> fetchTemplate(String temp) {
//        Optional.of(temp).orElseThrow(IllegalArgumentException::new);
        List<String> list = defaultTemplate();
        if (StringUtils.isNotEmpty(temp)) {
            list = new ArrayList<>();
            String[] tempArray = StringUtils.split(temp, ",");
            for (int i = 0; i < tempArray.length; i++) {
                if (StringUtils.isEmpty(tempArray[i])) {
                    continue;
                } else {
                    list.add(i, tempArray[i]);
                }
            }
        }
        return list;
    }



}
