package com.haohan.platform.service.sys.modules.xiaodian.service.merchant;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.CacheUtils;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.merchant.MerchantAppManageDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAppManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.util.MerchantCacheKeyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商家应用Service
 * @author haohan
 * @version 2017-12-25
 */
@Service
@Transactional(readOnly = true)
public class MerchantAppManageService extends CrudService<MerchantAppManageDao, MerchantAppManage> {

    @Autowired
    @Lazy(true)
    private AuthAppService authAppService;

	public MerchantAppManage get(String id) {
		return super.get(id);
	}
	
	public List<MerchantAppManage> findList(MerchantAppManage merchantAppManage) {
		return super.findList(merchantAppManage);
	}
	
	public Page<MerchantAppManage> findPage(Page<MerchantAppManage> page, MerchantAppManage merchantAppManage) {
		return super.findPage(page, merchantAppManage);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantAppManage merchantAppManage) {
		super.save(merchantAppManage);
		// 有更新记录时，删除缓存
        removeCache();
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantAppManage merchantAppManage) {
		super.delete(merchantAppManage);
        // 有更新记录时，删除缓存
        removeCache();
	}

	public MerchantAppManage fetchMerchantApp(String merchantId, String appId){
        MerchantAppManage app = new MerchantAppManage();
        app.setAppId(appId);
        app.setMerchantId(merchantId);
        return findList(app).stream().findAny().orElse(null);
    }

    // 查询所有状态的商家应用
    public MerchantAppManage fetchByAppIdAll(String appId){
        MerchantAppManage app = new MerchantAppManage();
        app.setAppId(appId);
        List<MerchantAppManage> list = findList(app);
        return (CollectionUtils.isEmpty(list))?null:list.get(0);
    }

	// 只查询上线成功状态的商家应用
	public MerchantAppManage fetchByAppId(String appId){
		MerchantAppManage app = new MerchantAppManage();
		app.setAppId(appId);
		app.setStatus(IMerchantConstant.MerchantAppStatus.online_success.getCode());
		List<MerchantAppManage> list = findList(app);
		return (CollectionUtils.isEmpty(list))?null:list.get(0);
	}

	public List<MerchantAppManage> findByMerchantIdEnable(String merchantId){
	    MerchantAppManage merchantAppManage = new MerchantAppManage();
	    merchantAppManage.setMerchantId(merchantId);
	    merchantAppManage.setStatus(IMerchantConstant.MerchantAppStatus.online_success.getCode());
	    List<MerchantAppManage> list = dao.findList(merchantAppManage);
        return CollectionUtils.isEmpty(list)?null:list;
	}

	// 查询所有商家应用 优先从缓存读取
    public List<MerchantAppManage> findAllList() {
        // 从缓存读取商家应用
        List<MerchantAppManage> merchantAppManageList;
        String cacheName = MerchantCacheKeyUtils.getCacheName();
        String cacheKey = MerchantCacheKeyUtils.getAppListKey();
        Object obj =  CacheUtils.get(cacheName,cacheKey);
        if(null != obj){
            merchantAppManageList = (List<MerchantAppManage>)obj;
        }else{
            merchantAppManageList = findList(new MerchantAppManage());
            CacheUtils.put(cacheName, cacheKey, merchantAppManageList);
        }
        return merchantAppManageList;
    }
    // 删除缓存
    public void removeCache(){
        CacheUtils.remove(MerchantCacheKeyUtils.getCacheName(),MerchantCacheKeyUtils.getAppListKey());
        CacheUtils.remove(MerchantCacheKeyUtils.getCacheName(),MerchantCacheKeyUtils.fetchCacheKey("appMap"));
    }

    /**
     *  查询 店铺的应用
     * @param shop 必须 merchantId、id
     * @return
     */
    public AuthApp fetchAuthApp(Shop shop) {

        MerchantAppManage queryMerchantApp = new MerchantAppManage();
        queryMerchantApp.setMerchantId(shop.getMerchantId());
        queryMerchantApp.setExt(shop.getId());
        List<MerchantAppManage> merchantAppManageList = super.findList(queryMerchantApp);
        String appId;
        if(Collections3.isEmpty(merchantAppManageList)){
            return null;
        }
        appId = merchantAppManageList.get(0).getAppId();
        if(StringUtils.isEmpty(appId)){
            return null;
        }
        return authAppService.fetchByAppId(appId);
    }

    // 查询所有商家的应用appId与appName
//    public Map<String,String> fetchAllAppMap(){
//        // 从缓存读取
//        Map<String,String> appMap;
//        String cacheName = MerchantCacheKeyUtils.getCacheName();
//        String cacheKey = MerchantCacheKeyUtils.fetchCacheKey("appMap");
//        Object obj =  CacheUtils.get(cacheName,cacheKey);
//        if(null != obj){
//            appMap = (Map<String,String>)obj;
//        }else{
//            List<MerchantAppManage> appList = findAllList();
//            appMap = new HashMap<String,String>();
//            for(MerchantAppManage m :appList){
//                appMap.put(m.getAppId(),m.getAppName());
//            }
//            CacheUtils.put(cacheName, cacheKey, appMap);
//        }
//        return appMap;
//    }

}