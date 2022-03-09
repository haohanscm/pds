package com.haohan.platform.service.sys.modules.pss.api.entity.req;

/**
 * @author shenyu
 * @create 2018/12/28
 */
public class PssPageApiReq extends PssBaseApiReq {
    private Integer pageNo;
    private Integer pageSize;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
