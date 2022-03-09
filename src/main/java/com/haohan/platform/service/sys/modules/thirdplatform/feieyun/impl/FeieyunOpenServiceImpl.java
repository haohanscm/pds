package com.haohan.platform.service.sys.modules.thirdplatform.feieyun.impl;


import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.HttpClientUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.feieyun.entity.FeieyunDataResp;
import com.haohan.platform.service.sys.modules.thirdplatform.feieyun.entity.FeieyunRequestParam;
import com.haohan.platform.service.sys.modules.thirdplatform.feieyun.inf.IFeieyunOpenService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dy on 2018/8/1.
 */
@Service
public class FeieyunOpenServiceImpl implements IFeieyunOpenService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 发送post请求
    private void apiHttpPost(BaseResp baseResp, Map<String, String> reqParams) {
//        Map<String, String> headMap = new HashMap<>();
        String respData = HttpClientUtils.getInstance().httpPost(url, reqParams, null, "UTF-8");
        FeieyunDataResp resp = JacksonUtils.readValue(respData, FeieyunDataResp.class);
        logger.debug("apiBegin\nreqApi:{},{},\nreqParams:{}\n,respParams:{}", url, addPrinterApiName, JacksonUtils.toJson(reqParams), respData);
        if (FeieyunDataResp.newSuccess().getRet().equals(resp.getRet())) {
            baseResp.success();
            baseResp.setExt(JacksonUtils.toJson(resp.getData()));
        } else {
            baseResp.error();
            baseResp.setMsg(resp.getMsg());
        }
    }

    @Override
    public Map<String, String> fetchCommonParams(String apiName) {
        Map<String, String> params = new HashMap<>();
        String stime = DateUtils.getUnixTime().toString();
        params.put("user", user);
        params.put("stime", stime);
        // 进行SHA1加密,生成签名字符串
        String sig = DigestUtils.sha1Hex(user + ukey + stime);
        params.put("sig", sig);
        params.put("apiname", apiName);
        return params;
    }

    @Override
    public BaseResp addPrinter(FeieyunRequestParam param) {
        BaseResp baseResp = new BaseResp();
        if (StringUtils.isEmpty(param.getPrinterContent())) {
            baseResp.error();
            baseResp.setMsg("未提供需添加的打印机");
            return baseResp;
        }
        // 获取参数
        Map<String, String> reqParams = fetchCommonParams(addPrinterApiName);
        reqParams.put("printerContent", param.getPrinterContent());

        apiHttpPost(baseResp, reqParams);
        return baseResp;
    }

    @Override
    public BaseResp printMsg(FeieyunRequestParam param) {
        BaseResp baseResp = new BaseResp();
        if (StringUtils.isAnyEmpty(param.getSn(), param.getContent())) {
            baseResp.error();
            baseResp.setMsg("未提供打印机编号或打印内容");
            return baseResp;
        }
        // 获取参数
        Map<String, String> reqParams = fetchCommonParams(printMsgApiName);
        reqParams.put("sn", param.getSn());
        reqParams.put("content", param.getContent());
        // 默认打印次数 1次
        reqParams.put("times", StringUtils.isEmpty(param.getTimes()) ? "1" : param.getTimes());

        apiHttpPost(baseResp, reqParams);
        return baseResp;
    }

    @Override
    public BaseResp delPrinter(FeieyunRequestParam param) {
        BaseResp baseResp = new BaseResp();
        if (StringUtils.isEmpty(param.getSnList())) {
            baseResp.error();
            baseResp.setMsg("未提供打印机编号");
            return baseResp;
        }
        // 获取参数
        Map<String, String> reqParams = fetchCommonParams(delPrinterApiName);
        reqParams.put("snlist", param.getSnList());

        apiHttpPost(baseResp, reqParams);
        return baseResp;
    }

    @Override
    public BaseResp editPrinter(FeieyunRequestParam param) {
        BaseResp baseResp = new BaseResp();
        if (StringUtils.isEmpty(param.getSn())) {
            baseResp.error();
            baseResp.setMsg("未提供打印机编号");
            return baseResp;
        }
        // 获取参数
        Map<String, String> reqParams = fetchCommonParams(editPrinterApiName);
        reqParams.put("sn", param.getSn());
        if (StringUtils.isNotEmpty(param.getName())) {
            reqParams.put("name", param.getName());
        }
        if (StringUtils.isNotEmpty(param.getPhoneNum())) {
            reqParams.put("phonenum", param.getPhoneNum());
        }
        apiHttpPost(baseResp, reqParams);
        return baseResp;
    }

    @Override
    public BaseResp cleanPrinter(FeieyunRequestParam param) {
        BaseResp baseResp = new BaseResp();
        if (StringUtils.isEmpty(param.getSn())) {
            baseResp.error();
            baseResp.setMsg("未提供打印机编号");
            return baseResp;
        }
        // 获取参数
        Map<String, String> reqParams = fetchCommonParams(cleanPrinterApiName);
        reqParams.put("sn", param.getSn());

        apiHttpPost(baseResp, reqParams);
        return baseResp;
    }

    @Override
    public BaseResp orderState(FeieyunRequestParam param) {
        BaseResp baseResp = new BaseResp();
        if (StringUtils.isEmpty(param.getOrderId())) {
            baseResp.error();
            baseResp.setMsg("未提供打印订单号");
            return baseResp;
        }
        // 获取参数
        Map<String, String> reqParams = fetchCommonParams(orderStateApiName);
        reqParams.put("orderid", param.getOrderId());

        apiHttpPost(baseResp, reqParams);
        return baseResp;
    }

    @Override
    public BaseResp orderInfo(FeieyunRequestParam param) {
        BaseResp baseResp = new BaseResp();
        if (StringUtils.isAnyEmpty(param.getSn(), param.getDate())) {
            baseResp.error();
            baseResp.setMsg("未提供打印机编号或查询日期");
            return baseResp;
        }
        // 获取参数
        Map<String, String> reqParams = fetchCommonParams(orderInfoApiName);
        reqParams.put("sn", param.getSn());
        reqParams.put("date", param.getDate());

        apiHttpPost(baseResp, reqParams);
        return baseResp;
    }

    @Override
    public BaseResp printerState(FeieyunRequestParam param) {
        BaseResp baseResp = new BaseResp();
        if (StringUtils.isEmpty(param.getSn())) {
            baseResp.error();
            baseResp.setMsg("未提供打印机编号");
            return baseResp;
        }
        // 获取参数
        Map<String, String> reqParams = fetchCommonParams(printerStateApiName);
        reqParams.put("sn", param.getSn());

        apiHttpPost(baseResp, reqParams);
        return baseResp;
    }
}
