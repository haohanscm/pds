package com.haohan.platform.service.sys.modules.xiaodian.web.database;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.ProductAttrValue;
import com.haohan.platform.service.sys.modules.xiaodian.service.database.ProductAttrValueService;
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
 * 商品库属性值管理Controller
 * @author dy
 * @version 2018-10-22
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/database/productAttrValue")
public class ProductAttrValueController extends BaseController {

	@Autowired
	private ProductAttrValueService productAttrValueService;
	
	@ModelAttribute
	public ProductAttrValue get(@RequestParam(required=false) String id) {
		ProductAttrValue entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productAttrValueService.get(id);
		}
		if (entity == null){
			entity = new ProductAttrValue();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:database:productAttrValue:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductAttrValue productAttrValue, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductAttrValue> page = new Page<ProductAttrValue>(request, response);
		productAttrValue.setPage(page);
		page.setList(productAttrValueService.findJoinList(productAttrValue));
		model.addAttribute("page", page);
		return "modules/xiaodian/database/productAttrValueList";
	}

	@RequiresPermissions("xiaodian:database:productAttrValue:view")
	@RequestMapping(value = "form")
	public String form(ProductAttrValue productAttrValue, Model model) {
		model.addAttribute("productAttrValue", productAttrValue);
		return "modules/xiaodian/database/productAttrValueForm";
	}

	@RequiresPermissions("xiaodian:database:productAttrValue:edit")
	@RequestMapping(value = "save")
	public String save(ProductAttrValue productAttrValue, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productAttrValue)){
			return form(productAttrValue, model);
		}
		productAttrValueService.save(productAttrValue);
		addMessage(redirectAttributes, "保存商品库属性值成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/database/productAttrValue/?repage";
	}
	
	@RequiresPermissions("xiaodian:database:productAttrValue:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductAttrValue productAttrValue, RedirectAttributes redirectAttributes) {
		productAttrValueService.delete(productAttrValue);
		addMessage(redirectAttributes, "删除商品库属性值成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/database/productAttrValue/?repage";
	}

    /**
     * 查询 属性名/值 列表
     * @param attrNameId  属性名id
     * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("xiaodian:database:productAttrValue:view")
	@RequestMapping(value = "findAttrList")
    @ResponseBody
	public List<ProductAttrValue> findAttrList(@RequestParam(value = "attrNameId", required=false) String attrNameId, @RequestParam(value = "spuId", required=false) String spuId, RedirectAttributes redirectAttributes) {
	    ProductAttrValue productAttrValue = new ProductAttrValue();
	    productAttrValue.setAttrNameId(attrNameId);
	    productAttrValue.setSpuId(spuId);
		return productAttrValueService.findJoinList(productAttrValue);
	}

}