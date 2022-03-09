package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGroupManage;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgw on 2018/1/13.
 */
public class MerchantFiles implements Serializable{


    private String merchantId;

    private PhotoGroupManage shopPhotos;

    private PhotoGroupManage merchantPhotos;

    private PhotoGroupManage protocolFiles;

    private PhotoGroupManage productPhotos;

    //新增 by shenyu
    private PhotoGroupManage cateringPhotos;

    private PhotoGroupManage licensePhotos;

    private PhotoGroupManage idCardsPhotos;

    private PhotoGroupManage bankCardsPhotos;

    public PhotoGroupManage getLicensePhotos() {
        return licensePhotos;
    }

    public void setLicensePhotos(PhotoGroupManage licensePhotos) {
        this.licensePhotos = licensePhotos;
    }

    public PhotoGroupManage getIdCardsPhotos() {
        return idCardsPhotos;
    }

    public void setIdCardsPhotos(PhotoGroupManage idCardsPhotos) {
        this.idCardsPhotos = idCardsPhotos;
    }

    public PhotoGroupManage getBankCardsPhotos() {
        return bankCardsPhotos;
    }

    public void setBankCardsPhotos(PhotoGroupManage bankCardsPhotos) {
        this.bankCardsPhotos = bankCardsPhotos;
    }

    public PhotoGroupManage getCateringPhotos() {
        return cateringPhotos;
    }

    public void setCateringPhotos(PhotoGroupManage cateringPhotos) {
        this.cateringPhotos = cateringPhotos;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public PhotoGroupManage getShopPhotos() {
        return shopPhotos;
    }

    public void setShopPhotos(PhotoGroupManage shopPhotos) {
        this.shopPhotos = shopPhotos;
    }

    public PhotoGroupManage getMerchantPhotos() {
        return merchantPhotos;
    }

    public void setMerchantPhotos(PhotoGroupManage merchantPhotos) {
        this.merchantPhotos = merchantPhotos;
    }

    public PhotoGroupManage getProtocolFiles() {
        return protocolFiles;
    }

    public void setProtocolFiles(PhotoGroupManage protocolFiles) {
        this.protocolFiles = protocolFiles;
    }

    public PhotoGroupManage getProductPhotos() {
        return productPhotos;
    }

    public void setProductPhotos(PhotoGroupManage productPhotos) {
        this.productPhotos = productPhotos;
    }

    public String toJson(){

        return JacksonUtils.toJson(this);
    }

    public static MerchantFiles trsToMerchantFiles(List<PhotoGroupManage> list,String merchantId) {

        MerchantFiles merchantFiles = new MerchantFiles();

        Map<String,PhotoGroupManage> mapFiles = new HashMap<>();
        if(CollectionUtils.isNotEmpty(list)){
            for (PhotoGroupManage photoGroupManage:list){
                mapFiles.put(photoGroupManage.getGroupNum().replace(merchantId.concat("-"),""),photoGroupManage);
            }
        }

        merchantFiles.setShopPhotos(mapFiles.get(IMerchantConstant.shopPhotosNum));
        merchantFiles.setMerchantPhotos(mapFiles.get(IMerchantConstant.merchantPhotosNum));
        merchantFiles.setProtocolFiles(mapFiles.get(IMerchantConstant.protocolFilesNum));
        merchantFiles.setProductPhotos(mapFiles.get(IMerchantConstant.productPhotosNum));
        merchantFiles.setMerchantId(merchantId);

        return merchantFiles;
    };


    private String fetchGroupNum(String merchantId,String suffix){

        return merchantId.concat("-").concat(suffix);
    }


    public String genGroupNum(String merchantId, IMerchantConstant.MerchantFilesType merchantFilesType) {
        return fetchGroupNum(merchantId,merchantFilesType.getGroupNum());
    }
}
