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
 * ?????? ??????????????????
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


    // ?????????????????? ??????
    public ArrayList<RespPayInfo> fetchPayChannelInfo(String merchantId) {
        ArrayList<RespPayInfo> result = new ArrayList<>();

        MerchantPayInfo payInfo = new MerchantPayInfo();
        payInfo.setMerchantId(merchantId);
        List<MerchantPayInfo> payInfoList = merchantPayInfoService.findList(payInfo);
        // ???????????????
        if (Collections3.isEmpty(payInfoList)) {
            return result;
        }
        ChannelRateManage channel = new ChannelRateManage();
        channel.setMerchantId(merchantId);
        List<ChannelRateManage> channelList = channelRateManageService.findList(channel);
        // ????????? ????????????
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

        // ???????????????????????????
        for (MerchantPayInfo m : payInfoList) {
            // ????????????
            RespPayInfo respPayInfo = new RespPayInfo();
            respPayInfo.copyFromPayInfo(m);
            respPayInfo.setChannelList(tempMap.get(m.getId()));
            result.add(respPayInfo);
        }
        return result;
    }

    // ??????????????????
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

    // ????????????
    public RespPage<TerminalManage> fetchTerminalList(TerminalManage terminalManage) {
        List<TerminalManage> list = terminalManageService.findList(terminalManage);
        RespPage<TerminalManage> page = new RespPage<>();
        page.fetchFromPage(terminalManage.getPage());
        page.setList(list);
        return page;
    }

    // ????????????
    public TerminalManage fetchTerminalInfo(TerminalManage terminalManage) {
        TerminalManage terminal = terminalManageService.get(terminalManage.getId());
        if(null != terminal && !StringUtils.equals(terminal.getMerchantId(),terminalManage.getMerchantId())){
            return null;
        }
        return terminal;
    }

    // ??????????????????
    @Transactional(readOnly = false)
    public void terminalModify(TerminalManage terminalManage) {
        // ???????????????
        if(StringUtils.isNotEmpty(terminalManage.getId())){
            TerminalManage terminal = fetchTerminalInfo(terminalManage);
            if(null == terminal){
                terminalManage.setId(null);
            }
        }
        terminalManageService.save(terminalManage);
    }

    // ???????????????????????????
    public RespPage<FeiePrinter> fetchPrinterList(FeiePrinter feiePrinter) {
        List<FeiePrinter> list = feiePrinterService.findList(feiePrinter);
        RespPage<FeiePrinter> page = new RespPage<>();
        page.fetchFromPage(feiePrinter.getPage());
        // ?????????????????????
        for(FeiePrinter f:list){
            f.delProperties();
        }
        page.setList(list);
        return page;
    }

    // ?????????????????????
    public FeiePrinter fetchPrinterInfo(FeiePrinter feiePrinter) {
        return feiePrinterService.get(feiePrinter.getId());
    }

    // ???????????????
    @Transactional(readOnly = false)
    public BaseResp printerModify(FeiePrinter feiePrinter) {
        BaseResp resp = BaseResp.newError();
        FeiePrinter printer;
        // ??????????????????????????????
        boolean flag = false;
        //  ????????? ?????????????????????
        if(StringUtils.isEmpty(feiePrinter.getId())){
            printer = feiePrinterService.getPrinterBySn(feiePrinter.getPrinterSn());
            if(null != printer){
                resp.setMsg("?????????????????????");
                return resp;
            }
            flag = true;
        }else{
            //  ????????? ????????????????????? ??????????????? Sn/ Key
            printer = feiePrinterService.get(feiePrinter.getId());
            if(StringUtils.equals(printer.getStatus(), ICommonConstant.YesNoType.yes.getCode())){
                feiePrinter.setPrinterSn(printer.getPrinterSn());
                feiePrinter.setPrinterKey(printer.getPrinterKey());
            }else if(StringUtils.equals(feiePrinter.getUseable(), ICommonConstant.YesNoType.yes.getCode())){
                // ?????????????????????????????????????????????
                flag = true;
            }
        }
        if(flag){
            // ??????????????????????????? ?????????
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
