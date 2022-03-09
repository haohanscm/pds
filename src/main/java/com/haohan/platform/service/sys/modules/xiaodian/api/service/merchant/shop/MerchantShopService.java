package com.haohan.platform.service.sys.modules.xiaodian.api.service.merchant.shop;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.manage.RespShop;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.shop.ReqShop;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.shop.RespShopInfo;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IShopConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGallery;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGroupManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoGroupManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 商户 店铺管理
 * Created by dy on 2018/10/6.
 */
@Service
@Transactional(readOnly = true)
public class MerchantShopService {

    @Autowired
    @Lazy(true)
    private ShopService shopService;
    @Autowired
    @Lazy(true)
    private PhotoGroupManageService photoGroupManageService;

    /**
     * 获取店铺详情
     *
     * @param shop
     * @return
     */
    public RespShopInfo fetchShopInfo(Shop shop) {
        Shop findShop = shopService.get(shop.getId());
        if (null == findShop) {
            return null;
        }
        if (!StringUtils.equals(shop.getMerchantId(), findShop.getMerchantId())) {
            return null;
        }
        RespShopInfo shopInfo = new RespShopInfo();
        // 店铺信息
        shopInfo.copyFromShop(findShop);
        // 返回信息处理  图片
        //设置店铺Logo
        String logoUrl = "";
        if (StringUtils.isNotEmpty(shopInfo.getShopLogo())) {
            logoUrl = fetchPhotoUrl(shopInfo.getShopLogo());
        }
        shopInfo.setShopLogo(logoUrl);

        //设置店铺支付二维码
        String payCodeUrl = "";
        if (StringUtils.isNotEmpty(shopInfo.getPayCode())) {
            payCodeUrl = fetchPhotoUrl(shopInfo.getPayCode());
        }
        shopInfo.setPayCode(payCodeUrl);

        //设置店铺二维码
        String qrcodeUrl = "";
        if (StringUtils.isNotEmpty(shopInfo.getQrcode())) {
            qrcodeUrl = fetchPhotoUrl(shopInfo.getQrcode());
        }
        shopInfo.setQrcode(qrcodeUrl);
        return shopInfo;
    }

    /**
     * 修改店铺信息
     *
     * @param reqShop
     * @return
     */
    @Transactional(readOnly = false)
    public BaseResp modifyShopInfo(ReqShop reqShop) {
        BaseResp resp = BaseResp.newError();
        Shop findShop = shopService.get(reqShop.getShopId());
        if (null == findShop) {
            return resp;
        }
        if (!StringUtils.equals(reqShop.getMerchantId(), findShop.getMerchantId())) {
            return resp;
        }
        // 复制需修改属性
        Shop shop = new Shop();
        shop.setId(reqShop.getShopId());
        shop.setMerchantId(reqShop.getMerchantId());
        reqShop.transfModifyProperties(shop);
        // 修改图片
        IShopConstant.ShopPhotoType type;
        String groupNum;
        // logo
        if (StringUtils.isNotEmpty(reqShop.getLogoGalleryId())) {
            type = IShopConstant.ShopPhotoType.logo;
            groupNum = saveShopPhoto(reqShop, type, reqShop.getLogoGalleryId());
        } else {
            groupNum = null;
        }
        shop.setShopLogo(groupNum);
        // 收款码
        if (StringUtils.isNotEmpty(reqShop.getPayCodeGalleryId())) {
            type = IShopConstant.ShopPhotoType.payCode;
            groupNum = saveShopPhoto(reqShop, type, reqShop.getPayCodeGalleryId());
        } else {
            groupNum = null;
        }
        shop.setPayCode(groupNum);
        // 二维码
        if (StringUtils.isNotEmpty(reqShop.getQrcodeGalleryId())) {
            type = IShopConstant.ShopPhotoType.qrcode;
            groupNum = saveShopPhoto(reqShop, type, reqShop.getQrcodeGalleryId());
        } else {
            groupNum = null;
        }
        shop.setQrcode(groupNum);
        int result = shopService.modifyShopInfo(shop);
        if (result > 0) {
            resp.success();
        }
        return resp;
    }


    /**
     * 添加店铺
     *
     * @param reqShop
     * @return
     */
    @Transactional(readOnly = false)
    public BaseResp addShop(ReqShop reqShop) {
        Shop shop = new Shop();
        reqShop.transfShop(shop);
        // 设置初始属性
        shop.setStatus(IShopConstant.ShopStatus.check.getCode());
        shop.setIsUpdateJisu(ICommonConstant.YesNoType.no.getCode());
        shopService.settingShopLevel(shop);
        shop.setId(IdGen.uuid());
        shop.setIsNewRecord(true);
        // 用于保存图片
        reqShop.setShopId(shop.getId());
        // logo图片 保存
        IShopConstant.ShopPhotoType type;
        String groupNum;
        // logo
        if (StringUtils.isNotEmpty(reqShop.getLogoGalleryId())) {
            type = IShopConstant.ShopPhotoType.logo;
            groupNum = saveShopPhoto(reqShop, type, reqShop.getLogoGalleryId());
        } else {
            groupNum = null;
        }
        shop.setShopLogo(groupNum);
        shopService.save(shop);
        BaseResp baseResp = BaseResp.newSuccess();
        RespShop respShop = new RespShop();
        respShop.setShopId(shop.getId());
        respShop.setName(shop.getName());
        baseResp.setExt(respShop);
        return baseResp;
    }

    /**
     * 根据图片组编号 查找图片  只要第1张
     * @param groupNum
     * @return
     */
    private String fetchPhotoUrl(String groupNum) {
        //设置店铺Logo
        String url = "";
        try {
            PhotoGroupManage groupManage = photoGroupManageService.fetchByGroupNum(groupNum);
            if (null != groupManage && !Collections3.isEmpty(groupManage.getPhotoManageList())) {
                url = groupManage.getPhotoManageList().get(0).getPicUrl();
            }
        } catch (Exception e) {
        }
        return url;
    }

    /**
     * 查找店铺下相应类型的图片 更新第1张
     *
     * @param shop 必需shopId  用于生成图片组编号
     * @param type
     * @return 返回图片组编号
     */
    private String saveShopPhoto(ReqShop shop, IShopConstant.ShopPhotoType type, String photoGalleryId) {
        List<PhotoManage> list = new ArrayList<>();
        PhotoManage photoManage;
        // 拼接 groupNum
        String groupNum = shop.getShopId() + "-" + type.getCode();
        // 查找以前的图片
        PhotoGroupManage groupManage = photoGroupManageService.fetchByGroupNum(groupNum);
        // 找到时  替换第1张
        if (null != groupManage && !Collections3.isEmpty(groupManage.getPhotoManageList())) {
            photoManage = groupManage.getPhotoManageList().get(0);
            photoManage.setPhotoGallery(new PhotoGallery(photoGalleryId));
        } else {
            groupManage = new PhotoGroupManage();
            groupManage.setMerchantId(shop.getMerchantId());
            groupManage.setGroupNum(groupNum);
            groupManage.setGroupName(type.getDesc());
            photoManage = new PhotoManage();
            photoManage.setPhotoGallery(new PhotoGallery(photoGalleryId));
        }
        // 只更新第一张
        list.add(photoManage);
        groupManage.setPhotoManageList(list);
        photoGroupManageService.save(groupManage);
        return groupNum;
    }


}
