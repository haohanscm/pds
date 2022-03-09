package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankRespStatus;

public class BaseResponse {

	private String action;
	private String responseCode;
	private String errorMsg;

	public BaseResponse() {
	}

	public BaseResponse(String responseCode, String errorMsg) {
		this.responseCode = responseCode;
		this.errorMsg = errorMsg;
	}

	public static BaseResponse error(String errorMsg){
		BaseResponse error =  new BaseResponse(RespStatus.ERROR.getCode().toString(),errorMsg);
		return error;

	}

	public boolean isSuccess(){

		return StringUtils.equals(XmBankRespStatus.SUCCESS.getResponseCode(),this.responseCode);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String toJson(){
		return JacksonUtils.toJson(this);
	}

}
