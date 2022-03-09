package com.haohan.platform.service.sys.modules.xiaodian.core.passport;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.passport.PassportCheckReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.passport.PassportLoginReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.passport.PassportRegReq;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IPassportConstant;

/**
 * Created by zgw on 2018/12/3.
 */
public interface IPassportService extends IPassportConstant{

    BaseResp reg(PassportRegReq regReq);

    BaseResp login(PassportLoginReq loginReq);

    BaseResp check(PassportCheckReq checkReq);

}
