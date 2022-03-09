package com.haohan.platform.service.sys.modules.xiaodian.service;

import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IUserConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.UserOpenPlatformDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 开放平台用户管理Service
 *
 * @author haohan
 * @version 2017-08-05
 */
@Service
@Transactional(readOnly = true)
public class UserOpenPlatformService extends CrudService<UserOpenPlatformDao, UserOpenPlatform> {

    @Autowired
    private UserOpenPlatformDao userOpenPlatformDao;
    @Autowired
    private UPassportService passportService;

    public UserOpenPlatform get(String id) {
        return super.get(id);
    }

    public List<UserOpenPlatform> findList(UserOpenPlatform userOpenPlatform) {
        return super.findList(userOpenPlatform);
    }

    public Page<UserOpenPlatform> findPage(Page<UserOpenPlatform> page, UserOpenPlatform userOpenPlatform) {
        return super.findPage(page, userOpenPlatform);
    }

    // 可传入merchantId/shopId 查询对应开发平台用户
    public List<UserOpenPlatform> findListByMerchantShop(UserOpenPlatform userOpenPlatform) {
        return dao.findListByMerchantShop(userOpenPlatform);
    }

    @Transactional(readOnly = false)
    public void save(UserOpenPlatform userOpenPlatform) {
        super.save(userOpenPlatform);
    }

    @Transactional(readOnly = false)
    public void delete(UserOpenPlatform userOpenPlatform) {
        super.delete(userOpenPlatform);
    }


    public UserOpenPlatform fetchByAppIdAndOpenId(String appId, String openId) {
        UserOpenPlatform openPlatform = new UserOpenPlatform();
        openPlatform.setAppId(appId);
        openPlatform.setOpenId(openId);
        List<UserOpenPlatform> list = findList(openPlatform);

        return CollectionUtils.isEmpty(list) ? null : list.get(0);

    }

    public UserOpenPlatform fetchBuyAppIdAndUnionId(String appId,String unionId){
        UserOpenPlatform openPlatform = new UserOpenPlatform();
        openPlatform.setAppId(appId);
        openPlatform.setUnionId(unionId);
        List<UserOpenPlatform> list = findList(openPlatform);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    public UserOpenPlatform fetchByAppIdAndUid(String appId, String uid) {

        UserOpenPlatform openPlatform = new UserOpenPlatform();
        openPlatform.setAppId(appId);
        openPlatform.setUid(uid);
        List<UserOpenPlatform> list = findList(openPlatform);

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    public UserOpenPlatform findByUserToken(String userToken) {

        UserOpenPlatform openPlatform = new UserOpenPlatform();
        openPlatform.setAccessToken(userToken);
        List<UserOpenPlatform> list = findList(openPlatform);

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 用于查找用户关注的公众号的开放平台用户
     * @param uid  upassportId
     * @param appId  公众号的appId
     * @return
     */
    public UserOpenPlatform findByWechatPublic(String uid, String appId) {

        UserOpenPlatform openPlatform = new UserOpenPlatform();
        openPlatform.setUid(uid);
        openPlatform.setAppId(appId);
        List<UserOpenPlatform> list = dao.findByWechatPublic(openPlatform);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }


    @Transactional(readOnly = false)
    public void addUserByWxMpUser(String merchantId,AuthApp authApp, WxMpUser wxMpUser) {

        if (null == wxMpUser) {
            return;
        }

        UPassport passport = null;
        if (StringUtils.isNotEmpty(wxMpUser.getUnionId())) {
            passport = passportService.fetchByUnionID(wxMpUser.getUnionId());
        }
        if (null == passport) {
            passport = new UPassport();
            passport.setLoginName(wxMpUser.getNickname());
            passport.setMerchantId(merchantId);//商家ID
            passport.setUnionId(wxMpUser.getUnionId());
            passport.setStatus(ICommonConstant.IsEnable.enable.getCode());
            passport.setIsTest(ICommonConstant.YesNoType.no.getCode());
            passport.setAvatar(wxMpUser.getHeadImgUrl());
            //passport.setRegIp(IpUtils.getRemoteIpAddr(request));
            passport.setRegType(IUserConstant.RegType.wechat.getCode());
            passport.setRegFrom(IUserConstant.RegFrom.app.getCode());
            passportService.save(passport);
        }
        UserOpenPlatform userOpen= findByUnionId(wxMpUser.getUnionId());
        if (null == userOpen) {
            userOpen = new UserOpenPlatform();
        }
        userOpen.setUid(passport.getId());
//				userOpen.setAccessToken(wxMpUser.get);
        if(null != authApp) {
            userOpen.setAppId(authApp.getAppId());
        }
        userOpen.setAppType("1");//微信公众号
        userOpen.setAlbumUrl(wxMpUser.getHeadImgUrl());
        userOpen.setNickName(wxMpUser.getNickname());
        userOpen.setCity(wxMpUser.getCity());
        userOpen.setProvince(wxMpUser.getProvince());
        userOpen.setSex(wxMpUser.getSex() + "");
        userOpen.setUnionId(wxMpUser.getUnionId());
        userOpen.setOpenId(wxMpUser.getOpenId());
        userOpen.setMemo(wxMpUser.getRemark());
        userOpen.setPersonalInfo(JacksonUtils.toJson(wxMpUser));
        String crDate = com.haohan.framework.utils.DateUtils.fromUinxTime(wxMpUser.getSubscribeTime(), "yyyy-MM-dd HH:mm:ss");
        userOpen.setCreateTime(DateUtils.parseDate(crDate));
        userOpen.setUpdateDate(new Date());
        save(userOpen);
    }

    private UserOpenPlatform findByUnionId(String unionId) {
        UserOpenPlatform openPlatform = new UserOpenPlatform();
        if (StringUtils.isEmpty(unionId)){
            return null;
        }
        openPlatform.setUnionId(unionId);

        List<UserOpenPlatform> list = findList(openPlatform);

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    public Integer countUserNum(String merchantId, String shopId, Date startTime, Date endTime) {
        Integer result = dao.countUserNum(merchantId,shopId,startTime,endTime);
        return null == result ? 0 : result;
    }

    /**
     * 可传入merchantId/shopId 查询对应开发平台用户
     * @param userOpenPlatform 必须传pmId
     * @return
     */
    public List<UserOpenPlatform> findListByPmId(UserOpenPlatform userOpenPlatform) {
        return dao.findListByPmId(userOpenPlatform);
    }
}