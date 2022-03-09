package com.haohan.platform.service.sys.modules.xiaodian.web.database;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.ProductAttrName;
import com.haohan.platform.service.sys.modules.xiaodian.service.database.ProductAttrNameService;
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
 * 商品库属性名管理Controller
 * @author dy
 * @version 2018-10-22
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/database/productAttrName")
public class ProductAttrNameController extends BaseController {

	@Autowired
	private ProductAttrNameService productAttrNameService;
	
	@ModelAttribute
	public ProductAttrName get(@RequestParam(required=false) String id) {
		ProductAttrName entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productAttrNameService.get(id);
		}
		if (entity == null){
			entity = new ProductAttrName();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:database:productAttrName:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductAttrName productAttrName, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductAttrName> page = productAttrNameService.findPage(new Page<ProductAttrName>(request, response), productAttrName); 
		model.addAttribute("page", page);
		return "modules/xiaodian/database/productAttrNameList";
	}

	@RequiresPermissions("xiaodian:database:productAttrName:view")
	@RequestMapping(value = "form")
	public String form(ProductAttrName productAttrName, Model model) {
		model.addAttribute("productAttrName", productAttrName);
		return "modules/xiaodian/database/productAttrNameForm";
	}

	@RequiresPermissions("xiaodian:database:productAttrName:edit")
	@RequestMapping(value = "save")
	public String save(ProductAttrName productAttrName, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productAttrName)){
			return form(productAttrName, model);
		}
		productAttrNameService.save(productAttrName);
		addMessage(redirectAttributes, "保存商品库属性名成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/database/productAttrName/?repage";
	}
	
	@RequiresPermissions("xiaodian:database:productAttrName:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductAttrName productAttrName, RedirectAttributes redirectAttributes) {
		productAttrNameService.delete(productAttrName);
		addMessage(redirectAttributes, "删除商品库属性名成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/database/productAttrName/?repage";
	}

	/**
	 * 查询 属性名 列表
	 * @param spuId
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("xiaodian:database:productAttrName:view")
	@RequestMapping(value = "findAttrList")
	@ResponseBody
	public List<ProductAttrName> findAttrList(@RequestParam(value = "spuId", required = false) String spuId, RedirectAttributes redirectAttributes) {
		ProductAttrName productAttrName = new ProductAttrName();
		productAttrName.setSpuId(spuId);
		return productAttrNameService.findList(productAttrName);
	}

}