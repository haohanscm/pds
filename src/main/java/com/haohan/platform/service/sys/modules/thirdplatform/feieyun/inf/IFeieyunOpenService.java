package com.haohan.platform.service.sys.modules.thirdplatform.feieyun.inf;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.thirdplatform.feieyun.entity.FeieyunRequestParam;

import java.util.Map;

/**
 * Created by dy on 2018/8/1.
 */
public interface IFeieyunOpenService {

    String user = "it-service@haohanshop.com";

    String ukey = "XWNVIWyZ4Nvb74Rz";

    String url = "http://api.feieyun.cn/Api/Open/";
//    String url = "https://api.feieyun.cn/Api/Open/";

    String addPrinterApiName = "Open_printerAddlist";// 批量添加打印机

    String printMsgApiName = "Open_printMsg"; // 发送用户需要打印的订单内容给飞鹅云打印机

    String delPrinterApiName = "Open_printerDelList"; // 批量删除打印机

    String editPrinterApiName = "Open_printerEdit"; // 修改打印机信息

    String cleanPrinterApiName = "Open_delPrinterSqs"; // 清空待打印队列

    String orderStateApiName = "Open_queryOrderState"; // 根据订单ID,去查询订单是否打印成功,订单ID由接口Open_printMsg返回

    String orderInfoApiName = "Open_queryOrderInfoByDate"; // 查询指定打印机某天的订单详情，返回已打印订单数和等待打印数。

    String printerStateApiName = "Open_queryPrinterStatus"; // 查询指定打印机状态，返回该打印机在线或离线，正常或异常的信息。

    /**
     *  设置公共参数
     * @param apiName 请求的接口名称
     * @return  user 飞鹅云后台注册用户名;stime 当前UNIX时间戳，10位，精确到秒;
     *           sig 对参数 user+UKEY+stime拼接后（+号表示连接符）进行SHA1加密得到签名，值为40位小写字符串;
     */
    Map<String, String> fetchCommonParams(String apiName);

    /**
     * 批量添加打印机
     * @param  param printerContent 打印机编号(必填) # 打印机识别码(必填) # 备注名称(选填) # 流量卡号码(选填)，
     *               多台打印机请换行（\n）添加新打印机信息，每次最多100台。
     * @return data  返回array，包含成功和失败的信息
     */
    BaseResp addPrinter(FeieyunRequestParam param);

    /**
     * 打印订单
     * @param param  sn 打印机编号; content 打印内容,不能超过5000字节; times 打印次数，默认为1。
     * @return data  返回string, 正确返回订单ID。
     */
    BaseResp printMsg(FeieyunRequestParam param);

    /**
     * 删除批量打印机
     * @param  param snList 打印机编号，多台打印机请用减号“-”连接起来。
     * @return data  返回array，包含成功和失败的信息
     */
    BaseResp delPrinter(FeieyunRequestParam param);

    /**
     * 修改打印机信息
     * @param param sn 打印机编号; name 打印机备注名称; phonenum 打印机流量卡号码
     * @return data  返回boolean, 正确返回true
     */
    BaseResp editPrinter(FeieyunRequestParam param);

    /**
     * 清空待打印队列
     * @param param sn 打印机编号;
     * @return data  返回boolean, 正确返回true
     */
    BaseResp cleanPrinter(FeieyunRequestParam param);

    /**
     * 查询订单是否打印成功
     * @param param orderid 订单ID，由打印订单接口返回
     * @return data  返回boolean, 已打印返回true,未打印返回false
     */
    BaseResp orderState(FeieyunRequestParam param);

    /**
     * 查询指定打印机某天的订单统计数
     * @param param sn 打印机编号; date 查询日期，格式YY-MM-DD，如：2016-09-20
     * @return data  返回array, 返回已打印订单数和等待打印数
     */
    BaseResp orderInfo(FeieyunRequestParam param);

    /**
     * 获取某台打印机状态
     * @param param sn 打印机编号;
     * @return data  返回string, 返回打印机状态信息。共三种：
     * 1、离线
     * 2、在线，工作状态正常
     * 3、在线，工作状态不正常。
     * 备注：异常一般是无纸，离线的判断是打印机与服务器失去联系超过5分钟。
     */
    BaseResp printerState(FeieyunRequestParam param);




}
