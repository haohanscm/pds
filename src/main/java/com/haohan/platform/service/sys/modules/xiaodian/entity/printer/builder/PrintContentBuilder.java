package com.haohan.platform.service.sys.modules.xiaodian.entity.printer.builder;

import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.PrintProduct;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2019/3/25
 */
public class PrintContentBuilder {
    private String header = "";
    private String body = "";
    private String footer = "";

    public PrintContentBuilder buildHeader(String templateHeader,String[] args){
        header = String.format(templateHeader,args);
        return this;
    }

    public PrintContentBuilder buildBody(String templateBody, PrintProduct... products) throws UnsupportedEncodingException {
        templateBody = gbkToIso8859_1(templateBody);
        StringBuffer sbf = new StringBuffer();
        String bodyTemp ;
        for (PrintProduct product : products){
            String[] params = product.getParamList();
            params = Arrays.stream(params).map(String -> gbkToIso8859_1(String)).collect(Collectors.toList()).toArray(new String[]{});
            bodyTemp = String.format(templateBody,params);
            sbf.append(bodyTemp);
//            String name = gbkToIso8859_1(Optional.ofNullable(product.getName()).orElseThrow(IllegalArgumentException::new));
//            String unit = gbkToIso8859_1(Optional.ofNullable(product.getUnit()).orElseThrow(IllegalArgumentException::new));
//            bodyTemp = String.format(templateBody,name,product.getNumber(),unit,product.getPrice());
//            sbf.append(bodyTemp);
        }
        body = iso8859_1ToGbk(sbf.toString());
        return this;
    }

    public PrintContentBuilder buildFooter(String templateFooter, String[] args){
        footer = String.format(templateFooter,args);
        return this;
    }

    public String create(){
        return header+body+footer;
    }

    private static String gbkToIso8859_1 (String source) {
        String result = "";
        try {
            result = new String(source.getBytes("gbk"),"iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    private static String iso8859_1ToGbk (String source) {
        String result = "";
        try {
            result = new String(source.getBytes("iso-8859-1"),"gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}
