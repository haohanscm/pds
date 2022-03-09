package com.haohan.platform.service.sys.modules.pds.web.cost;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.cost.DealCostDetail;
import com.haohan.platform.service.sys.modules.pds.service.cost.DealCostDetailService;
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

/**
 * 交易成本明细Controller
 *
 * @author haohan
 * @version 2018-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/cost/dealCostDetail")
public class DealCostDetailController extends BaseController {

    @Autowired
    private DealCostDetailService dealCostDetailService;

    @ModelAttribute
    public DealCostDetail get(@RequestParam(required = false) String id) {
        DealCostDetail entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = dealCostDetailService.get(id);
        }
        if (entity == null) {
            entity = new DealCostDetail();
        }
        return entity;
    }

    @RequiresPermissions("pds:cost:dealCostDetail:view")
    @RequestMapping(value = {"list", ""})
    public String list(DealCostDetail dealCostDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<DealCostDetail> page = dealCostDetailService.findPage(new Page<DealCostDetail>(request, response), dealCostDetail);
        model.addAttribute("page", page);
        return "modules/pds/cost/dealCostDetailList";
    }

    @RequiresPermissions("pds:cost:dealCostDetail:view")
    @RequestMapping(value = "form")
    public String form(DealCostDetail dealCostDetail, Model model) {
        model.addAttribute("dealCostDetail", dealCostDetail);
        return "modules/pds/cost/dealCostDetailForm";
    }

    @RequiresPermissions("pds:cost:dealCostDetail:edit")
    @RequestMapping(value = "save")
    public String save(DealCostDetail dealCostDetail, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, dealCostDetail)) {
            return form(dealCostDetail, model);
        }
        dealCostDetailService.save(dealCostDetail);
        addMessage(redirectAttributes, "保存交易成本明细成功");
        return "redirect:" + Global.getAdminPath() + "/pds/cost/dealCostDetail/?repage";
    }

    @RequiresPermissions("pds:cost:dealCostDetail:edit")
    @RequestMapping(value = "delete")
    public String delete(DealCostDetail dealCostDetail, RedirectAttributes redirectAttributes) {
        dealCostDetailService.delete(dealCostDetail);
        addMessage(redirectAttributes, "删除交易成本明细成功");
        return "redirect:" + Global.getAdminPath() + "/pds/cost/dealCostDetail/?repage";
    }

}