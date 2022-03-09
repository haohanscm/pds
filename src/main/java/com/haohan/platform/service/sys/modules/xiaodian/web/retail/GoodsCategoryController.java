package com.haohan.platform.service.sys.modules.xiaodian.web.retail;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsCategory;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsCategoryService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品分类Controller
 * @author haohan
 * @version 2017-08-06
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/retail/goodsCategory")
public class GoodsCategoryController extends BaseController {

	@Autowired
	private GoodsCategoryService goodsCategoryService;
	@Autowired
	private ShopService shopService;
    @Autowired
    @Lazy(true)
    private MerchantService merchantService;

	
	@ModelAttribute
	public GoodsCategory get(@RequestParam(required=false) String id) {
		GoodsCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsCategoryService.get(id);
		}
		if (entity == null){
			entity = new GoodsCategory();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:retail:goodsCategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsCategory goodsCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 查询分类下商品数
		goodsCategory.setGoodsCount("-1");
		// 默认节点数5 即不同的shopId
		int defaultNodeNum = 5;
		Page<GoodsCategory> page = new Page<>(request, response);
		int num = StringUtils.toInteger(goodsCategory.getRootNodeNum());
		page.setPageSize(num == 0 ? defaultNodeNum : num);
		goodsCategory.setPage(page);
		// 根节点
		goodsCategory.setParent(new GoodsCategory("0"));
		List<GoodsCategory> list = goodsCategoryService.findJoinList(goodsCategory);
		goodsCategory.setPage(null);
		List<GoodsCategory> resultList = new ArrayList<>(30);
		if (Collections3.isEmpty(list)) {
			page = goodsCategoryService.findPage(page, goodsCategory);
		} else {
			for (GoodsCategory temp : list) {
			    resultList.add(temp);
				goodsCategory.setParent(temp);
				resultList.addAll(goodsCategoryService.findJoinList(goodsCategory));
			}
			page.setList(resultList);
		}
		model.addAttribute("page",page);
		return "modules/xiaodian/retail/goodsCategoryList";
	}

	@RequiresPermissions("xiaodian:retail:goodsCategory:view")
	@RequestMapping(value = "form")
	public String form(GoodsCategory goodsCategory, Model model) {
		if (goodsCategory.getParent()!=null && StringUtils.isNotBlank(goodsCategory.getParent().getId())){
			goodsCategory.setParent(goodsCategoryService.get(goodsCategory.getParent().getId()));
			// 添加下级分类时,设置店铺
			if (goodsCategory.getParent() != null) {
				goodsCategory.setShopId(goodsCategory.getParent().getShopId());
			}
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(goodsCategory.getId())){
				GoodsCategory goodsCategoryChild = new GoodsCategory();
				goodsCategoryChild.setParent(new GoodsCategory(goodsCategory.getParent().getId()));
				List<GoodsCategory> list = goodsCategoryService.findList(goodsCategory);
				if (list.size() > 0){
					goodsCategory.setSort(list.get(list.size()-1).getSort());
					if (goodsCategory.getSort() != null){
						goodsCategory.setSort(goodsCategory.getSort() + 30);
					}
				}
			}
		}
		if (goodsCategory.getSort() == null){
			goodsCategory.setSort(30);
		}
		model.addAttribute("goodsCategory", goodsCategory);
		List<Merchant> merchantList = merchantService.findList(new Merchant());
		model.addAttribute("merchantList", merchantList);
		return "modules/xiaodian/retail/goodsCategoryForm";
	}

	@RequiresPermissions("xiaodian:retail:goodsCategory:edit")
	@RequestMapping(value = "save")
	public String save(GoodsCategory goodsCategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsCategory)){
			return form(goodsCategory, model);
		}
		goodsCategoryService.save(goodsCategory);
		addMessage(redirectAttributes, "保存商品分类成功");
        if(StringUtils.isNotEmpty(goodsCategory.getShopId())){
            Shop shop = shopService.get(goodsCategory.getShopId());
            // 同步即速
            if (null!= shop && StringUtils.isNotEmpty(shop.getCode())) {
                // 启用同步时
                if (StringUtils.equals(shop.getIsUpdateJisu(), ICommonConstant.YesNoType.yes.getCode())) {
                    // 父分类设置
                    GoodsCategory parent = goodsCategory.getParent();
                    String parentSn = "";
                    if(null != parent){
                        parentSn = parent.getCategorySn();
                    }
                    goodsCategory.setParentSn(parentSn);
                }
            }
        }
		return "redirect:"+Global.getAdminPath()+"/xiaodian/retail/goodsCategory/?shopId="+goodsCategory.getShopId();
	}
	
	@RequiresPermissions("xiaodian:retail:goodsCategory:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsCategory goodsCategory, RedirectAttributes redirectAttributes) {
		goodsCategoryService.deleteByCategory(goodsCategory);
		addMessage(redirectAttributes, "删除商品分类成功");
        if(StringUtils.isNoneEmpty(goodsCategory.getShopId(), goodsCategory.getCategorySn())){
            Shop shop = shopService.get(goodsCategory.getShopId());
            // 同步即速
            if (null!= shop && StringUtils.isNotEmpty(shop.getCode())) {
                // 启用同步时
                if (StringUtils.equals(shop.getIsUpdateJisu(), ICommonConstant.YesNoType.yes.getCode())) {
                    List<String> list = new ArrayList<>();
                    list.add(goodsCategory.getCategorySn());
                }
            }
        }
		return "redirect:"+Global.getAdminPath()+"/xiaodian/retail/goodsCategory/?shopId="+goodsCategory.getShopId();
	}

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false)String limitShopId, @RequestParam(required=false)String limitMerchantId) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        GoodsCategory category = new GoodsCategory();
        List<GoodsCategory> list;
        // 限制查询的merchantId、shopId
        if(StringUtils.isNotEmpty(limitShopId)){
            category.setShopId(limitShopId);
        }
        if(StringUtils.isNotEmpty(limitMerchantId)){
            category.setMerchantId(limitMerchantId);
        }
        list = goodsCategoryService.findJoinList(category);
        for (int i=0; i<list.size(); i++){
            GoodsCategory e = list.get(i);
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

}