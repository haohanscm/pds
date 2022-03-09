package com.haohan.platform.service.sys.modules.xiaodian.api.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOssConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.inf.IOssFileManageBiz;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class OssFileManageAliyunBizImpl implements IOssFileManageBiz {

	public static final String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
	// private static final String MTS_REGION = "cn-beijing";
	// private static final String OSS_REGION = "oss-cn-beijing";
	// private static final String mtsEndpoint = "mts.cn-beijing.aliyuncs.com";

	public BaseResp uploadTempFile(String filePath, String fileName) {
		// 创建OSSClient实例
		BaseResp resp = BaseResp.newSuccess();
		OSSClient ossClient = new OSSClient(ENDPOINT, IOssConstant.ALIYUN_ACCESS_KEY, IOssConstant.ALIYUN_ACCESS_SECRET);
		// 上传
		String bucketName = IOssConstant.ALIYUN_FILE_BUCKET;

		try {
			ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(Files.readAllBytes(Paths.get(filePath))));

			resp.setExt(IOssConstant.ALIYUN_FILE_BUCKET_HTTP.concat("/").concat(fileName));

			return resp;

		} catch (OSSException oe) {
			// System.out.println("Caught an OSSException, which means your request made it
			// to OSS, " + "but was rejected with an error response for some reason.");
			// System.out.println("Error Message: " + oe.getErrorCode());
			// System.out.println("Error Code: " + oe.getErrorCode());
			// System.out.println("Request ID: " + oe.getRequestId());
			// System.out.println("Host ID: " + oe.getHostId());
//			LogUtils.error(oe.getMessage(), oe);
			return BaseResp.newError().msg(JacksonUtils.toJson(oe));
		} catch (ClientException ce) {
			// System.out.println("Caught an ClientException, which means the client
			// encountered " + "a serious internal problem while trying to communicate with
			// OSS, " + "such as not being able to access the network.");
			// System.out.println("Error Message: " + ce.getMessage());
//			LogUtils.error(ce.getMessage(), ce);
			return BaseResp.newError().msg(JacksonUtils.toJson(ce));
		} catch (IOException ee) {
//			LogUtils.error(ee.getMessage(), ee);
			return BaseResp.newError().msg(ee.getMessage());
		} finally {
			/*
			 * Do not forget to shut down the client finally to release all allocated resources.
			 */
			// 关闭client
			ossClient.shutdown();
		}
	}

	public BaseResp tempToUploadFile(String oldFileName, String newFileName) {

		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(ENDPOINT, IOssConstant.ALIYUN_ACCESS_KEY, IOssConstant.ALIYUN_ACCESS_SECRET);
		// 上传
		String bucketName = IOssConstant.ALIYUN_FILE_BUCKET;

		try {
			ossClient.copyObject(bucketName, oldFileName, bucketName, newFileName);
			deleteFile(oldFileName);
			return BaseResp.newSuccess();
		} catch (OSSException oe) {
			// System.out.println("Caught an OSSException, which means your request made it
			// to OSS, " + "but was rejected with an error response for some reason.");
			// System.out.println("Error Message: " + oe.getErrorCode());
			// System.out.println("Error Code: " + oe.getErrorCode());
			// System.out.println("Request ID: " + oe.getRequestId());
			// System.out.println("Host ID: " + oe.getHostId());
//			LogUtils.error(oe.getMessage(), oe);
			return BaseResp.newError().msg(JacksonUtils.toJson(oe));
		} catch (ClientException ce) {
			// System.out.println("Caught an ClientException, which means the client
			// encountered " + "a serious internal problem while trying to communicate with
			// OSS, " + "such as not being able to access the network.");
			// System.out.println("Error Message: " + ce.getMessage());
//			LogUtils.error(ce.getMessage(), ce);
			return BaseResp.newError().msg(JacksonUtils.toJson(ce));
		} finally {
			/*
			 * Do not forget to shut down the client finally to release all allocated resources.
			 */
			// 关闭client
			ossClient.shutdown();
		}

	}

	public void deleteFile(String fileName) {// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(ENDPOINT, IOssConstant.ALIYUN_ACCESS_KEY, IOssConstant.ALIYUN_ACCESS_SECRET);
		try {
			ossClient.deleteObject(IOssConstant.ALIYUN_FILE_BUCKET, fileName);
		} catch (OSSException oe) {
			// System.out.println("Caught an OSSException, which means your request made it
			// to OSS, " + "but was rejected with an error response for some reason.");
			// System.out.println("Error Message: " + oe.getErrorCode());
			// System.out.println("Error Code: " + oe.getErrorCode());
			// System.out.println("Request ID: " + oe.getRequestId());
			// System.out.println("Host ID: " + oe.getHostId());
//			LogUtils.error(oe.getMessage(), oe);
		} catch (ClientException ce) {
			// System.out.println("Caught an ClientException, which means the client
			// encountered " + "a serious internal problem while trying to communicate with
			// OSS, " + "such as not being able to access the network.");
			// System.out.println("Error Message: " + ce.getMessage());

//			LogUtils.error(ce.getMessage(), ce);
		} finally {
			/*
			 * Do not forget to shut down the client finally to release all allocated resources.
			 */
			// 关闭client
			ossClient.shutdown();
		}
	}

	public BaseResp hasAliyunFileObj(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return BaseResp.newError();
		}
		OSSClient ossClient = new OSSClient(ENDPOINT, IOssConstant.ALIYUN_ACCESS_KEY, IOssConstant.ALIYUN_ACCESS_SECRET);

		// Object是否存在
		boolean found = ossClient.doesObjectExist(IOssConstant.ALIYUN_FILE_BUCKET, fileName);
		// 关闭client
		ossClient.shutdown();
		if (found) {
			return BaseResp.newSuccess().ext(fileName);
		}
		return BaseResp.newError();
	}

	@Override
	public BaseResp uploadFile(File file, String fileName) {
		// 创建OSSClient实例
		BaseResp resp = BaseResp.newSuccess();
		OSSClient ossClient = new OSSClient(ENDPOINT, IOssConstant.ALIYUN_ACCESS_KEY, IOssConstant.ALIYUN_ACCESS_SECRET);
		// 上传
		String bucketName = IOssConstant.ALIYUN_FILE_BUCKET;

		try {
			resp.setExt(IOssConstant.ALIYUN_FILE_BUCKET_HTTP.concat("/").concat(fileName));

			ossClient.putObject(bucketName,fileName,file);

			return resp;

		} catch (Exception e) {
			e.printStackTrace();
			resp.error();
			resp.setMsg("图片上传阿里云失败");
			return resp;
		} finally {
			// 关闭client
			ossClient.shutdown();
		}
	}

}
