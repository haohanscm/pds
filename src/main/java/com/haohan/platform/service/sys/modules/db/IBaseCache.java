package com.haohan.platform.service.sys.modules.db;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zgw on 2018/6/14.
 */
public interface IBaseCache<PK, T extends Serializable> {

    boolean insertT(T t);

    T getT(T t);

    boolean updT(T t);

    boolean batchDeleteT(Object[] objs);

    boolean batchUpdateT(List<T> list);

    void clerT(T t);

    List<T> findListT(T t);

    boolean insertListT(T req, List<T> list);

}