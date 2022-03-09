package com.haohan.platform.service.sys.modules.xiaodian.web.office;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.mapper.JsonMapper;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.sys.entity.Dict;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.xiaodian.entity.office.CompanyPerformance;
import com.haohan.platform.service.sys.modules.xiaodian.service.office.CompanyPerformanceService;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 绩效考核Controller
 * @author haohan
 * @version 2018-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/office/companyPerformance")
public class CompanyPerformanceController extends BaseController {

	@Autowired
	private CompanyPerformanceService companyPerformanceService;
	
	@ModelAttribute
	public CompanyPerformance get(@RequestParam(required=false) String id) {
		CompanyPerformance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyPerformanceService.get(id);
		}
		if (entity == null){
			entity = new CompanyPerformance();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:office:companyPerformance:view")
	@RequestMapping(value = {"list", ""})
	public String list(CompanyPerformance companyPerformance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CompanyPerformance> page = companyPerformanceService.findPage(new Page<CompanyPerformance>(request, response), companyPerformance); 
		model.addAttribute("page", page);
		return "modules/xiaodian/office/companyPerformanceList";
	}

	@RequiresPermissions("xiaodian:office:companyPerformance:view")
	@RequestMapping(value = "form")
	public String form(CompanyPerformance companyPerformance, Model model) {
        // 有考核信息时 添加 考核信息列表
        String info = companyPerformance.getEvaluateInfo();
        if(StringUtils.isNotEmpty(info) && !StringUtils.equals(info,"{}")){
            Map<String,String> infoMap = (Map<String,String>)JsonMapper.fromJsonString(info,LinkedHashMap.class);
            companyPerformance.setEvaluateItems(infoMap);
        }

		model.addAttribute("companyPerformance", companyPerformance);

		return "modules/xiaodian/office/companyPerformanceForm";
	}

	@RequiresPermissions("xiaodian:office:companyPerformance:edit")
	@RequestMapping(value = "save")
	public String save(CompanyPerformance companyPerformance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, companyPerformance)){
			return form(companyPerformance, model);
		}
		companyPerformanceService.save(companyPerformance);
		addMessage(redirectAttributes, "保存绩效考核管理成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/office/companyPerformance/?repage";
	}
	
	@RequiresPermissions("xiaodian:office:companyPerformance:edit")
	@RequestMapping(value = "delete")
	public String delete(CompanyPerformance companyPerformance, RedirectAttributes redirectAttributes) {
		companyPerformanceService.delete(companyPerformance);
		addMessage(redirectAttributes, "删除绩效考核管理成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/office/companyPerformance/?repage";
	}

    @RequiresPermissions("xiaodian:office:companyPerformance:view")
    @RequestMapping(value = "add")
    public String add(CompanyPerformance companyPerformance, Model model) {
	    CompanyPerformance newInfo = new CompanyPerformance();

        newInfo.setOffice(companyPerformance.getOffice());
        newInfo.setPosition(companyPerformance.getPosition());
        newInfo.setUser(companyPerformance.getUser());
        // 根据岗位获取考核项的名称列表
        newInfo.setEvaluateItems(fetchEvaluateItems(newInfo.getPosition()));

        model.addAttribute("companyPerformance", newInfo);
        return "modules/xiaodian/office/companyPerformanceForm";
    }

    // 根据岗位获取考核项的名称列表  备注信息中根据,分隔
    private static Map<String,String> fetchEvaluateItems(String position){
        Map<String,String> result = new LinkedHashMap<String,String>();
        List<Dict> list = DictUtils.getDictList("job_list");
        String remarks = null;
        for (Dict d:list){
            if(d.getValue().equals(position)){
                remarks = d.getRemarks();
                break;
            }
        }
        if(remarks!=null){
            String[] array = StringUtils.split(remarks,",");
            for(String a:array){
                result.put(a,"");
            }
        }else{
            result.put("请通知管理员","该岗位还未添加考核项目");
        }
        return result;
    }


}