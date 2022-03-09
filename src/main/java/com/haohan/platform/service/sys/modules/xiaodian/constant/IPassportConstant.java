package com.haohan.platform.service.sys.modules.xiaodian.constant;

/**
 * Created by zgw on 2018/12/3.
 */
public interface IPassportConstant {


  enum AccountType {
      LoginName,Mobile,Email
  }

  enum PassportAction {
     reg,login,logout,check
  }


  enum PassportChannel {
      web,app,wap
  }


}
