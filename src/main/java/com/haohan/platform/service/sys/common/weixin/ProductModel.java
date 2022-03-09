package com.haohan.platform.service.sys.common.weixin;

import java.io.Serializable;

/****
 * 生成随机串 ，通过hash存储
 *
 * @author lcw
 *
 */
public class ProductModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1600435798413416517L;
    private String id;
    /***
     * 0 有效 ，1无效 表示使用过
     */
    private String valid;

    public String getId() {
        return id;
    }

    public String getValid() {
        return valid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

}
