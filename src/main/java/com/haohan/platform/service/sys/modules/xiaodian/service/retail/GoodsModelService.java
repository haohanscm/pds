package com.haohan.platform.service.sys.modules.xiaodian.service.retail;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.retail.GoodsModelDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品规格管理Service
 *
 * @author haohan
 * @version 2018-09-11
 */
@Service
@Transactional(readOnly = true)
public class GoodsModelService extends CrudService<GoodsModelDao, GoodsModel> {

    public GoodsModel get(String id) {
        return super.get(id);
    }

    public List<GoodsModel> findList(GoodsModel goodsModel) {
        return super.findList(goodsModel);
    }

    // 联查goods表  结果带goodsName shopId goodsCategoryId
    public List<GoodsModel> findJoinList(GoodsModel goodsModel) {
        return dao.findJoinList(goodsModel);
    }

    public Page<GoodsModel> findPage(Page<GoodsModel> page, GoodsModel goodsModel) {
        return super.findPage(page, goodsModel);
    }

    /**
     * 联查goods表  结果带goodsName shopId goodsCategoryId
     *
     * @param id goodsModleId
     * @return
     */
    public GoodsModel fetch(String id) {
        return dao.fetch(id);
    }

    /**
     * 联查goods表  结果带goodsName shopId goodsCategoryId
     *
     * @param goodsModel goodsModleId / shopId
     * @return
     */
    public GoodsModel fetchWithShop(GoodsModel goodsModel) {
        return dao.fetchWithShop(goodsModel);
    }

    // 此处保存时 未处理 goodsModelSn;
    @Transactional(readOnly = false)
    public void save(GoodsModel goodsModel) {
        super.save(goodsModel);
    }

    @Transactional(readOnly = false)
    public void delete(GoodsModel goodsModel) {
        super.delete(goodsModel);
    }

    @Transactional(readOnly = false)
    public void deleteByGoodsId(String goodsId) {
        GoodsModel goodsModel = new GoodsModel();
        goodsModel.setGoodsId(goodsId);
        goodsModel.setGoodsModelSn("");
        dao.deleteByGoodsId(goodsModel);
    }

    public List<GoodsModel> findByGoodsId(String goodsId) {
        if (StringUtils.isEmpty(goodsId)) {
            return new ArrayList<>();
        }
        GoodsModel goodsModel = new GoodsModel();
        goodsModel.setGoodsId(goodsId);
        return findList(goodsModel);
    }

    /**
     * 查询所有规格包括删除, 该方法一般不使用
     * @param goodsId
     * @return
     */
    public List<GoodsModel> findByGoodsIdWithDel(String goodsId){
        if (StringUtils.isEmpty(goodsId)) {
            return new ArrayList<>();
        }
        return dao.findByGoodsIdWithDel(goodsId);
    }

    public GoodsModel fetchByJsModelId(String jsModelId) {
        GoodsModel goodsModel = new GoodsModel();
        goodsModel.setThirdModelSn(jsModelId);
        List<GoodsModel> list = super.findList(goodsModel);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }
}