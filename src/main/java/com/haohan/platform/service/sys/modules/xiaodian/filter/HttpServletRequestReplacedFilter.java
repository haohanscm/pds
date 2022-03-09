package com.haohan.platform.service.sys.modules.xiaodian.filter;


import com.haohan.platform.service.sys.common.utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 创建一个实现Filter的类，重写doFilter方法，将ServletRequest替换为自定义的request类
 *
 * @author dy
 * @version 2018/12/15
 */
public class HttpServletRequestReplacedFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            // 只过滤json请求
            if (StringUtils.equalsIgnoreCase(request.getContentType(), "application/json")) {
                requestWrapper = new MyHttpServletRequestWrapper((HttpServletRequest) request);
            }
        }
        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }

    }

    @Override
    public void destroy() {

    }
}
