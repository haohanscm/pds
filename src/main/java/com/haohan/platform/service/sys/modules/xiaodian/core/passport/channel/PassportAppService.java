package com.haohan.platform.service.sys.modules.xiaodian.core.passport.channel;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.passport.PassportCheckReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.passport.PassportLoginReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.passport.PassportRegReq;
import com.haohan.platform.service.sys.modules.xiaodian.core.passport.AbsPassportService;
import org.springframework.stereotype.Service;

/**
 * Created by zgw on 2018/12/3.
 */
@Service
public class PassportAppService extends AbsPassportService{


    @Override
    public BaseResp reg(PassportRegReq regReq) {
        return null;
    }

    @Override
    public BaseResp login(PassportLoginReq loginReq) {

        return null;
    }

    @Override
    public BaseResp check(PassportCheckReq checkReq) {
        return null;
    }
}
