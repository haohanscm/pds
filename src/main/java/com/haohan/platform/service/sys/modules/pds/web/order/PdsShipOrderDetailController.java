package com.haohan.platform.service.sys.modules.pds.web.order;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.order.PdsShipOrderDetail;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.order.PdsShipOrderDetailService;
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
 * 送货单明细Controller
 *
 * @author yu.shen
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/order/pdsShipOrderDetail")
public class PdsShipOrderDetailController extends BaseController {

    @Autowired
    private PdsShipOrderDetailService pdsShipOrderDetailService;
    @Autowired
    private PdsBuyerService pdsBuyerService;

    @ModelAttribute
    public PdsShipOrderDetail get(@RequestParam(required = false) String id) {
        PdsShipOrderDetail entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = pdsShipOrderDetailService.get(id);
        }
        if (entity == null) {
            entity = new PdsShipOrderDetail();
        }
        return entity;
    }

    @RequiresPermissions("pds:order:pdsShipOrderDetail:view")
    @RequestMapping(value = {"list", ""})
    public String list(PdsShipOrderDetail pdsShipOrderDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<PdsShipOrderDetail> page = new Page<PdsShipOrderDetail>(request, response);
        pdsShipOrderDetail.setPage(page);
        page.setList(pdsShipOrderDetailService.findJoinList(pdsShipOrderDetail));
        model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/pdsShipOrderDetailList";
    }

    @RequiresPermissions("pds:order:pdsShipOrderDetail:view")
    @RequestMapping(value = "form")
    public String form(PdsShipOrderDetail pdsShipOrderDetail, Model model) {
        model.addAttribute("pdsShipOrderDetail", pdsShipOrderDetail);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/pdsShipOrderDetailForm";
    }

    @RequiresPermissions("pds:order:pdsShipOrderDetail:edit")
    @RequestMapping(value = "save")
    public String save(PdsShipOrderDetail pdsShipOrderDetail, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, pdsShipOrderDetail)) {
            return form(pdsShipOrderDetail, model);
        }
        pdsShipOrderDetailService.save(pdsShipOrderDetail);
        addMessage(redirectAttributes, "保存送货单明细成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/pdsShipOrderDetail/?repage";
    }

    @RequiresPermissions("pds:order:pdsShipOrderDetail:edit")
    @RequestMapping(value = "delete")
    public String delete(PdsShipOrderDetail pdsShipOrderDetail, RedirectAttributes redirectAttributes) {
        pdsShipOrderDetailService.delete(pdsShipOrderDetail);
        addMessage(redirectAttributes, "删除送货单明细成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/pdsShipOrderDetail/?repage";
    }

}