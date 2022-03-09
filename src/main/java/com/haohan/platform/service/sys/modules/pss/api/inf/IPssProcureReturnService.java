package com.haohan.platform.service.sys.modules.pss.api.inf;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssProcureReturnApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssProcureReturnDetailApiReq;
import com.haohan.platform.service.sys.modules.pss.entity.procure.PurchaseReturn;
import com.haohan.platform.service.sys.modules.xiaodian.exception.DataNotFoundException;
import com.haohan.platform.service.sys.modules.xiaodian.exception.GoodsStockNotEnoughException;
import org.springframework.stereotype.Service;

/**
 * @author shenyu
 * @create 2018/11/27
 */
@Service
public interface IPssProcureReturnService {
    //采购退货列表(分页)
    BaseResp procureReturnPage(PssProcureReturnApiReq returnApiReq, Page reqPage) throws Exception;

    //保存退货单
    BaseResp saveReturn(PurchaseReturn purchaseReturn) throws DataNotFoundException;

//    //新增退货
//    BaseResp addProcureReturn(MerchantAuth merchantAuth, PurchaseReturn purchaseReturn);

//    //保存退货明细
//    BaseResp saveDetails(MerchantAuth merchantAuth, List<PurchaseReturnDetail> detailList, String returnId);

    //删除退货单
    BaseResp deleteProcureReturn(String id);

    //删除退货明细
    BaseResp delDetail(String detailId);

    //退货清单
    BaseResp procureReturnDetails(PssProcureReturnDetailApiReq detailApiReq,Page page);

    //确定退货
    BaseResp confirm(String returnId) throws GoodsStockNotEnoughException;


}
