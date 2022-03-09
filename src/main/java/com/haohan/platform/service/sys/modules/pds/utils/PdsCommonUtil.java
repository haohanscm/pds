package com.haohan.platform.service.sys.modules.pds.utils;

import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zgw on 2018/10/20.
 */
public class PdsCommonUtil implements IPdsConstant, Serializable {


//    public static String incrIdByClass(Class t) {
//        String name = t.getSimpleName();
//        String prefix = name.substring(0, 1).toUpperCase();
//        return prefix + JedisUtils.fetchIncrNum(name, IdStartIndex).toString();
//    }

    /**
     * 使用前缀生成编号
     * key: 前缀 + 实体类类名 + ":"  + 租户id
     *
     * @param t
     * @param prefix
     * @return
     */
    public static String incrIdByClass(Class t, String prefix) {
        // tenant 设置
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        Integer tenantId = (Integer) session.getAttribute(TENANT_KEY);
        String name = SN_PREFIX + t.getSimpleName() + ":" + tenantId;
        return prefix + tenantId + JedisUtils.fetchIncrNum(name, IdStartIndex).toString();
    }

    // 创建 交易流程编号
    public static String createProcessSn(Date deliveryTime, String buySeq) {
        String date = DateUtils.formatDate(deliveryTime, "yyyyMMdd");
        return PROCESS_PREFIX + date + PROCESS_INFIX + buySeq;
    }

}
