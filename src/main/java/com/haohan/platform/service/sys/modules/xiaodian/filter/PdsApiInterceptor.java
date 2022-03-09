package com.haohan.platform.service.sys.modules.xiaodian.filter;


import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.service.BaseService;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 采购配送系统 管理后台 拦截器
 * Created by dy on 2018/11/13.
 */
public class PdsApiInterceptor extends BaseService implements HandlerInterceptor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
        // 拦截 path="${frontPath}/pds/api/**"
//        BaseResp resp = BaseResp.newError();
//        String id = "";
//
//        try {
//            // 登录请求不拦截
//            String requestURI = request.getRequestURI();
//            if (requestURI.contains("pds/api/auth/login") || requestURI.contains("pds/api/common/uidCheck") || requestURI.contains("pds/api/common/bindTel")) {
//                return true;
//            }
//
//            Object principal = UserUtils.getPrincipal();
//            Session session = UserUtils.getSession();
//            id = (String) session.getId();
//            if (null == principal) {
//                // 利用shiro的session验证
//                principal = JedisUtils.getObject(id);
//                if(null == principal){
//                    id = CookieUtils.getCookie(request, IPdsConstant.TOKEN_KEY);
//                    principal = JedisUtils.getObject(id);
//                }
//            }
//            if (null == principal) {
//                Map<String, Object> reqMap;
//                if (StringUtils.equalsIgnoreCase(request.getContentType(), "application/json")) {
//                    // body中验证
//                    MyHttpServletRequestWrapper wrapper = new MyHttpServletRequestWrapper(request);
//                    String body = wrapper.getBody();
//                    logger.debug("--req---\n{}\n body:{}\n", "request中token验证", body);
//                    reqMap = JacksonUtils.readMapValue(body, Object.class);
//                } else {
//                    // request 中获取token 验证
//                    reqMap = CommonUtils.getRequestParameters(request);
//                }
//                Object s = reqMap.get(IPdsConstant.TOKEN_KEY.toString());
//                id = StringUtils.toString(s, "");
//                if (StringUtils.isNotEmpty(id)) {
//                    principal = JedisUtils.getObject(id);
//                    logger.debug("--resp---\n{}\n session:{}\n", "request中token验证", id);
//                }
//            }
//            if (null != principal) {
//                // 更新token时间
//                Long time = JedisUtils.ttl(id);
//                if (null != time && time < IPdsConstant.EXPIRE_TIME / 2) {
//                    JedisUtils.setObject(id, principal, IPdsConstant.EXPIRE_TIME);
//                    session.setTimeout(IPdsConstant.EXPIRE_TIME * 1000);
//                }
//                return true;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        resp.setCode(999);
//        resp.setMsg("当前用户未登录");
//        sendJsonMessage(response, resp);
//        logger.debug("--resp---\nsession:{}  当前用户未登录\n", id);
//        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
