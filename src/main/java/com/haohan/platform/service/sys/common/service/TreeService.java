/**
 * Copyright &copy; 2012-2017 <a href="http://www.haohanwork.com">haohan</a> All rights reserved
 */
package com.haohan.platform.service.sys.common.service;

import com.haohan.platform.service.sys.common.exception.ServiceException;
import com.haohan.platform.service.sys.common.persistence.TreeDao;
import com.haohan.platform.service.sys.common.persistence.TreeEntity;
import com.haohan.platform.service.sys.common.utils.Reflections;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service基类
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class TreeService<D extends TreeDao<T>, T extends TreeEntity<T>> extends CrudService<D, T> {

    @Transactional(readOnly = false)
    public void save(T entity) {

        @SuppressWarnings("unchecked")
        Class<T> entityClass = Reflections.getClassGenricType(getClass(), 1);

        // 如果没有设置父节点，则代表为跟节点，有则获取父节点实体
        if (entity.getParent() == null || StringUtils.isBlank(entity.getParentId())
                || "0".equals(entity.getParentId())) {
            entity.setParent(null);
        } else {
            entity.setParent(super.get(entity.getParentId()));
        }
        if (entity.getParent() == null) {
            T parentEntity = null;
            try {
                parentEntity = entityClass.getConstructor(String.class).newInstance("0");
            } catch (Exception e) {
                throw new ServiceException(e);
            }
            entity.setParent(parentEntity);
            entity.getParent().setParentIds(StringUtils.EMPTY);
        }

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = entity.getParentIds();

        // 设置新的父节点串
        entity.setParentIds(entity.getParent().getParentIds() + entity.getParent().getId() + ",");

        // 保存或更新实体
        super.save(entity);

        // 更新子节点 parentIds
        T o = null;
        try {
            o = entityClass.newInstance();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        o.setParentIds("%," + entity.getId() + ",%");
        List<T> list = dao.findByParentIdsLike(o);
        for (T e : list) {
            if (e.getParentIds() != null && oldParentIds != null) {
                e.setParentIds(e.getParentIds().replace(oldParentIds, entity.getParentIds()));
                preUpdateChild(entity, e);
                dao.updateParentIds(e);
            }
        }

    }

    /**
     * 预留接口，用户更新子节前调用
     *
     * @param childEntity
     */
    protected void preUpdateChild(T entity, T childEntity) {

    }

    // 查找分类中的 根节点(parentId为“0”)
    public List<T> fetchRootList(T entity) {
        @SuppressWarnings("unchecked")
        Class<T> entityClass = Reflections.getClassGenricType(getClass(), 1);

        if (entity.getParent() != null) {
            entity.getParent().setId("0");
        } else {
            T parentEntity = null;
            try {
                parentEntity = entityClass.getConstructor(String.class).newInstance("0");
            } catch (Exception e) {
                throw new ServiceException(e);
            }
            parentEntity.setId("0");
            entity.setParent(parentEntity);
        }
        return dao.findList(entity);
    }

}
