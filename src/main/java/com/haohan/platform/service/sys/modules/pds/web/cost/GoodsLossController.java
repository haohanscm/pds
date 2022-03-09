package com.haohan.platform.service.sys.modules.pds.web.cost;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.cost.GoodsLoss;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.cost.GoodsLossService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
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
import java.util.List;

/**
 * 商品损耗管理Controller
 *
 * @author haohan
 * @version 2018-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/cost/goodsLoss")
public class GoodsLossController extends BaseController {

    @Autowired
    private GoodsLossService goodsLossService;
    @Autowired
    private PdsBuyerService pdsBuyerService;

    @ModelAttribute
    public GoodsLoss get(@RequestParam(required = false) String id) {
        GoodsLoss entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = goodsLossService.get(id);
        }
        if (entity == null) {
            entity = new GoodsLoss();
        }
        return entity;
    }

    @RequiresPermissions("pds:cost:goodsLoss:view")
    @RequestMapping(value = {"list", ""})
    public String list(GoodsLoss goodsLoss, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<GoodsLoss> page = new Page<GoodsLoss>(request, response);
        goodsLoss.setPage(page);
        page.setList(goodsLossService.findJoinList(goodsLoss));
        model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/cost/goodsLossList";
    }

    @RequiresPermissions("pds:cost:goodsLoss:view")
    @RequestMapping(value = "form")
    public String form(GoodsLoss goodsLoss, Model model) {
        model.addAttribute("goodsLoss", goodsLoss);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/cost/goodsLossForm";
    }

    @RequiresPermissions("pds:cost:goodsLoss:edit")
    @RequestMapping(value = "save")
    public String save(GoodsLoss goodsLoss, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, goodsLoss)) {
            return form(goodsLoss, model);
        }
        goodsLossService.save(goodsLoss);
        addMessage(redirectAttributes, "保存商品损耗成功");
        return "redirect:" + Global.getAdminPath() + "/pds/cost/goodsLoss/?repage";
    }

    @RequiresPermissions("pds:cost:goodsLoss:edit")
    @RequestMapping(value = "delete")
    public String delete(GoodsLoss goodsLoss, RedirectAttributes redirectAttributes) {
        goodsLossService.delete(goodsLoss);
        addMessage(redirectAttributes, "删除商品损耗成功");
        return "redirect:" + Global.getAdminPath() + "/pds/cost/goodsLoss/?repage";
    }

}