package com.haohan.platform.service.sys.modules.thirdplatform.feieyun.ctrl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.thirdplatform.feieyun.entity.FeieyunRequestParam;
import com.haohan.platform.service.sys.modules.thirdplatform.feieyun.inf.IFeieyunOpenService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by dy on 2018/8/1.
 * 飞鹅云开放平台
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/feieyun")
public class FeieyunOpenApiCtrl extends BaseController {

    @Qualifier("feieyunOpenServiceImpl")
    @Autowired
    private IFeieyunOpenService iFeieyunOpenService;


    @RequestMapping("addPrinter")
    @ResponseBody
    public String addPrinter(HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();
        Map<String,Object> params = getRequestParameters(request);

        FeieyunRequestParam param = new FeieyunRequestParam();
        try{
            BeanUtils.populate(param, params);
            baseResp = iFeieyunOpenService.addPrinter(param);
        }catch (Exception e){
            e.printStackTrace();
            baseResp.setMsg("请求参数有误:"+e.getCause().getClass()+e.getCause().getMessage());
        }
        return baseResp.toJson();
    }

    @RequestMapping("printMsg")
    @ResponseBody
    public String printMsg(HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();
        Map<String,Object> params = getRequestParameters(request);

        FeieyunRequestParam param = new FeieyunRequestParam();
        try{
            BeanUtils.populate(param, params);
            baseResp = iFeieyunOpenService.printMsg(param);
        }catch (Exception e){
            e.printStackTrace();
            baseResp.setMsg("请求参数有误:"+e.getCause().getClass()+e.getCause().getMessage());
        }
        return baseResp.toJson();
    }

    @RequestMapping("delPrinter")
    @ResponseBody
    public String delPrinter(HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();
        Map<String,Object> params = getRequestParameters(request);

        FeieyunRequestParam param = new FeieyunRequestParam();
        try{
            BeanUtils.populate(param, params);
            baseResp = iFeieyunOpenService.delPrinter(param);
        }catch (Exception e){
            e.printStackTrace();
            baseResp.setMsg("请求参数有误:"+e.getCause().getClass()+e.getCause().getMessage());
        }
        return baseResp.toJson();
    }

    @RequestMapping("editPrinter")
    @ResponseBody
    public String editPrinter(HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();
        Map<String,Object> params = getRequestParameters(request);

        FeieyunRequestParam param = new FeieyunRequestParam();
        try{
            BeanUtils.populate(param, params);
            baseResp = iFeieyunOpenService.editPrinter(param);
        }catch (Exception e){
            e.printStackTrace();
            baseResp.setMsg("请求参数有误:"+e.getCause().getClass()+e.getCause().getMessage());
        }
        return baseResp.toJson();
    }

    @RequestMapping("cleanPrinter")
    @ResponseBody
    public String cleanPrinter(HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();
        Map<String,Object> params = getRequestParameters(request);

        FeieyunRequestParam param = new FeieyunRequestParam();
        try{
            BeanUtils.populate(param, params);
            baseResp = iFeieyunOpenService.cleanPrinter(param);
        }catch (Exception e){
            e.printStackTrace();
            baseResp.setMsg("请求参数有误:"+e.getCause().getClass()+e.getCause().getMessage());
        }
        return baseResp.toJson();
    }

    @RequestMapping("orderState")
    @ResponseBody
    public String orderState(HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();
        Map<String,Object> params = getRequestParameters(request);

        FeieyunRequestParam param = new FeieyunRequestParam();
        try{
            BeanUtils.populate(param, params);
            baseResp = iFeieyunOpenService.orderState(param);
        }catch (Exception e){
            e.printStackTrace();
            baseResp.setMsg("请求参数有误:"+e.getCause().getClass()+e.getCause().getMessage());
        }
        return baseResp.toJson();
    }

    @RequestMapping("orderInfo")
    @ResponseBody
    public String orderInfo(HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();
        Map<String,Object> params = getRequestParameters(request);

        FeieyunRequestParam param = new FeieyunRequestParam();
        try{
            BeanUtils.populate(param, params);
            baseResp = iFeieyunOpenService.orderInfo(param);
        }catch (Exception e){
            e.printStackTrace();
            baseResp.setMsg("请求参数有误:"+e.getCause().getClass()+e.getCause().getMessage());
        }
        return baseResp.toJson();
    }

    @RequestMapping("printerState")
    @ResponseBody
    public String printerState(HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();
        Map<String,Object> params = getRequestParameters(request);

        FeieyunRequestParam param = new FeieyunRequestParam();
        try{
            BeanUtils.populate(param, params);
            baseResp = iFeieyunOpenService.printerState(param);
        }catch (Exception e){
            e.printStackTrace();
            baseResp.setMsg("请求参数有误:"+e.getCause().getClass()+e.getCause().getMessage());
        }
        return baseResp.toJson();
    }



}
