package com.haohan.platform.service.sys.modules.pss.web.goods;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.entity.goods.PssGoodsDatabase;
import com.haohan.platform.service.sys.modules.pss.service.goods.PssGoodsDatabaseService;
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
 * 商品数据库管理Controller
 * @author haohan
 * @version 2018-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/pss/goods/pssGoodsDatabase")
public class PssGoodsDatabaseController extends BaseController {

	@Autowired
	private PssGoodsDatabaseService pssGoodsDatabaseService;
	
	@ModelAttribute
	public PssGoodsDatabase get(@RequestParam(required=false) String id) {
		PssGoodsDatabase entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = pssGoodsDatabaseService.get(id);
		}
		if (entity == null){
			entity = new PssGoodsDatabase();
		}
		return entity;
	}
	
	@RequiresPermissions("pss:goods:pssGoodsDatabase:view")
	@RequestMapping(value = {"list", ""})
	public String list(PssGoodsDatabase pssGoodsDatabase, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PssGoodsDatabase> page = pssGoodsDatabaseService.findPage(new Page<PssGoodsDatabase>(request, response), pssGoodsDatabase);
		model.addAttribute("page", page);
		return "modules/pss/goods/pssGoodsDatabaseList";
	}

	@RequiresPermissions("pss:goods:pssGoodsDatabase:view")
	@RequestMapping(value = "form")
	public String form(PssGoodsDatabase pssGoodsDatabase, Model model) {
		model.addAttribute("pssGoodsDatabase", pssGoodsDatabase);
		return "modules/pss/goods/pssGoodsDatabaseForm";
	}

	@RequiresPermissions("pss:goods:pssGoodsDatabase:edit")
	@RequestMapping(value = "save")
	public String save(PssGoodsDatabase pssGoodsDatabase, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, pssGoodsDatabase)){
			return form(pssGoodsDatabase, model);
		}
		pssGoodsDatabaseService.save(pssGoodsDatabase);
		addMessage(redirectAttributes, "保存商品数据库成功");
		return "redirect:"+Global.getAdminPath()+"/pss/goods/pssGoodsDatabase/?repage";
	}
	
	@RequiresPermissions("pss:goods:pssGoodsDatabase:edit")
	@RequestMapping(value = "delete")
	public String delete(PssGoodsDatabase pssGoodsDatabase, RedirectAttributes redirectAttributes) {
		pssGoodsDatabaseService.delete(pssGoodsDatabase);
		addMessage(redirectAttributes, "删除商品数据库成功");
		return "redirect:"+Global.getAdminPath()+"/pss/goods/pssGoodsDatabase/?repage";
	}

}