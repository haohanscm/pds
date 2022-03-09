package com.haohan.platform.service.sys.modules.xiaodian.api.service.merchant.manage;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.RespPage;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.manage.RespChannel;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.manage.RespPayInfo;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.manage.RespShop;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.resp.MerchantInfoResp;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.merchant.MerchantDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.ChannelRateManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.MerchantPayInfo;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.FeiePrinter;
import com.haohan.platform.service.sys.modules.xiaodian.entity.teiminal.TerminalManage;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.ChannelRateManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.MerchantPayInfoService;
import com.haohan.platform.service.sys.modules.xiaodian.service.printer.FeiePrinterService;
import com.haohan.platform.service.sys.modules.xiaodian.service.terminal.TerminalManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商户 管理信息设置
 * Created by dy on 2018/10/6.
 */
@Service
@Transactional(readOnly = true)
public class MerchantManageService {

    @Autowired
    @Lazy(true)
    private ChannelRateManageService channelRateManageService;
    @Autowired
    @Lazy(true)
    private MerchantPayInfoService merchantPayInfoService;
    @Autowired
    @Lazy(true)
    private ShopService shopService;
    @Autowired
    @Lazy(true)
    private TerminalManageService terminalManageService;
    @Autowired
    @Lazy(true)
    private FeiePrinterService feiePrinterService;
    @Autowired
    private MerchantDao merchantDao;


    // 商家支付渠道 查询
    public ArrayList<RespPayInfo> fetchPayChannelInfo(String merchantId) {
        ArrayList<RespPayInfo> result = new ArrayList<>();

        MerchantPayInfo payInfo = new MerchantPayInfo();
        payInfo.setMerchantId(merchantId);
        List<MerchantPayInfo> payInfoList = merchantPayInfoService.findList(payInfo);
        // 无商家账户
        if (Collections3.isEmpty(payInfoList)) {
            return result;
        }
        ChannelRateManage channel = new ChannelRateManage();
        channel.setMerchantId(merchantId);
        List<ChannelRateManage> channelList = channelRateManageService.findList(channel);
        // 有效的 支付渠道
        Map<String, List<RespChannel>> tempMap = new HashMap<>();
        for (ChannelRateManage c : channelList) {
            if (tempMap.containsKey(c.getPayInfoId())) {
                if (StringUtils.isNoneEmpty(c.getRate(), c.getStatus())) {
                    RespChannel respChannel = new RespChannel();
                    respChannel.copyFromChannel(c);
                    tempMap.get(c.getPayInfoId()).add(respChannel);
                }
            } else {
                List<RespChannel> list = new ArrayList<>();
                if (StringUtils.isNoneEmpty(c.getRate(), c.getStatus())) {
                    RespChannel respChannel = new RespChannel();
                    respChannel.copyFromChannel(c);
                    list.add(respChannel);
                }
                tempMap.put(c.getPayInfoId(), list);
            }
        }

        // 商家多支付账户信息
        for (MerchantPayInfo m : payInfoList) {
            // 支付账户
            RespPayInfo respPayInfo = new RespPayInfo();
            respPayInfo.copyFromPayInfo(m);
            respPayInfo.setChannelList(tempMap.get(m.getId()));
            result.add(respPayInfo);
        }
        return result;
    }

    // 商家店铺列表
    public ArrayList<RespShop> fetchShopList(String merchantId) {
        Shop shop = new Shop();
        shop.setMerchantId(merchantId);
        List<Shop> list = shopService.findList(shop);
        ArrayList<RespShop> result = new ArrayList<>();
        for (Shop s : list) {
            RespShop respShop = new RespShop();
            respShop.copyFromShop(s);
            result.add(respShop);
        }
        return result;
    }

    // 设备列表
    public RespPage<TerminalManage> fetchTerminalList(TerminalManage terminalManage) {
        List<TerminalManage> list = terminalManageService.findList(terminalManage);
        RespPage<TerminalManage> page = new RespPage<>();
        page.fetchFromPage(terminalManage.getPage());
        page.setList(list);
        return page;
    }

    // 设备详细
    public TerminalManage fetchTerminalInfo(TerminalManage terminalManage) {
        TerminalManage terminal = terminalManageService.get(terminalManage.getId());
        if(null != terminal && !StringUtils.equals(terminal.getMerchantId(),terminalManage.getMerchantId())){
            return null;
        }
        return terminal;
    }

    // 设备信息修改
    @Transactional(readOnly = false)
    public void terminalModify(TerminalManage terminalManage) {
        // 修改时判断
        if(StringUtils.isNotEmpty(terminalManage.getId())){
            TerminalManage terminal = fetchTerminalInfo(terminalManage);
            if(null == terminal){
                terminalManage.setId(null);
            }
        }
        terminalManageService.save(terminalManage);
    }

    // 查询飞鹅打印机列表
    public RespPage<FeiePrinter> fetchPrinterList(FeiePrinter feiePrinter) {
        List<FeiePrinter> list = feiePrinterService.findList(feiePrinter);
        RespPage<FeiePrinter> page = new RespPage<>();
        page.fetchFromPage(feiePrinter.getPage());
        // 部分属性不展示
        for(FeiePrinter f:list){
            f.delProperties();
        }
        page.setList(list);
        return page;
    }

    // 打印机详细获取
    public FeiePrinter fetchPrinterInfo(FeiePrinter feiePrinter) {
        return feiePrinterService.get(feiePrinter.getId());
    }

    // 打印机修改
    @Transactional(readOnly = false)
    public BaseResp printerModify(FeiePrinter feiePrinter) {
        BaseResp resp = BaseResp.newError();
        FeiePrinter printer;
        // 是否添加至飞鹅云标记
        boolean flag = false;
        //  新增时 验证是否已存在
        if(StringUtils.isEmpty(feiePrinter.getId())){
            printer = feiePrinterService.getPrinterBySn(feiePrinter.getPrinterSn());
            if(null != printer){
                resp.setMsg("该打印机已添加");
                return resp;
            }
            flag = true;
        }else{
            //  修改时 若飞鹅云已添加 不允许修改 Sn/ Key
            printer = feiePrinterService.get(feiePrinter.getId());
            if(StringUtils.equals(printer.getStatus(), ICommonConstant.YesNoType.yes.getCode())){
                feiePrinter.setPrinterSn(printer.getPrinterSn());
                feiePrinter.setPrinterKey(printer.getPrinterKey());
            }else if(StringUtils.equals(feiePrinter.getUseable(), ICommonConstant.YesNoType.yes.getCode())){
                // 当飞鹅云未添加并想启用打印机时
                flag = true;
            }
        }
        if(flag){
            // 添加打印机至飞鹅云 并启用
            BaseResp baseResp = feiePrinterService.addYunPrinter(feiePrinter,false);
            if(!baseResp.isSuccess()){
                feiePrinter.setUseable(ICommonConstant.YesNoType.no.getCode());
                feiePrinter.setStatus(ICommonConstant.YesNoType.no.getCode());
            }
        }
        feiePrinterService.save(feiePrinter);
        resp.success();
        return resp;
    }


    public MerchantInfoResp getMerchantInfoResp(Integer tenantId) {

        return merchantDao.findMerchantInfo(tenantId);
    }
}
