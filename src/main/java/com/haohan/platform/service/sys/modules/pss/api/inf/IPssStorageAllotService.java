package com.haohan.platform.service.sys.modules.pss.api.inf;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssGoodsAllotApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssGoodsAllotDetailApiReq;
import com.haohan.platform.service.sys.modules.pss.entity.storage.GoodsAllot;
import com.haohan.platform.service.sys.modules.pss.entity.storage.GoodsAllotDetail;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 库存调拨
 * @author shenyu
 * @create 2018/11/27
 */
@Service
public interface IPssStorageAllotService {
    //调拨记录列表(分页)
    BaseResp findAllotPage(PssGoodsAllotApiReq allotApiReq, Page reqPage) throws Exception;

    //调拨明细列表(分页)
    BaseResp findAllotDetailPage(PssGoodsAllotDetailApiReq allotDetailApiReq,Page reqPage) throws Exception;

    //新增调拨
    BaseResp saveAllot(String merchantId,GoodsAllot goodsAllot);

    //新增调拨明细
    BaseResp saveAllotDetail(String merchantId, List<GoodsAllotDetail> goodsAllotDetailList,String allotId);

    //删除明细
    BaseResp delDetail(String detailId);

    //确认调拨
    BaseResp confirm(String allotId) throws Exception;
}
