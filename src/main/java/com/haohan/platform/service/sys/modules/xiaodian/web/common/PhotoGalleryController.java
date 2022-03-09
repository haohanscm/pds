package com.haohan.platform.service.sys.modules.xiaodian.web.common;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOssConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGallery;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoUpload;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoGalleryService;
import com.haohan.platform.service.sys.modules.xiaodian.util.OssUploadUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 图片资源库管理Controller
 * @author haohan
 * @version 2018-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/common/photoGallery")
public class PhotoGalleryController extends BaseController {

	@Autowired
	private PhotoGalleryService photoGalleryService;
	
	@ModelAttribute
	public PhotoGallery get(@RequestParam(required=false) String id) {
		PhotoGallery entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = photoGalleryService.get(id);
		}
		if (entity == null){
			entity = new PhotoGallery();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:common:photoGallery:view")
	@RequestMapping(value = {"list", ""})
	public String list(PhotoGallery photoGallery, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PhotoGallery> page = photoGalleryService.findPage(new Page<PhotoGallery>(request, response), photoGallery); 
		model.addAttribute("page", page);
		return "modules/xiaodian/common/photoGalleryList";
	}

	@RequiresPermissions("xiaodian:common:photoGallery:view")
	@RequestMapping(value = "form")
	public String form(PhotoGallery photoGallery, Model model) {
		model.addAttribute("photoGallery", photoGallery);
		return "modules/xiaodian/common/photoGalleryForm";
	}

	@RequiresPermissions("xiaodian:common:photoGallery:view")
	@RequestMapping(value = "batchUpload")
	public String batchUpload(PhotoUpload photoUpload, Model model) {
		model.addAttribute("photoUpload", photoUpload);
		return "modules/xiaodian/common/photoGalleryAdd";
	}



	@RequiresPermissions("xiaodian:common:photoGallery:edit")
	@RequestMapping(value = "save")
	public String save(PhotoGallery photoGallery, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, photoGallery)){
			return form(photoGallery, model);
		}
		photoGalleryService.save(photoGallery);
		addMessage(redirectAttributes, "保存图片成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/photoGallery/?repage";
	}


	@RequiresPermissions("xiaodian:common:photoGallery:edit")
	@RequestMapping(value = "uploadPhoto/{merchantId}/{type}")
	@ResponseBody
	public BaseResp uploadPhoto(@RequestParam("file") MultipartFile files, @PathVariable("merchantId") String merchantId, @PathVariable("type") String type, HttpServletRequest request) {

		PhotoGallery photoGallery = new PhotoGallery();
		BaseResp resp;
		boolean keepName =false;
		try {
            resp = OssUploadUtils.upload(merchantId, files, type, keepName);
            if(resp.isSuccess()) {
                photoGallery.setPicSize(String.valueOf(files.getSize()));
                photoGallery.setPicType(IOssConstant.picTypeFileCode);
                photoGallery.setOssType(IOssConstant.aliyunCode);
                photoGallery.setPicName(resp.getExt().toString());
                photoGallery.setPicUrl(resp.getMsg());
                photoGallery.setPicFrom("platform");
                photoGallery.setStatus(IMerchantConstant.available);
                photoGalleryService.save(photoGallery);
                resp.setExt(photoGallery.toJson());
                return resp;
            }
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return  BaseResp.newError();
	}
	
	@RequiresPermissions("xiaodian:common:photoGallery:edit")
	@RequestMapping(value = "delete")
	public String delete(PhotoGallery photoGallery, RedirectAttributes redirectAttributes) {
		photoGalleryService.delete(photoGallery);
		addMessage(redirectAttributes, "删除图片成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/photoGallery/?repage";
	}

}