package com.haohan.platform.service.sys.modules.xiaodian.web.database;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.StandardProductUnit;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsCategory;
import com.haohan.platform.service.sys.modules.xiaodian.service.database.CommonGoodsService;
import com.haohan.platform.service.sys.modules.xiaodian.service.database.StandardProductUnitService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 标准商品库管理Controller
 * @author dy
 * @version 2018-10-17
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/database/standardProductUnit")
public class StandardProductUnitController extends BaseController {

	@Autowired
	private StandardProductUnitService standardProductUnitService;
    @Autowired
    @Lazy(true)
    private CommonGoodsService commonGoodsService;
	@Autowired
    @Lazy(true)
    private MerchantService merchantService;

	@ModelAttribute
	public StandardProductUnit get(@RequestParam(required=false) String id) {
		StandardProductUnit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = standardProductUnitService.get(id);
		}
		if (entity == null){
			entity = new StandardProductUnit();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:database:standardProductUnit:view")
	@RequestMapping(value = {"list", ""})
	public String list(StandardProductUnit standardProductUnit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StandardProductUnit> page = standardProductUnitService.findJoinPage(new Page<StandardProductUnit>(request, response), standardProductUnit);
		model.addAttribute("page", page);
		return "modules/xiaodian/database/standardProductUnitList";
	}

	@RequiresPermissions("xiaodian:database:standardProductUnit:view")
	@RequestMapping(value = "form")
	public String form(StandardProductUnit standardProductUnit, Model model) {
	    if(StringUtils.isNotEmpty(standardProductUnit.getId())){
            standardProductUnit = standardProductUnitService.getWith(standardProductUnit);
        }
		model.addAttribute("standardProductUnit", standardProductUnit);
		return "modules/xiaodian/database/standardProductUnitForm";
	}

	@RequiresPermissions("xiaodian:database:standardProductUnit:edit")
	@RequestMapping(value = "save")
	public String save(StandardProductUnit standardProductUnit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, standardProductUnit)){
			return form(standardProductUnit, model);
		}
		standardProductUnitService.save(standardProductUnit);
		addMessage(redirectAttributes, "保存标准商品成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/database/standardProductUnit/?repage";
	}
	
	@RequiresPermissions("xiaodian:database:standardProductUnit:edit")
	@RequestMapping(value = "delete")
	public String delete(StandardProductUnit standardProductUnit, RedirectAttributes redirectAttributes) {
		standardProductUnitService.deleteWithSku(standardProductUnit);
		addMessage(redirectAttributes, "删除标准商品成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/database/standardProductUnit/?repage";
	}

    /**
     * 商品列表
     * @param standardProductUnit
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "goodsList")
    @ResponseBody
    public BaseResp goodsList(StandardProductUnit standardProductUnit) {
        BaseResp baseResp = BaseResp.newError();
        List<StandardProductUnit> goodsList = standardProductUnitService.findList(standardProductUnit);
        if(Collections3.isEmpty(goodsList)){
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.success();
        baseResp.setExt(new ArrayList<>(goodsList));
        return baseResp;
    }

    /**
     * 单个spu商品导出至零售商品
     * @param standardProductUnit
     * @param model
     * @return
     */
    @RequiresPermissions("xiaodian:database:standardProductUnit:view")
    @RequestMapping(value = "trans")
    public String trans(StandardProductUnit standardProductUnit, Model model) {
        List<Merchant> merchantList = merchantService.findList(new Merchant());
        model.addAttribute("merchantList",merchantList);
        Goods goods = new Goods();
        goods.setId(standardProductUnit.getId());
        goods.setGoodsName(standardProductUnit.getGoodsName());
        model.addAttribute("goods", goods);
        return "modules/xiaodian/database/importRetailGoodsSingle";
    }

    /**
     * 单个spu商品导出至零售商品
     * @param
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("xiaodian:database:standardProductUnit:edit")
    @RequestMapping(value = "transToRetailGoods")
    public String transToRetailGoods(Goods goods, Model model, RedirectAttributes redirectAttributes) {
        // 找到 分类及其所有父级分类
        BaseResp baseResp;
        if(StringUtils.isAnyEmpty(goods.getShopId(), goods.getMerchantId(), goods.getGoodsCategoryId())){
            addMessage(model, "选择错误,需选择分类/商家/店铺");
            return "modules/xiaodian/database/standardProductUnitForm";
        }else{
            // 转为零售商品
            GoodsCategory goodsCategory = new GoodsCategory();
            goodsCategory.setId(goods.getGoodsCategoryId());
            goodsCategory.setShopId(goods.getShopId());
            goodsCategory.setMerchantId(goods.getMerchantId());
            baseResp = commonGoodsService.importToRetailGoods(goodsCategory, goods.getId());
        }
        addMessage(redirectAttributes, baseResp.getMsg());
        return "redirect:"+Global.getAdminPath()+"/xiaodian/database/standardProductUnit/?repage";
    }

	/**
	 * Excel导入信息
	 *
	 * @param file
	 * @return
	 */
    @RequiresPermissions("xiaodian:database:standardProductUnit:edit")
	@RequestMapping(value = "import")
	public String importFile(MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		addMessage(redirectAttributes, commonGoodsService.fileImport(file, "spu"));
        return "redirect:"+Global.getAdminPath()+"/xiaodian/database/standardProductUnit/?repage";
	}

	/**
	 * 下载导出数据
	 *
	 * @param response
	 * @return
	 */
    @RequiresPermissions("xiaodian:database:standardProductUnit:edit")
	@RequestMapping(value = "export")
	public String exportFile(@RequestParam("flag") String flag, StandardProductUnit standardProductUnit, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if(StringUtils.equals("true", flag)){
			Page<StandardProductUnit> page = new Page<>();
			page.setPageNo(1);
			page.setPageSize(10);
            standardProductUnit.setPage(page);
		}
		List<StandardProductUnit> list = standardProductUnitService.findJoinList(standardProductUnit);
		BaseResp baseResp = commonGoodsService.spuExportFile(list, response);
		if(!baseResp.isSuccess()){
			addMessage(redirectAttributes, baseResp.getMsg());
            return "redirect:"+Global.getAdminPath()+"/xiaodian/database/standardProductUnit/?repage";
		}
		return null;
	}

}