package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.IpUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.BusinessNote;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantDatabase;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.BusinessNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgw on 2018/2/23.
 * 公共API接口
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/common")
public class CommonApiCtrl extends BaseController{
    @Autowired
    private BusinessNoteService businessNoteService;


    @RequestMapping(value = "businessNote")
    @ResponseBody
    public String businessNote(BusinessNote businessNote, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();

        try {
            if(StringUtils.isAnyEmpty(businessNote.getUserName(),businessNote.getMessage())){
                resp.setMsg("用户名称或留言信息为空");
                return resp.toJson();
            }
            businessNote.setRemarks(IpUtils.getRemoteIpAddr(request));
            businessNote.setCreateDate(new Date());
            businessNoteService.save(businessNote);
            return BaseResp.newSuccess().toJson();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return resp.toJson();
        }

    }


    // 商家合作申请
    @RequestMapping(value = "merchantApply")
    @ResponseBody
    public String merchantApply(MerchantDatabase merchantDatabase) {

        BaseResp baseResp = new BaseResp();
        try {
            // 输入合法性 校验
            String merchantName = merchantDatabase.getRegName();
            String address = merchantDatabase.getShopAddress();
            String telephone = merchantDatabase.getTelephone();
            String contact = merchantDatabase.getContact();
            // 必填参数项
            Map<String, Object> requiredParams = new HashMap<String, Object>();
            requiredParams.put("merchantName",merchantName);
            requiredParams.put("shopAddress",address);
            requiredParams.put("telephone",telephone);
            requiredParams.put("contact",contact);
            //错误处理  是否存在必填参数项
            baseResp = paramsValid(requiredParams);
            if (!baseResp.isSuccess()) {
                return baseResp.toJson();
            }
            baseResp.putStatus(RespStatus.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.putStatus(RespStatus.ERROR);
        } finally {
            return baseResp.toJson();
        }
    }



}
