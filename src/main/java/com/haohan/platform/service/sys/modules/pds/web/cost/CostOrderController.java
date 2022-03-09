package com.haohan.platform.service.sys.modules.pds.web.cost;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.cost.CostOrder;
import com.haohan.platform.service.sys.modules.pds.service.cost.CostOrderService;
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
 * 成本核算单Controller
 *
 * @author haohan
 * @version 2018-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/cost/costOrder")
public class CostOrderController extends BaseController {

    @Autowired
    private CostOrderService costOrderService;

    @ModelAttribute
    public CostOrder get(@RequestParam(required = false) String id) {
        CostOrder entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = costOrderService.get(id);
        }
        if (entity == null) {
            entity = new CostOrder();
        }
        return entity;
    }

    @RequiresPermissions("pds:cost:costOrder:view")
    @RequestMapping(value = {"list", ""})
    public String list(CostOrder costOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CostOrder> page = costOrderService.findPage(new Page<CostOrder>(request, response), costOrder);
        model.addAttribute("page", page);
        return "modules/pds/cost/costOrderList";
    }

    @RequiresPermissions("pds:cost:costOrder:view")
    @RequestMapping(value = "form")
    public String form(CostOrder costOrder, Model model) {
        model.addAttribute("costOrder", costOrder);
        return "modules/pds/cost/costOrderForm";
    }

    @RequiresPermissions("pds:cost:costOrder:edit")
    @RequestMapping(value = "save")
    public String save(CostOrder costOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, costOrder)) {
            return form(costOrder, model);
        }
        costOrderService.save(costOrder);
        addMessage(redirectAttributes, "保存成本核算成功");
        return "redirect:" + Global.getAdminPath() + "/pds/cost/costOrder/?repage";
    }

    @RequiresPermissions("pds:cost:costOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(CostOrder costOrder, RedirectAttributes redirectAttributes) {
        costOrderService.delete(costOrder);
        addMessage(redirectAttributes, "删除成本核算成功");
        return "redirect:" + Global.getAdminPath() + "/pds/cost/costOrder/?repage";
    }

}