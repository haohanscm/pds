package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl.merchant.common;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.sys.entity.Area;
import com.haohan.platform.service.sys.modules.sys.service.AreaService;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.MerchantAuth;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2018/9/26
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/merchant/v2.0/common/area")
public class MerchantAddressCtrl extends BaseController {
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "fetchAddress")
    @ResponseBody
    public BaseResp fetchAddress(HttpServletRequest request, HttpServletResponse response){

        //测试用token
//        CookieUtils.setCookie(response,"haohanshop-token","123456");

        BaseResp baseResp = BaseResp.newError();
        String parentId = "";

        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        Map<String,Object> reqMap = getRequestParameters(request);
        if (null == reqMap){
            reqMap = new HashMap<>();
        }
        String provinceCode = (String) reqMap.get("provinceCode");
        String cityCode = (String) reqMap.get("cityCode");

        if (StringUtils.isNotEmpty(cityCode)){
            parentId = areaService.fetchByCode(cityCode).getId();
        }else if (StringUtils.isNotEmpty(provinceCode)){
            parentId = areaService.fetchByCode(provinceCode).getId();
        }else {
            parentId = "1";
        }

        List<Area> areaList = areaService.findChildren(parentId);
        ArrayList<Area> list = areaList.stream().collect(Collectors.toCollection(ArrayList::new));
        if (CollectionUtils.isEmpty(list)){
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.success();
        baseResp.setExt(list);
        return baseResp;
    }
}
