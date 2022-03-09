package com.haohan.platform.service.sys.modules.xiaodian.api.inf;

import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.PayNotify;

import java.util.Map;

/**
 * Created by zgw on 2017/12/10.
 */
public interface IBankApiService {


   PayNotify bankPayNotify(Map<String,String> reqMap);


}
