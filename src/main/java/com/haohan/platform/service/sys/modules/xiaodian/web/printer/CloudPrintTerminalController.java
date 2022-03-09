package com.haohan.platform.service.sys.modules.xiaodian.web.printer;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.inf.IYiCloudCommonService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.OpenPlatformConfig;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.CloudPrintTerminal;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.OpenPlatformConfigService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.printer.CloudPrintTerminalService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 易联云打印机管理Controller
 * @author haohan
 * @version 2018-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/printer/cloudPrintTerminal")
public class CloudPrintTerminalController extends BaseController {

	@Autowired
	private CloudPrintTerminalService cloudPrintTerminalService;
	@Autowired
	private OpenPlatformConfigService openPlatformConfigService;
	@Resource
	private IYiCloudCommonService yiCloudCommonService;
	@Autowired
	private MerchantService merchantService;
    @Autowired
    private ShopService shopService;
	
	@ModelAttribute
	public CloudPrintTerminal get(@RequestParam(required=false) String id) {
		CloudPrintTerminal entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cloudPrintTerminalService.get(id);
		}
		if (entity == null){
			entity = new CloudPrintTerminal();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:printer:cloudPrintTerminal:view")
	@RequestMapping(value = {"list", ""})
	public String list(CloudPrintTerminal cloudPrintTerminal, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CloudPrintTerminal> page = new Page<CloudPrintTerminal>(request, response);
		cloudPrintTerminal.setPage(page);
		page.setList(cloudPrintTerminalService.findJoinList(cloudPrintTerminal));
		model.addAttribute("page", page);
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
        List<Shop> shopList = shopService.findList(new Shop());
        model.addAttribute("shopList",shopList);
		return "modules/xiaodian/printer/cloudPrintTerminalList";
	}

	@RequiresPermissions("xiaodian:printer:cloudPrintTerminal:view")
	@RequestMapping(value = "form")
	public String form(CloudPrintTerminal cloudPrintTerminal, Model model) {
		OpenPlatformConfig openPlatformConfig = new OpenPlatformConfig();
		openPlatformConfig.setAppType(ICommonConstant.AppType.yilCloud.getCode());
		openPlatformConfig.setStatus(IMerchantConstant.available);
		List<OpenPlatformConfig> openPlatformConfigList = openPlatformConfigService.findList(openPlatformConfig);
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
		model.addAttribute("openPlatformConfigList",openPlatformConfigList);
		model.addAttribute("cloudPrintTerminal", cloudPrintTerminal);
		return "modules/xiaodian/printer/cloudPrintTerminalForm";
	}

	@RequiresPermissions("xiaodian:printer:cloudPrintTerminal:edit")
	@RequestMapping(value = "save")
	public String save(CloudPrintTerminal cloudPrintTerminal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cloudPrintTerminal)){
			return form(cloudPrintTerminal, model);
		}
		cloudPrintTerminalService.save(cloudPrintTerminal);
		addMessage(redirectAttributes, "保存易联云成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/printer/cloudPrintTerminal/?repage";
	}
	
	@RequiresPermissions("xiaodian:printer:cloudPrintTerminal:edit")
	@RequestMapping(value = "delete")
	public String delete(CloudPrintTerminal cloudPrintTerminal, RedirectAttributes redirectAttributes) {
		cloudPrintTerminalService.delete(cloudPrintTerminal);
		addMessage(redirectAttributes, "删除易联云成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/printer/cloudPrintTerminal/?repage";
	}

	@RequiresPermissions("xiaodian:printer:cloudPrintTerminal:edit")
	@RequestMapping("bindApp")
	@ResponseBody
	public BaseResp bindApp(String id) {
		BaseResp resp = BaseResp.newError();
		try {
			CloudPrintTerminal cloudPrintTerminal = cloudPrintTerminalService.get(id);
			if (null == cloudPrintTerminal){
				resp.setMsg("打印终端不存在,请先保存");
				return resp;
			}
			String clientId = cloudPrintTerminal.getClientId();
			String machineCode = cloudPrintTerminal.getMachineCode();
			String machineSecret = cloudPrintTerminal.getSecret();
			if (StringUtils.isAnyEmpty(clientId,machineCode,machineSecret)){
				resp.setMsg("clientId,machineCode或secret不能为空");
				return resp;
			}

			resp =	yiCloudCommonService.addPrinter(clientId,machineCode,machineSecret,cloudPrintTerminal.getName());
		}catch (Exception e){
			e.printStackTrace();
			resp.setMsg("系统错误");
		}finally {
			return resp;
		}

	}

}