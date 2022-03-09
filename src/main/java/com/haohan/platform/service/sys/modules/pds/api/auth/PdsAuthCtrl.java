package com.haohan.platform.service.sys.modules.pds.api.auth;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.CookieUtils;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.utils.wx.MD5Util;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import com.haohan.platform.service.sys.modules.sys.security.SystemAuthorizingRealm;
import com.haohan.platform.service.sys.modules.sys.utils.UserUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IShopConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.MerchantEmployeeService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * 管理后台 权限
 * Created by dy on 2018/11/13.
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/auth")
public class PdsAuthCtrl extends BaseController implements IPdsConstant {

    @Autowired
    private MerchantEmployeeService merchantEmployeeService;
    @Autowired
    @Lazy(true)
    private ShopService shopService;

    /**
     * 管理后台 登录
     * 使用员工表的 角色名与电话登录
     *
     * @return
     */
    @RequestMapping(value = "login")
    @ResponseBody
    public BaseResp login(String userName, String password, HttpServletRequest request, HttpServletResponse response) {
        BaseResp baseResp = BaseResp.newError();
        baseResp.setMsg("登录失败");
        //验证参数
        if (StringUtils.isAnyEmpty(userName, password)) {
            baseResp.setMsg("缺少参数userName/password");
            return baseResp;
        }
        // 查找 通行证
        MerchantEmployee merchantEmployee = new MerchantEmployee();
        merchantEmployee.setName(userName);
        merchantEmployee.setRole(EmployeeRole.operator.getCode());
        List<MerchantEmployee> uPassportList = merchantEmployeeService.fetchPassword(merchantEmployee);

        // 验证
        if (!Collections3.isEmpty(uPassportList)) {
            merchantEmployee = uPassportList.get(0);
            // 验证密码
            String dbPassword = merchantEmployee.getPassword();
            if (!StringUtils.equalsAny(dbPassword, password, MD5Util.MD5Encode(password, "UTF-8"))) {
                return baseResp;
            }
            Session session = UserUtils.getSession();
            String id = (String) session.getId();
            Object obj = JedisUtils.getObject(id);
            if (null == obj) {
                // TODO 获取用户
                User user = new User();
                user.setName(merchantEmployee.getName());
                SystemAuthorizingRealm.Principal principal = new SystemAuthorizingRealm.Principal(user, false);
                obj = principal;
            }
            session.setTimeout(EXPIRE_TIME * 1000);
            CookieUtils.setCookie(response, "userName", merchantEmployee.getName(), EXPIRE_TIME);
            CookieUtils.setCookie(response, TOKEN_KEY, id, EXPIRE_TIME);
            JedisUtils.setObject(id, obj, EXPIRE_TIME);
            baseResp.success();
            // 返回信息 平台商家 pmId 平台商家店铺 shopId
            HashMap<String, Object> result = new HashMap<>(8);
            result.put("pmId", merchantEmployee.getPmId());
            result.put("pmName", merchantEmployee.getPmName());
            if (IOrderServiceConstant.selfPdsPmId.equals(merchantEmployee.getPmId())) {
                result.put("isSelfPm", true);
            } else {
                result.put("isSelfPm", false);
            }
            // 平台商家的店铺 用于采购配送系统
            Shop shop = new Shop();
            shop.setMerchantId(merchantEmployee.getPmId());
            shop.setShopLevel(IShopConstant.ShopLevelType.pds.getCode());
            List<Shop> shopList = shopService.fetchByMerchantIdEnable(shop);
            if (!Collections3.isEmpty(shopList)) {
                result.put("shopId", shopList.get(0).getId());
            }
            baseResp.setExt(result);
            return baseResp;
        }
        return baseResp;
    }

}