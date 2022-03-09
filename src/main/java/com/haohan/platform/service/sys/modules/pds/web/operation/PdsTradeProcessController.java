package com.haohan.platform.service.sys.modules.pds.web.operation;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.core.operation.IPlatformOperationService;
import com.haohan.platform.service.sys.modules.pds.entity.operation.PdsTradeProcess;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.service.operation.PdsTradeProcessService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 平台交易流程管理Controller
 *
 * @author dy
 * @version 2018-10-26
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/operation/pdsTradeProcess")
public class PdsTradeProcessController extends BaseController {

    @Autowired
    private PdsTradeProcessService pdsTradeProcessService;
    @Autowired
    @Lazy(true)
    private IPlatformOperationService platformOperationService;

    @ModelAttribute
    public PdsTradeProcess get(@RequestParam(required = false) String id) {
        PdsTradeProcess entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = pdsTradeProcessService.get(id);
        }
        if (entity == null) {
            entity = new PdsTradeProcess();
        }
        return entity;
    }

    @RequiresPermissions("pds:operation:pdsTradeProcess:view")
    @RequestMapping(value = {"list", ""})
    public String list(PdsTradeProcess pdsTradeProcess, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<PdsTradeProcess> page = pdsTradeProcessService.findPage(new Page<PdsTradeProcess>(request, response), pdsTradeProcess);
        model.addAttribute("page", page);
        return "modules/pds/operation/pdsTradeProcessList";
    }

    @RequiresPermissions("pds:operation:pdsTradeProcess:view")
    @RequestMapping(value = "form")
    public String form(PdsTradeProcess pdsTradeProcess, Model model) {
        model.addAttribute("pdsTradeProcess", pdsTradeProcess);
        return "modules/pds/operation/pdsTradeProcessForm";
    }

    @RequiresPermissions("pds:operation:pdsTradeProcess:edit")
    @RequestMapping(value = "save")
    public String save(PdsTradeProcess pdsTradeProcess, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, pdsTradeProcess)) {
            return form(pdsTradeProcess, model);
        }
        pdsTradeProcessService.save(pdsTradeProcess);
        addMessage(redirectAttributes, "保存平台交易流程成功");
        return "redirect:" + Global.getAdminPath() + "/pds/operation/pdsTradeProcess/?repage";
    }

    @RequiresPermissions("pds:operation:pdsTradeProcess:edit")
    @RequestMapping(value = "delete")
    public String delete(PdsTradeProcess pdsTradeProcess, RedirectAttributes redirectAttributes) {
        pdsTradeProcessService.delete(pdsTradeProcess);
        addMessage(redirectAttributes, "删除平台交易流程成功");
        return "redirect:" + Global.getAdminPath() + "/pds/operation/pdsTradeProcess/?repage";
    }

    // 采购单状态变更为待确认
    @RequiresPermissions("pds:operation:pdsTradeProcess:edit")
    @RequestMapping(value = "statusWait")
    @ResponseBody
    public BaseResp updateBuyOrderStatus(BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        if (null == buyOrder.getDeliveryTime() || StringUtils.isEmpty(buyOrder.getBuySeq())) {
            baseResp.setMsg("缺少参数deliveryTime/buySeq");
            return baseResp;
        }
        baseResp = platformOperationService.updateBuyOrderStatus(buyOrder.getBuySeq(), buyOrder.getDeliveryTime());
        return baseResp;
    }

}