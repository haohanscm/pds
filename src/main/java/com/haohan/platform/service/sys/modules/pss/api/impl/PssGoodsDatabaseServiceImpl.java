package com.haohan.platform.service.sys.modules.pss.api.impl;

import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssGoodsDatabaseService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 进销存 商品获取
 * Created by dy on 2018/10/16.
 */
@Service
@Transactional(readOnly = true)
public class PssGoodsDatabaseServiceImpl implements IPssGoodsDatabaseService {

    @Autowired
    @Lazy(true)
    private GoodsModelService goodsModelService;

    // 零售规格商品列表   分页查询
    public ApiRespPage<GoodsModel> goodsModelList(GoodsModel goodsModel, int pageNo, int pageSize) {
        Page<GoodsModel> page = new Page<>(pageNo, pageSize);
        goodsModel.setPage(page);
        page.setList(goodsModelService.findJoinList(goodsModel));
        ApiRespPage<GoodsModel> respPage = new ApiRespPage<>();
        if (Collections3.isEmpty(page.getList())) {
            return respPage;
        }
        // 获取属性
        respPage.fetchFromPage(page);
        return respPage;
    }


}
