package com.haohan.platform.service.sys.modules.xiaodian.web.pay;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.sys.entity.Dict;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.entity.ReqParams;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.service.inf.IXmBankPayService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.AppPayRelation;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.ChannelRateManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.MerchantPayConfig;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.MerchantPayInfo;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.AppPayRelationService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantAppManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.ChannelRateManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.MerchantPayInfoService;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.ArrayList;
import java.util.List;

import static com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant.BankRegStatus;

/**
 * 商家支付信息Controller
 * @author haohan
 * @version 2017-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/pay/merchantPayInfo")
public class MerchantPayInfoController extends BaseController {

	@Autowired
	private MerchantPayInfoService merchantPayInfoService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
    private IXmBankPayService xmBankPayService;
	@Autowired
	private ChannelRateManageService channelRateManageService;
	@Autowired
	private AppPayRelationService appPayRelationService;
	@Autowired
	private MerchantAppManageService merchantAppManageService;

	@ModelAttribute
	public MerchantPayInfo get(@RequestParam(required=false) String id) {
		MerchantPayInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantPayInfoService.get(id);
		}
		if (entity == null){
			entity = new MerchantPayInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:pay:merchantPayInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantPayInfo merchantPayInfo, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<MerchantPayInfo> page = merchantPayInfoService.findPage(new Page<MerchantPayInfo>(request, response), merchantPayInfo);
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList", merchantList);
		model.addAttribute("page", page);
		return "modules/xiaodian/pay/merchantPayInfoList";
	}

	@RequiresPermissions("xiaodian:pay:merchantPayInfo:view")
	@RequestMapping(value = "form")
	public String form(MerchantPayInfo merchantPayInfo, Model model) {
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList", merchantList);
		model.addAttribute("merchantPayInfo", merchantPayInfo);
        List<AppPayRelation> appList;
		String partnerId = merchantPayInfo.getPartnerId();
		if(StringUtils.isEmpty(partnerId)){
		    appList = new ArrayList<>();
        }else {
            AppPayRelation appPayRelation = new AppPayRelation();
            appPayRelation.setPartnerId(partnerId);
            appList = appPayRelationService.findJoinList(appPayRelation);
        }
		model.addAttribute("appList", appList);
		return "modules/xiaodian/pay/merchantPayInfoForm";
	}

	// 复制添加
	@RequiresPermissions("xiaodian:pay:merchantPayInfo:view")
	@RequestMapping(value = "copy")
	public String copy(MerchantPayInfo merchantPayInfo, RedirectAttributes redirectAttributes) {
	    String oldId = merchantPayInfo.getId();
		merchantPayInfo.setAgrNo(null);
		merchantPayInfo.setPartnerId(null);
		merchantPayInfo.setRegStatus(BankRegStatus.TODO.getCode());
        merchantPayInfo.setRegTime(null);
		merchantPayInfo.setRespCode(null);
		merchantPayInfo.setRespDesc(null);
		merchantPayInfo.setRemarks(null);
		merchantPayInfo.setIsNewRecord(true);
		merchantPayInfo.setId(IdGen.uuid());
		merchantPayInfoService.save(merchantPayInfo);
		// 支付渠道费率 复制
        List<ChannelRateManage> rateManageList = channelRateManageService.fetchByPayInfoId(oldId);
        if(CollectionUtils.isEmpty(rateManageList)){
            rateManageList = new ArrayList<>();
            List<Dict> dicts = 	DictUtils.getDictList("pay_channel");
            for(Dict dict:dicts) {
                ChannelRateManage channelRateManage = new ChannelRateManage();
                channelRateManage.setChannel(dict.getValue());
                channelRateManage.setMerchantId(merchantPayInfo.getMerchantId());
                rateManageList.add(channelRateManage);
            }
        }

		for(ChannelRateManage rateManage:rateManageList){
			rateManage.setPayInfoId(merchantPayInfo.getId());
            rateManage.setRespCode(null);
            rateManage.setRespMsg(null);
            rateManage.setStatus((StringUtils.isEmpty(rateManage.getStatus())?null: BankRegStatus.TODO.getCode().toString()));
            rateManage.setCustId(null);
            rateManage.setIsNewRecord(true);
            rateManage.setId(IdGen.uuid());
			channelRateManageService.save(rateManage);
		}

		// 显示相同商户简称的数据
        redirectAttributes.addAttribute("mercAbbr",merchantPayInfo.getMercAbbr());
		return "redirect:"+ Global.getAdminPath()+"/xiaodian/pay/merchantPayInfo/?repage";
	}


	@RequiresPermissions("xiaodian:pay:merchantPayInfo:edit")
	@RequestMapping(value = "save")
	public String save(MerchantPayInfo merchantPayInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantPayInfo)) {
			return form(merchantPayInfo, model);

		}
		String merchantId = merchantPayInfo.getMerchantId();
		if (StringUtils.isNotEmpty(merchantId)){
			if (merchantPayInfo.getIsEnable().equals(ICommonConstant.YesNoType.yes.getCode())){
				MerchantPayInfo payInfo = merchantPayInfoService.fetchByMerchantIdEnable(merchantId,merchantPayInfo.getBankChannel());
				if (null != payInfo && !StringUtils.equals(merchantPayInfo.getId(),payInfo.getId())){
					addMessage(redirectAttributes,"保存失败,该商家已经存在启用的账户!");
					return "redirect:"+ Global.getAdminPath()+"/xiaodian/pay/merchantPayInfo/?repage";
				}
			}
		}

		merchantPayInfoService.save(merchantPayInfo);
		addMessage(redirectAttributes, "保存商家支付信息成功");
		return "redirect:"+ Global.getAdminPath()+"/xiaodian/pay/merchantPayInfo/?repage";
	}
	
	@RequiresPermissions("xiaodian:pay:merchantPayInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(MerchantPayInfo merchantPayInfo, RedirectAttributes redirectAttributes) {
		merchantPayInfoService.delete(merchantPayInfo);
        //删除相应的支付渠道信息
        List<ChannelRateManage> rateManageList = channelRateManageService.fetchByPayInfoId(merchantPayInfo.getId());
        if(CollectionUtils.isNotEmpty(rateManageList)){
            for (ChannelRateManage channelRateManage:rateManageList){
                channelRateManageService.delete(channelRateManage);
            }
        }
        addMessage(redirectAttributes, "删除商家支付信息成功");
		return "redirect:"+ Global.getAdminPath()+"/xiaodian/pay/merchantPayInfo/?repage";
	}

	@RequiresPermissions("xiaodian:pay:merchantPayInfo:edit")
	@RequestMapping(value = "reg")
	public String reg(MerchantPayInfo merchantPayInfo, RedirectAttributes redirectAttributes) {

//		merchantPayInfo = bankPayService.merchantReg(merchantPayInfo);

		xmBankPayService.merchantReg(merchantPayInfo);

		addMessage(redirectAttributes, merchantPayInfo.getRespDesc());
		merchantPayInfoService.save(merchantPayInfo);
		return "redirect:"+ Global.getAdminPath()+"/xiaodian/pay/merchantPayInfo/?repage";
	}

	@RequiresPermissions("xiaodian:pay:merchantPayInfo:view")
	@RequestMapping(value = "payConfig")
	public String payConfig(MerchantPayInfo merchantPayInfo, Model model) {

		List<ChannelRateManage> rateManageList = channelRateManageService.fetchByPayInfoId(merchantPayInfo.getId());
		if(CollectionUtils.isEmpty(rateManageList)){
			rateManageList = new ArrayList<>();
		List<Dict> dicts = 	DictUtils.getDictList("pay_channel");
			for(Dict dict:dicts) {
				ChannelRateManage channelRateManage = new ChannelRateManage();
				channelRateManage.setChannel(dict.getValue());
				channelRateManage.setMerchantId(merchantPayInfo.getMerchantId());
				rateManageList.add(channelRateManage);
			}
		}
		MerchantPayConfig config = new MerchantPayConfig();
		config.setChannelRateManageList(rateManageList);
		config.setMerchantPayInfo(merchantPayInfo);
		model.addAttribute("rateManageList", rateManageList);
		model.addAttribute("merchantPayInfo", merchantPayInfo);
		model.addAttribute("merchantPayConfig", config);

		return "modules/xiaodian/pay/merchantPayConfig";
	}

	@RequiresPermissions("xiaodian:pay:merchantPayInfo:view")
	@RequestMapping(value = "savePayConfig")
	public String savePayConfig(MerchantPayConfig config, Model model, RedirectAttributes redirectAttributes){

		List<ChannelRateManage> rateManageList = config.getChannelRateManageList();
		MerchantPayInfo payInfo = config.getMerchantPayInfo();
		payInfo = merchantPayInfoService.get(payInfo.getId());

		for(ChannelRateManage rateManage:rateManageList){
			rateManage.setMerchantId(payInfo.getMerchantId());
			rateManage.setPayInfoId(payInfo.getId());
			channelRateManageService.save(rateManage);
		}

		addMessage(redirectAttributes,"添加成功");
		return "redirect:"+ Global.getAdminPath()+"/xiaodian/pay/merchantPayInfo/payConfig?id="+payInfo.getId();
	}





	@RequiresPermissions("xiaodian:pay:merchantPayInfo:edit")
	@RequestMapping(value = "queryStatus")
	public String queryStatus(MerchantPayInfo merchantPayInfo, RedirectAttributes redirectAttributes) {

		String merchantId = merchantPayInfo.getMerchantId();
		ChannelRateManage rateManage = new ChannelRateManage();
		rateManage.setMerchantId(merchantId);

		List<ChannelRateManage>  rateManageList = channelRateManageService.findList(rateManage);
		for(ChannelRateManage rate:rateManageList) {
			if(StringUtils.equals(BankRegStatus.CHECK.getCode()+"",rate.getStatus())){
				ReqParams params = new ReqParams();
				params.setCoopMchtId(merchantPayInfo.getId());
				params.setAcquirerType(rate.getChannel());
				MerchantPayInfo merchantResp = xmBankPayService.merchantQuery(params);
				if(null != merchantResp) {
					rate.setStatus(merchantResp.getRegStatus().toString());
					rate.setCustId(merchantResp.getCustId());
					channelRateManageService.save(rate);
					if(BankRegStatus.SUCCESS.getCode() == merchantResp.getRegStatus()){

						//TODO 修改状态 前端运营核对后手动修改 MerchantPayInfo 表开户状态
					}
				}
			}
		}
		// TODO 所有都通过后则修改MerchantPayInfo 表开户状态

		addMessage(redirectAttributes, "查询完成");
//		merchantPayInfoService.save(merchantPayInfo);
		return "redirect:"+ Global.getAdminPath()+"/xiaodian/pay/merchantPayInfo/payConfig?id="+merchantPayInfo.getId();
	}

	@RequiresPermissions("xiaodian:pay:merchantPayInfo:edit")
	@RequestMapping(value = "bindApp")
	public String bindApp(MerchantPayInfo merchantPayInfo, String partnerId, String appId, RedirectAttributes redirectAttributes, Model model) {
		BaseResp baseResp = BaseResp.newError();
		if (StringUtils.isAnyBlank(partnerId,appId)){
			baseResp.setMsg("操作失败,partnerId/appId不能为空");
		}else {
//			MerchantAppManage merchantAppManage = merchantAppManageService.fetchByAppId(appId);
//			if (null == merchantAppManage){
//				baseResp.setMsg("无效的APPID");
//			}else {
				AppPayRelation appPayRelation = new AppPayRelation();
				appPayRelation.setAppId(appId);
				appPayRelation.setPartnerId(partnerId);
				appPayRelation.setStatus(ICommonConstant.IsEnable.enable.getCode());
				List<AppPayRelation> list = appPayRelationService.findList(appPayRelation);
				if (CollectionUtils.isNotEmpty(list)){
					baseResp.setMsg("app已绑定,请先解除其他绑定关系");
				}else {
					appPayRelationService.save(appPayRelation);
					baseResp.success();
				}
//			}
		}
		redirectAttributes.addFlashAttribute("tip_message", baseResp.getMsg());
		return "redirect:"+ Global.getAdminPath()+"/xiaodian/pay/merchantPayInfo/form/?repage&id="+merchantPayInfo.getId();
	}


}