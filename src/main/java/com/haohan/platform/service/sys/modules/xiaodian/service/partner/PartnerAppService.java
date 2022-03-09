package com.haohan.platform.service.sys.modules.xiaodian.service.partner;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.cache.ParterAppCache;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.partner.PartnerAppDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.partner.PartnerApp;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 厂商应用管理Service
 *
 * @author haohan
 * @version 2018-05-29
 */
@Service
@Transactional(readOnly = true)
public class PartnerAppService extends CrudService<PartnerAppDao, PartnerApp> {
    @Resource
    private ParterAppCache parterAppCache;

    public PartnerApp get(String id) {
        return super.get(id);
    }

    public List<PartnerApp> findList(PartnerApp partnerApp) {
        List<PartnerApp> partnerAppList = null;
        if (!parterAppCache.isCache()) {
            return super.findList(partnerApp);
        }
        //缓存启用,从缓存获取
        partnerAppList = parterAppCache.findListT(partnerApp);
        //缓存未找到,从数据库查询
        if (CollectionUtils.isEmpty(partnerAppList)) {
            partnerAppList = super.findList(partnerApp);
            //判断数据是否为空,更新缓存
            if (CollectionUtils.isNotEmpty(partnerAppList)) {
                parterAppCache.insertListT(partnerApp, partnerAppList);
            }
        }
        return partnerAppList;
    }

    public Page<PartnerApp> findPage(Page<PartnerApp> page, PartnerApp partnerApp) {
        return super.findPage(page, partnerApp);
    }

    @Transactional(readOnly = false)
    public void save(PartnerApp partnerApp)
    {
        super.save(partnerApp);

        if(parterAppCache.isCache()){
            parterAppCache.clearListT();
        }
    }

    @Transactional(readOnly = false)
    public void delete(PartnerApp partnerApp) {

        super.delete(partnerApp);
        if(parterAppCache.isCache()){
            parterAppCache.clearListT();
        }

    }

    public PartnerApp findByAppKey(String appkey) {
        PartnerApp app = new PartnerApp();
        app.setAppKey(appkey);
        app.setStatus(IMerchantConstant.available);
        List<PartnerApp> partnerAppList = findList(app);
        return CollectionUtils.isNotEmpty(partnerAppList) ? partnerAppList.get(0) : null;
    }

    public PartnerApp fetchByPartnerNum(String partnerNum){
        PartnerApp app = new PartnerApp();
        app.setPartnerNum(partnerNum);
        app.setStatus(IMerchantConstant.available);
        List<PartnerApp> partnerAppList = findList(app);
        return CollectionUtils.isNotEmpty(partnerAppList) ? partnerAppList.get(0) : null;
    }
}