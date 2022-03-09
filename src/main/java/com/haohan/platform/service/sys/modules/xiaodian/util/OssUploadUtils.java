package com.haohan.platform.service.sys.modules.xiaodian.util;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.GenerateUtil;
import com.haohan.platform.service.sys.common.utils.SpringContextHolder;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOssConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.inf.IOssFileManageBiz;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OssUploadUtils {

	/**
	 * 
	 * @Title: deleteFile
	 * @Description: 删除普通文件 可删除临时文件夹 和 上传文件夹里面的数据
	 * @author laijie
	 * @param url
	 *            文件目录
	 * @return
	 */
	public static BaseResp deleteFile(String url) {
		BaseResp resp = new BaseResp();
		if (StringUtils.isBlank(url)) {
			return resp.error();
		}
		IOssFileManageBiz fileManage = SpringContextHolder.getBean(IOssFileManageBiz.class);
		if (null == fileManage) {
			resp.error();
		}

		String http = IOssConstant.ALIYUN_FILE_BUCKET_HTTP.concat("/");
		String filePath = null;
		if (url.startsWith(http)) {
			filePath = url.replaceFirst(http, "");
		}
		if (null == filePath) {
			resp.error();
		}
		fileManage.deleteFile(filePath);
		return resp.success();
	}

	/**
	 * 
	 * @Title: tempToUploadFile
	 * @Description: 上传除音视频之外的普通文件
	 * @author laijie
	 * @param url
	 *            文件目录
	 * @param parent
	 *            上级目录
	 * @return
	 */
	public static String tempToUploadFile(String url, String... parent) {
		if (StringUtils.isBlank(url)) {
			return url;
		}
		if (url.startsWith("http") && url.contains("/" + IOssConstant.STATIC_UPLOAD_FILE + "/")) {
			return url;
		}
		IOssFileManageBiz fileManage = SpringContextHolder.getBean(IOssFileManageBiz.class);
		if (null == fileManage) {
			return url;
		}
		StringBuffer parentFile = new StringBuffer(IOssConstant.STATIC_UPLOAD_FILE);
		if (StringUtils.isBlank(url)) {
			return null;
		}
		if (null != parent && parent.length > 0) {
			for (String str : parent) {
				parentFile.append("/").append(str);
			}
		}
		String http = IOssConstant.ALIYUN_FILE_BUCKET_HTTP.concat("/").concat(IOssConstant.STATIC_TEMP_FILE);
		String filePath = null;
		if (url.startsWith(http)) {
			filePath = url.replaceFirst(http, "");
		}
		if (null == filePath) {
			return url;
		}

		String oldFileName = IOssConstant.STATIC_TEMP_FILE + filePath;
		String newFileName = parentFile + filePath;
		BaseResp br = fileManage.tempToUploadFile(oldFileName, newFileName);
		if (br.isSuccess()) {
			url = IOssConstant.ALIYUN_FILE_BUCKET_HTTP.concat("/").concat(parentFile.toString()).concat(filePath);
		}

		// 本地处理集群是可能为空
		Path p = Paths.get(IOssConstant.STATIC_RESOURSE_DISK_PATH + IOssConstant.STATIC_TEMP_FILE + filePath);
		if (Files.notExists(p)) {
			return url;
		}
		Path up = Paths.get(IOssConstant.STATIC_RESOURSE_DISK_PATH + parentFile.toString() + filePath);
		if (Files.notExists(up.getParent())) {
			try {
				Files.createDirectories(up.getParent());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			Files.move(p, up, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return url;
	}

	/**
	 * 
	 * @Title: genTempPath
	 * @Description: 获取暂存目录
	 * @author laijie
	 * @param id
	 * @param parentDir
	 * @return
	 */
	public static String genTempPath(String id, String parentDir) {
		StringBuilder pidSb = new StringBuilder(13);
		if (StringUtils.isNotBlank(id)) {
			if (StringUtils.isNumeric(id)) {
				pidSb.append(StringUtils.leftPad(id.toString(), 9, "0"));
				pidSb.insert(7, File.separatorChar).insert(5, File.separatorChar).insert(3, File.separatorChar);
			} else {
				pidSb.append(id);
			}
			pidSb.append(File.separator);
		}
		//
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM" + File.separator + "dd" + File.separator);
		String dateStr = sdf.format(date);
		//
		StringBuilder sb = new StringBuilder(30);
		sb.append(parentDir);
		sb.append(File.separator);
		sb.append(dateStr);
		sb.append(pidSb.toString());
		return sb.toString();
	}

	/**
	 * 头像磁盘相对目录
	 * 
	 * @return
	 */
	public static String genPhotoPath(Long id, String parentDir) {
		StringBuilder pidSb = new StringBuilder(13);
		pidSb.append(StringUtils.leftPad(id.toString(), 9, "0"));
		pidSb.insert(7, File.separatorChar).insert(5, File.separatorChar).insert(3, File.separatorChar);
		//
		// Date date=new Date();
		// SimpleDateFormat sdf=new
		// SimpleDateFormat("yyyyMM"+File.separator+"dd"+File.separator);
		// String dateStr= sdf.format(date);
		//
		StringBuilder sb = new StringBuilder(30);
		sb.append(parentDir);
		sb.append(File.separator);
		// sb.append(dateStr);
		sb.append(pidSb.toString());
		sb.append(File.separator);
		// System.out.println(pidSb.length()+"~"+sb.length());//长度测算
		return sb.toString();
	}

	public static String getFilePath(String id,String type){

		//日期
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd" + File.separator);
		String dateStr = sdf.format(date);
		//路径拼接
		StringBuilder sb = new StringBuilder();
		if(type.equals("0")){
			sb.append("shopTemplateFiles");
		}else{
			sb.append(IOssConstant.STATIC_MERCHANT_FILE_FLODER);
		}
		sb.append(File.separator);
		sb.append(id);
		sb.append(File.separator);
		sb.append(type);
		sb.append(File.separator);
		sb.append(dateStr);

		return sb.toString();
	}

	public static BaseResp upload(String id, MultipartFile mf, String type, Boolean keepName) throws IOException {

		BaseResp resp = new BaseResp();
		String tempPath = getFilePath(id,type);

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
			if (org.apache.commons.lang3.StringUtils.isNotBlank(ofn) && ofn.contains(".")) {
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


		IOssFileManageBiz fileManageBiz = SpringContextHolder.getBean(IOssFileManageBiz.class);
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
		resp.setExt(ofn);
		return resp;
	}


}
