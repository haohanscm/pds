package com.haohan.platform.service.sys.modules.xiaodian.api.service.merchant.goods;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * Created by dy on 2018/9/27.
 */
@Service
@Transactional(readOnly = true)
public class MerchantIndexService {

    @Autowired
    private GoodsService goodsService;

    public Page<Goods> fetchGoodsList(Goods goods, int pageNo, int pageSize){

        return goodsService.findPage(new Page<>(pageNo, pageSize), goods);
    }


    public BaseResp goodsBaseModify(Goods goods) {
        return BaseResp.newError();
    }

    public BaseResp goodsExtModify(Goods goods) {
        return BaseResp.newError();
    }

    // 批量删除
    public void goodsDelete(Goods goods) {

    }

    // 首页数据
    public HashMap<String, Object> fetchDashboardInfo(String merchantId) {
        return null;
    }


}
