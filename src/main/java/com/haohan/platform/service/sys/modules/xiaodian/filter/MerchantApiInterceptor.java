package com.haohan.platform.service.sys.modules.xiaodian.filter;

import com.haohan.platform.service.sys.common.service.BaseService;
import com.haohan.platform.service.sys.modules.xiaodian.service.UPassportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zgw on 2018/9/26.
 */
@Deprecated
public class MerchantApiInterceptor extends BaseService implements HandlerInterceptor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    @Lazy(true)
    private UPassportService uPassportService;

    static int expireTime = 7200;//2小时不过期

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 拦截 path="${frontPath}/xiaodian/api/merchant/**"
//        BaseResp resp = BaseResp.newError();
//        String id = "";
//        try {
//            // 登录请求/获取验证码  不拦截
//            String requestURI = request.getRequestURI();
//            if (requestURI.contains("merchant/auth/login") || requestURI.contains("merchant/fetchValidCode")) {
//                return true;
//            }
//
//            Object principal = UserUtils.getPrincipal();
//            Session session = UserUtils.getSession();
//            id = (String) session.getId();
//            if (null == principal) {
//                // 利用shiro的session验证
//                principal = JedisUtils.getObject(id);
//            }
//            String key = "merchantAuth-" + id;
//            Object obj = JedisUtils.getObject(key);
//            if (null == obj && null == principal) {
//                Map<String, Object> reqMap;
//                if (StringUtils.equalsIgnoreCase(request.getContentType(), "application/json")) {
//                    // body中验证
//                    MyHttpServletRequestWrapper wrapper = new MyHttpServletRequestWrapper(request);
//                    String body = wrapper.getBody();
//                    reqMap = JacksonUtils.readMapValue(body, Object.class);
//                } else {
//                    // request 中获取token 验证
//                    reqMap = CommonUtils.getRequestParameters(request);
//                }
//                Object s = reqMap.get(IPdsConstant.TOKEN_KEY.toString());
//                id = StringUtils.toString(s, "");
//                if (StringUtils.isNotEmpty(id)) {
//                    obj = JedisUtils.getObject("merchantAuth-" + id);
//                    logger.debug("--resp---\n{}\n session:{}\n", "request中token验证", id);
//                }
//            }
//            if (null != obj || null != principal) {
//                MerchantAuth merchantAuth;
//                if (null != obj) {
//                    merchantAuth = (MerchantAuth) obj;
//                } else {
//                    // platform登录
//                    merchantAuth = new MerchantAuth();
//                    merchantAuth.setPrincipal((SystemAuthorizingRealm.Principal) principal);
//                }
//                String merchantId = merchantAuth.getMerchantId();
//                // 绑定商家id
//                if (null == merchantId) {
//                    User user = UserUtils.getUser();
//                    UPassport uPassport = null;
//                    if (null != user && StringUtils.isNotEmpty(user.getMobile())) {
//                        uPassport = uPassportService.fetchByMobile(user.getMobile());
//                    }
//                    if (null != uPassport) {
//                        merchantId = uPassport.getMerchantId();
//                    }
//                    if (StringUtils.isEmpty(merchantId)) {
//                        // TODO 绑定默认的商家id
//                        merchantId = "";
//                    }
//                    merchantAuth.setMerchantId(merchantId);
//                    JedisUtils.setObject(key, merchantAuth, expireTime);
//                    JedisUtils.setObject(id, merchantAuth.getPrincipal(), expireTime);
//                    session.setTimeout(expireTime * 1000);
//                }
//                // 更新时间
//                Long time = session.getTimeout();
//                if (null != time && time < expireTime * 1000 / 2) {
//                    JedisUtils.setObject(key, merchantAuth, expireTime);
//                    JedisUtils.setObject(id, merchantAuth.getPrincipal(), expireTime);
//                    session.setTimeout(expireTime * 1000);
//                }
//                request.setAttribute("merchantAuth", merchantAuth);
//                return true;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        resp.setCode(999);
//        resp.setMsg("账号未登录");
//        sendJsonMessage(response, resp);
//        logger.debug("--resp---\nsession:{}  当前用户未登录\n", id);
//        return false;
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
