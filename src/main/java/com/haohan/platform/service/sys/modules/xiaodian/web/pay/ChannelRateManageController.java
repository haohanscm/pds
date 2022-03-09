package com.haohan.platform.service.sys.modules.xiaodian.web.pay;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.BankPayService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.ChannelRateManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.MerchantPayInfo;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.ChannelRateManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.MerchantPayInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 支付渠道费率管理Controller
 *
 * @author haohan
 * @version 2017-12-11
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/pay/channelRateManage")
public class ChannelRateManageController extends BaseController {

    @Autowired
    private ChannelRateManageService channelRateManageService;

    @Autowired
    private BankPayService bankPayService;

    @Autowired
    private MerchantPayInfoService merchantPayInfoService;

    @ModelAttribute
    public ChannelRateManage get(@RequestParam(required = false) String id) {
        ChannelRateManage entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = channelRateManageService.get(id);
        }
        if (entity == null) {
            entity = new ChannelRateManage();
        }
        return entity;
    }

    @RequiresPermissions("xiaodian:pay:channelRateManage:view")
    @RequestMapping(value = {"list", ""})
    public String list(ChannelRateManage channelRateManage, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ChannelRateManage> page = channelRateManageService.findPage(new Page<ChannelRateManage>(request, response), channelRateManage);
        model.addAttribute("page", page);
        return "modules/xiaodian/pay/channelRateManageList";
    }


    @RequiresPermissions("xiaodian:pay:channelRateManage:view")
    @RequestMapping(value = "form")
    public String form(ChannelRateManage channelRateManage, Model model) {
        model.addAttribute("channelRateManage", channelRateManage);
        List<MerchantPayInfo> merchantPayInfoList = merchantPayInfoService.findList(new MerchantPayInfo());
        model.addAttribute("merchantPayInfoList", merchantPayInfoList);
        return "modules/xiaodian/pay/channelRateManageForm";
    }

    @RequiresPermissions("xiaodian:pay:channelRateManage:edit")
    @RequestMapping(value = "save")
    public String save(ChannelRateManage channelRateManage, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, channelRateManage)) {
            return form(channelRateManage, model);
        }

        MerchantPayInfo payInfo = merchantPayInfoService.get(channelRateManage.getPayInfoId());
        if (null != payInfo) {
            channelRateManage.setMerchantId(payInfo.getMerchantId());
            channelRateManage.setPayInfoId(payInfo.getId());
        }
        channelRateManageService.save(channelRateManage);
        addMessage(redirectAttributes, "保存渠道费率成功");
        return "redirect:" + Global.getAdminPath() + "/xiaodian/pay/channelRateManage/?repage";
    }

    @RequiresPermissions("xiaodian:pay:channelRateManage:edit")
    @RequestMapping(value = "delete")
    public String delete(ChannelRateManage channelRateManage, RedirectAttributes redirectAttributes) {
        channelRateManageService.delete(channelRateManage);
        addMessage(redirectAttributes, "删除渠道费率成功");
        return "redirect:" + Global.getAdminPath() + "/xiaodian/pay/channelRateManage/?repage";
    }

    @RequiresPermissions("xiaodian:pay:channelRateManage:edit")
    @RequestMapping(value = "api")
    public String api(ChannelRateManage channelRateManage, RedirectAttributes redirectAttributes) {
        boolean flag = bankPayService.merchantPayRateConfig(channelRateManage);
        if (flag) {
            channelRateManage.setStatus(IBankServiceConstant.BankRegStatus.SUCCESS.getCode().toString());
        } else {
            channelRateManage.setStatus(IBankServiceConstant.BankRegStatus.FAIL.getCode().toString());
        }
        addMessage(redirectAttributes, channelRateManage.getRespMsg());
        channelRateManageService.save(channelRateManage);
        return "redirect:" + Global.getAdminPath() + "/xiaodian/pay/channelRateManage/?repage";
    }



}