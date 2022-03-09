package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.listener;


import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.util.ApmpSecurityUtil;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.util.XmBankConfigUtil;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by james on 2017/7/17 0017.
 */
public class ConsoleRecordHandler implements RecordHandler {

	@Override
	public void handleRecord(Record record) throws UnsupportedEncodingException {
		String text = record.getRecord();
		System.out.println("======== record =======" + new Date());
		String body = record.getBody();
		String sign = record.getSign();
		System.out.println("sign=" + sign);
		System.out.println("body=" + body);
		String publicKey = XmBankConfigUtil.getPropertie("host.pub.key");
		boolean verify = ApmpSecurityUtil.verify(body.getBytes("UTF-8"), sign, publicKey);
		if (verify){
			System.out.println("验签成功！");
		}else{
			System.out.println("验签失败！");
		}
	}
}
