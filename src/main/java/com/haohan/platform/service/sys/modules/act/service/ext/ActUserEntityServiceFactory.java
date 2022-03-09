/**
 * Copyright &copy; 2012-2017 <a href="http://www.haohanwork.com">haohan</a> All rights reserved
 */
package com.haohan.platform.service.sys.modules.act.service.ext;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Activiti User Entity Service Factory
 *
 * @author ThinkGem
 * @version 2013-11-03
 */
public class ActUserEntityServiceFactory implements SessionFactory {

    @Autowired
    private ActUserEntityService actUserEntityService;

    public Class<?> getSessionType() {
        // 返回原始的UserIdentityManager类型
        return UserIdentityManager.class;
    }

    public Session openSession() {
        // 返回自定义的UserEntityManager实例
        return actUserEntityService;
    }

}
