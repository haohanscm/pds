package com.haohan.platform.service.sys.common.persistence;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zgw on 2018/10/21.
 */
public class RespPage<T> extends Page<T> {


    public RespPage(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public RespPage(int pageNo, int pageSize) {
        super(pageNo, pageSize);
    }

    @Override
    public String toString() {
        return "";
    }
}
