package com.haohan.platform.service.sys.modules.xiaodian.web.database;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.StandardProductUnit;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.StockKeepingUnit;
import com.haohan.platform.service.sys.modules.xiaodian.service.database.CommonGoodsService;
import com.haohan.platform.service.sys.modules.xiaodian.service.database.StandardProductUnitService;
import com.haohan.platform.service.sys.modules.xiaodian.service.database.StockKeepingUnitService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 库存商品管理Controller
 * @author dy
 * @version 2018-10-20
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/database/stockKeepingUnit")
public class StockKeepingUnitController extends BaseController {

	@Autowired
	private StockKeepingUnitService stockKeepingUnitService;
    @Autowired
    @Lazy(true)
    private StandardProductUnitService standardProductUnitService;
	@Autowired
	@Lazy(true)
	private CommonGoodsService commonGoodsService;
	
	@ModelAttribute
	public StockKeepingUnit get(@RequestParam(required=false) String id) {
		StockKeepingUnit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = stockKeepingUnitService.get(id);
		}
		if (entity == null){
			entity = new StockKeepingUnit();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:database:stockKeepingUnit:view")
	@RequestMapping(value = {"list", ""})
	public String list(StockKeepingUnit stockKeepingUnit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StockKeepingUnit> page = stockKeepingUnitService.findPage(new Page<StockKeepingUnit>(request, response), stockKeepingUnit); 
		model.addAttribute("page", page);
		return "modules/xiaodian/database/stockKeepingUnitList";
	}

	@RequiresPermissions("xiaodian:database:stockKeepingUnit:view")
	@RequestMapping(value = "form")
	public String form(StockKeepingUnit stockKeepingUnit, Model model) {
	    StandardProductUnit spu = new StandardProductUnit();
        List<StandardProductUnit> spuList = standardProductUnitService.findList(spu);
		model.addAttribute("spuList", spuList);
		model.addAttribute("stockKeepingUnit", stockKeepingUnit);
		return "modules/xiaodian/database/stockKeepingUnitForm";
	}

	@RequiresPermissions("xiaodian:database:stockKeepingUnit:edit")
	@RequestMapping(value = "save")
	public String save(StockKeepingUnit stockKeepingUnit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, stockKeepingUnit)){
			return form(stockKeepingUnit, model);
		}
		stockKeepingUnitService.save(stockKeepingUnit);
		addMessage(redirectAttributes, "保存库存商品成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/database/stockKeepingUnit/?repage";
	}
	
	@RequiresPermissions("xiaodian:database:stockKeepingUnit:edit")
	@RequestMapping(value = "delete")
	public String delete(StockKeepingUnit stockKeepingUnit, RedirectAttributes redirectAttributes) {
		stockKeepingUnitService.delete(stockKeepingUnit);
		addMessage(redirectAttributes, "删除库存商品成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/database/stockKeepingUnit/?repage";
	}

    /**
     * 生成所有库存商品的商品编号
     * @param stockKeepingUnit
     * @return
     */
    @RequiresPermissions("xiaodian:database:stockKeepingUnit:edit")
    @RequestMapping(value = "genSkuSn")
    @ResponseBody
    public BaseResp genSkuSn(StockKeepingUnit stockKeepingUnit) {
        BaseResp baseResp = BaseResp.newError();
        if(StringUtils.equals("all", stockKeepingUnit.getStockGoodsSn())){
            baseResp = commonGoodsService.genAllSkuSn();
        }
        return baseResp;
    }

	/**
	 * Excel导入信息
	 *
	 * @param file
	 * @return
	 */
	@RequiresPermissions("xiaodian:database:stockKeepingUnit:edit")
	@RequestMapping(value = "import")
	public String importFile(MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		addMessage(redirectAttributes, commonGoodsService.fileImport(file, "sku"));
		return "redirect:"+Global.getAdminPath()+"/xiaodian/database/stockKeepingUnit/?repage";
	}

	/**
	 * 下载导出数据
	 *
	 * @param response
	 * @return
	 */
	@RequiresPermissions("xiaodian:database:stockKeepingUnit:edit")
	@RequestMapping(value = "export")
	public String exportFile(@RequestParam("flag") String flag, StockKeepingUnit stockKeepingUnit, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if(StringUtils.equals("true", flag)){
			Page<StockKeepingUnit> page = new Page<>();
			page.setPageNo(1);
			page.setPageSize(10);
			stockKeepingUnit.setPage(page);
		}
		List<StockKeepingUnit> list = stockKeepingUnitService.findListWithAttrInfo(stockKeepingUnit);
		BaseResp baseResp = commonGoodsService.skuExportFile(list, response);
		if(!baseResp.isSuccess()){
			addMessage(redirectAttributes, baseResp.getMsg());
            return "redirect:"+Global.getAdminPath()+"/xiaodian/database/stockKeepingUnit/?repage";
		}
		return null;
	}
}