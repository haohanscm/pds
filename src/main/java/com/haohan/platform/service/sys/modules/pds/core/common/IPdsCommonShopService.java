package com.haohan.platform.service.sys.modules.pds.core.common;

import com.haohan.platform.service.sys.modules.pds.api.entity.dto.ShopDTO;

/**
 * @author dy
 * @date 2019/9/7
 */
public interface IPdsCommonShopService {


    /**
     * 获取平台商家店铺信息
     *
     * @param pmId
     * @return
     */
    ShopDTO fetchPmShop(String pmId);


}
