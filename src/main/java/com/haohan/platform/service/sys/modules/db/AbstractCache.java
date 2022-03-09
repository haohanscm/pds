package com.haohan.platform.service.sys.modules.db;

import com.haohan.framework.utils.JacksonUtils;
import com.haohan.framework.utils.MD5Util;
import com.haohan.framework.utils.TransBeanMap;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zgw on 2018/6/14.
 */
public abstract class AbstractCache<PK, T> {

    protected abstract PK getPK(T t);

    protected abstract boolean isCache();

    private static Logger logger = LoggerFactory.getLogger("cache");

    /**
     * 全局缓存前缀
     */
    protected static final String CACHE_PREFIX = "HAOHANSHOP:CACHE";

    protected static final String CACHE_PREFIX_LIST = "LIST";

    protected static final String CACHE_SEPARATOR = ":";

    protected static final String CACHE_PORTAL = "portal";

    /**
     * 默认1小时 </br>
     * 单位：秒
     */
    protected int DEFAULT_TIMEOUT_SECOND = 60 * 60 * 1;

    /**
     * 默认1天 </br>
     * 单位：秒
     */
    protected int ONE_DAY_TIMEOUT_SECOND = 60 * 60 * 24 * 1;

    /**
     * 默认15天 </br>
     * 单位：秒
     */
    protected int HALF_MONTH_TIMEOUT_SECOND = 60 * 60 * 24 * 15;

    /**
     * 默认10天 </br>
     * 单位：秒
     */
    protected int TEN_DAY_TIMEOUT_SECOND = 60 * 60 * 24 * 10;


    // 获取缓存KEY前缀
    public abstract String getPrefixCacheKey();

    // 获取主缓存KEY
    public abstract String getMasterCacheKey();

    // 获取查询对象CACHE KEY
    protected String getQueryCacheKey(Map map) {
        return getPrefixCacheKey().concat(CACHE_SEPARATOR).concat(MD5Util.MD5(JacksonUtils.toJson(map)));
    }

    // 获取List查询对象CACHE KEY
    protected String getQueryCacheListKey(Map map) {
        return getPrefixCacheKey().concat(CACHE_PREFIX_LIST).concat(CACHE_SEPARATOR)
                .concat(Integer.toString(map.toString().hashCode()));

    }

    // 获取对象 CACHE KEY
    protected String getTCacheKey(T t) {
        return getTCacheKeyByPk(getPK(t));
    }

    // 根据PK获取对象CACHE KEY
    protected String getTCacheKeyByPk(PK pk) {
        return getPrefixCacheKey().concat(CACHE_SEPARATOR).concat(String.valueOf(pk));
    }

    /**
     * 清除数据
     */
    protected void clearT(T t) {

        if (t == null) {
            return;
        }
        try {
            String key = getTCacheKey(t);
            JedisUtils.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 新增数据
     */
    protected boolean insertT(T t) {

        if (t == null) {
            return false;
        }
        try {
            String key = getTCacheKey(t);
            JedisUtils.setObject(key, t, DEFAULT_TIMEOUT_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @Title: updT @Description: 更新操作 @param @param dao @param @param
     * t @param @return 设定文件 @return BaseResp 返回类型 @throws
     */
    protected boolean updT(T t) {
        if (t == null) {
            return false;
        }
        // 更新缓存
        try {
            String key = getTCacheKey(t);
            if (JedisUtils.exists(key)) {
                JedisUtils.setObject(key, t, DEFAULT_TIMEOUT_SECOND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @SuppressWarnings("unchecked")
    protected boolean batchDeleteT(Object[] objs) {

        if (objs == null) {
            return false;
        }
        // 循环删除多个KEY
        for (Object obj : objs) {
            JedisUtils.del(getTCacheKeyByPk((PK) obj));
        }
        return true;
    }

    protected List<T> findListT(T req) {

        try {
            Map map = TransBeanMap.transBean2Map(req);
            String key = getQueryCacheListKey(map);
            String data = JedisUtils.get(key);
            if (StringUtils.isNotEmpty(data)) {
                List<T> list = (List<T>) JacksonUtils.readListValue(data, req.getClass());

                return list;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected boolean insertListT(T req, List<T> list) {
        try {
            String key = getQueryCacheListKey(TransBeanMap.transBean2Map(req));
            JedisUtils.set(key, JacksonUtils.toJson(list), DEFAULT_TIMEOUT_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void clearListT() {
        clearList(getPrefixCacheKey().concat(CACHE_PREFIX_LIST).concat(CACHE_SEPARATOR));
    }

    private void clearList(String patten) {
        JedisUtils.deleteKeyByPrefix(patten);
    }


    protected boolean batchUpdateT(List<T> list) {

        if (list == null) {
            return false;
        }
        try {
            for (T t : list) {

                String key = getTCacheKey(t);
                if (JedisUtils.exists(key)) {
                    JedisUtils.setObject(key, t, DEFAULT_TIMEOUT_SECOND);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @Title: getT @Description: 查找对象信息 @param @param dao @param @param
     * t @param @return 设定文件 @return T 返回类型 @throws
     */
    protected T getT(PK pk) {
        // 缓存执行时间检测
        Long carryOutStartTime = System.currentTimeMillis();
        T result = null;

        if (null == pk) {
            return result;
        }
        T t = (T) JedisUtils.getObject(getTCacheKeyByPk(pk));
        // 添加到perDebug日志
        if (t != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("[key : ");
            sb.append(getTCacheKeyByPk(pk));
            sb.append("|");
            sb.append("className : ");
            sb.append(this.getClass().getName());
            sb.append("|");
            sb.append("getT方法,缓存查询执行时间 :");
            sb.append(System.currentTimeMillis() - carryOutStartTime);
            logger.debug("getT:{}", sb.toString());
        }

        return t;
    }


    /**
     * 向缓存中放入string类型对象
     *
     * @param key
     * @param
     * @param o
     * @return
     */
    public boolean set(final String key, String o) {
        JedisUtils.set(key, o, DEFAULT_TIMEOUT_SECOND);
        return true;
    }

    /**
     * 想缓存中放入序列化对象
     *
     * @param key
     * @param o
     * @return
     */
    public boolean set(String key, Serializable o) {
        JedisUtils.setObject(key, o, DEFAULT_TIMEOUT_SECOND);
        return true;
    }

    /**
     * 取单个信息(对象)
     *
     * @param key
     * @return Object
     */
    public <T extends Serializable> T get(String key) {
        return (T) JedisUtils.getObject(key);
    }

    /**
     * 删除 本类总接口全部捕获异常
     *
     * @param key
     * @return
     */
    public boolean remove(String key) {
        JedisUtils.del(key);
        return true;
    }
}
