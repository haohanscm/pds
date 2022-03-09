package com.haohan.platform.service.sys.modules.pds.web.business;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 供应商管理Controller
 *
 * @author haohan
 * @version 2018-10-26
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/business/pdsSupplier")
public class PdsSupplierController extends BaseController {

    @Autowired
    private PdsSupplierService pdsSupplierService;
    @Autowired
    @Lazy(true)
    private MerchantService merchantService;
    @Autowired
    private PdsBuyerService pdsBuyerService;

    @ModelAttribute
    public PdsSupplier get(@RequestParam(required = false) String id) {
        PdsSupplier entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = pdsSupplierService.get(id);
        }
        if (entity == null) {
            entity = new PdsSupplier();
        }
        return entity;
    }

    @RequiresPermissions("pds:business:pdsSupplier:view")
    @RequestMapping(value = {"list", ""})
    public String list(PdsSupplier pdsSupplier, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<PdsSupplier> page = new Page<PdsSupplier>(request, response);
        pdsSupplier.setPage(page);
        page.setList(pdsSupplierService.findJoinList(pdsSupplier));
        model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/business/pdsSupplierList";
    }

    @RequiresPermissions("pds:business:pdsSupplier:view")
    @RequestMapping(value = "form")
    public String form(PdsSupplier pdsSupplier, Model model) {
        model.addAttribute("pdsSupplier", pdsSupplier);
        // 查询已启用的商家
        List<Merchant> list = merchantService.findListEnabled(new Merchant());
        List<Merchant> pmList = new ArrayList<>();
        for (Merchant merchant : list) {
            if (StringUtils.equals(merchant.getPdsType(), IMerchantConstant.PdsType.platform.getType())) {
                pmList.add(merchant);
            }
        }
        model.addAttribute("merchantList", list);
        model.addAttribute("pmList", pmList);
        return "modules/pds/business/pdsSupplierForm";
    }

    @RequiresPermissions("pds:business:pdsSupplier:edit")
    @RequestMapping(value = "save")
    public String save(PdsSupplier pdsSupplier, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, pdsSupplier)) {
            return form(pdsSupplier, model);
        }
        pdsSupplierService.save(pdsSupplier);
        addMessage(redirectAttributes, "保存供应商成功");
        return "redirect:" + Global.getAdminPath() + "/pds/business/pdsSupplier/?repage";
    }

    @RequiresPermissions("pds:business:pdsSupplier:edit")
    @RequestMapping(value = "delete")
    public String delete(PdsSupplier pdsSupplier, RedirectAttributes redirectAttributes) {
        pdsSupplierService.delete(pdsSupplier);
        addMessage(redirectAttributes, "删除供应商成功");
        return "redirect:" + Global.getAdminPath() + "/pds/business/pdsSupplier/?repage";
    }

    @RequiresPermissions("pds:business:pdsSupplier:edit")
    @RequestMapping(value = "supplierList")
    @ResponseBody
    public BaseResp supplierList(PdsSupplier pdsSupplier) {
        BaseResp baseResp = BaseResp.newError();
        List<PdsSupplier> list = pdsSupplierService.findList(pdsSupplier);
        if (Collections3.isEmpty(list)) {
            baseResp.setMsg("没有查到供应商");
            return baseResp;
        }
        baseResp.success();
        baseResp.setExt(new ArrayList<>(list));
        return baseResp;
    }

}