package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.GenerateUtil;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOssConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.inf.IOssFileManageBiz;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGallery;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoGalleryService;
import com.haohan.platform.service.sys.modules.xiaodian.util.OssUploadUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @ClassName: UploadCtrl
 * @Description: 上传文件类，上传到临时文件夹中
 * @date Nov 28, 2017 11:07:58 AM
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/upload")
public class UploadCtrl {

	@Resource
	private IOssFileManageBiz fileManageBiz;

	@Autowired
	private PhotoGalleryService photoGalleryService;


	@RequestMapping("photo")
	@ResponseBody
	public BaseResp uploadPhoto(@RequestParam("file") MultipartFile files, String id, String parent, @RequestParam(defaultValue = "false") Boolean keepName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (null == files) {
			return BaseResp.newError();
		}
		PhotoGallery photoGallery = new PhotoGallery();
		HashMap<String,String> resultMap = new HashMap<>(8);
		if(StringUtils.isEmpty(id)){
			id= IdGen.uuid();
		}
		if(StringUtils.isEmpty(parent)){
			parent= IMerchantConstant.MerchantFilesType.ShopPhotos.getGroupNum();
		}

		BaseResp resp =  OssUploadUtils.upload(id, files, parent, keepName);
		if(resp.isSuccess()) {
			photoGalleryService.transfPhoto(photoGallery, String.valueOf(files.getSize()), resp.getExt().toString(), resp.getMsg(), parent);
			photoGalleryService.save(photoGallery);
			resultMap.put("src",resp.getMsg());
			resp.setExt(resultMap);
			return resp;
		}
		return BaseResp.newError();
	}
	/**
	 * 
	 * @Title: uploadtemp
	 * @Description: 上传普通文件进暂存目录
	 * @param id
	 * @param parent
	 *            文件上层目录
	 * @param keepName
	 *            保持名称
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("temp")
	@ResponseBody
	public BaseResp uploadtemp(@RequestParam("file") MultipartFile[] files, String id, String parent, @RequestParam(defaultValue = "false") Boolean keepName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (null == files || files.length == 0) {
			return BaseResp.newError();
		}
		BaseResp br = BaseResp.newSuccess();
		ArrayList<String> list = new ArrayList<>();

		for (int i = 0; i < files.length; i++) {
			BaseResp b = upload(id, files[i], parent, keepName);
			if (b.isSuccess()) {
				list.add(b.getMsg());
			}
		}
		br.ext(list);
		return br;
	}

	/**
	 * 
	 * @Title: uploadtemp
	 * @Description: 上传普通文件进暂存目录
	 * @param id
	 * @param parent
	 *            文件上层目录
	 * @param keepName
	 *            保持名称
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("temptwo")
	@ResponseBody
	public BaseResp uploadtempTwo(String id, String parent, @RequestParam(defaultValue = "false") Boolean keepName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		BaseResp br = BaseResp.newSuccess();
		ArrayList<String> list = new ArrayList<>();

		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 记录上传过程起始时的时间，用来计算上传时间
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					BaseResp b = upload(id, file, parent, keepName);
					if (b.isSuccess()) {
						list.add(b.getMsg());
					}
				}
			}

		}

		br.setExt(list);

		return br;
	}

	private BaseResp upload(String id, MultipartFile mf, String parent, Boolean keepName) throws IOException {

		BaseResp resp = new BaseResp();

		String tempPath = OssUploadUtils.genTempPath(id, IOssConstant.STATIC_TEMP_FILE + (StringUtils.isBlank(parent) ? "" : File.separator + parent));
		String filePath = IOssConstant.STATIC_RESOURSE_DISK_PATH;
		if (!(filePath.endsWith("/") || filePath.endsWith("\\"))) {
			filePath += "/";
		}
		filePath += tempPath;
		Path path = Paths.get(filePath);
		// 创建文件夹
		if (Files.notExists(path)) {
			Files.createDirectories(path);
		}
		String ofn = mf.getOriginalFilename();
		String fileName = ofn;
		if (!keepName) {
			String suffix = "";
			if (StringUtils.isNotBlank(ofn) && ofn.contains(".")) {
				suffix = ofn.substring(ofn.lastIndexOf("."));
			}
			if ((!suffix.toUpperCase().equals((".JPEG"))) && (!suffix.toUpperCase().equals((".GIF"))) && (!suffix.toUpperCase().equals((".JPG"))) && (!suffix.toUpperCase().equals((".PNG"))) && (!suffix.toUpperCase().equals((".BMP")))) {
				return resp.putStatus(RespStatus.NOT_FOUND_ERROR);
			}
			String timestamp = String.valueOf(System.currentTimeMillis());
			fileName = timestamp.concat(GenerateUtil.getRandomStr(4)).concat(suffix);
		}

		Path p = Paths.get(filePath + fileName);
		if (keepName) {
			Files.deleteIfExists(p);
		}
		Files.createFile(p);
		File file = p.toFile();
		mf.transferTo(file);

		BaseResp br = fileManageBiz.uploadTempFile(p.toString(), (tempPath + fileName).replace('\\', '/'));
		if (!br.isSuccess()) {
			return br;
		}
		// ---
		String url = IOssConstant.ALIYUN_FILE_BUCKET_HTTP;
		url = url.concat("/");
		url = url + tempPath + fileName;
		url = url.replace('\\', '/');

		resp = BaseResp.newSuccess();
		resp.setMsg(url);
		return resp;
	}
}
