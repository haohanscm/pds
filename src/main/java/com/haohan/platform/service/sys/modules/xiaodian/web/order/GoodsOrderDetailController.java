package com.haohan.platform.service.sys.modules.xiaodian.web.order;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
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
 * 商品订单明细Controller
 * @author haohan
 * @version 2018-09-01
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/order/goodsOrderDetail")
public class GoodsOrderDetailController extends BaseController {

	@Autowired
	private GoodsOrderDetailService goodsOrderDetailService;
	
	@ModelAttribute
	public GoodsOrderDetail get(@RequestParam(required=false) String id) {
		GoodsOrderDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsOrderDetailService.get(id);
		}
		if (entity == null){
			entity = new GoodsOrderDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:order:goodsOrderDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsOrderDetail goodsOrderDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsOrderDetail> page = goodsOrderDetailService.findPage(new Page<GoodsOrderDetail>(request, response), goodsOrderDetail); 
		model.addAttribute("page", page);
		return "modules/xiaodian/order/goodsOrderDetailList";
	}

	@RequiresPermissions("xiaodian:order:goodsOrderDetail:view")
	@RequestMapping(value = "form")
	public String form(GoodsOrderDetail goodsOrderDetail, Model model) {
		model.addAttribute("goodsOrderDetail", goodsOrderDetail);
		return "modules/xiaodian/order/goodsOrderDetailForm";
	}

	@RequiresPermissions("xiaodian:order:goodsOrderDetail:edit")
	@RequestMapping(value = "save")
	public String save(GoodsOrderDetail goodsOrderDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsOrderDetail)){
			return form(goodsOrderDetail, model);
		}
		goodsOrderDetailService.save(goodsOrderDetail);
		addMessage(redirectAttributes, "保存商品订单明细成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/order/goodsOrderDetail/?repage";
	}
	
	@RequiresPermissions("xiaodian:order:goodsOrderDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsOrderDetail goodsOrderDetail, RedirectAttributes redirectAttributes) {
		goodsOrderDetailService.delete(goodsOrderDetail);
		addMessage(redirectAttributes, "删除商品订单明细成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/order/goodsOrderDetail/?repage";
	}

}