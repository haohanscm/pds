package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.util;

import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import static org.springframework.util.ResourceUtils.getFile;

public class XmBankConfigUtil {
	private static String CONFIG_FILEPATH =   "";
//			".\\src\\main\\resources\\Config.properties";

	private static Properties prop;
	static {
		prop = new Properties();
		try {
			String proFile = "classpath:pay/config.properties";
			CONFIG_FILEPATH = getFile(proFile).getAbsolutePath();
			InputStream in = new FileInputStream(CONFIG_FILEPATH);
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getPropertie(String key) {
		if (null == key) {
			return "";
		}
		if (null == prop) {
			return "";
		}
		String value = prop.getProperty(key);
		if (null == value) {
			return "";
		}
		try {
			return new String(value.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/** 以下测试与生产环境参数由APMP统一分配 */
	/** APP的ID */
	public static String getAppKey() {
		return getPropertie("app.key");
	}

	/** 前置地址 */
	public static String getUrl() {
		return getPropertie("url");
	}

	/** 平台公钥配置 */
	public static String getHostPubKey() {
		return getPropertie("host.pub.key");
	}

	/** 商户拓展号 */
	public static String getBankMchtId() {
		return getPropertie("bank.mcht.id");
	}

	/** 商户私钥配置 */
	public static String getPrivateKeyStr() {
		return getPropertie("mcht.private.key");
	}

	/** 商户公钥配置 */
	public static String getPublicKeyStr() {
		return getPropertie("mcht.pub.key");
	}

	/** 接入方私钥配置 */
	public static String getCoopPrivateKey() {
		return getPropertie("coop.private.key");
	}

	/** 接入方注册结果回调地址 */
	public static String getRegNotifyUrl() {
		return getPropertie("reg.notify.url");
	}

	/** 接入支付回调地址 */
	public static String getPayNotifyUrl() {
		return getPropertie("pay.notify.url");
	}

	/** H5支付地址 */
	public static String getH5PayUrl() {
		return getPropertie("h5.pay.url");
	}

	/** 码尚惠支付baseurl */
	public static String getMshPayUrl(){return getPropertie("msh.pay.baseurl");}

	/** 码尚signkey */
	public static String getSignKey(){return getPropertie("msh.pay.signkey");}

	/** 码尚支付结果回调 */
	public static String getMshNotifyUrl(){return getPropertie("msh.pay.notify.url");}

	/** 码尚渠道编号 */
	public static String getMshOrgNo(){return getPropertie("msh.pay.orgno");}

	public static String getBaseServerName(){return getPropertie("baseServer.name");}

}
