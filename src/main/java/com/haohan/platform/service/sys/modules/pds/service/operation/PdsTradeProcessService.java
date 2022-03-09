package com.haohan.platform.service.sys.modules.pds.service.operation;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.dao.operation.PdsTradeProcessDao;
import com.haohan.platform.service.sys.modules.pds.entity.operation.PdsTradeProcess;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 平台交易流程管理Service
 * @author dy
 * @version 2018-10-26
 */
@Service
@Transactional(readOnly = true)
public class PdsTradeProcessService extends CrudService<PdsTradeProcessDao, PdsTradeProcess> {

	public PdsTradeProcess get(String id) {
		return super.get(id);
	}
	
	public List<PdsTradeProcess> findList(PdsTradeProcess pdsTradeProcess) {
		return super.findList(pdsTradeProcess);
	}
	
	public Page<PdsTradeProcess> findPage(Page<PdsTradeProcess> page, PdsTradeProcess pdsTradeProcess) {
		return super.findPage(page, pdsTradeProcess);
	}
	
	@Transactional(readOnly = false)
	public void save(PdsTradeProcess pdsTradeProcess) {
        // 创建 交易流程编号
        if (StringUtils.isEmpty(pdsTradeProcess.getProcessSn()) && StringUtils.isNotEmpty(pdsTradeProcess.getBuySeq()) && null != pdsTradeProcess.getDeliveryTime()) {
            pdsTradeProcess.setProcessSn(PdsCommonUtil.createProcessSn(pdsTradeProcess.getDeliveryTime(), pdsTradeProcess.getBuySeq()));
        }
		super.save(pdsTradeProcess);
	}
	
	@Transactional(readOnly = false)
	public void delete(PdsTradeProcess pdsTradeProcess) {
		super.delete(pdsTradeProcess);
	}

	// 根据批次及送货时间查询 没有时返回null
    public PdsTradeProcess fetchProcess(String buySeq, Date deliveryTime){
	    PdsTradeProcess pdsTradeProcess = new PdsTradeProcess();
	    pdsTradeProcess.setBuySeq(buySeq);
	    pdsTradeProcess.setDeliveryTime(deliveryTime);
	    List<PdsTradeProcess> list = super.findList(pdsTradeProcess);
	    if(Collections3.isEmpty(list)){
	        pdsTradeProcess = null;
        }else{
	        pdsTradeProcess = list.get(0);
        }
	    return pdsTradeProcess;
    }

    // 根据采购编号查询  找不到时返回null
	public PdsTradeProcess fetchProcessByBuyId(String buyId){
		PdsTradeProcess pdsTradeProcess = new PdsTradeProcess();
		pdsTradeProcess.setBuyId(buyId);
		List<PdsTradeProcess> list = super.findList(pdsTradeProcess);
		if(Collections3.isEmpty(list)){
			pdsTradeProcess = null;
		}else{
			pdsTradeProcess = list.get(0);
		}
		return pdsTradeProcess;
	}

	
}