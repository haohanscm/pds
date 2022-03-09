/**
 * Copyright &copy; 2012-2017 <a href="http://www.haohanwork.com">haohan</a> All rights reserved
 */
package com.haohan.platform.service.sys.common.utils;

import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 *
 * @author ThinkGem
 * @version 2013-01-15
 */
@Service
@Lazy(false)
public class IdGen implements IdGenerator, SessionIdGenerator {

    private static SecureRandom random = new SecureRandom();

    private static Integer START_NUM = null;

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 自定义主键生成规则
     * 生成主键id 根据实体类名
     * key:  前缀 + 实体类类名
     *
     * @param clazz
     * @return
     */
    public static String genByT(Class clazz) {
        if (null == START_NUM) {
            START_NUM = Integer.valueOf(DictUtils.getDictValue("start_index", "id_start_index", "500"));
//            ID_PREFIX = DictUtils.getDictValue("id_prefix", "id_start_index", "scm-id:");
        }
        // 处理 两个系统间 实体类名称的不同
        String name = clazz.getSimpleName();
        if (StringUtils.startsWith(name, "Pds")) {
            name = StringUtils.substring(name, 3);
        }
        return JedisUtils.fetchIncrNum(IPdsConstant.ID_PREFIX + name, START_NUM).toString();
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    /**
     * 基于Base62编码的SecureRandom随机生成bytes.
     */
    public static String randomBase62(int length) {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return Encodes.encodeBase62(randomBytes);
    }

    /**
     * Activiti ID 生成
     */
    @Override
    public String getNextId() {
        return IdGen.uuid();
    }

    @Override
    public Serializable generateId(Session session) {
        return IdGen.uuid();
    }

    public static void main(String[] args) {
        System.out.println(IdGen.uuid());
        System.out.println(IdGen.uuid().length());
        System.out.println(new IdGen().getNextId());
        for (int i = 0; i < 1000; i++) {
            System.out.println(IdGen.randomLong() + "  " + IdGen.randomBase62(5));
        }
    }

}
