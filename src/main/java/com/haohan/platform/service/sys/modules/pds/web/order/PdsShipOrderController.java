package com.haohan.platform.service.sys.modules.pds.web.order;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.order.PdsShipOrder;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.order.PdsShipOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
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
 * 送货单Controller
 *
 * @author yu.shen
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/order/pdsShipOrder")
public class PdsShipOrderController extends BaseController {

    @Autowired
    private PdsShipOrderService pdsShipOrderService;
    @Autowired
    private PdsBuyerService pdsBuyerService;

    @ModelAttribute
    public PdsShipOrder get(@RequestParam(required = false) String id) {
        PdsShipOrder entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = pdsShipOrderService.get(id);
        }
        if (entity == null) {
            entity = new PdsShipOrder();
        }
        return entity;
    }

    @RequiresPermissions("pds:order:pdsShipOrder:view")
    @RequestMapping(value = {"list", ""})
    public String list(PdsShipOrder pdsShipOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<PdsShipOrder> page = new Page<PdsShipOrder>(request, response);
        pdsShipOrder.setPage(page);
        page.setList(pdsShipOrderService.findJoinList(pdsShipOrder));
        model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/pdsShipOrderList";
    }

    @RequiresPermissions("pds:order:pdsShipOrder:view")
    @RequestMapping(value = "form")
    public String form(PdsShipOrder pdsShipOrder, Model model) {
        model.addAttribute("pdsShipOrder", pdsShipOrder);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/pdsShipOrderForm";
    }

    @RequiresPermissions("pds:order:pdsShipOrder:edit")
    @RequestMapping(value = "save")
    public String save(PdsShipOrder pdsShipOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, pdsShipOrder)) {
            return form(pdsShipOrder, model);
        }
        pdsShipOrderService.save(pdsShipOrder);
        addMessage(redirectAttributes, "保存送货单成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/pdsShipOrder/?repage";
    }

    @RequiresPermissions("pds:order:pdsShipOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(PdsShipOrder pdsShipOrder, RedirectAttributes redirectAttributes) {
        pdsShipOrderService.delete(pdsShipOrder);
        addMessage(redirectAttributes, "删除送货单成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/pdsShipOrder/?repage";
    }

}