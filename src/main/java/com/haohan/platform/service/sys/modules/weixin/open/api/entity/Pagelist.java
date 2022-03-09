package com.haohan.platform.service.sys.modules.weixin.open.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zgw on 2017/12/25.
 */
public class Pagelist {


   @JsonProperty("index")
   private String index;

   @JsonProperty("page/list")
   private String list;

   @JsonProperty("page/detail")
   private String detail;

   public String getIndex() {
      return index;
   }

   public void setIndex(String index) {
      this.index = index;
   }

   public String getList() {
      return list;
   }

   public void setList(String list) {
      this.list = list;
   }

   public String getDetail() {
      return detail;
   }

   public void setDetail(String detail) {
      this.detail = detail;
   }
}
