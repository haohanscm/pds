package com.haohan.platform.service.sys.modules.xiaodian.service.common;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.common.PhotoGalleryDao;
import com.haohan.platform.service.sys.modules.xiaodian.dao.common.PhotoGroupManageDao;
import com.haohan.platform.service.sys.modules.xiaodian.dao.common.PhotoManageDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGallery;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGroupManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoManage;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 图片组管理Service
 *
 * @author haohan
 * @version 2018-01-12
 */
@Service
@Transactional(readOnly = true)
public class PhotoGroupManageService extends CrudService<PhotoGroupManageDao, PhotoGroupManage> {

    @Autowired
    private PhotoManageDao photoManageDao;

    @Autowired
    private PhotoGalleryDao photoGalleryDao;

    public PhotoGroupManage get(String id) {
        PhotoGroupManage photoGroupManage = super.get(id);
        photoGroupManage.setPhotoManageList(photoManageDao.findList(new PhotoManage(photoGroupManage)));
        return photoGroupManage;
    }

    public PhotoGroupManage fetchPhotoGroupManage(String merchantId,String groupNum) {
        PhotoGroupManage photoGroupManage = new PhotoGroupManage();
        photoGroupManage.setMerchantId(merchantId);
        photoGroupManage.setGroupNum(groupNum);
        List<PhotoGroupManage> list = findList(photoGroupManage);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        photoGroupManage = list.get(0);
        photoGroupManage.setPhotoManageList(photoManageDao.findList(new PhotoManage(photoGroupManage)));
        return photoGroupManage;
    }


    public List<PhotoGroupManage> findList(PhotoGroupManage photoGroupManage) {
        return super.findList(photoGroupManage);
    }

    public Page<PhotoGroupManage> findPage(Page<PhotoGroupManage> page, PhotoGroupManage photoGroupManage) {
        return super.findPage(page, photoGroupManage);
    }

    public PhotoGroupManage fetchByGroupNum(String groupNum){
        PhotoGroupManage photoGroupManage = new PhotoGroupManage();
        photoGroupManage.setGroupNum(groupNum);
        List<PhotoGroupManage> list = findList(photoGroupManage);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        photoGroupManage = list.get(0);
        photoGroupManage.setPhotoManageList(photoManageDao.findList(new PhotoManage(photoGroupManage)));
        return photoGroupManage;
    }

    @Transactional(readOnly = false)
    public void save(PhotoGroupManage photoGroupManage) {

        for (PhotoManage photoManage : photoGroupManage.getPhotoManageList()) {
            String groupNum = photoGroupManage.getGroupNum();
            if(StringUtils.isNotBlank(groupNum)) {
                PhotoGroupManage groupManage = fetchByGroupNum(groupNum);
                if(null == groupManage){
                    super.save(photoGroupManage);
                }
                photoManage.setPhotoGroupManage(photoGroupManage);
            }
            if(null !=photoManage.getPhotoGallery() && StringUtils.isNotBlank(photoManage.getPhotoGallery().getId())){
                PhotoGallery photoGallery =  photoGalleryDao.get(photoManage.getPhotoGallery().getId());
                if(null != photoGallery){
                    photoManage.setPicName(photoGallery.getPicName());
                    photoManage.setPicUrl(photoGallery.getPicUrl());
                    photoManage.setPhotoGallery(photoGallery);
                }
            }
            if (PhotoManage.DEL_FLAG_NORMAL.equals(photoManage.getDelFlag())) {
                if (StringUtils.isBlank(photoManage.getId())) {
                    photoManage.setCreateDate(new Date());
                    photoManage.preInsert();
                    photoManageDao.insert(photoManage);
                } else {
                    photoManage.preUpdate();
                    photoManageDao.update(photoManage);
                }
            } else {
                photoManageDao.delete(photoManage);
            }
        }
    }

    @Transactional(readOnly = false)
    public void delete(PhotoGroupManage photoGroupManage) {
        super.delete(photoGroupManage);
        photoManageDao.delete(new PhotoManage(photoGroupManage));
    }

    @Transactional(readOnly = false)
    public void saveAll(PhotoGroupManage groupPhotos) {

        super.save(groupPhotos);

        String ids = groupPhotos.getPhotoGalleryIds();
        for (PhotoManage photo : groupPhotos.getPhotoManageList()) {
            if (photo.getId() == null) {
                continue;
            }

        if(StringUtils.isNotBlank(ids) &&  StringUtils.isNotBlank(photo.getId())) {
            String[] photoGalleryIds = ids.split(",");
        for (String id : photoGalleryIds) {
            PhotoManage photoManage = new PhotoManage();
            PhotoGallery photoGallery = photoGalleryDao.get(id);
            photoManage.setPhotoGallery(photoGallery);
            photoManage.setPhotoGroupManage(groupPhotos);
            if(null !=photoGallery) {
                photoManage.setPicUrl(photoGallery.getPicUrl());
                photoManage.setPicName(photoGallery.getPicName());
            }
            photoManage.setId(photo.getId());
            photoManageDao.update(photoManage);
         }
        }
        }

    }

    @Transactional(readOnly = false)
    public void saveOnly(PhotoGroupManage groupPhotos) {
        super.save(groupPhotos);
    }



}