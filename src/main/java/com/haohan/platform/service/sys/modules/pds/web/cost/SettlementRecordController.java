package com.haohan.platform.service.sys.modules.pds.web.cost;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.core.settlement.ISettlementRecordCoreService;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SettlementRecord;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.cost.BuyerPaymentService;
import com.haohan.platform.service.sys.modules.pds.service.cost.SettlementRecordService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 结算记录管理Controller
 *
 * @author dy
 * @version 2018-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/cost/settlementRecord")
public class SettlementRecordController extends BaseController {

    @Autowired
    private SettlementRecordService settlementRecordService;
    @Autowired
    @Lazy(true)
    private PdsBuyerService pdsBuyerService;
    @Autowired
    @Lazy(true)
    private PdsSupplierService supplierService;
    @Autowired
    @Lazy(true)
    private BuyerPaymentService buyerPaymentService;
    @Autowired
    @Lazy(true)
    private SupplierPaymentService supplierPaymentService;
    @Autowired
    @Lazy(true)
    private ISettlementRecordCoreService settlementRecordCoreService;


    @ModelAttribute
    public SettlementRecord get(@RequestParam(required = false) String id) {
        SettlementRecord entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = settlementRecordService.get(id);
        }
        if (entity == null) {
            entity = new SettlementRecord();
        }
        return entity;
    }

    @RequiresPermissions("pds:cost:settlementRecord:view")
    @RequestMapping(value = {"list", ""})
    public String list(SettlementRecord settlementRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SettlementRecord> page = new Page<SettlementRecord>(request, response);
        settlementRecord.setPage(page);
        page.setList(settlementRecordService.findJoinList(settlementRecord));
        model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/cost/settlementRecordList";
    }

    @RequiresPermissions("pds:cost:settlementRecord:view")
    @RequestMapping(value = "form")
    public String form(SettlementRecord settlementRecord, Model model) {
        model.addAttribute("settlementRecord", settlementRecord);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/cost/settlementRecordForm";
    }

    @RequiresPermissions("pds:cost:settlementRecord:edit")
    @RequestMapping(value = "save")
    public String save(SettlementRecord settlementRecord, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, settlementRecord)) {
            return form(settlementRecord, model);
        }
        settlementRecordService.save(settlementRecord);
        String msg = "保存结算记录成功";
        // 根据货款单号 修改货款单状态为已结算
        settlementRecordCoreService.paymentSettlement(settlementRecord, msg);
        addMessage(redirectAttributes, msg);
        return "redirect:" + Global.getAdminPath() + "/pds/cost/settlementRecord/?repage";
    }

    @RequiresPermissions("pds:cost:settlementRecord:edit")
    @RequestMapping(value = "delete")
    public String delete(SettlementRecord settlementRecord, RedirectAttributes redirectAttributes) {
        settlementRecordService.delete(settlementRecord);
        addMessage(redirectAttributes, "删除结算记录成功");
        return "redirect:" + Global.getAdminPath() + "/pds/cost/settlementRecord/?repage";
    }


    /**
     * 根据结算公司类型查询采购商/供应商
     *
     * @param settlementRecord
     * @return
     */
    @RequiresPermissions("pds:cost:settlementRecord:edit")
    @RequestMapping(value = "companyList")
    @ResponseBody
    public BaseResp companyList(SettlementRecord settlementRecord) {
        return settlementRecordCoreService.fetchCompanyList(settlementRecord);
    }

    /**
     * 根据结算公司类型查询采购商/供应商 结算金额
     *
     * @param settlementRecord
     * @return
     */
    @RequiresPermissions("pds:cost:settlementRecord:edit")
    @RequestMapping(value = "countPayment")
    @ResponseBody
    public BaseResp countPayment(SettlementRecord settlementRecord) {
        return settlementRecordCoreService.countPayment(settlementRecord);
    }

}