package com.haohan.platform.service.sys.modules.pds.web.business;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.business.MessageManage;
import com.haohan.platform.service.sys.modules.pds.service.business.MessageManageService;
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

/**
 * 消息管理Controller
 *
 * @author haohan
 * @version 2018-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/business/messageManage")
public class MessageManageController extends BaseController {

    @Autowired
    private MessageManageService messageManageService;

    @ModelAttribute
    public MessageManage get(@RequestParam(required = false) String id) {
        MessageManage entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = messageManageService.get(id);
        }
        if (entity == null) {
            entity = new MessageManage();
        }
        return entity;
    }

    @RequiresPermissions("pds:business:messageManage:view")
    @RequestMapping(value = {"list", ""})
    public String list(MessageManage messageManage, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<MessageManage> page = messageManageService.findPage(new Page<MessageManage>(request, response), messageManage);
        model.addAttribute("page", page);
        return "modules/pds/business/messageManageList";
    }

    @RequiresPermissions("pds:business:messageManage:view")
    @RequestMapping(value = "form")
    public String form(MessageManage messageManage, Model model) {
        model.addAttribute("messageManage", messageManage);
        return "modules/pds/business/messageManageForm";
    }

    @RequiresPermissions("pds:business:messageManage:edit")
    @RequestMapping(value = "save")
    public String save(MessageManage messageManage, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, messageManage)) {
            return form(messageManage, model);
        }
        messageManageService.save(messageManage);
        addMessage(redirectAttributes, "保存消息成功");
        return "redirect:" + Global.getAdminPath() + "/pds/business/messageManage/?repage";
    }

    @RequiresPermissions("pds:business:messageManage:edit")
    @RequestMapping(value = "delete")
    public String delete(MessageManage messageManage, RedirectAttributes redirectAttributes) {
        messageManageService.delete(messageManage);
        addMessage(redirectAttributes, "删除消息成功");
        return "redirect:" + Global.getAdminPath() + "/pds/business/messageManage/?repage";
    }

}