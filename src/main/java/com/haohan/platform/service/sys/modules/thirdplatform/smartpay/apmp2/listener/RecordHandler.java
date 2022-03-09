package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.listener;

import java.io.UnsupportedEncodingException;

/**
 * Created by james on 2017/7/17 0017.
 */
public interface RecordHandler {
	public void handleRecord(Record record) throws UnsupportedEncodingException;
}
