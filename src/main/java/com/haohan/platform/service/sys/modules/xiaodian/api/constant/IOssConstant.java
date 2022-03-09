package com.haohan.platform.service.sys.modules.xiaodian.api.constant;

import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;

public interface IOssConstant {

	String AliyunOssType = "aliyun_oss_params";

	public static String ALIYUN_ACCESS_KEY = DictUtils.getDictValue("accessKey",AliyunOssType,"xad");

	public static String ALIYUN_ACCESS_SECRET = DictUtils.getDictValue("secretKey",AliyunOssType,"xas");

	public static String ALIYUN_FILE_BUCKET = DictUtils.getDictValue("bucket",AliyunOssType,	"-file");

	public static String ALIYUN_FILE_BUCKET_HTTP = DictUtils.getDictValue("bucket-domain",AliyunOssType,	"http://s-file.oss-cn-beijing.aliyuncs.com");

	public static String STATIC_RESOURSE_DISK_PATH = DictUtils.getDictValue("disk-path",AliyunOssType,	"http://s-file.oss-cn-beijing.aliyuncs.com");
	public static String STATIC_TEMP_FILE = "temp";
	public static String STATIC_UPLOAD_FILE = "upload";

	public static final String ossType = "oss_type";

	public static String STATIC_MERCHANT_FILE_FLODER = "merchantFiles";

	public static final String aliyunCode = DictUtils.getDictValue("阿里云",ossType,"");

	public static final String picType = "pic_type";
	public static final String picTypeFileCode = DictUtils.getDictValue("资料",picType,"");

}
