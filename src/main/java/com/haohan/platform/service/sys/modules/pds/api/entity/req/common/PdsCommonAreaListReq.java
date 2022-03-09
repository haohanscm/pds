package com.haohan.platform.service.sys.modules.pds.api.entity.req.common;

import org.hibernate.validator.constraints.Length;

/**
 * 请求 区域列表
 *
 * @author dy
 * @date 2019/02/20
 */
public class PdsCommonAreaListReq {

    @Length(min = 0, max = 64, message = "区域编码code长度必须介于 0 和 64 之间")
    private String code;
    @Length(min = 0, max = 64, message = "区域名称name长度必须介于 0 和 64 之间")
    private String name;
    @Length(min = 0, max = 5, message = "区域类型type长度必须介于 0 和 5 之间")
    private String type;
    @Length(min = 0, max = 64, message = " 父级区域编号长度必须介于 0 和 64 之间")
    private String parentId;
    @Length(min = 0, max = 64, message = "所有父级区域编号长度必须介于 0 和 64 之间")
    private String parentIds;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
}
