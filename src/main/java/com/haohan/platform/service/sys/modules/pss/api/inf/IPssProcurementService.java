package com.haohan.platform.service.sys.modules.pss.api.inf;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.ApiProcurementReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssProcurementApiReq;
import com.haohan.platform.service.sys.modules.pss.entity.procure.Procurement;
import com.haohan.platform.service.sys.modules.pss.entity.procure.ProcurementDetail;
import com.haohan.platform.service.sys.modules.xiaodian.exception.StorageOperationException;

import java.util.List;

/**
 * @author shenyu
 * @create 2018/11/16
 */
public interface IPssProcurementService {
    //新增或保存采购
    BaseResp saveProcurement(Procurement procurement) throws StorageOperationException;

//    //修改采购单
//    BaseResp editProcurement(Procurement procurement) throws Exception;
//
//    //修改采购单明细
//    BaseResp editProcurementDetail(List<ProcurementDetail> procurementDetailList,String merchantId,String procureNum) throws Exception;

    //删除采购单
    BaseResp delProcurement(String merchantId,String id);

    //删除采购明细
    BaseResp delDetail(String detailId);

    //采购列表
    BaseResp listProcurement(PssProcurementApiReq procurementApiReq,Page page);

    //采购明细列表
    BaseResp listProcureDetails(Page page, String procureNum);

    //采购单入库
    BaseResp enterStock(String procureId);

    // 根据采购单 创建报价单
    BaseResp createOfferOrder(Procurement procurement);

}
