package com.haohan.platform.service.sys.modules.xiaodian.api.service.merchant.member;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.RespPage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.service.UserOpenPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商家 会员管理
 * Created by dy on 2018/10/6.
 */
@Service
@Transactional(readOnly = true)
public class MerchantMemberService {

    @Autowired
    @Lazy(true)
    private UserOpenPlatformService userOpenPlatformService;

    public RespPage<UserOpenPlatform> fetchMemberList(String merchantId, String shopId, int pageNo, int pageSize) {
        RespPage<UserOpenPlatform> respPage = new RespPage<>();

        UserOpenPlatform user = new UserOpenPlatform();
        Page<UserOpenPlatform> page = new Page<>(pageNo, pageSize);
        user.setPage(page);
        user.setMerchantId(merchantId);
        user.setShopId(shopId);
        List<UserOpenPlatform> list = userOpenPlatformService.findListByMerchantShop(user);
        if (Collections3.isEmpty(list)) {
            return respPage;
        }
        // 获取属性
        respPage.fetchFromPage(page);
        respPage.setList(list);
        return respPage;
    }
}
