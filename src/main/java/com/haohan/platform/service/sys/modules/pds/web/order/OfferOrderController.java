package com.haohan.platform.service.sys.modules.pds.web.order;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.entity.order.OfferOrder;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.order.OfferOrderService;
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
 * 报价单Controller
 *
 * @author haohan
 * @version 2018-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/order/offerOrder")
public class OfferOrderController extends BaseController {

    @Autowired
    private OfferOrderService offerOrderService;
    @Autowired
    private PdsBuyerService pdsBuyerService;
    @Autowired
    private PdsSupplierService pdsSupplierService;

    @ModelAttribute
    public OfferOrder get(@RequestParam(required = false) String id) {
        OfferOrder entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = offerOrderService.get(id);
        }
        if (entity == null) {
            entity = new OfferOrder();
        }
        return entity;
    }

    @RequiresPermissions("pds:order:offerOrder:view")
    @RequestMapping(value = {"list", ""})
    public String list(OfferOrder offerOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<OfferOrder> page = new Page<OfferOrder>(request, response);
        offerOrder.setPage(page);
        page.setList(offerOrderService.findJoinList(offerOrder));
        model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        List<PdsSupplier> supplierList = pdsSupplierService.findUsableList();
        model.addAttribute("supplierList", supplierList);
        return "modules/pds/order/offerOrderList";
    }

    @RequiresPermissions("pds:order:offerOrder:view")
    @RequestMapping(value = "form")
    public String form(OfferOrder offerOrder, Model model) {
        model.addAttribute("offerOrder", offerOrder);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/offerOrderForm";
    }

    @RequiresPermissions("pds:order:offerOrder:edit")
    @RequestMapping(value = "save")
    public String save(OfferOrder offerOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, offerOrder)) {
            return form(offerOrder, model);
        }
        offerOrderService.save(offerOrder);
        addMessage(redirectAttributes, "保存报价单成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/offerOrder/?repage";
    }

    @RequiresPermissions("pds:order:offerOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(OfferOrder offerOrder, RedirectAttributes redirectAttributes) {
        offerOrderService.delete(offerOrder);
        addMessage(redirectAttributes, "删除报价单成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/offerOrder/?repage";
    }

}