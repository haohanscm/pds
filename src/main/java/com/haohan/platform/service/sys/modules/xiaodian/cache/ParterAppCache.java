package com.haohan.platform.service.sys.modules.xiaodian.cache;

import com.haohan.platform.service.sys.modules.db.AbstractCache;
import com.haohan.platform.service.sys.modules.db.IBaseCache;
import com.haohan.platform.service.sys.modules.xiaodian.entity.partner.PartnerApp;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zgw on 2018/6/14.
 */
@Service
public class ParterAppCache extends AbstractCache<String, PartnerApp> implements IBaseCache<String, PartnerApp> {


    @Override
    public String getPK(PartnerApp partnerApp) {
        return partnerApp.getPartnerNum();
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
    public boolean insertT(PartnerApp t) {

        return super.insertT(t);
    }

    @Override
    public PartnerApp getT(PartnerApp partnerApp) {
        return super.getT(getPK(partnerApp));
    }

    @Override
    public boolean updT(PartnerApp t) {
        return super.updT(t);
    }

    @Override
    public boolean batchDeleteT(Object[] objs) {
        return super.batchDeleteT(objs);
    }

    @Override
    public boolean insertListT(PartnerApp req, List<PartnerApp> list) {

        return super.insertListT(req,list);
    }

    @Override
    public List<PartnerApp> findListT(PartnerApp t) {
        return super.findListT(t);
    }

    @Override
    public void clerT(PartnerApp partnerApp) {
           super.clearT(partnerApp);
    }

    @Override
    public boolean batchUpdateT(List<PartnerApp> list) {
        return  batchUpdateT(list);
    }


}
