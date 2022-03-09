package com.haohan.platform.service.sys.modules.thirdplatform.feieyun.entity;


import java.io.Serializable;

/**
 * Created by dy on 2018/8/1.
 */
public class FeieyunDataResp implements Serializable{

    private static final long serialVersionUID = 1L;
    private Integer ret;// 返回码，正确返回0，【注意：结果正确与否的判断请用此返回参数】，错误返回非零
    private String msg;// 结果提示信息，正确返回”ok”，如果有错误，返回错误信息
    private Object data; // 数据类型和内容详看私有返回参数data，如果有错误，返回null。
    private String serverExecutedTime; // 服务器程序执行之间，单位：毫秒。

    public static FeieyunDataResp newSuccess(){
        FeieyunDataResp data = new FeieyunDataResp();
        data.setRet(0);
        data.setMsg("ok");
        return data;
    }

    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getServerExecutedTime() {
        return serverExecutedTime;
    }

    public void setServerExecutedTime(String serverExecutedTime) {
        this.serverExecutedTime = serverExecutedTime;
    }
}
