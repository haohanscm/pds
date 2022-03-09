package com.haohan.platform.service.sys.modules.xiaodian.api.inf;


import com.haohan.framework.entity.BaseResp;

import java.io.File;

public interface IOssFileManageBiz {

	/**
	 * 
	 * @Title: uploadTempFile
	 * @Description: 普通文件
	 * @param filePath
	 *            文件路径 不包含目录
	 * @param fileName
	 * @return
	 */
	BaseResp uploadTempFile(String filePath, String fileName);

	/**
	 * 
	 * @Title: tempToUploadFile
	 * @Description: 暂存目录上传正式目录
	 * @param oldFileName
	 *            文件相对文件名
	 * @param newFileName
	 *            新的文件目录和名称
	 * @return
	 */
	BaseResp tempToUploadFile(String oldFileName, String newFileName);

	/**
	 * 
	 * @Title: deleteFile
	 * @Description: 删除文件
	 * @param fileName
	 */
	void deleteFile(String fileName);

	/**
	 * 
	 * @Title: hasAliyunFileObj
	 * @Description: 判断阿里云文件是否存在
	 * @param fileName
	 * @return
	 */
	BaseResp hasAliyunFileObj(String fileName);


	BaseResp uploadFile(File file, String fileName);
}
