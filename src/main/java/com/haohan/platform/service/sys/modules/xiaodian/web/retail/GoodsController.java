package com.haohan.platform.service.sys.modules.xiaodian.web.retail;

import com.google.common.collect.Lists;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.utils.excel.ExportExcel;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IGoodsConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsInfoExt;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsCategoryService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.RetailGoodsOperationService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ????????????Controller
 * @author haohan
 * @version 2017-08-06
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/retail/goods")
public class GoodsController extends BaseController {

	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsCategoryService goodsCategoryService;
	@Autowired
    private ShopService shopService;
    @Autowired
    @Lazy(true)
    private MerchantService merchantService;
    @Autowired
    @Lazy(true)
    private RetailGoodsOperationService retailGoodsOperationService;



    @ModelAttribute
	public Goods get(@RequestParam(required=false) String id) {
		Goods entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsService.get(id);
		}
		if (entity == null){
			entity = new Goods();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:retail:goods:view")
	@RequestMapping(value = {"list", ""})
	public String list(Goods goods, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Goods> page = goodsService.findPage(new Page<Goods>(request, response), goods); 
		model.addAttribute("page", page);
		return "modules/xiaodian/retail/goodsList";
	}

	@RequiresPermissions("xiaodian:retail:goods:view")
	@RequestMapping(value = "form")
	public String form(Goods goods, GoodsInfoExt goodsInfoExt, Model model) {
        // ?????????????????????
		if(StringUtils.isEmpty(goodsInfoExt.getGoodsName())){
            GoodsInfoExt g = goodsService.getGoodsInfoExt(goods.getId());
            if(null != g){
                goodsInfoExt = g;
            }
        }
        model.addAttribute("goodsInfoExt", goodsInfoExt);
        List<Merchant> merchantList = merchantService.findList(new Merchant());
        model.addAttribute("merchantList",merchantList);
		return "modules/xiaodian/retail/goodsForm";
	}

	@RequiresPermissions("xiaodian:retail:goods:edit")
	@RequestMapping(value = "save")
	public String save(GoodsInfoExt goodsInfoExt, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsInfoExt)){
			return form(new Goods(), goodsInfoExt, model);
		}
		// ??????????????????
//        goodsInfoExt.transToModelInfo();
		String goodsFrom = IGoodsConstant.GoodsFromType.platform.getCode();
		boolean flag = false;
		String jisuAppId = "";
		if(StringUtils.isNotEmpty(goodsInfoExt.getShopId())){
            Shop shop = shopService.get(goodsInfoExt.getShopId());
            if (null != shop && StringUtils.isNotEmpty(shop.getCode())) {
                // ???????????????
                if (StringUtils.equals(shop.getIsUpdateJisu(), ICommonConstant.YesNoType.yes.getCode())) {
                    // ????????????
                    goodsFrom = IGoodsConstant.GoodsFromType.jisuapp.getCode();
                    // ????????????
                    if (StringUtils.isNotEmpty(goodsInfoExt.getGoodsCategoryId())) {
                        String categorySn = goodsCategoryService.getCategorySns(goodsInfoExt.getGoodsCategoryId());
                        goodsInfoExt.setCategorySn(categorySn);
                    }
                }
            }
        }
        // ??????goods???????????????
        goodsInfoExt.setGoodsFrom(goodsFrom);
        goodsService.saveGoods(goodsInfoExt);
		addMessage(redirectAttributes, "??????????????????");
        return "redirect:" + Global.getAdminPath() + "/xiaodian/retail/goods/list?shopId=" + goodsInfoExt.getShopId();
	}
	
	@RequiresPermissions("xiaodian:retail:goods:edit")
	@RequestMapping(value = "delete")
	public String delete(Goods goods, RedirectAttributes redirectAttributes) {
		goodsService.deleteGoodsInfoExt(goods);
		addMessage(redirectAttributes, "??????????????????");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/retail/goods/?shopId="+ goods.getShopId();
	}

    @RequiresPermissions("xiaodian:retail:goods:edit")
    @RequestMapping(value = "copy")
    public String copy(Goods goods, Model model) {
        GoodsInfoExt goodsInfoExt = goodsService.getGoodsInfoExt(goods.getId());
        if (null == goodsInfoExt){
            goodsInfoExt = new GoodsInfoExt();
        }
        // ??????id ??????
        goodsInfoExt.setId(null);
        goodsInfoExt.setGoodsSn(null);
        goodsInfoExt.setThirdGoodsSn(null);
        goodsInfoExt.setMerchantId(null);
        goodsInfoExt.setShopId(null);
        goodsInfoExt.setGoodsCategoryId(null);
        goodsInfoExt.setCategoryName(null);
        model.addAttribute("goodsInfoExt", goodsInfoExt);
        List<Merchant> merchantList = merchantService.findList(new Merchant());
        model.addAttribute("merchantList",merchantList);
        return "modules/xiaodian/retail/goodsForm";
    }

    @RequiresPermissions("xiaodian:retail:goods:view")
    @ResponseBody
    @RequestMapping(value = "fetchGoodsList")
    public String fetchGoodsList(Goods goods){
        Map<String,String> map = goodsService.fetchNameMap(goods);
        ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Map<String,String> obj;
        for(Map.Entry<String,String> e:map.entrySet()){
            obj = new HashMap<String, String>();
            obj.put("id",e.getKey());
            obj.put("text",e.getValue());
            list.add(obj);
        }
	    return JacksonUtils.toJson(list);
    }

    @RequiresPermissions(value = {"xiaodian:retail:goods:edit", "xiaodian:manage:merchant:product:edit"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "fetchGoodsInfoExt")
    public String fetchGoodsInfoExt(@RequestParam String goodsId){
        GoodsInfoExt goodsInfoExt = goodsService.getGoodsInfoExt(goodsId);
        return JacksonUtils.toJson(goodsInfoExt);
    }
    
    /**
     * Excel????????????????????????
     *
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions(value = {"xiaodian:retail:goods:edit", "xiaodian:manage:merchant:product:edit"}, logical = Logical.OR)
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
//        addMessage(redirectAttributes, retailGoodsOperationService.goodsImport(file));
        addMessage(redirectAttributes, "????????????");
        return "redirect:" + Global.getAdminPath() + "/xiaodian/retail/goods/?repage";
    }

    /**
     * ????????????????????????
     *
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions(value = {"xiaodian:retail:goods:edit", "xiaodian:manage:merchant:product:edit"}, logical = Logical.OR)
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "????????????????????????.xlsx";
            List<Goods> list = Lists.newArrayList();
            Map<String, String> headerMapper = retailGoodsOperationService.fetchHeaderMapper();
            new ExportExcel("????????????", headerMapper, retailGoodsOperationService.fetchCommentMapper()).setBeanList(list, headerMapper).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "??????????????????????????????????????????" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/xiaodian/retail/goods/?repage";
    }

    /**
     * ??????????????????
     *
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions(value = {"xiaodian:retail:goods:edit", "xiaodian:manage:merchant:product:edit"}, logical = Logical.OR)
    @RequestMapping(value = "export/{flag}")
    public String exportFile(@PathVariable("flag") String flag, Goods goods, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "????????????????????????" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<Goods> page;
            // ????????????
            if (StringUtils.equals("false", flag)) {
                page = new Page();
                page.setList(goodsService.findJoinList(goods));
            }else{
                // ?????????????????????  TODO ?????????????????????
                page = goodsService.findPage(new Page<Goods>(request, response), goods);
            }
            Map<String, String> headerMapper = retailGoodsOperationService.fetchHeaderMapper();
            new ExportExcel("??????????????????", headerMapper, retailGoodsOperationService.fetchCommentMapper()).setBeanList(page.getList(), headerMapper).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "????????????????????????????????????????????????" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/xiaodian/retail/goods/?repage";
    }


}