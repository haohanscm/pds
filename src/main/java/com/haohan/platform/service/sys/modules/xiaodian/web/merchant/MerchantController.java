package com.haohan.platform.service.sys.modules.xiaodian.web.merchant;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGroupManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantFiles;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.service.UPassportService;
import com.haohan.platform.service.sys.modules.xiaodian.service.UserOpenPlatformService;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoGroupManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.OrderPayRecordService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商家管理Controller
 * @author haohan
 * @version 2017-08-05
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/merchant")
public class MerchantController extends BaseController {

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private PhotoGroupManageService photoGroupManageService;
	@Autowired
	private UPassportService passportService;
	@Autowired
	private OrderPayRecordService orderPayRecordService;
	@Autowired
	private UserOpenPlatformService userOpenPlatformService;
	
	@ModelAttribute
	public Merchant get(@RequestParam(required=false) String id) {
		Merchant entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantService.get(id);
		}
		if (entity == null){
			entity = new Merchant();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:merchant:view")
	@RequestMapping(value = {"list", ""})
	public String list(Merchant merchant, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Merchant> page = new Page<Merchant>(request, response);
		merchant.setPage(page);
		page.setList(merchantService.findJoinList(merchant));
		model.addAttribute("page", page);
        List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
        model.addAttribute("merchantList",merchantList);
		return "modules/xiaodian/merchant/merchantList";
	}

	@RequiresPermissions("xiaodian:merchant:view")
	@RequestMapping(value = "form")
	public String form(Merchant merchant, Model model) {
		UPassport passport = passportService.get(merchant.getUpassport());
		merchant.setUpassport(passport);
		model.addAttribute("upassport",passport);
		model.addAttribute("merchant", merchant);
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
		return "modules/xiaodian/merchant/merchantForm";
	}

	@RequiresPermissions("xiaodian:merchant:edit")
	@RequestMapping(value = "addMerchant/{oId}")
	public String addMerchant(Merchant merchant, @PathVariable("oId") String oId,Model model) {
		OrderPayRecord payRecord = orderPayRecordService.get(oId);
		UPassport passport = null;
		if(null != payRecord){
			UserOpenPlatform userOpenPlatform = userOpenPlatformService.fetchByAppIdAndOpenId(payRecord.getAppid(),payRecord.getOpenid());
			if(null != userOpenPlatform) {
				passport = passportService.get(userOpenPlatform.getUid());
			}
		}
		merchant.setUpassport(passport);
		model.addAttribute("upassport",passport);
		model.addAttribute("merchant", merchant);

		return "modules/xiaodian/merchant/merchantForm";
	}

	@RequiresPermissions("xiaodian:merchant:edit")
	@RequestMapping(value = "save")
	public String save(Merchant merchant, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchant)){
			return form(merchant, model);
		}
		UPassport passport = merchant.getUpassport();

		if(null != passport && StringUtils.isNotEmpty(passport.getId())){
			passport.setId(passport.getId());
			List<UPassport> passports = passportService.findList(passport);
			passport = CollectionUtils.isEmpty(passports)?null:passports.get(0);
		}
		merchant.setUpassport(passport);
		merchantService.save(merchant);
		addMessage(redirectAttributes, "保存商家成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/merchant/?repage";
	}

	@RequiresPermissions("xiaodian:merchant:edit")
	@RequestMapping(value = "photoManage")
	public String photoManage(Merchant merchant, Model model) {
		PhotoGroupManage photoGroupManage = new PhotoGroupManage();
		photoGroupManage.setMerchantId(merchant.getId());
		MerchantFiles merchantFiles = merchantService.findMerchantFiles(merchant.getId());
		model.addAttribute("merchant", merchant);
		model.addAttribute("merchantFiles", merchantFiles);
		model.addAttribute("shopPhotos", merchantFiles.getShopPhotos());
		model.addAttribute("merchantPhotos", merchantFiles.getMerchantPhotos());
		model.addAttribute("protocolFiles", merchantFiles.getProtocolFiles());
		model.addAttribute("productPhotos", merchantFiles.getProductPhotos());
		model.addAttribute("cateringPhotos",merchantFiles.getCateringPhotos());

        model.addAttribute("licensePhotos",merchantFiles.getLicensePhotos());
        model.addAttribute("idCardsPhotos",merchantFiles.getIdCardsPhotos());
        model.addAttribute("bankCardsPhotos",merchantFiles.getBankCardsPhotos());

		return "modules/xiaodian/merchant/merchantPhotoManage";
	}


	@RequiresPermissions("xiaodian:merchant:edit")
	@RequestMapping(value = "saveMerchantFiles")
	public String saveMerchantFiles(MerchantFiles merchantFiles, Model model) {
		String merchantId = merchantFiles.getMerchantId();

		PhotoGroupManage merchantPhotos = merchantFiles.getMerchantPhotos();
		if(null != merchantPhotos) {
			merchantPhotos.setMerchantId(merchantId);
			photoGroupManageService.save(merchantPhotos);
		}
		PhotoGroupManage shopPhotos = merchantFiles.getShopPhotos();
		if(null != shopPhotos) {
			shopPhotos.setMerchantId(merchantId);
			photoGroupManageService.save(shopPhotos);
		}

		PhotoGroupManage protocolFiles = merchantFiles.getProtocolFiles();
		if(null != protocolFiles) {
			protocolFiles.setMerchantId(merchantId);
			photoGroupManageService.save(protocolFiles);
		}

		PhotoGroupManage productPhotos =  merchantFiles.getProductPhotos();
		if(null != productPhotos) {
			productPhotos.setMerchantId(merchantId);
			photoGroupManageService.save(productPhotos);
		}

		//分割线
		PhotoGroupManage licensePhotos = merchantFiles.getLicensePhotos();
		if (null != licensePhotos){
			licensePhotos.setMerchantId(merchantId);
			photoGroupManageService.save(licensePhotos);
		}

		PhotoGroupManage cateringPhotos = merchantFiles.getLicensePhotos();
		if (null != cateringPhotos){
			cateringPhotos.setMerchantId(merchantId);
			photoGroupManageService.save(cateringPhotos);
		}

		PhotoGroupManage idCardsPhotos = merchantFiles.getLicensePhotos();
		if (null != idCardsPhotos){
			idCardsPhotos.setMerchantId(merchantId);
			photoGroupManageService.save(idCardsPhotos);
		}

		model.addAttribute("merchantFiles", merchantFiles);

		return "redirect:"+Global.getAdminPath()+"/xiaodian/merchant/photoManage?id="+merchantId;
	}




	@RequiresPermissions("xiaodian:merchant:edit")
	@RequestMapping(value = "delete")
	public String delete(Merchant merchant, RedirectAttributes redirectAttributes) {
		merchantService.delete(merchant);
		addMessage(redirectAttributes, "删除商家成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/merchant/?repage";
	}

}