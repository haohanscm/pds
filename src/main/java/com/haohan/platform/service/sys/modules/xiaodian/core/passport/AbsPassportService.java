package com.haohan.platform.service.sys.modules.xiaodian.core.passport;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.utils.wx.MD5Util;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.passport.PassportRegReq;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import com.haohan.platform.service.sys.modules.xiaodian.service.UPassportService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * Created by zgw on 2018/12/3.
 */
public abstract class AbsPassportService implements IPassportService{


    @Autowired
    private UPassportService uPassportService;


    public BaseResp commonReg(PassportRegReq regReq){
        BaseResp resp = BaseResp.newError();
        UPassport passport = new UPassport();
        try {
            BeanUtils.copyProperties(passport,regReq);
            passport.setCreateDate(new Date());
            uPassportService.save(passport);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        resp.putStatus(RespStatus.SUCCESS);
        return resp;
    }


    public BaseResp commonLogin(String loginAccount, String password, AccountType accountType){

        BaseResp resp = BaseResp.newError();
        UPassport passport = null;

        if(AccountType.Mobile  == accountType) {
            passport =  uPassportService.fetchByMobile(loginAccount);
        }
        else  if(AccountType.Email == accountType){
            passport =  uPassportService.fetchByEmail(loginAccount);
        }
        else  if(AccountType.LoginName == accountType){
            passport =  uPassportService.fetchByLoginName(loginAccount);
        }
        if(null == passport){
            resp.setMsg("账户不存在");
            return resp;
        }

        //验证密码
        String dbPassword = passport.getPassword();
        if(StringUtils.equals(dbPassword, MD5Util.MD5Encode(password, "UTF-8"))){
            resp.putStatus(RespStatus.SUCCESS);
            resp.setMsg("登录成功");
        }else{
            resp.setMsg("密码错误");
        }
        return resp;

    }


}
