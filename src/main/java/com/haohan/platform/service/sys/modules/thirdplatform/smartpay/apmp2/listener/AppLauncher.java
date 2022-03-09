package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.listener;

/**
 * Created by james on 2017/7/17 0017.
 */
public class AppLauncher {
	public static void main(String[] args) {
		//所有需要异步通知的test都需要开启此监听，用于监听端口号，test中的notifyUrl端口必须和此端口号相同
		HttpServer httpServer = new HttpServer(8090);
		httpServer.addRecordHandler(new ConsoleRecordHandler());
		httpServer.start();
	}
}
