package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商户后台 分页返回参数
 * Created by dy on 2018/10/10.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespPage<T> implements Serializable {

    private int pageNo;  // 当前页码
    private int pageSize;  // 页面大小
    private long count;  // 总记录数
    private int totalPage; // 总页数
    private List<T> list = new ArrayList<>();

    public RespPage() {
    }

    public RespPage (Page<T> page){
        fetchFromPage(page);
    }

    public void fetchFromPage(Page<T> page) {
        this.pageSize = page.getPageSize();
        this.count = page.getCount();
        this.list = page.getList();
        // 总页数计算
        try {
            this.totalPage = (int) Math.ceil((double) page.getCount() / page.getPageSize());
        } catch (Exception e) {
        }
        // 当页数超过最大页数时 返回值为第1页
        this.pageNo = (page.getPageNo() > totalPage) ? 1 : page.getPageNo();
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
