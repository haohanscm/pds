package com.haohan.platform.service.sys.modules.xiaodian.filter;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.service.BaseService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.util.ManagePlatformCacheKeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 合作商管理 请求拦截处理类
 */
@Transactional(readOnly = false)
public class ManagePlatformApiInterceptor extends BaseService implements HandlerInterceptor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final ThreadLocal<Long> startTimeThreadLocal =
            new NamedThreadLocal<Long>("ThreadLocal StartTime");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 跨域设置
//        String originHeader = request.getHeader("Origin");
//        String[] IPs = {"http://admin.haohanshop.com"};
//        if (Arrays.asList(IPs).contains(originHeader))
//        {
//            response.setHeader("Access-Control-Allow-Origin", originHeader);
//        }

        // 登录请求不拦截
        String requestURI = request.getRequestURI();
        if (requestURI.contains("manage/user/login")) {
            return true;
        }

        // 登录验证
        String userId = request.getParameter("userId");
        String accessToken = request.getParameter("token");
        // 从缓存获取用户对应的token 验证
        if (StringUtils.isNoneEmpty(userId, accessToken)) {
            Object[] cache = ManagePlatformCacheKeyUtils.fetchUserInfo(userId);
            String token = (String) cache[0];
            String userType = (String) cache[1];
            Date date = (Date) cache[2];
            if (ManagePlatformCacheKeyUtils.isUsable(date) && StringUtils.equals(token, accessToken)) {
                // token有效时间重计
                if (new Date().getTime() - date.getTime() > ManagePlatformCacheKeyUtils.DEFAULT_FLUSH_SECOND) {
                    ManagePlatformCacheKeyUtils.flushTokenTime(userId);
                }
                request.setAttribute("userType", userType);
                return true;
            }
        }
        BaseResp resp = new BaseResp();
        resp.setCode(999);
        resp.setMsg("当前用户未登录");
        sendJsonMessage(response, resp);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            logger.info("ViewName: " + modelAndView.getViewName());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

        // 打印接口信息。
//        if (logger.isDebugEnabled()){
//            long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
//            long endTime = System.currentTimeMillis(); 	//2、结束时间
//            logger.debug("计时结束：{}  耗时：{}  URI: {}  ",
//                    new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), (endTime - beginTime)+"ms", request.getRequestURI());
//            //删除线程变量中的数据，防止内存泄漏
//            startTimeThreadLocal.remove();
//        }

    }



}