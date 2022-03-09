package com.haohan.platform.service.sys.modules.xiaodian.api.service.merchant.common;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGallery;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoGalleryService;
import com.haohan.platform.service.sys.modules.xiaodian.util.OssUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * 商户 店铺管理
 * Created by dy on 2018/10/6.
 */
@Service
@Transactional(readOnly = true)
public class MerchantCommonService {

    @Autowired
    @Lazy(true)
    private PhotoGalleryService photoGalleryService;

    /**
     * 保存上传的图片
     *
     * @param files      图片文件
     * @param merchantId 商家id
     * @param type       文件类型
     * @return
     */
    @Transactional(readOnly = false)
    public BaseResp savePhoto(MultipartFile files, String merchantId, String type) {
        BaseResp resp;
        if (StringUtils.isEmpty(merchantId)) {
            merchantId = IMerchantConstant.DEFAULT_MERCHANT_ID;
        }
        // 图片保存类型 默认 店铺照片
        IMerchantConstant.MerchantFilesType fileType = IMerchantConstant.MerchantFilesType.ShopPhotos.fetchMerchantFiles(type);
        if(null == fileType){
            type = IMerchantConstant.MerchantFilesType.ShopPhotos.getGroupNum();
        }
        // 上传至阿里云 并在图片资源库保存
        try {
            PhotoGallery photoGallery = new PhotoGallery();
            HashMap<String, String> resultMap = new HashMap<>(8);
            resp = OssUploadUtils.upload(merchantId, files, type, false);
            if (resp.isSuccess()) {
                photoGalleryService.transfPhoto(photoGallery, String.valueOf(files.getSize()), resp.getExt().toString(), resp.getMsg(), type);
                photoGalleryService.save(photoGallery);
                resultMap.put("photoUrl", resp.getMsg());
                resultMap.put("photoGalleryId", photoGallery.getId());
                resp.setMsg("上传图片成功");
                resp.setExt(resultMap);
                return resp;
            }
        } catch (Exception e) {
            resp = BaseResp.newError();
            resp.setMsg("上传图片失败");
        }
        return resp;
    }

}
