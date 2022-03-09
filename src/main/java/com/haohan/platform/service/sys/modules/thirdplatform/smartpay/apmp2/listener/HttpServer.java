package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.listener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by james on 2017/7/17 0017.
 */
public class HttpServer implements Runnable {
	/**
	 * 服务器监听
	 */
	private ServerSocket serverSocket;
	/**
	 * 标志位，表示当前服务器是否正在运行
	 */
	private boolean isRunning;
	/**
	 * 观察者
	 */
	private List<RecordHandler> recordHandlers = new ArrayList<RecordHandler>();
	public HttpServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void stop() {
		this.isRunning = false;
	}
	public void start() {
		this.isRunning = true;
		new Thread(this).start();
	}
	@Override
	public void run() {
		while (isRunning) {//一直监听，直到受到停止的命令
			Socket socket = null;
			try {
				socket = serverSocket.accept();//如果没有请求，会一直hold在这里等待，有客户端请求的时候才会继续往下执行
				// log
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				StringBuilder stringBuilder = new StringBuilder();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line);
				}
				Record record = new Record();
				record.setRecord(stringBuilder.toString());
				record.setVisitDate(new Date(System.currentTimeMillis()));
				String[] params = record.getRecord().split("&body=");
				String body = params[1];
				String sign = params[0].split("sign=")[1];
				record.setSign(sign);
				record.setBody(body);
				notifyRecordHandlers(record);//通知日志记录者对日志作操作
				// echo
				/*PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);//这里第二个参数表示自动刷新缓存
				doEcho(printWriter, record);//将日志输出到浏览器
				// release
				printWriter.close();*/
				bufferedReader.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 将得到的信写回客户端
	 *
	 * @param printWriter
	 * @param record
	 */
	private void doEcho(PrintWriter printWriter, Record record) {
		printWriter.write(record.getRecord());
	}
	/**
	 * 通知已经注册的监听者做处理
	 *
	 * @param record
	 */
	private void notifyRecordHandlers(Record record) throws UnsupportedEncodingException {
		for (RecordHandler recordHandler : this.recordHandlers) {
			recordHandler.handleRecord(record);
		}
	}
	/**
	 * 添加一个监听器
	 *
	 * @param recordHandler
	 */
	public void addRecordHandler(RecordHandler recordHandler) {
		this.recordHandlers.add(recordHandler);
	}
}
