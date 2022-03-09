package com.haohan.platform.service.sys.modules.pds.web.cost;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.core.supplier.ISupplierPaymentService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SupplierPayment;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.cost.SupplierPaymentService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
 * 供应商货款统计Controller
 *
 * @author haohan
 * @version 2018-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/cost/supplierPayment")
public class SupplierPaymentController extends BaseController {

    @Autowired
    private SupplierPaymentService supplierPaymentService;
    @Autowired
    @Lazy(true)
    private PdsSupplierService supplierService;
    @Autowired
    @Lazy(true)
    private ISupplierPaymentService paymentService;
    @Autowired
    private PdsBuyerService pdsBuyerService;

    @ModelAttribute
    public SupplierPayment get(@RequestParam(required = false) String id) {
        SupplierPayment entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = supplierPaymentService.get(id);
        }
        if (entity == null) {
            entity = new SupplierPayment();
        }
        return entity;
    }

    @RequiresPermissions("pds:cost:supplierPayment:view")
    @RequestMapping(value = {"list", ""})
    public String list(SupplierPayment supplierPayment, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SupplierPayment> page = new Page<SupplierPayment>(request, response);
        supplierPayment.setPage(page);
        page.setList(supplierPaymentService.findJoinList(supplierPayment));
        model.addAttribute("page", page);
        List<PdsSupplier> supplierList = supplierService.findUsableList();
        model.addAttribute("supplierList", supplierList);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/cost/supplierPaymentList";
    }

    @RequiresPermissions("pds:cost:supplierPayment:view")
    @RequestMapping(value = "form")
    public String form(SupplierPayment supplierPayment, Model model) {
        model.addAttribute("supplierPayment", supplierPayment);
        List<PdsSupplier> supplierList = supplierService.findUsableList();
        model.addAttribute("supplierList", supplierList);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/cost/supplierPaymentForm";
    }

    @RequiresPermissions("pds:cost:supplierPayment:edit")
    @RequestMapping(value = "save")
    public String save(SupplierPayment supplierPayment, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, supplierPayment)) {
            return form(supplierPayment, model);
        }
        supplierPaymentService.save(supplierPayment);
        addMessage(redirectAttributes, "保存供应商货款成功");
        return "redirect:" + Global.getAdminPath() + "/pds/cost/supplierPayment/?repage";
    }

    @RequiresPermissions("pds:cost:supplierPayment:edit")
    @RequestMapping(value = "delete")
    public String delete(SupplierPayment supplierPayment, RedirectAttributes redirectAttributes) {
        supplierPaymentService.delete(supplierPayment);
        addMessage(redirectAttributes, "删除供应商货款成功");
        return "redirect:" + Global.getAdminPath() + "/pds/cost/supplierPayment/?repage";
    }

    /**
     * 供应商 交易单 按日 生成货款记录
     *
     * @return
     */
    @RequiresPermissions("pds:cost:supplierPayment:edit")
    @RequestMapping(value = "paymentRecord")
    public String paymentRecord(SupplierPayment supplierPayment, RedirectAttributes redirectAttributes) {

        BaseResp baseResp = BaseResp.newError();
        //验证参数
        if (StringUtils.isAnyEmpty(supplierPayment.getPmId(), supplierPayment.getSupplierId()) || null == supplierPayment.getSupplyDate()) {
            baseResp.setMsg("缺少参数pmId/supplierId/supplyDate");
        } else {
            baseResp = paymentService.paymentRecord(supplierPayment);
        }
        addMessage(redirectAttributes, baseResp.getMsg());
        return "redirect:" + Global.getAdminPath() + "/pds/cost/supplierPayment/?repage";
    }

}