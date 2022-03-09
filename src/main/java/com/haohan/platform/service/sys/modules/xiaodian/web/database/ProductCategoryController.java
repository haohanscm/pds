package com.haohan.platform.service.sys.modules.xiaodian.web.database;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.ProductCategory;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsCategory;
import com.haohan.platform.service.sys.modules.xiaodian.service.database.CommonGoodsService;
import com.haohan.platform.service.sys.modules.xiaodian.service.database.ProductCategoryService;
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
import java.util.Map;

/**
 * 商品库分类管理Controller
 * @author dy
 * @version 2018-10-17
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/database/productCategory")
public class ProductCategoryController extends BaseController {

	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
    @Lazy(true)
	private CommonGoodsService commonGoodsService;
    @Autowired
    @Lazy(true)
	private MerchantService merchantService;

	
	@ModelAttribute
	public ProductCategory get(@RequestParam(required=false) String id) {
		ProductCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productCategoryService.get(id);
		}
		if (entity == null){
			entity = new ProductCategory();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:database:productCategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductCategory productCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
        // 分页查询根节点
        Page<ProductCategory> page = new Page<>(request, response);
        page.setPageSize(5);
        productCategory.setPage(page);
        // 查询分类下商品数
        productCategory.setGoodsCount(-1);
        List<ProductCategory> list = productCategoryService.fetchRootList(productCategory);
        productCategory.setPage(null);
        List<ProductCategory> resultList = new ArrayList<>(30);
        if (Collections3.isEmpty(list)) {
            page = productCategoryService.findPage(page, productCategory);
        } else {
            for (ProductCategory temp : list) {
                resultList.add(temp);
                productCategory.setParent(temp);
                resultList.addAll(productCategoryService.findList(productCategory));
            }
            page.setList(resultList);
        }
        model.addAttribute("page", page);
		return "modules/xiaodian/database/productCategoryList";
	}

	@RequiresPermissions("xiaodian:database:productCategory:view")
	@RequestMapping(value = "form")
	public String form(ProductCategory productCategory, Model model) {
		if (productCategory.getParent()!=null && StringUtils.isNotBlank(productCategory.getParent().getId())){
			productCategory.setParent(productCategoryService.get(productCategory.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(productCategory.getId())){
				ProductCategory productCategoryChild = new ProductCategory();
				productCategoryChild.setParent(new ProductCategory(productCategory.getParent().getId()));
				List<ProductCategory> list = productCategoryService.findList(productCategory); 
				if (list.size() > 0){
					productCategory.setSort(list.get(list.size()-1).getSort());
					if (productCategory.getSort() != null){
						productCategory.setSort(productCategory.getSort() + 30);
					}
				}
			}
		}
		if (productCategory.getSort() == null){
			productCategory.setSort(30);
		}
		model.addAttribute("productCategory", productCategory);
		return "modules/xiaodian/database/productCategoryForm";
	}

	@RequiresPermissions("xiaodian:database:productCategory:edit")
	@RequestMapping(value = "save")
	public String save(ProductCategory productCategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productCategory)){
			return form(productCategory, model);
		}
		productCategoryService.save(productCategory);
		addMessage(redirectAttributes, "保存商品库分类成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/database/productCategory/?repage";
	}
	
	@RequiresPermissions("xiaodian:database:productCategory:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductCategory productCategory, RedirectAttributes redirectAttributes) {
		productCategoryService.deleteWithChildren(productCategory);
		addMessage(redirectAttributes, "删除商品库分类成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/database/productCategory/?repage";
	}

    /**
     * 生成所的商品分类编号
     */
    @RequiresPermissions("xiaodian:database:productCategory:edit")
    @RequestMapping(value = "genCategorySn")
    @ResponseBody
    public BaseResp genCategorySn(ProductCategory productCategory) {
        BaseResp baseResp = BaseResp.newError();
        if(StringUtils.equals("all", productCategory.getGeneralCategorySn())){
            baseResp = commonGoodsService.genAllCategorySn();
        }
        return baseResp;
    }

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false)String limitType, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		// 限制查询条件
        ProductCategory category = new ProductCategory();
		List<ProductCategory> list;
		if(StringUtils.isNotEmpty(limitType)){
		    category.setAggregationType(limitType);
        }
        list = productCategoryService.findList(category);
		for (int i=0; i<list.size(); i++){
			ProductCategory e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}

	/**
	 * 分类商品导入至零售商品
	 * @param
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("xiaodian:database:productCategory:edit")
	@RequestMapping(value = "importRetailGoods")
	public String importRetailGoods(GoodsCategory goodsCategory,Model model, RedirectAttributes redirectAttributes) {
	    List<Merchant> merchantList = merchantService.findList(new Merchant());
	    model.addAttribute("merchantList",merchantList);
		return "modules/xiaodian/database/importRetailGoods";
	}

    /**
     * 分类商品导入至零售商品
     * @param
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("xiaodian:database:productCategory:edit")
    @RequestMapping(value = "transToRetailGoods")
    public String transToRetailGoods(GoodsCategory goodsCategory, RedirectAttributes redirectAttributes) {
    	// 找到 分类及其所有父级分类
        BaseResp baseResp;
        if(StringUtils.isAnyEmpty(goodsCategory.getId(), goodsCategory.getShopId(), goodsCategory.getMerchantId())){
            baseResp = BaseResp.newError();
            baseResp.setMsg("选择错误,需选择分类/商家/店铺");
        }else{
            List<ProductCategory> parentList = productCategoryService.findListByIdsWithParent(goodsCategory.getId());
            baseResp = commonGoodsService.importToRetailGoods(parentList, goodsCategory.getShopId(), goodsCategory.getMerchantId());
        }
        addMessage(redirectAttributes, baseResp.getMsg());
        return "redirect:"+Global.getAdminPath()+"/xiaodian/database/productCategory/importRetailGoods?repage";
    }

    /**
     * Excel导入信息
     *
     * @param file
     * @return
     */
    @RequiresPermissions("xiaodian:database:productCategory:edit")
    @RequestMapping(value = "import")
    public String importFile(MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        addMessage(redirectAttributes, commonGoodsService.fileImport(file, "category"));
        return "redirect:"+Global.getAdminPath()+"/xiaodian/database/productCategory/?repage";
    }

    /**
     * 下载导出数据
     *
     * @param response
     * @return
     */
    @RequiresPermissions("xiaodian:database:productCategory:edit")
    @RequestMapping(value = "export")
    public String exportFile(@RequestParam("flag") String flag, ProductCategory productCategory, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        if(StringUtils.equals("true", flag)){
            Page<ProductCategory> page = new Page<>(request, response);
            page.setPageNo(1);
            page.setPageSize(10);
            productCategory.setPage(page);
        }
        List<ProductCategory> list = productCategoryService.findJoinList(productCategory);
        // 父分类名称放入parentIds中
        for(ProductCategory category :list ){
            category.setParentIds(category.getParentName());
        }
        BaseResp baseResp = commonGoodsService.categoryExportFile(list, response);
        if(!baseResp.isSuccess()){
            addMessage(redirectAttributes, baseResp.getMsg());
            return "redirect:" + Global.getAdminPath() + "/xiaodian/database/productCategoryList/?repage";
        }
        return null;
    }
}