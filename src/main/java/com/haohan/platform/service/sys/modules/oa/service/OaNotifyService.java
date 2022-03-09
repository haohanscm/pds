/**
 * Copyright &copy; 2012-2017 <a href="http://www.haohanwork.com">haohan</a> All rights reserved
 */
package com.haohan.platform.service.sys.modules.oa.service;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.oa.dao.OaNotifyDao;
import com.haohan.platform.service.sys.modules.oa.dao.OaNotifyRecordDao;
import com.haohan.platform.service.sys.modules.oa.entity.OaNotify;
import com.haohan.platform.service.sys.modules.oa.entity.OaNotifyRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 通知通告Service
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OaNotifyService extends CrudService<OaNotifyDao, OaNotify> {

    @Autowired
    private OaNotifyRecordDao oaNotifyRecordDao;

    public OaNotify get(String id) {
        OaNotify entity = dao.get(id);
        return entity;
    }

    /**
     * 获取通知发送记录
     *
     * @param oaNotify
     * @return
     */
    public OaNotify getRecordList(OaNotify oaNotify) {
        oaNotify.setOaNotifyRecordList(oaNotifyRecordDao.findList(new OaNotifyRecord(oaNotify)));
        return oaNotify;
    }

    public Page<OaNotify> find(Page<OaNotify> page, OaNotify oaNotify) {
        oaNotify.setPage(page);
        page.setList(dao.findList(oaNotify));
        return page;
    }

    /**
     * 获取通知数目
     *
     * @param oaNotify
     * @return
     */
    public Long findCount(OaNotify oaNotify) {
        return 0L;
//		return dao.findCount(oaNotify);
    }

    @Transactional(readOnly = false)
    public void save(OaNotify oaNotify) {
        super.save(oaNotify);

        // 更新发送接受人记录
        oaNotifyRecordDao.deleteByOaNotifyId(oaNotify.getId());
        if (oaNotify.getOaNotifyRecordList().size() > 0) {
            oaNotifyRecordDao.insertAll(oaNotify.getOaNotifyRecordList());
        }
    }

    /**
     * 更新阅读状态
     */
    @Transactional(readOnly = false)
    public void updateReadFlag(OaNotify oaNotify) {
        OaNotifyRecord oaNotifyRecord = new OaNotifyRecord(oaNotify);
        oaNotifyRecord.setUser(oaNotifyRecord.getCurrentUser());
        oaNotifyRecord.setReadDate(new Date());
        oaNotifyRecord.setReadFlag("1");
        oaNotifyRecordDao.update(oaNotifyRecord);
    }
}