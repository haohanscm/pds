package com.haohan.platform.service.sys.modules.xiaodian.core.passport;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.SpringContextHolder;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.passport.PassportCheckReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.passport.PassportLoginReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.passport.PassportRegReq;
import com.haohan.platform.service.sys.modules.xiaodian.core.passport.channel.PassportAppService;
import com.haohan.platform.service.sys.modules.xiaodian.core.passport.channel.PassportWebService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgw on 2018/12/3.
 */
@Service
public class PassportBaseService extends AbsPassportService implements IPassportService {


    public static Map<String, AbsPassportService> channelService = new HashMap<>();

    static {
        channelService.put(PassportChannel.web.toString(), SpringContextHolder.getBean(PassportWebService.class));
        channelService.put(PassportChannel.app.toString(), SpringContextHolder.getBean(PassportAppService.class));
    }

    @Override
    public BaseResp reg(PassportRegReq regReq) {
        String channel = regReq.getChannel();
        AbsPassportService passportService =  channelService.get(channel);
        if(null == passportService){
            //私有reg

        }
        return  passportService.reg(regReq);
    }

    @Override
    public BaseResp login(PassportLoginReq loginReq) {
        String channel = loginReq.getChannel();
             AbsPassportService passportService =  channelService.get(channel);
             if(null == passportService){
                 //私有login
                 commonLogin(loginReq.getLoginName(),loginReq.getPassword(),AccountType.LoginName);
             }
           return  passportService.login(loginReq);
    }

    @Override
    public BaseResp check(PassportCheckReq checkReq) {
        BaseResp resp = BaseResp.newError();
        String channel = checkReq.getChannel();
        AbsPassportService passportService =  channelService.get(channel);
        if(null == passportService){
            //check
        resp.setMsg("没有对应的服务");
        return resp;
        }
        return  passportService.check(checkReq);
    }




}
