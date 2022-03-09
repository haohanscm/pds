package com.haohan.platform.service.sys.modules.xiaodian.cache;

import com.haohan.platform.service.sys.modules.db.AbstractCache;
import com.haohan.platform.service.sys.modules.db.IBaseCache;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.ShopServiceDistrict;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenyu
 * @create 2018/9/12
 */
@Service
public class ShopServiceDistrictCache extends AbstractCache<String,ShopServiceDistrict> implements IBaseCache<String,ShopServiceDistrict> {

    @Override
    public String getPK(ShopServiceDistrict shopServiceDistrict) {
        return shopServiceDistrict.getShopId();
    }

    @Override
    public boolean isCache() {
        return true;
    }

    @Override
    public String getPrefixCacheKey() {
        return CACHE_PREFIX.concat(CACHE_SEPARATOR).concat(getClass().getName());
    }

    @Override
    public String getMasterCacheKey() {

        //关联KEY
        return null;
    }

    @Override
    public boolean insertT(ShopServiceDistrict t) {

        return super.insertT(t);
    }

    @Override
    public ShopServiceDistrict getT(ShopServiceDistrict shopServiceDistrict) {
        return super.getT(getPK(shopServiceDistrict));
    }

    @Override
    public boolean updT(ShopServiceDistrict t) {
        return super.updT(t);
    }

    @Override
    public boolean batchDeleteT(Object[] objs) {
        return super.batchDeleteT(objs);
    }

    @Override
    public boolean insertListT(ShopServiceDistrict req, List<ShopServiceDistrict> list) {

        return super.insertListT(req,list);
    }

    @Override
    public List<ShopServiceDistrict> findListT(ShopServiceDistrict t) {
        return super.findListT(t);
    }

    @Override
    public void clerT(ShopServiceDistrict shopServiceDistrict) {
        super.clearT(shopServiceDistrict);
    }

    @Override
    public boolean batchUpdateT(List<ShopServiceDistrict> list) {
        return  batchUpdateT(list);
    }
}
