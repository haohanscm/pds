package com.haohan.platform.service.sys.modules.xiaodian.web.printer;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IShopConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.FeiePrinter;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.PrintOrder;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.printer.FeiePrinterService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 飞鹅打印机管理Controller
 * @author haohan
 * @version 2018-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/printer/feiePrinter")
public class FeiePrinterController extends BaseController {

	@Autowired
	private FeiePrinterService feiePrinterService;
    @Autowired
    private ShopService shopService;
	@Autowired
	private MerchantService merchantService;

	@ModelAttribute
	public FeiePrinter get(@RequestParam(required=false) String id) {
		FeiePrinter entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = feiePrinterService.get(id);
		}
		if (entity == null){
			entity = new FeiePrinter();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:printer:feiePrinter:view")
	@RequestMapping(value = {"list", ""})
	public String list(FeiePrinter feiePrinter, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FeiePrinter> page = feiePrinterService.findPage(new Page<FeiePrinter>(request, response), feiePrinter); 
		model.addAttribute("page", page);
        List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
        model.addAttribute("merchantList",merchantList);
        List<Shop> shopList = shopService.findList(new Shop());
        model.addAttribute("shopList",shopList);
		return "modules/xiaodian/printer/feiePrinterList";
	}

	@RequiresPermissions("xiaodian:printer:feiePrinter:view")
	@RequestMapping(value = "form")
	public String form(FeiePrinter feiePrinter, Model model) {
		model.addAttribute("feiePrinter", feiePrinter);
        List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
        model.addAttribute("merchantList",merchantList);
		return "modules/xiaodian/printer/feiePrinterForm";
	}

	@RequiresPermissions("xiaodian:printer:feiePrinter:edit")
	@RequestMapping(value = "save")
	public String save(FeiePrinter feiePrinter, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, feiePrinter)){
			return form(feiePrinter, model);
		}
		feiePrinterService.save(feiePrinter);
		addMessage(redirectAttributes, "保存打印机成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/printer/feiePrinter/?repage";
	}
	
	@RequiresPermissions("xiaodian:printer:feiePrinter:edit")
	@RequestMapping(value = "delete")
	public String delete(FeiePrinter feiePrinter, RedirectAttributes redirectAttributes) {
		feiePrinterService.delete(feiePrinter);
		addMessage(redirectAttributes, "删除打印机成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/printer/feiePrinter/?repage";
	}


	@RequiresPermissions("xiaodian:printer:feiePrinter:view")
	@RequestMapping(value = "printOrder")
	@ResponseBody
	public String printOrder(FeiePrinter feiePrinter, @RequestParam("orderId") String orderId) {
        PrintOrder order = feiePrinterService.fetchPrintOrderById(orderId);
        // 备注处理
        String printerRemarks = StringUtils.defaultString(feiePrinter.getRemarks());
        String orderRemarks = StringUtils.defaultString(order.getRemarks());
        if (StringUtils.isEmpty(printerRemarks) || StringUtils.isEmpty(orderRemarks)) {
            order.setRemarks(orderRemarks.concat(printerRemarks));
        } else {
            order.setRemarks(orderRemarks.concat("<BR>").concat(printerRemarks));
        }
        BaseResp baseResp = feiePrinterService.printOrder(feiePrinter,order);
		return baseResp.toJson();
	}

    @RequiresPermissions("xiaodian:printer:feiePrinter:view")
    @RequestMapping(value = "delYunPrinter")
    @ResponseBody
    public String delYunPrinter(FeiePrinter feiePrinter) {
        BaseResp baseResp = feiePrinterService.delYunPrinter(feiePrinter);
        return baseResp.toJson();
    }

}