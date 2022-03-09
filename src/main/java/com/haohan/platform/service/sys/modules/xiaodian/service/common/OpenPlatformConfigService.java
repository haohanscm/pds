package com.haohan.platform.service.sys.modules.xiaodian.service.common;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.common.OpenPlatformConfigDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.OpenPlatformConfig;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 开放平台管理Service
 *
 * @author haohan
 * @version 2017-08-06
 */
@Service
@Transactional(readOnly = true)
public class OpenPlatformConfigService extends CrudService<OpenPlatformConfigDao, OpenPlatformConfig> {

    public OpenPlatformConfig get(String id) {
        return super.get(id);
    }

    public List<OpenPlatformConfig> findList(OpenPlatformConfig openPlatformConfig) {
        return super.findList(openPlatformConfig);
    }

    public Page<OpenPlatformConfig> findPage(Page<OpenPlatformConfig> page, OpenPlatformConfig openPlatformConfig) {
        return super.findPage(page, openPlatformConfig);
    }

    @Transactional(readOnly = false)
    public void save(OpenPlatformConfig openPlatformConfig) {
        super.save(openPlatformConfig);
    }

    @Transactional(readOnly = false)
    public void delete(OpenPlatformConfig openPlatformConfig) {
        super.delete(openPlatformConfig);
    }


    public OpenPlatformConfig fetchByAppId(String appId) {
        OpenPlatformConfig config = new OpenPlatformConfig();
        config.setAppId(appId);
        // 查询已启用的对象  2:启用  -1:停用  0:待审核
        config.setStatus("2");
        List<OpenPlatformConfig> lists = findList(config);

        return CollectionUtils.isEmpty(lists) ? null : lists.get(0);
    }

    public OpenPlatformConfig fetchByAppIdAndType(String appId, ICommonConstant.AppType appType){
        OpenPlatformConfig config = new OpenPlatformConfig();
        config.setAppId(appId);
        config.setStatus("2");
        config.setAppType(appType.getCode());
        List<OpenPlatformConfig> lists = dao.findList(config);
        return CollectionUtils.isEmpty(lists) ? null : lists.get(0);
    }

}