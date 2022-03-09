package com.haohan.platform.service.sys.modules.pds.web.delivery;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.TruckManage;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.delivery.TruckManageService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 车辆管理Controller
 *
 * @author haohan
 * @version 2018-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/delivery/truckManage")
public class TruckManageController extends BaseController {

    @Autowired
    private TruckManageService truckManageService;
    @Autowired
    @Lazy(true)
    private PdsBuyerService pdsBuyerService;
    @Resource
    private MerchantEmployeeService merchantEmployeeService;

    @ModelAttribute
    public TruckManage get(@RequestParam(required = false) String id) {
        TruckManage entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = truckManageService.get(id);
        }
        if (entity == null) {
            entity = new TruckManage();
        }
        return entity;
    }

    @RequiresPermissions("pds:delivery:truckManage:view")
    @RequestMapping(value = {"list", ""})
    public String list(TruckManage truckManage, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TruckManage> page = new Page<TruckManage>(request, response);
        truckManage.setPage(page);
        page.setList(truckManageService.findJoinList(truckManage));
        model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/delivery/truckManageList";
    }

    @RequiresPermissions("pds:delivery:truckManage:view")
    @RequestMapping(value = "form")
    public String form(TruckManage truckManage, Model model) {
        MerchantEmployee merchantEmployee = new MerchantEmployee();
        merchantEmployee.setRole(IPdsConstant.EmployeeRole.driver.getCode());
        List<MerchantEmployee> merchantEmployeeList = merchantEmployeeService.findList(merchantEmployee);

        model.addAttribute("merchantEmployeeList", merchantEmployeeList);
        model.addAttribute("truckManage", truckManage);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/delivery/truckManageForm";
    }

    @RequiresPermissions("pds:delivery:truckManage:edit")
    @RequestMapping(value = "save")
    public String save(TruckManage truckManage, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, truckManage)) {
            return form(truckManage, model);
        }
        truckManageService.save(truckManage);
        addMessage(redirectAttributes, "保存车辆管理成功");
        return "redirect:" + Global.getAdminPath() + "/pds/delivery/truckManage/?repage";
    }

    @RequiresPermissions("pds:delivery:truckManage:edit")
    @RequestMapping(value = "delete")
    public String delete(TruckManage truckManage, RedirectAttributes redirectAttributes) {
        truckManageService.delete(truckManage);
        addMessage(redirectAttributes, "删除车辆管理成功");
        return "redirect:" + Global.getAdminPath() + "/pds/delivery/truckManage/?repage";
    }

}