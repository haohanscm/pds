package com.haohan.platform.service.sys.modules.pds.service.business;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.dao.business.PdsPlatformGoodsPriceDao;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsPlatformGoodsPrice;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 平台商品定价管理Service
 *
 * @author haohan
 * @version 2018-12-08
 */
@Service
@Transactional(readOnly = true)
public class PdsPlatformGoodsPriceService extends CrudService<PdsPlatformGoodsPriceDao, PdsPlatformGoodsPrice> {

    public PdsPlatformGoodsPrice get(String id) {
        return super.get(id);
    }

    public List<PdsPlatformGoodsPrice> findList(PdsPlatformGoodsPrice pdsPlatformGoodsPrice) {
        return super.findList(pdsPlatformGoodsPrice);
    }

    public Page<PdsPlatformGoodsPrice> findPage(Page<PdsPlatformGoodsPrice> page, PdsPlatformGoodsPrice pdsPlatformGoodsPrice) {
        return super.findPage(page, pdsPlatformGoodsPrice);
    }

    @Transactional(readOnly = false)
    public void save(PdsPlatformGoodsPrice pdsPlatformGoodsPrice) {
        super.save(pdsPlatformGoodsPrice);
    }

    @Transactional(readOnly = false)
    public void delete(PdsPlatformGoodsPrice pdsPlatformGoodsPrice) {
        super.delete(pdsPlatformGoodsPrice);
    }

    /**
     * 查询参数 可选 pmId/merchantId/buyerId
     * @param goodsPrice
     * @return
     */
    public PdsPlatformGoodsPrice fetch(PdsPlatformGoodsPrice goodsPrice){
        return dao.fetch(goodsPrice);
    }

    /**
     * 联查 pmName merchantName
     * @param pdsPlatformGoodsPrice
     * @return
     */
    public List<PdsPlatformGoodsPrice> findJoinList(PdsPlatformGoodsPrice pdsPlatformGoodsPrice) {
        return dao.findJoinList(pdsPlatformGoodsPrice);
    }

    /**
     * 根据  queryDate时间/pmId/merchantId/modelId 返回确定的一条记录
     * @param goodsPrice
     * @return
     */
    public PdsPlatformGoodsPrice fetchGoodsPrice(PdsPlatformGoodsPrice goodsPrice) {
        goodsPrice.setStatus(ICommonConstant.YesNoType.yes.getCode());
        List<PdsPlatformGoodsPrice> list = dao.fetchGoodsPrice(goodsPrice);
        return Collections3.isEmpty(list) ? null : list.get(0);
    }

//    public PdsPlatformGoodsPrice fetchPrice(Date queryDate,String pmId,String merchantId,String modelId){
//        PdsPlatformGoodsPrice goodsPrice = new PdsPlatformGoodsPrice();
//        goodsPrice.setQueryDate(queryDate);
//        goodsPrice.setPmId(pmId);
//        goodsPrice.setMerchantId(merchantId);
//        goodsPrice.setModelId(modelId);
//        List<PdsPlatformGoodsPrice> list = dao.fetchGoodsPrice(goodsPrice);
//        return Collections3.isEmpty(list) ? null : list.get(0);
//    }

//    // 根据  起止时间/pmId/buyerId  返回唯一的时间段内的记录
//    public List<PdsPlatformGoodsPrice> fetchPeriodList(PdsPlatformGoodsPrice goodsPrice) {
//        return dao.fetchPeriodList(goodsPrice);
//    }

    /**
     * 查询 与选择时间段 有交集的 定价记录 数量
     * @param goodsPrice 必需 pmId/merchantId/ startDate /endDate  可选status
     * @return
     */
    public int countRangePrice(PdsPlatformGoodsPrice goodsPrice){
        return dao.countRangePrice(goodsPrice);
    }

    /**
     * 初始化 采购商的商品
     * @param goodsPrice
     * @return
     */
    @Transactional(readOnly = false)
    public boolean initBuyerGoods(PdsPlatformGoodsPrice goodsPrice) {
        // 先查询是否已有记录
        // 验证是否已存在报价信息
        if(countRangePrice(goodsPrice) > 0){
            return false;
        }
        // 从平台店铺零售商品库 复制数据
        goodsPrice.preInsert();
        return dao.initBuyerGoods(goodsPrice) > 0 ? true : false;
    }

    /**
     * 修改 采购价
     * @param goodsPrice  id/price
     * @return
     */
    @Transactional(readOnly = false)
    public boolean updateGoodsPrice(PdsPlatformGoodsPrice goodsPrice) {
        if (StringUtils.isEmpty(goodsPrice.getId()) || null == goodsPrice.getPrice()
                || goodsPrice.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        int i = dao.updateGoodsPrice(goodsPrice);
        return i > 0 ? true : false;
    }

    /**
     * 复制平台下 源采购商商家 在 指定时间段 的采购价数据
     * @param goodsPrice pmId/merchantId/newMerchantId/queryDate   rate
     * @return
     */
    @Transactional(readOnly = false)
    public boolean copyToNewBuyerMerchant(PdsPlatformGoodsPrice goodsPrice){
        goodsPrice.preInsert();
        int affectRows = dao.copyToNewBuyerMerchant(goodsPrice);
        if (affectRows > 0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 批量删除
     * @param goodsPrice  pmId/merchantId/queryDate
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteBatch(PdsPlatformGoodsPrice goodsPrice){
        String pmId = goodsPrice.getPmId();
        Date date = goodsPrice.getQueryDate();
        if (StringUtils.isEmpty(pmId) || null == date){
            return 0;
        }else {
            return dao.deleteBatch(goodsPrice);
        }
    }

    /**
     * 删除单个
     * @param goodsPrice
     * @return
     */
    @Transactional(readOnly = false)
    public int deletePrice(PdsPlatformGoodsPrice goodsPrice) {
        if (StringUtils.isEmpty(goodsPrice.getId())){
            return 0;
        }
        return dao.deleteBatch(goodsPrice);
    }

    /**
     * goodsModel 关联 查询 采购商定价
     * @param goodsPrice  pmId/merchantId/queryDate  可选:goodsId/modelId/status
     * @return
     */
    public List<GoodsModel> findGoodsModelList(PdsPlatformGoodsPrice goodsPrice) {
        return dao.findGoodsModelList(goodsPrice);
    }

    /**
     * 返回联查 的 Name /marketPrice/ 商品状态(即速) goodsStatus
     * @param goodsPrice pmId/merchantId/queryDate
     * @return
     */
    public List<PdsPlatformGoodsPrice> findGoodsListByDateJoin(PdsPlatformGoodsPrice goodsPrice) {
        return dao.findGoodsListByDateJoin(goodsPrice);
    }
}