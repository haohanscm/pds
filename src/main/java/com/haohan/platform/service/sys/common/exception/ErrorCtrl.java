package com.haohan.platform.service.sys.common.exception;

import com.haohan.framework.entity.BaseResp;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgw on 2018/9/26.
 */
@RestController
@ControllerAdvice
public class ErrorCtrl {

    @ExceptionHandler(ApiException.class)
    public Map<String, Object> apiExceptionHandler(ApiException ex) {
        Map<String, Object> res = new HashMap<>();
        ex.printStackTrace();
        res.put("code", ex.getCode());
        res.put("msg", ex.getMsg());
        return res;
    }

    @ExceptionHandler(Exception.class)
    public Map<String, Object> ExceptionHandler(Exception ex) {
        BaseResp baseResp = BaseResp.newError();
        Map<String, Object> res = new HashMap<>();
        ex.printStackTrace();
        res.put("code", baseResp.getCode());
        res.put("msg", baseResp.getMsg());
        return res;
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Map<String, Object> authExceptionHandler(UnauthorizedException ex) {
        Map<String, Object> res = new HashMap<>();
        ex.printStackTrace();
        res.put("code", 900);
        res.put("msg", "没有操作权限");
        return res;
    }


}
