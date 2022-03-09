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
 * 租户Id处理 管理后台 拦截器
 *
 * @author dy
 * @date 2019/9/16
 */
public class TenantApiInterceptor extends BaseService implements HandlerInterceptor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 设置tenantId
        HttpSession session = request.getSession();
        Integer tenantId = (Integer) session.getAttribute(IPdsConstant.TENANT_KEY);
        // header 中获取并设置
        if (null == tenantId) {
            String tenant = request.getHeader(IPdsConstant.TENANT_ID);
            if (StringUtils.isNotBlank(tenant)) {
                try {
                    session.setAttribute(IPdsConstant.TENANT_KEY, Integer.valueOf((tenant)));
                    return true;
                } catch (Exception e) {
                }
            }
        }
        // 请求参数中 获取并设置
        if (null == tenantId) {
            Map<String, Object> reqMap;
            if (StringUtils.equalsIgnoreCase(request.getContentType(), "application/json")) {
                // body中验证
                MyHttpServletRequestWrapper wrapper = new MyHttpServletRequestWrapper(request);
                String body = wrapper.getBody();
                logger.debug("--req---\n{}\n body:{}\n", "request中pmId", body);
                reqMap = JacksonUtils.readMapValue(body, Object.class);
            } else {
                // request 中获取pmId
                reqMap = CommonUtils.getRequestParameters(request);
            }
            try {
                String pmId = reqMap.get(IPdsConstant.PM_KEY).toString();
                tenantId = Integer.valueOf(JedisUtils.getMap(IPdsConstant.TENANT_MAP_KEY).get(pmId));
            } catch (Exception e) {
            }
            if (null != tenantId) {
                session.setAttribute(IPdsConstant.TENANT_KEY, tenantId);
            }
        } else {
            logger.debug("--req---\n{}\n tenantId:{}\n", "session中tenantId", tenantId);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
