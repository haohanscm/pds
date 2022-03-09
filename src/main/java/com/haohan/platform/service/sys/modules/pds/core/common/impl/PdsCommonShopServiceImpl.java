package com.haohan.platform.service.sys.modules.pds.core.common.impl;

import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.dto.ShopDTO;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.common.IPdsCommonShopService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IShopConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dy
 * @date 2019/9/7
 */
@Service
public class PdsCommonShopServiceImpl implements IPdsCommonShopService {

    @Autowired
    private ShopService shopService;
    @Autowired
    private PhotoManageService photoManageService;


    @Override
    public ShopDTO fetchPmShop(String pmId) {
        ShopDTO result = null;
        // 返回平台商家的采购配送店铺
        Shop shop = new Shop();
        if (StringUtils.isBlank(pmId)) {
            // 租户1 的采购配送店铺
            Map<String, String> map = JedisUtils.getMap(IPdsConstant.TENANT_MAP_KEY);
            for (Map.Entry<String, String> e : map.entrySet()) {
                if (StringUtils.equals(e.getValue(), "1")) {
                    pmId = e.getKey();
                    break;
                }
            }
        }
        shop.setMerchantId(pmId);
        shop.setShopLevel(IShopConstant.ShopLevelType.pds.getCode());
        List<Shop> shopList = shopService.fetchByMerchantIdEnable(shop);
        if (!Collections3.isEmpty(shopList)) {
            result = new ShopDTO();
            shop = shopList.get(0);
            result.setPmId(pmId);
            result.setShopId(shop.getId());
            result.setShopName(shop.getName());

            if (StringUtils.isNotBlank(shop.getShopLogo())) {
                List<PhotoManage> logos = photoManageService.fetchByGroupNum(shop.getShopLogo());
                if (!Collections3.isEmpty(logos)) {
                    result.setShopLogo(logos.get(0).getPicUrl());
                }
            }
            if (StringUtils.isNotBlank(shop.getPhotoGroupNum())) {
                List<PhotoManage> manageList = photoManageService.fetchByGroupNum(shop.getPhotoGroupNum());
                if (!Collections3.isEmpty(manageList)) {
                    List<String> photoList = new ArrayList<>();
                    for (PhotoManage photoManage : manageList) {
                        photoList.add(photoManage.getPicUrl());
                    }
                    result.setPhotos(photoList);
                }
            }
        }
        return result;
    }

}
