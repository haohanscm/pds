package com.haohan.platform.service.sys.modules.pds.web.delivery;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.delivery.IPdsDeliveryService;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.DeliveryFlow;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.delivery.DeliveryFlowService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.MerchantEmployeeService;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 物流配送Controller
 *
 * @author haohan
 * @version 2018-10-17
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/delivery/deliveryFlow")
public class DeliveryFlowController extends BaseController {

    @Autowired
    private DeliveryFlowService deliveryFlowService;
    @Resource
    private MerchantEmployeeService merchantEmployeeService;
    @Autowired
    @Lazy(true)
    private IPdsDeliveryService pdsDeliveryService;
    @Autowired
    private PdsBuyerService pdsBuyerService;

    @ModelAttribute
    public DeliveryFlow get(@RequestParam(required = false) String id) {
        DeliveryFlow entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = deliveryFlowService.get(id);
        }
        if (entity == null) {
            entity = new DeliveryFlow();
        }
        return entity;
    }

    @RequiresPermissions("pds:delivery:deliveryFlow:view")
    @RequestMapping(value = {"list", ""})
    public String list(DeliveryFlow deliveryFlow, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<DeliveryFlow> page = new Page<DeliveryFlow>(request, response);
        deliveryFlow.setPage(page);
        page.setList(deliveryFlowService.findJoinList(deliveryFlow));
        model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/delivery/deliveryFlowList";
    }

    @RequiresPermissions("pds:delivery:deliveryFlow:view")
    @RequestMapping(value = "form")
    public String form(DeliveryFlow deliveryFlow, Model model) {
        MerchantEmployee merchantEmployee = new MerchantEmployee();
        merchantEmployee.setRole(IPdsConstant.EmployeeRole.driver.getCode());
        List<MerchantEmployee> merchantEmployeeList = merchantEmployeeService.findList(merchantEmployee);

        model.addAttribute("merchantEmployeeList", merchantEmployeeList);
        model.addAttribute("deliveryFlow", deliveryFlow);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/delivery/deliveryFlowForm";
    }

    @RequiresPermissions("pds:delivery:deliveryFlow:edit")
    @RequestMapping(value = "save")
    public String save(DeliveryFlow deliveryFlow, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, deliveryFlow)) {
            return form(deliveryFlow, model);
        }
        deliveryFlowService.save(deliveryFlow);
        addMessage(redirectAttributes, "保存物流配送成功");
        return "redirect:" + Global.getAdminPath() + "/pds/delivery/deliveryFlow/?repage";
    }

    @RequiresPermissions("pds:delivery:deliveryFlow:edit")
    @RequestMapping(value = "delete")
    public String delete(DeliveryFlow deliveryFlow, RedirectAttributes redirectAttributes) {
        deliveryFlowService.delete(deliveryFlow);
        addMessage(redirectAttributes, "删除物流配送成功");
        return "redirect:" + Global.getAdminPath() + "/pds/delivery/deliveryFlow/?repage";
    }

    // 货物送达
    @RequiresPermissions("pds:delivery:deliveryFlow:edit")
    @RequestMapping(value = "goodsArrived")
    @ResponseBody
    public BaseResp goodsArrived(DeliveryFlow deliveryFlow) {
        BaseResp baseResp = BaseResp.newError();

        if (StringUtils.isEmpty(deliveryFlow.getDeliveryId())) {
            baseResp.setMsg("缺少参数deliveryId");
            return baseResp;
        }
        baseResp = pdsDeliveryService.goodsArrived(deliveryFlow);
        return baseResp;
    }

}