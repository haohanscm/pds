package com.haohan.platform.service.sys.modules.xiaodian.web.pay;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.sys.utils.UserUtils;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.OrderPayRecordService;
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
 * 订单支付Controller
 *
 * @author haohan
 * @version 2017-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/pay/orderPayRecord")
public class OrderPayRecordController extends BaseController {

    @Autowired
    private OrderPayRecordService orderPayRecordService;

    @ModelAttribute
    public OrderPayRecord get(@RequestParam(required = false) String id) {
        OrderPayRecord entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = orderPayRecordService.get(id);
        }
        if (entity == null) {
            entity = new OrderPayRecord();
        }
        return entity;
    }

    @RequiresPermissions("xiaodian:pay:orderPayRecord:view")
    @RequestMapping(value = {"list", ""})
    public String list(OrderPayRecord orderPayRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
        String originPartnerId = orderPayRecord.getPartnerId();
        if (UserUtils.getPrincipal().getName().contains("五老头")){
            orderPayRecord.setPartnerId("130390000202761");
        }
        Page<OrderPayRecord> page = orderPayRecordService.findPage(new Page<OrderPayRecord>(request, response), orderPayRecord);

        orderPayRecord.setPartnerId(originPartnerId);
        model.addAttribute("page", page);
        return "modules/xiaodian/pay/orderPayRecordList";
    }

    @RequiresPermissions("xiaodian:pay:orderPayRecord:view")
    @RequestMapping(value = "form")
    public String form(OrderPayRecord orderPayRecord, Model model) {
        model.addAttribute("orderPayRecord", orderPayRecord);
        return "modules/xiaodian/pay/orderPayRecordForm";
    }

    @RequiresPermissions("xiaodian:pay:orderPayRecord:edit")
    @RequestMapping(value = "save")
    public String save(OrderPayRecord orderPayRecord, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, orderPayRecord)) {
            return form(orderPayRecord, model);
        }
        orderPayRecordService.save(orderPayRecord);
        addMessage(redirectAttributes, "保存支付信息成功");
        return "redirect:" + Global.getAdminPath() + "/xiaodian/pay/orderPayRecord/?repage";
    }

    @RequiresPermissions("xiaodian:pay:orderPayRecord:edit")
    @RequestMapping(value = "delete")
    public String delete(OrderPayRecord orderPayRecord, RedirectAttributes redirectAttributes) {
        orderPayRecordService.delete(orderPayRecord);
        addMessage(redirectAttributes, "删除支付信息成功");
        return "redirect:" + Global.getAdminPath() + "/xiaodian/pay/orderPayRecord/?repage";
    }


}